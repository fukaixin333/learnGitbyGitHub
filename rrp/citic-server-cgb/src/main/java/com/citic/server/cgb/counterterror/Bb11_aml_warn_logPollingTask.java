package com.citic.server.cgb.counterterror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.cgb.domain.AmlWarnDto;
import com.citic.server.cgb.domain.Bb11_data_task;
import com.citic.server.cgb.domain.GatewayHeader;
import com.citic.server.cgb.domain.request.AfpWarnLogToAml;
import com.citic.server.cgb.domain.request.OnlineTPQueryFKWarnInput;
import com.citic.server.cgb.domain.response.OnlineTPFKWarnDetail;
import com.citic.server.cgb.mapper.Bb11_aml_warn_logMapper;
import com.citic.server.inner.service.ISOAPMessageService;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.FileTools;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.BusiTx;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;

@Component("bb11_aml_warn_logPollingTask")
public class Bb11_aml_warn_logPollingTask extends AbstractPollingTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Bb11_aml_warn_logMapper warn_mapper;
	
	@Getter
	@Setter
	private volatile String lastTime = "";//记录上次查询截止时间yyyyMMddHHmmss
	
	String encoding = "GB18030";//文件编码
	int fixLenth = 850;//单行定长
	int BATCH_NUM = 200;//单次批量处理数
	
	@Autowired
	@Qualifier("soapMessageService")
	private ISOAPMessageService soapMessageService;
	
	/**
	 * 执行方法入口
	 * 0.调用在线交易平台接口获取核心预警信息
	 * 1.轮询扫描指定目录/cdadmin/UFSM/AFPS/save下对应系统目录
	 * 2.将文件ftp取回本地进行处理，处理完成后，将文件移到对应的备份文件目录
	 * 3.查询数据库，找出发送状态为0（待发送）的数据，将状态置为中间状态
	 * 4.将数据处理，调用反洗钱的R90001接口，将数据逐条发送给反洗钱系统。
	 */
	@Override
	public void executeAction() {
		//处理核心预警数据
		this.InsertCBOEAmlWarnlog();
		//处理文件预警数据
		this.InsertFileAmlWarnLog();
		//发送数据到反洗钱系统
		this.sendDateToAml();
	}
	
	/**
	 * 处理核心的预警数据（通过在线交易平台实时接口） <br>
	 * 根据时间段来获取预警数据 <br>
	 * http报文走网关查询
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午5:25:02
	 */
	private void InsertCBOEAmlWarnlog() {
		try {
			String start_time = "";
			if (StringUtils.isBlank(lastTime)) { // 查询上次截止日期
				Bb11_data_task task1 = new Bb11_data_task();
				task1.setTask_id("TK0002");
				task1 = warn_mapper.selectBb11_data_task(task1);
				start_time = task1.getTask_date_time();
			} else {
				start_time = lastTime;
			}
			
			String end_time = this.getEndTime();
			if (end_time.compareTo(start_time) > 0) {
//				//封装查询报文
//				AmlWarnDto reqDto = new AmlWarnDto();
//				reqDto.setStartTime(start_time);
//				reqDto.setEndTime(end_time);
//				reqDto.setLstCd("ZZZ");//默认查询ZZZ（反恐怖名单的预警信息,包含FAA和FAI）
//				reqDto.setPageSize("20");//默认每页20条
//				reqDto.setPageNum("1");//首次查询填1，后续按请求来
				
				OnlineTPQueryFKWarnInput in = new OnlineTPQueryFKWarnInput();
				in.setStartTime(start_time);//查询开始日期
				in.setEndTime(end_time);//查询截止日期
				in.setLstCd("ZZZ");//名单大类
				
				List<OnlineTPFKWarnDetail> warnDetailList = soapMessageService.queryFKWarnFromOnlineTP(in);
				//将预警的list入库，并记录查询截止日期
				if (warnDetailList != null && warnDetailList.size() > 0) {
					//对象转换
					List<AmlWarnDto> list = getAmlWarnDtoList(warnDetailList);
					//批量插入
					this.insertBb11_aml_warn_logByBatch(list);
					
					//记录查询截止日期
					Bb11_data_task task = new Bb11_data_task();
					task.setTask_id("TK0002");
					task.setTask_date_time(end_time);
					warn_mapper.updateBb11_data_taskTimeByID(task);
					lastTime = end_time;
				} else {
					logger.info("查询在线交易平台ZXFK01接口未获取到记录");
				}
			}
		} catch (Exception e) {
			logger.error("通过在线交易平台获取核心反恐怖预警数据异常", e);
		}
	}
	
	/**
	 * 处理文件形式的
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午5:26:28
	 */
	private void InsertFileAmlWarnLog() {
		//卡核心文件处理
		this.CCS0FileAmlWarnLog();
		//工单文件处理
		this.WBPSFileAmlWarnLog();
		//决策文件处理
		this.CDM6FileAmlWarnLog();
		//金融前置文件处理
		this.GFDAFileAmlWarnLog();
	}
	
	/**
	 * 卡核心文件处理
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午6:00:43
	 */
	private void CCS0FileAmlWarnLog() {
		String srcPath = ServerEnvironment.getStringValue(CgbKeys.WF_SAVE_DIR);//卡核心预警信息推送目录
		String destPath = ServerEnvironment.getStringValue(CgbKeys.AFP_BACKUP_DIR) + File.separatorChar + Utility.currDate8() + File.separatorChar + "CCS0";
		String nameStartStr = "CCS0.AFPS.CSTWAR";//卡核心预警文件名开头
		String sys_desc = "CCS0卡核心";
		this.FileAmlWarnLog(srcPath, destPath, nameStartStr, sys_desc);
	}
	
	/**
	 * 工单文件处理
	 * 
	 * @author yinxiong
	 * @date 2017年5月24日 下午9:28:16
	 */
	private void WBPSFileAmlWarnLog() {
		String srcPath = ServerEnvironment.getStringValue(CgbKeys.WF_SAVE_DIR) + File.separatorChar + "WBPS";//工单预警信息推送目录
		String destPath = ServerEnvironment.getStringValue(CgbKeys.AFP_BACKUP_DIR) + File.separatorChar + Utility.currDate8() + File.separatorChar + "WBPS";//工单预警文件备份目录
		String nameStartStr = "WBPS.AFPS.MCS001";//工单预警文件名开头
		String sys_desc = "WBPS工单";
		this.FileAmlWarnLog(srcPath, destPath, nameStartStr, sys_desc);
	}
	
	/**
	 * 决策文件处理
	 * 
	 * @author yinxiong
	 * @date 2017年5月24日 下午9:28:37
	 */
	private void CDM6FileAmlWarnLog() {
		String srcPath = ServerEnvironment.getStringValue(CgbKeys.WF_SAVE_DIR) + File.separatorChar + "CDM6";//决策预警信息推送目录
		String destPath = ServerEnvironment.getStringValue(CgbKeys.AFP_BACKUP_DIR) + File.separatorChar + Utility.currDate8() + File.separatorChar + "CDM6";//决策预警文件备份目录
		String nameStartStr = "CDM6.AFPS.CSTWAR";//决策预警文件名开头
		String sys_desc = "CDM6决策";
		this.FileAmlWarnLog(srcPath, destPath, nameStartStr, sys_desc);
	}
	
	/**
	 * 金融前置文件处理
	 * 
	 * @author yinxiong
	 * @date 2017年5月24日 下午9:29:45
	 */
	private void GFDAFileAmlWarnLog() {
		String srcPath = ServerEnvironment.getStringValue(CgbKeys.WF_SAVE_DIR) + File.separatorChar + "GFDA";//金融前置预警信息推送目录
		String destPath = ServerEnvironment.getStringValue(CgbKeys.AFP_BACKUP_DIR) + File.separatorChar + Utility.currDate8() + File.separatorChar + "GFDA";//金融前置预警文件备份目录
		String nameStartStr = "GFDA.AFPS.CSTWAR";//金融前置预警文件名开头
		String sys_desc = "GFDA金融前置";
		this.FileAmlWarnLog(srcPath, destPath, nameStartStr, sys_desc);
	}
	
	/**
	 * 文件处理
	 * 
	 * @param srcPath
	 * @param destPath
	 * @param nameStartStr
	 * @param sys_desc
	 * @author yinxiong
	 * @date 2017年5月24日 下午9:40:22
	 */
	private void FileAmlWarnLog(String srcPath, String destPath, String nameStartStr, String sys_desc) {
		try {
			//扫描指定目录下的文件并获取文件列表
			File dir = new File(srcPath);
			//获取.end文件
			String[] fileNames = FileTools.getFileNameListByWfSaveEnd(dir);
			if (fileNames != null && fileNames.length > 0) {
				logger.info(sys_desc + "预警文件处理开始");
				//处理文件,进行入库操作
				for (String name : fileNames) {
					if (name.startsWith(nameStartStr)) {
						String dataName = name.substring(0, name.length() - 4);
						List<AmlWarnDto> list = this.dealFileDate(srcPath, dataName);
						//入库
						this.insertBb11_aml_warn_logByBatch(list);
						list.clear();
						//移走数据文件
						FileUtils.moveFile(srcPath + File.separatorChar + dataName, destPath);
						//移走.end文件
						FileUtils.moveFile(srcPath + File.separatorChar + name, destPath);
						logger.info(sys_desc + "预警文件[" + dataName + "]处理结束");
					}
				}
			}
		} catch (Exception e) {
			logger.error(sys_desc + "预警文件处理异常：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 数据批量插入
	 * 
	 * @author yinxiong
	 * @date 2017年5月19日 下午5:42:58
	 */
	@BusiTx
	public void insertBb11_aml_warn_logByBatch(List<AmlWarnDto> list) {
		if (list != null && list.size() > 0) {
			int size = list.size();
			int m = size / BATCH_NUM;//整数部分
			int n = size % BATCH_NUM;//余数部分
			//处理满足BATCH_NUM的数据
			for (int i = 0; i < m; i++) {
				List<AmlWarnDto> list_x = list.subList(BATCH_NUM * i, BATCH_NUM * (i + 1));
				warn_mapper.batchInsertBb11_aml_warn_log(list_x);
			}
			//处理不满足BATCH_NUM的数据
			if (n != 0) {
				List<AmlWarnDto> list_y = list.subList(BATCH_NUM * m, BATCH_NUM * m + n);
				warn_mapper.batchInsertBb11_aml_warn_log(list_y);
			}
		}
	}
	
	/**
	 * 将文本数据转换为list
	 * 
	 * @param path
	 * @param name
	 * @author yinxiong
	 * @date 2017年5月19日 下午3:27:58
	 */
	private List<AmlWarnDto> dealFileDate(String path, String name) {
		InputStreamReader read = null;
		BufferedReader br = null;
		List<AmlWarnDto> list = new ArrayList<AmlWarnDto>();
		String createTime = Utility.currDateTime19();
		try {
			String filepath = path + File.separator + name;// 服务器文件路径
			//数据读取
			read = new InputStreamReader(new FileInputStream(filepath), encoding);// 处理中文乱码
			br = new BufferedReader(read);
			String r = br.readLine();
			while (r != null) {
				if (!StringUtils.equals("", r)) {// 保证数据存在
					byte[] bytes = r.getBytes(encoding); //获取数据字节
					//定长传输，保证长度正确
					if (bytes.length == fixLenth) {
						//TODO进行定长切分与校验
						String tx_organkey = this.getParam(bytes, 0, 6);//6	交易机构
						String acct_organkey = this.getParam(bytes, 6, 6);//6	账户归属机构
						String tx_time = this.getParam(bytes, 12, 14);//14	交易时间
						String party_id = this.getParam(bytes, 26, 28);//28	客户号
						String party_class_cd = this.getParam(bytes, 54, 1);//1	客户类型
						String party_name = this.getParam(bytes, 55, 122);//122	客户名称
						String cust_id_no = this.getParam(bytes, 177, 60);//60	客户证件号码
						String sys_ind = this.getParam(bytes, 237, 4);//4	系统标识
						String channel = this.getParam(bytes, 241, 30);//30	涉及渠道标识
						String tx_acctnum = this.getParam(bytes, 271, 32);//32	交易账户
						String curr_cd = this.getParam(bytes, 303, 3);//3	交易币种
						String amt = this.getParam(bytes, 306, 19);//19	交易金额
						//去掉金额前多余的0，若为空，金额给0
						amt = amt.replaceAll("^(0+)", "");
						amt = StringUtils.isBlank(amt) ? "0" : amt;
						String alert_reason = "客户主体触发的涉恐名单";//取默认值 this.getParam(bytes, 325, 255);//255	预警原因
						String san_name = this.getParam(bytes, 580, 4);//4	制裁参考名单
						String biz_type = this.getParam(bytes, 584, 30);//30	交易种类
						String tx_result = "已阻断";//取默认值 //this.getParam(bytes, 614, 10);//10	交易结果
						String alert_type = this.getParam(bytes, 624, 1);//1	预警类型
						String opp_acct_organkey = this.getParam(bytes, 625, 6);//6	交易对手行所名称
						String opp_party_class_cd = this.getParam(bytes, 631, 1);//1	交易对手类型
						String opp_tx_acctnum = this.getParam(bytes, 632, 32);//32	交易对手账号
						String opp_party_name = this.getParam(bytes, 664, 122);//122	交易对手姓名
						String opp_cust_id_no = this.getParam(bytes, 786, 60);//60	交易对手证件号码
						String rt_typ = this.getParam(bytes, 846, 4);//4	阻断类型
						//必输项校验
						if (StringUtils.isBlank(tx_organkey) && StringUtils.isBlank(acct_organkey)) {
							logger.warn("交易机构tx_organkey和账户归属机构acct_organkey二选一必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(tx_time) || tx_time.length() != 14) {
							logger.warn("交易时间tx_time非法，格式应为yyyyMMddHHmmss且必输，交易时间：" + tx_time);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(party_class_cd)) {
							logger.warn("客户类型party_class_cd必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						// 证件号码【GFDA金融前置】是空的，需要放开校验
						// if (StringUtils.isBlank(cust_id_no)) {
						// 	logger.warn("客户证件号码cust_id_no必输，本行数据：" + r);
						// 	r = br.readLine();
						// 	continue;
						// }
						if (StringUtils.isBlank(sys_ind)) {
							logger.warn("系统标识sys_ind必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(channel)) {
							logger.warn("渠道标识channel必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(channel)) {
							logger.warn("名单细类san_name必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(alert_type)) {
							logger.warn("预警类型alert_type必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						if (StringUtils.isBlank(rt_typ)) {
							logger.warn("阻断类型rt_typ必输，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						//TODO封装到dto，然后塞入list
						AmlWarnDto warnDto = new AmlWarnDto();
						warnDto.setTx_organkey(tx_organkey);
						warnDto.setAcct_organkey(acct_organkey);
						warnDto.setTx_time(Utility.toDateTime19(tx_time));//时间处理为19位
						warnDto.setParty_id(party_id);
						warnDto.setParty_class_cd(party_class_cd);
						warnDto.setParty_name(party_name);
						warnDto.setCust_id_no(cust_id_no);
						warnDto.setSys_ind(sys_ind);
						warnDto.setChannel(channel);
						warnDto.setTx_acctnum(tx_acctnum);
						warnDto.setCurr_cd(curr_cd);
						warnDto.setAmt(amt);//处理交易金额前多余的0，此时金额是实际的100倍
						warnDto.setAlert_reason(alert_reason);
						warnDto.setSan_name(san_name);
						warnDto.setBiz_type(biz_type);
						warnDto.setTx_result(tx_result);
						warnDto.setAlert_type(alert_type);
						warnDto.setOpp_acct_organkey(opp_acct_organkey);
						warnDto.setOpp_party_class_cd(opp_party_class_cd);
						warnDto.setOpp_tx_acctnum(opp_tx_acctnum);
						warnDto.setOpp_party_name(opp_party_name);
						warnDto.setOpp_cust_id_no(opp_cust_id_no);
						warnDto.setRt_typ(rt_typ);
						//设置附件的初始默认值
						warnDto.setCreate_time(createTime);
						warnDto.setSend_flag("0");//0:待发送 1：已发送 9：中间状态
						list.add(warnDto);
					} else {
						logger.warn("非约定长度，长度非法！本行数据：" + r);
					}
				}
				r = br.readLine();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 字段提取
	 * 
	 * @param bytes 待切分数组
	 * @param index 起始位置
	 * @param length 长度
	 * @return 去空格的数据
	 * @author yinxiong
	 * @date 2017年5月19日 下午2:48:37
	 */
	private String getParam(byte[] bytes, int index, int length) {
		String param = "";
		try {
			byte[] b = Arrays.copyOfRange(bytes, index, index + length);
			param = new String(b, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("字段提取发生异常，开始位置：" + index + " 原始数据：" + Arrays.toString(bytes), e);
		}
		
		return param.trim();
	}
	
	/**
	 * 发送预警数据给反洗钱系统
	 * 该接口编码为UTF-8
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午8:02:16
	 */
	private void sendDateToAml() {
		try {
			//将待发送的数据将状态置为中间状态(0-->9)
			warn_mapper.updateBb11_aml_warn_logSendFlagToNine("0");
			//获取中间状态的数据
			ArrayList<AmlWarnDto> list = warn_mapper.selectBb11_aml_warn_logListByNine("9");
			//循环操作：封装报文，发送数据
			if (list != null && list.size() > 0) {
				for (AmlWarnDto amlWarnDto : list) {
					//生成SOAP格式的XML报文
					String xmlStr = this.getSoapXmlToAML(amlWarnDto);
					logger.info("请求报文：" + xmlStr);
					// http通过网关发送给反洗钱系统
					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpPost httppost = new HttpPost(ServerEnvironment.getStringValue(CgbKeys.MD_REMOTE_ACCESS_URL));
					StringEntity strEnt = new StringEntity(xmlStr, StandardCharsets.UTF_8);
					httppost.setEntity(strEnt);
					CloseableHttpResponse response = httpclient.execute(httppost);
					
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						try {
							String resXml = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.toString());
							logger.info("响应报文：" + resXml);
							AfpWarnLogToAml resDto = new AfpWarnLogToAml();
							resDto = (AfpWarnLogToAml) CommonUtils.unmarshallContext(AfpWarnLogToAml.class, "binding_cgb_req_sendWarnLog", resXml);
							//处理结果判断
							if ("01".equals(resDto.getGatewayHeader().getGwErrorCode())) {//网关错误标识（01-成功；00-错误）
								if ("0000".equals(resDto.getReturn_Code())) {
									//更新数据发送状态
									amlWarnDto.setSend_time(Utility.currDateTime19());
									warn_mapper.updateBb11_aml_warn_logSendFlagToOne(amlWarnDto);
									logger.info("预警信息上送成功");
								} else {
									logger.error("预警信息上送异常，交易流水号：" + resDto.getGatewayHeader().getSenderSN());
								}
							} else {
								logger.error("网关返回错误消息：" + resDto.getGatewayHeader().getGwErrorMessage());
							}
						} catch (Exception e) {
							logger.error("通过JiBX解析信用卡回执报文失败：" + e.getMessage(), e);
						} finally {
							EntityUtils.consume(entity);
						}
					} else {
						logger.info("与网关通讯异常，http状态码：" + statusCode);
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("预警数据上送反洗钱系统异常：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 生成请求的soapXml报文（发送到反洗钱的）
	 * 
	 * @param md
	 * @return
	 * @author yinxiong
	 * @throws Exception
	 * @date 2017年5月22日 下午4:47:46
	 */
	private String getSoapXmlToAML(AmlWarnDto md) {
		String xml = "";
		//拼接xml申明
		String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		try {
			AfpWarnLogToAml xmlDto = new AfpWarnLogToAml();
			String time = Utility.currDateTime14() + (int) ((Math.random() * 9 + 1) * 10000000);// 日期＋随机数的22位流水号
			// 通用报文头处理
			GatewayHeader header = new GatewayHeader();
			header.setReceiverId("AMLS");//接收方系统标识
			header.setSenderId("AFPM");// 发起放系统标识
			header.setCommCode(GatewayHeader.COMMCODE_REQUEST);//请求：500001 响应：510001
			header.setSenderSN(time);
			header.setSenderDate(time.substring(0, 8));
			header.setSenderTime(time.substring(8, 14));
			header.setTradeCode("R90001");// 交易代码 
			header.setGwErrorCode("01"); //默认01：成功   
			xmlDto.setGatewayHeader(header);
			// body处理
			xmlDto.setTx_Organkey(md.getTx_organkey());
			xmlDto.setAcct_Organkey(md.getAcct_organkey());
			xmlDto.setTx_Time(Utility.toDateTime14(md.getTx_time()));//日期19转14
			xmlDto.setParty_Id(md.getParty_id());
			xmlDto.setParty_Class_Cd(md.getParty_class_cd());//
			xmlDto.setParty_Name(md.getParty_name().trim());
			xmlDto.setCust_Id_No(md.getCust_id_no());
			xmlDto.setSys_Ind(md.getSys_ind());
			xmlDto.setChannel(md.getChannel());
			xmlDto.setTx_Acctnum(md.getTx_acctnum());
			xmlDto.setCurr_Cd(md.getCurr_cd());
			xmlDto.setAmt(md.getAmt());
			xmlDto.setAlert_Reason(md.getAlert_reason());
			xmlDto.setSan_Name(md.getSan_name());
			xmlDto.setBiz_Type(md.getBiz_type());
			xmlDto.setTx_Result(md.getTx_result());
			xmlDto.setAlert_Type(md.getAlert_type());
			xmlDto.setOpp_acct_organkey(md.getOpp_acct_organkey());
			xmlDto.setOpp_party_class_cd(md.getOpp_party_class_cd());
			xmlDto.setOpp_tx_acctnum(md.getOpp_tx_acctnum());
			xmlDto.setOpp_party_name(md.getOpp_party_name());
			xmlDto.setOpp_cust_id_no(md.getOpp_cust_id_no());
			xmlDto.setRt_typ(md.getRt_typ());
			
			xml = CommonUtils.marshallContext(xmlDto, "binding_cgb_req_sendWarnLog", true);
			
		} catch (Exception e) {
			logger.error("生成发送反洗钱的报文异常", e);
		}
		return xmlHead + xml;
	}
	
	/**
	 * 生成请求的soapXml报文（发送到在线交易平台的）
	 * 
	 * @param md
	 * @return
	 * @throws JiBXException
	 * @author yinxiong
	 * @date 2017年5月24日 上午10:50:41
	 */
	//	private String getSoapXmlToOTSP(AmlWarnDto dto) throws JiBXException {
	//		AfpWarnLogfromCore xmlDto = new AfpWarnLogfromCore();
	//		String time = Utility.currDateTime14() + (int) ((Math.random() * 9 + 1) * 10000000);// 日期＋随机数的22位流水号
	//		// 通用报文头处理
	//		GatewayHeader header = new GatewayHeader();
	//		header.setReceiverId("OTSP");//接收方系统标识
	//		header.setSenderId("AFPM");// 发起放系统标识
	//		header.setCommCode(GatewayHeader.COMMCODE_REQUEST);//请求：500001 响应：510001
	//		header.setSenderSN(time);
	//		header.setSenderDate(time.substring(0, 8));
	//		header.setSenderTime(time.substring(8, 14));
	//		header.setTradeCode("ZXFK01");// 交易代码 
	//		xmlDto.setGatewayHeader(header);
	//		// body处理
	//		xmlDto.setStartTime(dto.getStartTime());//查询开始日期
	//		xmlDto.setEndTime(dto.getEndTime());//查询截止日期
	//		xmlDto.setJrnNo(dto.getJrnNo());//日志好
	//		xmlDto.setLstCd(dto.getLstCd());//名单大类
	//		xmlDto.setLstDt(dto.getLstDt());//名单细类
	//		xmlDto.setFiller1(dto.getFiller1());//预留1
	//		xmlDto.setFiller2(dto.getFiller2());//预留2
	//		xmlDto.setPageSize(dto.getPageSize());//每页输出行数
	//		xmlDto.setPageNum(dto.getPageNum());//页码
	//		
	//		//注【生成的报文不带xml申明】
	//		String xml = CommonUtils.marshallContext(xmlDto, "binding_cgb_req_getWarnLog", true);
	//		//TODO拼接xml申明
	//		String xmlHead = "<?xml version=\"1.0\" encoding=\"GB18030\"?>";
	//		return xmlHead + xml;
	//	}
	
	/**
	 * 从在线交易平台查询核心的预警信息 <br>
	 * 按时间段查询反恐怖预警信息
	 * 
	 * @author yinxiong
	 * @date 2017年5月24日 上午9:25:27
	 */
	//	private NoAs400Body_ZXFK01 sendReqDateToOTSP2(AmlWarnDto reqDto) {
	//		NoAs400Body_ZXFK01 responseDTO = null;
	//		try {
	//			//根据DTO生成请求的xml报文
	//			String xmlStr = this.getSoapXmlToOTSP(reqDto);
	//			
	//			logger.info("请求报文：" + xmlStr);
	//			
	//			// http通过网关发送给反洗钱系统
	//			CloseableHttpClient httpclient = HttpClients.createDefault();
	//			HttpPost httppost = new HttpPost(ServerEnvironment.getStringValue(CgbKeys.MD_REMOTE_ACCESS_URL));
	//			StringEntity strEnt = new StringEntity(xmlStr, encoding);
	//			httppost.setEntity(strEnt);
	//			CloseableHttpResponse response = httpclient.execute(httppost);
	//			
	//			StatusLine statusLine = response.getStatusLine();
	//			int statusCode = statusLine.getStatusCode();
	//			if (statusCode == HttpStatus.SC_OK) {
	//				HttpEntity entity = response.getEntity();
	//				try {
	//					String resXml = IOUtils.toString(entity.getContent(), encoding.toString());
	//					logger.info("响应报文：" + resXml);
	//					
	//					//=============================================
	//					GatewayResponse gatewayResponse = CommonUtils.unmarshallContext(GatewayResponse.class, resXml);
	//					
	//					GatewayHeader gatewayHeader = gatewayResponse.getGatewayHeader();
	//					OnlineTPQueryFKWarnResult gatewayResult = (OnlineTPQueryFKWarnResult) gatewayResponse.getGatewayResult();
	//					//解析绑定文件
	//					// OnlineTPQueryFKWarnResult resDto = (GatewayNoAS400_ZXFK01) CommonUtils.unmarshallContext(GatewayNoAS400_ZXFK01.class, resXml);
	//					//处理结果判断
	//					if ("01".equals(gatewayHeader.getGwErrorCode())) {//网关错误标识（01-成功；00-错误）
	//						if (!gatewayResult.hasException()) {
	//							//封装数据
	//							responseDTO = gatewayResult.getFkWarnlDetailList();
	//						} else {
	//							logger.error("查询异常，交易流水号：" + resDto.getGatewayHeader().getSenderSN());
	//						}
	//					} else {
	//						logger.error("网关返回错误消息：" + resDto.getGatewayHeader().getGwErrorMessage());
	//					}
	//				} catch (Exception e) {
	//					logger.error("通过JiBX解析在线交易平台ZXFK01接口回执报文失败：" + e.getMessage(), e);
	//				} finally {
	//					EntityUtils.consume(entity);
	//				}
	//			} else {
	//				logger.info("与网关通讯异常，http状态码：" + statusCode);
	//			}
	//		} catch (Exception e) {
	//			logger.error("查询在线交易平台的预警信息ZXFK01接口发生异常，" + e.getMessage(), e);
	//		}
	//		//返回响应报文
	//		return responseDTO;
	//	}
	
	/**
	 * 对象转换
	 * 
	 * @param elem
	 * @return
	 * @author yinxiong
	 * @date 2017年6月1日 上午10:10:58
	 */
	private List<AmlWarnDto> getAmlWarnDtoList(List<OnlineTPFKWarnDetail> elem) {
		List<AmlWarnDto> list = new ArrayList<AmlWarnDto>(elem.size());
		try {
			String create_time = Utility.currDateTime19();
			String send_flag = "0"; //发送标志,默认0：未发送
			for (OnlineTPFKWarnDetail dto : elem) {
				AmlWarnDto warn = new AmlWarnDto();
				warn.setTx_organkey(dto.getTxBr().trim());
				warn.setAcct_organkey(dto.getAcctBr().trim());
				warn.setTx_time(Utility.toDateTime19(dto.getTxTime().trim()));//日期14转19
				warn.setParty_id(dto.getCiNo().trim());
				warn.setParty_class_cd(dto.getCiTyp().trim());//
				warn.setParty_name(dto.getCiName().trim());
				warn.setCust_id_no(dto.getIdNo().trim());
				warn.setSys_ind("CBOE");
				warn.setChannel(dto.getChnl1().trim());
				warn.setTx_acctnum(dto.getTxAc().trim());//交易账户
				warn.setCurr_cd(dto.getTxCcy().trim());//交易币种
				warn.setAmt(dto.getTxAmt().trim());//交易金额  此处金额已经处理过，做展示则需除100
				warn.setAlert_reason("客户主体触发的涉恐名单");//预警原因
				warn.setSan_name(dto.getLstDt().trim());//名单细类
				warn.setBiz_type(dto.getMmo().trim());//交易种类
				warn.setTx_result("已阻断");//阻断结果
				warn.setAlert_type(dto.getAlertType().trim());//预警类型
				warn.setOpp_acct_organkey(dto.getOppBr().trim());//交易对手行所名称
				warn.setOpp_party_class_cd(dto.getOppCiTyp().trim());//交易对手类型
				warn.setOpp_tx_acctnum(dto.getOppTxAc().trim());//交易对手账号
				warn.setOpp_party_name(dto.getOppCiName().trim());//交易对手姓名
				warn.setOpp_cust_id_no(dto.getOppIdNo().trim());//交易对手证件号
				warn.setRt_typ(dto.getRtType().trim());//阻断类型
				warn.setCreate_time(create_time);//
				warn.setSend_flag(send_flag);//
				list.add(warn);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return list;
	}
	
	/**
	 * 获取当前时间回退N分钟后的时间 <br>
	 * 例如：当前2016-12-12 12:12:12，回退10分钟，返回2016-12-12 12:02:12
	 * 
	 * @return
	 * @author yinxiong
	 * @throws ParseException
	 * @date 2017年6月1日 下午8:33:30
	 */
	private String getEndTime() throws ParseException {
		String delayTime = ServerEnvironment.getStringValue(CgbKeys.FK_OTSP_DELAY_TIME);// 延时
		int amount = -Integer.parseInt(delayTime);
		int gran = 1;
		String end_time = Utility.currDateTime19();
		String format = "yyyyMMddHHmmss";
		return DtUtils.getTimeByGran(end_time, amount, gran, format);
	}
	
	/**
	 * 缓存加载配置
	 * 不需要缓存，返回null
	 */
	@Override
	protected String getTaskType() {
		return null;
	}
	
	/**
	 * 设置轮询间隔时间 <br>
	 * 2种方式：A填写【正整数字符串】，如20，则轮询每个20分钟执行一次 <br>
	 * B填写【每天|05:00】，则变为定时任务，每天05:00执行
	 */
	@Override
	protected String getExecutePeriodExpression() {
		return "30";
	}
	
}

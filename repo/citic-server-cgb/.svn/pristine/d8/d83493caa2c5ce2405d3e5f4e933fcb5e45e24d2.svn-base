/**
 * 
 */
package com.citic.server.cgb.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.cgb.domain.GatewayHeader;
import com.citic.server.cgb.domain.request.AfpSuspicious;
import com.citic.server.dict.DictCoder;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.dx.mapper.Br20_md_info_cgbMapper;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Lists;
import com.ml.bdbm.client.FTSRequest;
import com.ml.bdbm.client.FTSResponse;
import com.ml.bdbm.client.FTSTaskClient;
import com.ml.bdbm.client.TransferFile;

/**
 * @author yinxiong
 * @date 2016年11月29日
 */
@Service
public class InnerPollingTaskCoreService {
	private static final Logger logger = LoggerFactory.getLogger(InnerPollingTaskCoreService.class);


	@Autowired
	public Br20_md_info_cgbMapper br20_md_info_cgbMapper;
	/** 码表转换接口 */
	@Autowired
	private DictCoder dictCoder;
	

	/**
	 * 核心文件推送入口 <br>
	 * 1.查询行内的黑名单数据，调用信用卡接口，获取内部认定名单信息（客户名+证件号码+证件类型用接口返回的，其它沿用黑名单的数据）<br>
	 * 2.内部名单入库<br>
	 * 3.修改待发送到核心的名单数据为中间状态9【避免发送过程中有新的数据过来】，查询出这些数据<br>
	 * 4.用发送模块发送数据<br>
	 * 5.更新发送核心状态为已发送<br>
	 * 6.下次发送时间和最后发送时间
	 * 
	 * @param br20_md_info
	 */
	public void pushFileToCorce(Br20_md_info br20_md_info) {

		try {
			String localpath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND);// 本地增量名单待发送文件存放目录
			String mode = ServerEnvironment.getStringValue(Keys.HX_DATA_TYPE);// 数据方式：定长/分割符
			String separate = ServerEnvironment.getStringValue(Keys.HX_DATA_SEPARATE);// 分割符号
			// 查询调用信用卡接口的内部认定名单(条件：未发送&&调用信用卡接口)
			ArrayList<Br20_md_info> intefacelist = br20_md_info_cgbMapper.getInBankDataByCredit();
			// 调用信用卡接口，获取内部认定名单信息
			ArrayList<Br20_md_info> innerlist = this.getPartyInfoByCreditInterface(intefacelist);
			// 内部认定名单信息入库
			this.insertPartyInfo(innerlist);
			//释放资源
			innerlist.clear();
			//获取发送条数限制
			String topSize = br20_md_info_cgbMapper.selectTop_sizeByCore(); 
			// 将待发送的数据状态改为中间状态(条件：未发送&&发送类型为0和1的)
			br20_md_info_cgbMapper.updateSend_flagByMiddleCore(topSize);
			// 获取br20_md_data_log中待发送核心的名单
			ArrayList<Br20_md_info> incrementlist = br20_md_info_cgbMapper.getIncrementSendDataCore();
			if (incrementlist != null && incrementlist.size() > 0) {
				List<String> list = dealDataList(incrementlist, mode, separate);// 数据处理
				//释放资源
				incrementlist.clear();
				String filename = getTxt(localpath, br20_md_info, list);// 生成数据文件和发送记录
				logger.info("==文件已经生成==");
				if (fileToServer(filename)) {//发送数据文件
					if (fileEndToServer(filename)) {//确认文件
						updateSend_flag("9", br20_md_info.getNext_time());// 更新名单数据发送状态为已发送
						updateSendTime(br20_md_info);//更新发送时间 
					} else {
						logger.info("确认文件发送失败");
					}
				} else {
					logger.info("数据文件发送失败");
				}
			} else {
				logger.info("没有需要推送给核心的名单数据");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 调用信用卡接口，获取客户信息
	 * 
	 * @param intefacelist
	 * @return
	 */
	private ArrayList<Br20_md_info> getPartyInfoByCreditInterface(ArrayList<Br20_md_info> intefacelist) throws Exception {

		ArrayList<Br20_md_info> credit_list = new ArrayList<Br20_md_info>();
		try {
			if (intefacelist != null && intefacelist.size() > 0) {
				for (Br20_md_info br20_md_info : intefacelist) {
					//将字段进行转吗
					dictCoder.transcode(br20_md_info,null);
					
					// 生成xml字符串
					String xml = this.getSoapXml(br20_md_info);
					
					logger.info("生成的xml信息\r\n"+xml);
					
					// http发送给信用卡
					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpPost httppost = new HttpPost(ServerEnvironment.getStringValue(CgbKeys.MD_REMOTE_ACCESS_URL));
					StringEntity strEnt = new StringEntity(xml,"gb18030");
					httppost.setEntity(strEnt);
					CloseableHttpResponse response = httpclient.execute(httppost);

					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						logger.info("正在解析信用卡回执报文...");
						HttpEntity entity = response.getEntity();
						try {
							AfpSuspicious partyDto = new AfpSuspicious();
							partyDto = (AfpSuspicious) CommonUtils.unmarshallInputStream(AfpSuspicious.class, "binding_cgb_suspicious", entity.getContent(), "gb18030");
							// 内部认定名单赋值规则：替换3证内容，其它多数字段沿用
							if("01".equals(partyDto.getGatewayHeader().getGwErrorCode())){//网关错误标识（01-成功；00-错误）
								if (!StringUtils.isBlank(partyDto.getResult())&&!StringUtils.isBlank(partyDto.getCrId())) {//信用卡处理标识 Y、N正常  X异常
									//更新数据的接口调用状态
									br20_md_info.setInterface_type("4");
									br20_md_info_cgbMapper.updateInterface_typeByID(br20_md_info);
									//查询数据逆向转码为监管类型
									dictCoder.reverse(br20_md_info,null);
									//设置内部认定名单数据
									br20_md_info.setMd_kind("IDType_IDNumber");//修改实体类型为证件类
									br20_md_info.setMd_code(UUID.randomUUID().toString().replaceAll("-", ""));//设置名单编码
									br20_md_info.setMd_type("3");// 设置名单为内部认定名单
									br20_md_info.setP_name(partyDto.getCrName());// 客户姓名
									br20_md_info.setCardtype(partyDto.getCrIdType());//设置名单类型为证件类型
									br20_md_info.setMd_value(partyDto.getCrId());// 设置名单值为证件号码
									br20_md_info.setInterface_type("0");//0:不调用接口 1：调用核心接口 2：调用信用卡接口   3：已调用核心接口  4：已调用信用卡接口
									br20_md_info.setSend_type("1");// 0:都发 1:发怂核心 2:发送信用卡
									br20_md_info.setMd_flag("1");// 名单状态 0:无效 1:有效 2:白名单
									br20_md_info.setLast_update_time(Utility.currDateTime19());//设置更新时间
									br20_md_info.setCredit_send_flag("0");//重置信用卡发送状态
									//启用信用卡转码组 （将信用卡返回的证件类型转为监管类型）
									br20_md_info.setCoreflag(false);
									dictCoder.transcode(br20_md_info,null);
									
									credit_list.add(br20_md_info);
								}else{
									logger.error("行用卡端处理数据失败，查询账卡号："+partyDto.getCusNumber()+"==返回状态："+partyDto.getResult()+"==返回证件号码："+partyDto.getCrId());
								}
							}else{
								logger.error("网关返回错误消息："+partyDto.getGatewayHeader().getGwErrorMessage());
							}
						} catch (Exception e) {
							logger.info("响应的xml信息\r\n"+IOUtils.toString(entity.getContent(),  "gb18030") );
							logger.error("通过JiBX解析信用卡回执报文失败：" + e.getMessage());
						} finally {
							EntityUtils.consume(entity);
						}
					} else {
						logger.info("与网关通讯异常，http状态码："+statusCode);
					}
				}
			}
		} catch (Exception e) {
			logger.error("调用信用卡接口，获取客户信息发生异常。应查数据：" + intefacelist.size() + "条==实际查询：" + credit_list.size() + "条\r\n" + e.getMessage(),e);
		}
		return credit_list;
	}

	/**
	 * 客户信息入库
	 * 
	 * @param map
	 */
	private void insertPartyInfo(ArrayList<Br20_md_info> list) {
		try {
			if (list != null && list.size() > 0) {
				// 插入Br20_md_data_log
				br20_md_info_cgbMapper.insertBr20_md_data_logByBatch(list);
				// 插入Br20_md_data
				for(int i=0;i<list.size();i++){
					Br20_md_info md = list.get(i);
					if("-".equals(md.getOperate_flag())){
						//根据三证删除数据
						br20_md_info_cgbMapper.deleteBr20_md_dataByKeys(md);
					}else{
						//根据三证更新数据
						br20_md_info_cgbMapper.updateBr20_md_dataByMergeOne(md);
					}
				}
			} else {
				logger.info("没有需要推送核心的客户名单信息");
			}
		} catch (Exception e) {
			logger.error("客户信息入库发生异常" + e.getMessage(),e);
		}
	}

	/**
	 * 对象转换为soapxml
	 * 
	 * @param md
	 * @return
	 * @throws JiBXException
	 */
	private String getSoapXml(Br20_md_info md) throws JiBXException {
		AfpSuspicious xmlDto = new AfpSuspicious();
		String time = Utility.currDateTime14() + (int) ((Math.random() * 9 + 1) * 10000000);// 日期＋随机数的22位流水号
		// 通用报文头处理
		GatewayHeader header = new GatewayHeader();
		header.setReceiverId("SWT3");//接收方系统标识
		header.setSenderId("AFPN");// 发起放系统标识
		header.setCommCode(GatewayHeader.COMMCODE_REQUEST);//请求：500001 响应：510001
		header.setSenderSN(time);
		header.setSenderDate(time.substring(0, 8));
		header.setSenderTime(time.substring(8, 14));
		header.setTradeCode("AFP003");// 交易代码 
		xmlDto.setGatewayHeader(header);
		// header128处理
		xmlDto.setAcqDate(time.substring(0, 8));
		xmlDto.setAcqTime(time.substring(8, 14));
		xmlDto.setAcqSeiNumber(time);
		// body处理
		xmlDto.setRequestType(md.getOperate_flag());// 请求类型
		xmlDto.setInfoType(md.getMd_kind());// 信息类型 此处只有这两种
		xmlDto.setListType(md.getMd_type());//此处只有这两种
		xmlDto.setRelatedCusNumber(""); // 关联客户号
		xmlDto.setCusNumber(md.getMd_value()); // 证件号/卡账号
		xmlDto.setCusDesc(md.getP_name());// 姓名描述
		xmlDto.setIdType(md.getCardtype());// 证件类型[核心的证件类型]
		xmlDto.setBankNumber(md.getBank_id());// 机构编号
		xmlDto.setContacts(md.getPolice());// 联系人
		xmlDto.setPhone(md.getPolice_phone()); // 联系人电话
		String checkTime = StringUtils.replaceChars(md.getPolice_check_dt(), "-: ", "").trim();
		if (checkTime.length() == 14) {
			xmlDto.setBlkListDate(checkTime.substring(0, 8));// 审核通过黑名单日期
			xmlDto.setBlkListTime(checkTime.substring(8));// 审核通过黑名单时间
		}
		xmlDto.setOpenDate("");// 开户日期
		xmlDto.setOpenTime(""); // 开户时间
		String startTime = StringUtils.replaceChars(md.getStart_dt(), "-: ", "");
		if (startTime.length() == 14) {
			xmlDto.setEffDate(startTime.substring(0, 8));// 生效日期
			xmlDto.setEffTime(startTime.substring(8));// 生效时间
		}
		String endTime = StringUtils.replaceChars(md.getEnd_dt(), "-: ", "");
		if (endTime.length() == 14) {
			xmlDto.setExpiredDate(endTime.substring(0, 8));// 失效日期
			xmlDto.setExpiredTime(endTime.substring(8));// 失效时间
		}
		xmlDto.setListMemo(md.getRemark());// 名单说明
		xmlDto.setReason(md.getMd_source());// 加入原因（一般为名单来源机构）
		xmlDto.setListDetail(md.getCase_type());// 其它信息（一般为名单细类）
		xmlDto.setMemo(md.getOpen_card_dt()); // 备注 存放的是【名单类型冗余项】

		String xml = CommonUtils.marshallContext(xmlDto, "binding_cgb_suspicious", true);

		return xml;
	}

	/**
	 * 生成txt文本文件并插入一条发送记录
	 * 
	 * @param localpath
	 *            本地存放路劲
	 * @param br20_md_info
	 * @param list
	 *            库中数据定长处理后的list
	 * @return
	 */
	public String getTxt(String localpath, Br20_md_info br20_md_info, List<String> list) {
		File file = new File(localpath);
		if (!file.exists() || !file.isDirectory()) {// 保证目录存在
			file.mkdirs();
		}
		String filename = "";
		String head = StringUtils.rightPad("H", 1224, " ");//文件头
		String tail = StringUtils.rightPad("T", 1224, " ");//文件尾
		filename = "AFPS.CBOE.CICFQZ.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
		// 向数据库插入一条发送记录
		Br20_md_info md_info = new Br20_md_info();
		// String file_code = codeService.getSequenceValus("seq_br20_md_accept");//取序列
		String file_code = UUID.randomUUID().toString().replaceAll("-", "");// 文件编号
		md_info.setFile_code(file_code);
		md_info.setSyscode(br20_md_info.getSyscode());
		md_info.setFile_name(filename);
		md_info.setFile_type(br20_md_info.getFile_type());
		md_info.setFlag("1");// 发送标志 0:失败 1:成功
		md_info.setSend_time(br20_md_info.getNext_time());
		br20_md_info_cgbMapper.insertBr20_md_send(md_info);
		try {
			File outFile = new File(localpath + File.separator + filename);
			
			if (list != null && list.size() > 0) {
				list.add(0, head);//文件头部
				list.add(tail);//文件尾部
				String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
				FileUtils.writeLines(outFile, encoding, list);
				list.clear();//清理数据，释放空间
			}
		} catch (IOException e) {
			logger.info(filename + "文件写入失败！",e);
		}
		
		return filename;
	}

	/**
	 * 更新发送状态［增量］
	 * 
	 * @param keys
	 * @param send_time
	 */
	private void updateSend_flag(String send_flag, String send_time) {
		Br20_md_info mdkey = new Br20_md_info();
		mdkey.setSend_time(send_time);
		mdkey.setSend_flag(send_flag);// 0:未发送 1:已发送 9:中间状态［待发送］
		br20_md_info_cgbMapper.updateSend_flagBykeys(mdkey);
	}

	/**
	 * 更新发送时间 增量每2小时发送一次 全量每天发送一次
	 * 
	 * @param br20_md_info
	 */
	private void updateSendTime(Br20_md_info br20_md_info) {
		try {
			String nex_time = br20_md_info.getNext_time();
			int amount = Integer.valueOf(br20_md_info.getFrequency());// 频率
			int gran = Integer.valueOf(br20_md_info.getUnit());// 频率单位
			String new_next_time = "";
			if ("".equals(nex_time)) {// 下次发送时间为空
				nex_time = DtUtils.getNowTime();
				new_next_time = DtUtils.getTimeByGran(nex_time, amount, gran, "yyyy-MM-dd HH:mm:ss");
			} else {
				new_next_time = DtUtils.getTimeByGran(nex_time, amount, gran, "yyyy-MM-dd HH:mm:ss");
			}
			br20_md_info.setLast_time(nex_time);
			br20_md_info.setNext_time(new_next_time);

			br20_md_info_cgbMapper.updateBr_md_taskByVo(br20_md_info);

		} catch (ParseException e) {
			logger.info("生成发送文件时，时间转换失败！",e);
		}
	}

	/**
	 * 处理数据list
	 * 
	 * @param datalist
	 *            数据list
	 * @param mode
	 *            数据存放方式
	 * @param Separate
	 *            分隔符号
	 * @return
	 */
	private List<String> dealDataList(List<Br20_md_info> datalist, String mode, String Separate) {
		return getFixedLengthList(datalist);
	}

	/**
	 * 定长处理<br>
	 * 对每个object，将核心需要的字断做定长处理，然后按指定顺序拼接到一起 
	 * @param datalist
	 *            从库中查询的结果
	 * @return
	 */
	private List<String> getFixedLengthList(List<Br20_md_info> datalist) {
		List<String> list = null;
		if (datalist != null && datalist.size() > 0) {
			list = Lists.newArrayList();
			for (Br20_md_info md : datalist) {
				//将字段进行转吗
				dictCoder.transcode(md,null);
				
				StringBuffer sb = new StringBuffer();
				sb.append(md.getOperate_flag());//操作符号 1
				sb.append(md.getMd_kind());//数据类型 1
				sb.append(md.getMd_type());//名单类型
				sb.append("            ");// 关联客户号
				sb.append(rightPad(md.getMd_value(), 80));// 实体编号
				sb.append(rightPad(md.getP_name(), 200));// 实体名称
				sb.append(rightPad(md.getCardtype(),5));// 证件类型
				sb.append(rightPad(md.getBank_id(), 14));// 银行编码
				sb.append(rightPad(md.getPolice(), 122));// 公安联系人
				sb.append(rightPad(md.getPolice_phone(), 50));// 公安联系人电话
				sb.append(rightPad(StringUtils.replaceChars(md.getPolice_check_dt(), "-: ", ""), 14));// 公安审核通过时间戳
				sb.append(rightPad(" ", 14));// 开卡时间---名单说明v4.0中 【开卡时间】废弃，目前该字段无值
				sb.append(rightPad(StringUtils.replaceChars(md.getStart_dt(), "-: ", ""), 14));// 有效期开始时间
				sb.append(rightPad(StringUtils.replaceChars(md.getEnd_dt(), "-: ", ""), 14));// 有效期结束时间
				sb.append(rightPad(md.getRemark(), 240));// 名单说明
				sb.append(rightPad(md.getMd_source(), 120));// 加入原因(发送机构)
				sb.append(rightPad(md.getCase_type(), 120));// 其他信息(名单细类)
				sb.append(rightPad(md.getOpen_card_dt(), 200));// 备注200 存放的是【名单类型冗余项】
				list.add(sb.toString());
			}
		}
		return list;
	}

	/**
	 * 处理字节长度问题
	 * 
	 * @param str
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String rightPad(String str, int length) {
		int len = 0;
		try {
			len = str.getBytes("GBK").length;
			if (len >= length) {
				return str;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		return str + StringUtils.rightPad("", length - len, ' ');
	}
	/**
	 * 发送数据文件
	 * 
	 * @param srcName 数据文件名
	 * @return
	 */
	private boolean fileToServer(String srcName) {
		boolean isSucc = false;
		try {
			String ip = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_IP); //源系统ip
			int port = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_PORT)); //目标端口号
			int timeout = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_TIMEOUT));//超时 单位为秒 实际可改为5-10分钟
			String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录
			
			FTSTaskClient client = new FTSTaskClient(ip, port);
			String taskId = "PEAWZGGP";// taskid从UFS获取
			TransferFile file = new TransferFile(srcName, srcPath, srcName, destPath);
			TransferFile[] fileList = new TransferFile[] {file};
			FTSRequest request = new FTSRequest(taskId, fileList);
			FTSResponse res = client.callTask(request, timeout);
			
			if (StringUtils.equals("0000", res.getErrorCode())) {
				isSucc = true;
				logger.info("数据文件发送文服成功,代码："+res.getErrorCode()+"==消息："+res.getErrorMessage());
			}	
		} catch (Exception e) {
			logger.info("数据文件发送到文服失败",e);
		}
		
		return isSucc;
		
	}
	
	/**
	 * 生成.end文件并发送
	 * 
	 * @param srcName 数据文件名
	 */
	private boolean fileEndToServer(String srcName) {
		boolean isSucc = false;
		try {
			String ip = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_IP); //源系统ip
			int port = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_PORT)); //目标端口号
			int timeout = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_TIMEOUT));//超时 单位为秒 实际可改为5-10分钟
			String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录		
			String filename = srcName + ".end";
			String oneline = "AGENTDIR=/cdadmin/UFSM/agent/CBOE/save\n";//代理路径
			String twoline = "SYSTEMCOUNTS=001\n";//序列号
			String threeline = "ROUT001=PEAWZGGP;/cdadmin/UFSM/agent/CBOE/save/;CIQFQZ0;";//tasked+路径＋io文件名
			
			String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
			FileUtils.writeStringToFile(new File(srcPath + File.separator + filename), oneline + twoline + threeline, encoding);
			
			FTSTaskClient client = new FTSTaskClient(ip, port);
			String taskId = "PEAWZGGP";// taskid从UFS获取
			TransferFile file = new TransferFile(filename, srcPath, filename, destPath);
			TransferFile[] fileList = new TransferFile[] {file};
			FTSRequest request = new FTSRequest(taskId, fileList);
			FTSResponse res = client.callTask(request, timeout);
			
			if (StringUtils.equals("0000", res.getErrorCode())) {
				isSucc = true;
				logger.info("确认文件发送文服成功,代码："+res.getErrorCode()+"==消息："+res.getErrorMessage() );
			}
		} catch (Exception e) {
			logger.info("确认文件发送到文服失败",e);
		}
		
		return isSucc;
	}
	

}

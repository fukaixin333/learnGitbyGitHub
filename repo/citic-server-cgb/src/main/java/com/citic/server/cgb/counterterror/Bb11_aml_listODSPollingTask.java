package com.citic.server.cgb.counterterror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.cgb.domain.AmlWarnDto;
import com.citic.server.cgb.domain.Bb11_data_task;
import com.citic.server.cgb.mapper.Bb11_aml_warn_logMapper;
import com.citic.server.runtime.FileTools;
import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.BusiTx;
import com.citic.server.utils.DtUtils;

@Component("bb11_aml_listODSPollingTask")
public class Bb11_aml_listODSPollingTask extends AbstractPollingTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Bb11_aml_warn_logMapper warn_mapper;
	
	private final String OK_FLAG = ".ok";//ODS文件的确认文件结尾
	private final String DATA_FLAG = ".dat";//ODS文件的数据文件结尾
	private final String DATA_NAME = "V_TEROR_NAME_LIST";//ODS反恐怖名单文件名称（不带格式）
	private int BATCH_NUM = 1000;//单次批量处理数
	private final String ODS_ENCODE = "GB18030";//ODS文件的数据文件编码
	private final String ODS_SPR = "1B";//ODS数据分隔符号为0x1b,转16进制大写为1B
	private final String FORBIDDEN_DEAL_TIMES = "22,23,00";//禁止处理时间段
	
	/**
	 * 执行方法入口
	 * 1.从ODS获取数据并清洗后，插入业务表
	 * 备注：
	 * 1.反洗钱通过ODS方式在23:00左右处理T+1的数据文件到本系统，其他系统在02:00开始抽取数据。
	 * 2.设定22:00——01:00期间不处理文件数据
	 */
	@Override
	public void executeAction() {
//		try {
//			if (this.getIsAvailableTimes()) {
//				this.loadDataByFile();
//			} else {
//				logger.info("22:00-00:00暂停处理反恐怖名单数据");
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
		this.loadDataByFile();
	}
	
	/**
	 * 名单数据加载 <br>
	 * 查询bb11_data_task(待创建)，判断日期是否0（待处理） <br>
	 * 0（待处理）：查询.ok文件是否存在，存在则更新状态为1（处理中），然后处理文件，处理完成，更新数据日期+1和状态0 <br>
	 * 1(处理中)：结束，进入下次循环。 <br>
	 * 待解决：异常机制。方案一：需要再主控启动时，更新一下bb11_data_task表的状态，将1变为0。方案2：在web加功能
	 * 
	 * @author yinxiong
	 * @date 2017年5月26日 下午8:51:06
	 */
	private void loadDataByFile() {
		try {
			//查询待处理的数据日期
			Bb11_data_task task1 = new Bb11_data_task();
			task1.setTask_id("TK0001");//初始化的taskId
			task1.setTask_status("0");//待处理
			task1 = warn_mapper.selectBb11_data_task(task1);
			if (task1 != null) {
				String dateStr = Utility.toDate8(task1.getTask_date());
				String srcPath = "/odsdata/" + dateStr + "/FXQ";//源系统地址目录
				this.cleanDataByRule(srcPath, dateStr);
			} else {
				logger.info("无待处理的ODS反恐怖名单数据");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 文件处理
	 * 
	 * @param srcPath 源文件目录路径
	 * @param destPath 目标文件目录路径
	 * @author yinxiong
	 * @date 2017年5月26日 下午9:20:27
	 */
	private void cleanDataByRule(String srcPath, String dateStr) {
		try {
			//扫描指定目录下的文件并获取文件列表
			File dir = new File(srcPath);
			//获取.ok文件
			String fileName = FileTools.getFileNameByName(dir, DATA_NAME + OK_FLAG);
			if (!StringUtils.isBlank(fileName)) {
				logger.info("反洗钱的ODS反恐怖文件处理开始");
				//更新任务状态为处理中
				Bb11_data_task task = new Bb11_data_task();
				task.setTask_id("TK0001");//初始化的taskId
				task.setTask_status("1");//0：待处理 1：处理中
				task.setTask_date("");
				warn_mapper.updateBb11_data_taskByID(task);
				
				//处理文件,进行清洗和入库
				String dataName = fileName.replace(OK_FLAG, DATA_FLAG);//获取数据文件名
				List<AmlWarnDto> list = this.dealODSFileDate(srcPath, dataName);
				//入库
				this.insertBb11_aml_listByBatch(list);
				list.clear();
				logger.info("反洗钱的ODS反恐怖文件[" + dataName + "]处理结束");
				
				//更新下次任务日期和处理状态
				String nextDate = DtUtils.getTimeByGran(dateStr, 1, 3, "yyyy-MM-dd");//获取下个待处理日期
				task.setTask_date(nextDate);
				task.setTask_status("0");//0：待处理 1：处理中
				warn_mapper.updateBb11_data_taskByID(task);
			} else {
				logger.info("ODS目标文件未生成，目标路径：" + srcPath + File.separatorChar + DATA_NAME + OK_FLAG);
			}
		} catch (Exception e) {
			logger.error("反洗钱的ODS反恐怖文件处理异常：" + e.getMessage(), e);
		}
	}
	
	/**
	 * 数据读取和清洗
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @author yinxiong
	 * @date 2017年5月26日 下午9:49:26
	 */
	private List<AmlWarnDto> dealODSFileDate(String path, String name) {
		InputStreamReader read = null;
		BufferedReader br = null;
		List<AmlWarnDto> list = null;
		try {
			//定义证件号码+“@”+姓名 为key的map,用于去重
			HashMap<String, AmlWarnDto> map = new HashMap<String, AmlWarnDto>();
			String filepath = path + File.separator + name;// 服务器文件路径
			//数据读取
			read = new InputStreamReader(new FileInputStream(filepath), ODS_ENCODE);// 处理中文乱码
			br = new BufferedReader(read);
			String r = br.readLine();
			int m = 0;
			while (r != null) {
				if (StringUtils.isNotBlank(r)) {
					// 保证数据存在
					byte[] bytes = r.getBytes(ODS_ENCODE); //获取数据字节
					//分隔符号特殊，转为16进制字符串进行分隔，分隔后逆转回来
					String hexStr = HexCoder.encodeToString(bytes, false);
					String[] subs = hexStr.split(ODS_SPR, -1);
					//遭遇特殊字符串造成分隔符多余实际
					if (subs.length > 14) {
						subs = this.mySpilte(hexStr, ODS_SPR);
					}
					try {
						String source_id = new String(HexCoder.decode(subs[0]), ODS_ENCODE).trim();//监控名单唯一标识码
						String first_name = new String(HexCoder.decode(subs[1]), ODS_ENCODE).trim();//名
						String mid_name = new String(HexCoder.decode(subs[2]), ODS_ENCODE).trim();//中间名
						String sur_name = new String(HexCoder.decode(subs[3]), ODS_ENCODE).trim();//姓
						String org_scpt_name = new String(HexCoder.decode(subs[4]), ODS_ENCODE).trim();//原语言名
						//					String idtype = new String(HexCoder.decode(subs[5]), ODS_ENCODE).trim();//证件类型 不要，默认给空
						String idvalue = new String(HexCoder.decode(subs[6]), ODS_ENCODE).trim();//证件值
						String san_name_type = new String(HexCoder.decode(subs[7]), ODS_ENCODE).trim();//制裁参考名单类型 转换F001-F004
						String black_class_cd = new String(HexCoder.decode(subs[8]), ODS_ENCODE).trim();//名单客户类型
						String black_action = new String(HexCoder.decode(subs[9]), ODS_ENCODE).trim();//数据状态 
						String data_date = new String(HexCoder.decode(subs[10]), ODS_ENCODE).trim();//数据日期yyyy-MM-dd
						String act_status = new String(HexCoder.decode(subs[11]), ODS_ENCODE).trim();//名单状态  装换为“+”或者“-”
						//					String his_update_ind = new String(HexCoder.decode(subs[12]), ODS_ENCODE).trim();//操作标识 不处理
						//					String tx_date = new String(HexCoder.decode(subs[13]), ODS_ENCODE).trim();//ODS时间戳 不处理
						
						//拼接名+中间名+姓,去掉开头的【英文单引号】
						String pname = first_name.replace("'", "") + mid_name.replace("'", "") + sur_name.replace("'", "");
						//=======按规则校验字段===============
						
						//过滤名单值为空的数据
						if (StringUtils.isBlank(idvalue)) {
							logger.info("证件值为空，数据将被过滤，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						//证件值长度超80
						if (idvalue.getBytes(ODS_ENCODE).length > 78) {
							logger.info("证件值长度超过78字节，数据将被过滤，本行数据：" + r);
							r = br.readLine();
							continue;
						}
						//获取拆分的姓名list
						List<String> strList = this.getNameDeal(pname, org_scpt_name);
						if (strList != null && strList.size() > 0) {
							for (String nameStr : strList) {
								if (!map.containsKey(idvalue + "@" + nameStr)) {
									AmlWarnDto warnDto = new AmlWarnDto();
									String mdCode = UUID.randomUUID().toString().replaceAll("-", "");
									warnDto.setMd_code(mdCode);
									warnDto.setSource_id(source_id);
									warnDto.setPname(nameStr);
									warnDto.setIdtype(""); //默认给空
									warnDto.setIdvalue(idvalue);
									warnDto.setSan_name_type(this.getMappingByKey(san_name_type));//类型转换
									warnDto.setBlack_class_cd(black_class_cd);
									warnDto.setBlack_action(black_action);
									warnDto.setData_date(data_date);
									warnDto.setAct_status("Inactive".equals(act_status) ? "-" : "+");//Inactive:- 减少   Active：+ 增加
									warnDto.setRemark("");//预留
									warnDto.setSend_flag("0");//发送表值 0：待发送 1：已发送 9中间态
									
									map.put(idvalue + "@" + nameStr, warnDto);
								}
							}
						}
					} catch (Exception e) {
						logger.error(m + "行记录：" + r,e);
					}
				}
				r = br.readLine();
			}
			
			//map转换为list
			list = new ArrayList<AmlWarnDto>(map.size());
			list.addAll(map.values());
			
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
	 * 名单细类转换
	 * 
	 * @param key
	 * @return
	 *         0:F001-国内涉恐名单
	 *         1:F002-中国公安部涉恐名单
	 *         2:F003-中国人民银行涉恐名单
	 *         3:F004-联合国涉恐名单
	 * @author yinxiong
	 * @date 2017年5月27日 下午2:47:57
	 */
	private String getMappingByKey(String key) {
		if ("0".equals(key)) {
			return "F001";//
		} else if ("3".equals(key)) {
			return "F004";//
		} else {
			return "1".equals(key) ? "F002" : "F003";
		}
	}
	
	/**
	 * 姓名处理 <br>
	 * 原语言名称拆分:先按空格切分，然后替换unicode格式的字符 <br>
	 * 所有名称需要保证长度不超过120字节
	 * 
	 * @param pname 名+中间名+姓
	 * @param org_scpt_name 源语言名称
	 * @return
	 * @author yinxiong
	 * @date 2017年5月27日 上午11:37:45
	 */
	private List<String> getNameDeal(String pname, String org_scpt_name) {
		ArrayList<String> listq = new ArrayList<String>();
		try {
			//“名+中间名+姓”处理:替换非中文或英文，非姓名中用的分隔号，非空格
			pname = pname.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5'·\t\\s]", "");//替换非法字符
			if (!StringUtils.isBlank(pname) && pname.getBytes(ODS_ENCODE).length <= 120) {
				listq.add(pname);
			}
			//原语言名称处理
			String[] strTeam = org_scpt_name.split(" ");
			for (int i = 0; i < strTeam.length; i++) {
				String tmpStr = strTeam[i].replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5'·\\s]", "").trim();
				if (!StringUtils.isBlank(tmpStr) && tmpStr.getBytes(ODS_ENCODE).length <= 120) {
					listq.add(tmpStr);
				}
			}
		} catch (Exception e) {
			logger.error("处理ODS的反恐怖名单文件中姓名出错，" + e.getMessage(), e);
		}
		return listq;
	}
	
	/**
	 * 数据批量插入
	 * 
	 * @author yinxiong
	 * @date 2017年5月19日 下午5:42:58
	 */
	@BusiTx
	public void insertBb11_aml_listByBatch(List<AmlWarnDto> list) {
		if (list != null && list.size() > 0) {
			int size = list.size();
			int m = size / BATCH_NUM;//整数部分
			int n = size % BATCH_NUM;//余数部分
			//处理满足BATCH_NUM的数据
			for (int i = 0; i < m; i++) {
				List<AmlWarnDto> list_x = list.subList(BATCH_NUM * i, BATCH_NUM * (i + 1));
				warn_mapper.batchInsertBb11_aml_list(list_x);
			}
			//处理不满足BATCH_NUM的数据
			if (n != 0) {
				List<AmlWarnDto> list_y = list.subList(BATCH_NUM * m, BATCH_NUM * m + n);
				warn_mapper.batchInsertBb11_aml_list(list_y);
			}
		}
	}
	
	private boolean getIsAvailableTimes() {
		//获取小时（HH）
		String hours = DtUtils.getNowTime().substring(11, 13);
		//获取禁用时间段（小时HH）
		String x = FORBIDDEN_DEAL_TIMES;
		if (x.contains(hours)) {
			return false;
		} else {
			return true;
		}
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
		return "20";
	}
		
	/**
	 * 自定义分隔方法 <br>
	 * 将原分隔符号按字节读取替换为$$，然后切割
	 * 
	 * @param str
	 * @param sp
	 * @return
	 * @author yinxiong
	 * @date 2017年6月6日 下午1:58:31
	 */
	private String[] mySpilte(String str, String sp) {
		String[] team = null;
		char[] c = str.toCharArray();
		for (int i = 0; 2 * i < c.length; i++) {
			String a = c[2 * i] + "" + c[2 * i + 1];
			if (sp.equals(a)) {
				c[2 * i] = '$';
				c[2 * i + 1] = '$';
			}
		}
		String s = String.valueOf(c);
		team = s.split("\\$\\$", -1);
		return team;
	}
}

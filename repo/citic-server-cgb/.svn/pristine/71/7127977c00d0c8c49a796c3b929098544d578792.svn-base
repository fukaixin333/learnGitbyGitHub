package com.citic.server.cgb.counterterror;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.cgb.domain.AmlWarnDto;
import com.citic.server.cgb.mapper.Bb11_aml_warn_logMapper;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StrTools;
import com.citic.server.runtime.Utility;
import com.citic.server.runtime.WfTools;
import com.citic.server.utils.BusiTx;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Lists;

@Component("bb11_aml_listPollingTask")
public class Bb11_aml_listPollingTask extends AbstractPollingTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Bb11_aml_warn_logMapper warn_mapper;
	
	private final String FORBIDDEN_SEND_TIMES = "22,23,00,01,02,03,04";//禁止推数时间段，小时按逗号分隔
	private int BATCH_NUM = 1000;//单次批量处理数
	private int ADD_NUM = 50000;//每次文件按最大获取数量
	
	/**
	 * 执行方法入口
	 * 1.从ODS获取数据并清洗后，插入业务表
	 * 2.判断发送时间
	 * 3.选出待发送的名单数据
	 * 3.处理数据并发送
	 * 备注：
	 * 1.反洗钱通过ODS方式在23:00左右处理T+1的数据文件到本系统，其他系统在02:00开始抽取数据。
	 * 2.新核心22:00-03:00禁止推送数据；卡核心00:00-05:00禁止推送数据
	 * 3.数据推送是1份数据，分推2次
	 * 4.综合考虑，轮询获取数据（无限制），限定时间轮询推送数据（22：00-5:00）禁止推送
	 * 5.两个大核心最快5:00后获取名单数据，且数据为T+2
	 * 6.新核心有头尾，2个模块的顺序不能变，卡核心在前，新核心在后
	 * 状态变化：
	 * 0：待发送 1：已发送 8：已写入文件 9：中间态
	 * 0--》9---》8--》1
	 */
	@Override
	public void executeAction() {
		try {
			//判断是否允许发送时间段
			//			if(this.getIsAvailableTimes()){
			//				//更新待发送的数据，更新为中间状态
			//				String topSize = ServerEnvironment.getStringValue(CgbKeys.TOP_SIZE_BY_CBOE);//约定最大的文件记录条数
			//				warn_mapper.updateBb11_aml_listSendFlagToNine(StringUtils.isBlank(topSize)?"100000":topSize);
			//				//选取待中间态的的数据
			//				ArrayList<AmlWarnDto> list = warn_mapper.selectBb11_aml_listListByNine();
			//				if (list != null && list.size() > 0) {
			//					//定长转换处理
			//					List<String> strList = this.getFixedLengthList(list);
			//					//卡核心推送模块
			//					this.pushAmlListToCredit(strList);
			//					//新核心推送模块
			//					this.pushAmlListToCore(strList);
			//					//更新中间态数据为已发送
			//					warn_mapper.updateBb11_aml_listSendFlagToOne(Utility.currDateTime19());
			//				}
			//			}else{
			//				logger.info("新核心或卡核心禁推时间段");
			//			}
			if (this.getIsAvailableTimes()) {
				//新核心存在首和尾
				String head = StringUtils.rightPad("H", 1224, " ") + CommonUtils.localLineSeparator();//文件头
				String tail = StringUtils.rightPad("T", 1224, " ") + CommonUtils.localLineSeparator();//文件尾
				//定义文件名
				String srcNameCredit = "AFPS.CCS0.CSTTOR.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
				String srcNameCore = "AFPS.CBOE.CIKFQZ.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
				//定义单次追加数据量5w 
				int number = warn_mapper.selectBb11_aml_listCountByFlag();
				if (number > 0) {
					int m = number / ADD_NUM ;
					int n =  number%ADD_NUM;
					//有余数，需要多一次循环
					if(n!=0){
						m = m+1;
					}
					//循环获取数据，并追加写入文件
					for (int i = 0; i < m; i++) {
						boolean bzw = false;//发送文服标识（汇集所有数据后才会发送）
						if (i + 1 == m) {
							bzw = true;
						}
						//将待发送的数据，更新为中间状态
						warn_mapper.updateBb11_aml_listSendFlagToNine(ADD_NUM + "");
						//选取中间态的的数据，限定5w笔
						ArrayList<AmlWarnDto> list = warn_mapper.selectBb11_aml_listListByNine(ADD_NUM + "");
						//定长转换处理
						List<String> strList = this.getFixedLengthList(list);
						//卡核心推送模块
						this.pushAmlListToCredit2(strList, srcNameCredit, bzw);
						if (i == 0) {
							strList.add(0, head);//文件头部
						}
						if (i + 1 == m) {
							strList.add(tail);//文件尾部
						}
						//新核心推送模块
						this.pushAmlListToCore2(strList, srcNameCore, bzw);
						//更中间态数据为已写入状态8
						warn_mapper.updateBb11_aml_listSendFlagToEight();
					}
					//文件推送完成，更新全部已写入状态数据为已发送
					warn_mapper.updateBb11_aml_listSendFlagToOne(Utility.currDateTime19());
				}else{
					logger.info("没有待推送的反恐怖名单数据");
				}
			} else {
				logger.info("新核心或卡核心禁推时间段");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	/**
	 * 推送名文件到新核心
	 * 
	 * @author yinxiong
	 * @date 2017年5月20日 下午3:14:28
	 */
	private void pushAmlListToCore(List<String> srclist) {
		//生成文件名
		String srcName = "";
		try {
			//注[名称中第三个字段CIKFQZ代表业务类型，已改变]
			srcName = "AFPS.CBOE.CIKFQZ.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
			String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录（目标服务器保存目录）
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录（本地发送目录）
			String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
			String taskId = "PEAWZGGP";// taskid从UFS获取
			String destName = srcName;
			String sysId = "CBOE";
			String fileIOName = "CIQFQZ0";
			//生成数据文件（因为需要改变list结构，此处复制一个新的list)
			List<String> list = new ArrayList<String>(srclist.size());
			list.addAll(srclist);
			File outFile = new File(srcPath + File.separator + srcName);
			//新核心存在首和尾
			String head = StringUtils.rightPad("H", 1224, " ");//文件头
			String tail = StringUtils.rightPad("T", 1224, " ");//文件尾
			if (list != null && list.size() > 0) {
				list.add(0, head);//文件头部
				list.add(tail);//文件尾部
				FileUtils.writeLines(outFile, encoding, list);
				list.clear();//清理数据，释放空间
			}
			//调用文服API发送
			boolean flag = WfTools.sendFile(srcPath, srcName, destPath, destName, taskId, sysId, fileIOName, encoding);
			if (flag) {
				logger.info("文件[" + srcName + "]推送新核心成功");
			} else {
				logger.error("文件[" + srcName + "]推送新核心失败");
			}
		} catch (IOException e) {
			logger.info("文件[" + srcName + "]推送新核心过程发生异常", e);
		}
	}
	
	/**
	 * 防止内存溢出，采用追加的方式写入新核心文件
	 * <br>必须保证”源系统地址目录“存在，若不存在，需要手工创建
	 * @param srclist
	 * @author yinxiong
	 * @date 2017年6月14日 下午8:22:42
	 */
	private void pushAmlListToCore2(List<String> srclist, String srcName, boolean bzw) {
		//注[名称中第三个字段CIKFQZ代表业务类型，已改变]
		String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录（目标服务器保存目录）
		String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录（本地发送目录）
		String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
		String taskId = "PEAWZGGP";// taskid从UFS获取
		String sysId = "CBOE";
		String fileIOName = "CIQFQZ0";
		String destName = srcName;
		OutputStreamWriter writer = null;
		BufferedWriter bufW = null;
		if (srclist != null && srclist.size() > 0) {
			try {
				if (srclist != null && srclist.size() > 0) {
//					writer = new FileWriter(srcPath + File.separator + srcName, true);//内容追加模式
					writer = new  OutputStreamWriter(new FileOutputStream(srcPath + File.separator + srcName, true), "GB18030");
					bufW = new BufferedWriter(writer);
					for (String str : srclist) {
						bufW.write(str);
						//						bufW.write(CommonUtils.localLineSeparator());//本地换行符号
					}
					bufW.flush();
				}
			} catch (IOException e) {
				logger.info("文件[" + srcName + "]推送新核心过程发生异常", e);
			} finally {
				if (bufW != null) {
					try {
						bufW.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (bzw) {
					//调用文服API发送
					boolean flag = WfTools.sendFile(srcPath, srcName, destPath, destName, taskId, sysId, fileIOName, encoding);
					if (flag) {
						logger.info("文件[" + srcName + "]推送新核心成功");
					} else {
						logger.error("文件[" + srcName + "]推送新核心失败");
					}
				}
			}
		}
	}
	
	/**
	 * 防止内存溢出，采用追加的方式写入卡核心文件
	 * <br>必须保证”源系统地址目录“存在，若不存在，需要手工创建
	 * @param srclist
	 * @author yinxiong
	 * @date 2017年6月14日 下午8:00:59
	 */
	private void pushAmlListToCredit2(List<String> srclist, String srcName, boolean bzw) {
		//文件名
		String destPath = "/cdadmin/UFSM/agent/CCS0/save"; //目标地址目录
		String srcPath = "/cdadmin/UFSM/AFPS/send/CCS0";//源系统地址目录
		String taskId = "PEAWZGGP";// taskid从UFS获取
		String sysId = "CCS0";
		String fileIOName = "GCPTTOR0";
		String encoding = "GB18030";
		String destName = srcName;
		OutputStreamWriter writer = null;
		BufferedWriter bufW = null;
		if (srclist != null && srclist.size() > 0) {
			try {
				//生成数据文件
//				writer = new FileWriter(srcPath + File.separator + srcName, true);
				writer = new  OutputStreamWriter(new FileOutputStream(srcPath + File.separator + srcName, true), "GB18030");
				bufW = new BufferedWriter(writer);
				for (String str : srclist) {
					bufW.write(str);
					//					bufW.write(CommonUtils.localLineSeparator());//本地换行符号
				}
				bufW.flush();
			} catch (IOException e) {
				logger.info("文件[" + srcName + "]推送卡核心过程发生异常", e);
			} finally {
				if (bufW != null) {
					try {
						bufW.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (bzw) {
					//调用文服API发送
					boolean flag = WfTools.sendFile(srcPath, srcName, destPath, destName, taskId, sysId, fileIOName, encoding);
					if (flag) {
						logger.info("文件[" + srcName + "]推送卡核心成功");
					} else {
						logger.error("文件[" + srcName + "]推送卡核心失败");
					}
				}
			}
		}
	}
	
	/**
	 * 推送文件到卡核心
	 * 
	 * @param list
	 * @author yinxiong
	 * @date 2017年5月20日 下午3:15:21
	 */
	private void pushAmlListToCredit(List<String> srclist) {
		//文件名
		String srcName = "";
		try {
			srcName = "AFPS.CCS0.CSTTOR.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
			String destPath = "/cdadmin/UFSM/agent/CCS0/save"; //目标地址目录
			String srcPath = "/cdadmin/UFSM/AFPS/send/CCS0";//源系统地址目录
			String taskId = "PEAWZGGP";// taskid从UFS获取
			String destName = srcName;
			String sysId = "CCS0";
			String fileIOName = "GCPTTOR0";
			String encoding = "GB18030";
			//生成数据文件
			List<String> list = srclist;
			File outFile = new File(srcPath + File.separator + srcName);
			if (list != null && list.size() > 0) {
				FileUtils.writeLines(outFile, encoding, list);
				list.clear();//清理数据，释放空间
			}
			//调用文服API发送
			boolean flag = WfTools.sendFile(srcPath, srcName, destPath, destName, taskId, sysId, fileIOName, encoding);
			if (flag) {
				logger.info("文件[" + srcName + "]推送卡核心成功");
			} else {
				logger.error("文件[" + srcName + "]推送卡核心失败");
			}
		} catch (IOException e) {
			logger.info("文件[" + srcName + "]推送卡核心过程发生异常", e);
		}
	}
	
	/**
	 * 定长处理<br>
	 * 对每个object，将字断做定长处理，然后按指定顺序拼接到一起
	 * 
	 * @param datalist
	 *        从库中查询的结果
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private List<String> getFixedLengthList(List<AmlWarnDto> datalist) throws UnsupportedEncodingException {
		List<String> list = null;
		if (datalist != null && datalist.size() > 0) {
			list = Lists.newArrayList();
			for (AmlWarnDto md : datalist) {
				StringBuffer sb = new StringBuffer();
				sb.append("-".equals(md.getAct_status()) ? "D" : "A");//操作类型 1  需要转码“+”：A  “-”：D
				sb.append("2");//实体类型 1
				sb.append("FAA");//名单种类
				sb.append(StrTools.rightPad("", 12));// 关联客户号
				sb.append(StrTools.rightPad(md.getIdvalue(), 80));// 实体编号
				sb.append(StrTools.rightPad(md.getPname(), 200));// 实体名称
				sb.append(StrTools.rightPad("", 5));// 证件类型 目前给空
				sb.append(StrTools.rightPad("", 14));// 机构编号  忽略 
				sb.append(StrTools.rightPad("", 122));// 公安联系人 忽略
				sb.append(StrTools.rightPad("", 50));// 公安联系人电话 忽略
				sb.append(StrTools.rightPad(StringUtils.replaceChars(md.getData_date(), "-: ", ""), 8) + StrTools.rightPad("", 6));// 公安审核通过时间戳
				sb.append(StrTools.rightPad("", 14));// 开卡时间  忽略
				sb.append(StrTools.rightPad("", 14));// 有效期开始时间  忽略
				sb.append(StrTools.rightPad("", 14));// 有效期结束时间 忽略
				sb.append(StrTools.rightPad(md.getRemark(), 240));// 名单说明
				sb.append(StrTools.rightPad("", 120));// 加入原因(发送机构) 忽略
				sb.append(StrTools.rightPad(md.getSan_name_type(), 120));// 其他信息(名单细类)
				sb.append(StrTools.rightPad("", 200));// 备注200 存放的是【名单类型冗余项】 忽略
				//拼接换行符号
				sb.append(CommonUtils.localLineSeparator());
				list.add(sb.toString());
			}
			datalist.clear();
		}
		return list;
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
	
	/**
	 * 判断是否允许发送 <br>
	 * 非核心允许发送时间段即可发送
	 * 
	 * @return
	 * @author yinxiong
	 * @date 2017年5月26日 下午8:48:17
	 */
	private boolean getIsAvailableTimes() {
		//获取小时（HH）
		String hours = DtUtils.getNowTime().substring(11, 13);
		//获取禁用时间段（小时HH）
		String x = FORBIDDEN_SEND_TIMES;
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
		return "30";
	}
	
}

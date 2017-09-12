package com.citic.server.cgb.inner;

import java.text.ParseException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.cgb.service.InnerPollingTaskCoreService;
import com.citic.server.cgb.service.InnerPollingTaskCreditService;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.dx.mapper.Br20_md_info_cgbMapper;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Lists;

/**
 * 内联名单共享任务
 * 
 * @author Liu Xuanfei
 * @date 2016年11月21日 下午2:38:55
 */
@Component("innerPollingTaskLSS")
public class InnerPollingTaskLSS extends AbstractPollingTask {
	private static final Logger logger = LoggerFactory.getLogger(InnerPollingTaskLSS.class);
	
	@Autowired
	public Br20_md_info_cgbMapper br20_md_info_cgbMapper;
	@Autowired
	public InnerPollingTaskCoreService coreService;
	@Autowired
	public InnerPollingTaskCreditService creditService;
	@Getter
	@Setter
	public volatile String nextsendtime = "";
	
	@Override
	public void executeAction() {
		//推送核心
		this.push_file_core();
		//推送信用卡
		this.push_file_credit();
	}
	
	@Override
	protected String getTaskType() {
		return null; // lss
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.INNER_POLLING_TASK_SUSPICIOUS_PERIOD, "120"); // 默认2小时
	}
	
	// ==========================================================================================
	//                     Help Behavior
	// ==========================================================================================
	
	/**
	 * 推送名单数据到核心
	 * 沿用2小时推送制度
	 * 
	 * @author yinxiong
	 * @date 2016年12月3日 下午1:50:59
	 */
	private void push_file_core() {
		try {
			// 1.获取名单发送配置信息
			List<Br20_md_info> tasklist = Lists.newArrayList();
			tasklist = br20_md_info_cgbMapper.getTaskInfo();
			// 2.判断是否需要生成文件
			if (tasklist != null && tasklist.size() > 0) {
				logger.info("==名单推送任务开始==");
				for (Br20_md_info br20_md_info : tasklist) {
					nextsendtime = br20_md_info.getNext_time();// 下次发送时间
					int amount = Integer.valueOf(br20_md_info.getFrequency());//频率
					int gran = Integer.valueOf(br20_md_info.getUnit());//频率单位
					if (getSendFlag(nextsendtime, amount, gran)) {// 判断是否发送
						br20_md_info.setNext_time(nextsendtime);//更新下次发送时间
						//发送核心
						coreService.pushFileToCorce(br20_md_info);
					} else {
						logger.info("==未到核心名单推送时间==下次推送时间：" + nextsendtime);
					}
				}
			} else {
				logger.info("==没有名单推送任务==");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送名单数据到信用卡
	 * 跟随轮询时间，实时推送
	 * 
	 * @author yinxiong
	 * @date 2016年12月3日 下午1:51:39
	 */
	private void push_file_credit() {
		if(getIsAvailableTimesByCredit()){
			creditService.pushFile();
		}else{
			logger.info("==00:00到05:00暂停发送信用卡核心==");
		}
	}
	
	/**
	 * 是否发送
	 * 1.当前日期大于或等于下次发送时间，就可以发送了 两个日期字串格式必须一致
	 * 2.下次发送时间＋频率 小于 当前时间，说明下次发送时间不正确，纠正下次发送时间为当前时间
	 * 
	 * @param nextsendtime
	 * @param amount
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	private boolean getSendFlag(String nextsendtime, int amount, int gran) throws ParseException {
		boolean flag = false;
		boolean bzw = false;
		String nowtime = DtUtils.getNowTime();
		String next_send_time = "";
		if ("".equals(nextsendtime)) {// 下次发送时间为空
			this.setNextsendtime(nowtime);
			flag = true;
		} else {
			next_send_time = DtUtils.getTimeByGran(nextsendtime, amount, gran, "yyyy-MM-dd HH:mm:ss");
			bzw = nowtime.compareTo(next_send_time) > 0 ? true : false;
			if (bzw) {//下次发送时间错误［过小］，纠正时间，发送标识为true
				this.setNextsendtime(nowtime);
				if(getIsAvailableTimes(nowtime)){
					flag = true;
				}else{
					logger.info("==22:00到03:00暂停发送核心==");
				}
			} else {//判断是否到达发送时间
				if(nowtime.compareTo(nextsendtime) >= 0){
					if(getIsAvailableTimes(nowtime)){
						flag = true;
					}else{
						logger.info("==22:00到03:00暂停发送核心==");
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * 是否是允许时间段<br>
	 * 核心希望晚上22:00-03:00不要推送数据
	 * @return
	 * 
	 * @author yinxiong
	 * @date 2016年12月6日 下午4:38:20
	 */
	private boolean getIsAvailableTimes(String time){
		//获取小时（HH）
		String hours = time.substring(11, 13);
		//获取禁用时间段（小时HH）
		String x = ServerEnvironment.getStringValue(CgbKeys.MD_CORE_FORBIDDEN_SEND_TIMES);
		if(x.contains(hours)){
			return false;
		}else{
			return true;
		}
	}
	
	private boolean getIsAvailableTimesByCredit(){
		//获取小时（HH）
		String hours = DtUtils.getNowTime().substring(11, 13);
		//获取禁用时间段（小时HH）
		String x = ServerEnvironment.getStringValue(CgbKeys.MD_CREDIT_FORBIDDEN_SEND_TIMES);
		if(x.contains(hours)){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return this;
	}
	
}

package com.citic.server.cgb.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 广发银行日期任务表 <br>
 * 记录上次处理日期或者下次待处理日期该类型任务 <br>
 * 1.使用10位日期时：初始化task_date和status，令status=0，task_date=将要执行的日期 <br>
 * 检测到确认文件后，修改status=1，然后进行业务处理，处理成功则更新task_date为下一个日期并且status=0 <br>
 * 2.使用19位日期时，初始化task_date_time = 将要查询的日期，业务执行结束后，更新查询截止日期
 * 
 * @author yinxiong
 * @date 2017年6月1日 下午7:24:24
 */
@Data
public class Bb11_data_task implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5381913480446177320L;
	
	//任务编码：6位，TK+4位编号
	private String task_id = "";
	//yyyy-MM-dd格式的10位日期
	private String task_date = "";
	//yyyy-MM-dd HH:mm:ss格式的19位时间
	private String task_date_time = "";
	//任务状态 0：待处理 1：处理中
	private String task_status = "";
	//备注
	private String remark = "";
	
	//===============任务类型===============
	//  TK0001：ODS抽取反恐怖名单
	//  TK0002：轮询查询在线交易平台的预警信息
	//  
}

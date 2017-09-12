package com.citic.server.cgb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.cgb.domain.AmlWarnDto;
import com.citic.server.cgb.domain.Bb11_data_task;

/**
 * 
 * @author yinxiong
 *
 */
public interface Bb11_aml_warn_logMapper {
	
	/**
	 * 获取待发送预警名单信息
	 * @param amlWarnDto
	 * @return
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午5:06:56
	 */
	public ArrayList<AmlWarnDto> selectBb11_aml_warn_logListByNine(String send_flag);
	
	/**
	 * 更新预警信息为中间状态
	 * @param send_flag
	 * 
	 * @author yinxiong
	 * @date 2017年5月18日 下午5:12:41
	 */
	public void updateBb11_aml_warn_logSendFlagToNine(String send_flag);
	
	/**
	 * 更新预警信息为已发送状态（单条更新）
	 * @param send_flag
	 * 
	 * @author yinxiong
	 * @date 2017年5月22日 下午7:27:47
	 */
	public void updateBb11_aml_warn_logSendFlagToOne(AmlWarnDto amlWarnDto);
	
	/**
	 * 批量插入预警信息
	 * @param list
	 * 
	 * @author yinxiong
	 * @date 2017年5月19日 下午5:57:40
	 */
	public void batchInsertBb11_aml_warn_log(List<AmlWarnDto> list);

	/**
	 * 将待发送的名单数据更新为中间态
	 * rows 记录数限制
	 * 
	 * @author yinxiong
	 * @date 2017年5月20日 下午1:50:20
	 */
	public void updateBb11_aml_listSendFlagToNine(String rows);
	
	/**
	 * 将中间态数据修改为已发送
	 * 
	 * @author yinxiong
	 * @date 2017年5月20日 下午2:07:42
	 */
	public void updateBb11_aml_listSendFlagToOne(String send_time);
	/**
	 * 查询出中间态的数据list
	 * @return
	 * 
	 * @author yinxiong
	 * @date 2017年5月20日 下午2:05:10
	 */
	public ArrayList<AmlWarnDto> selectBb11_aml_listListByNine(String rows);
	
	/**
	 * 批量插入反恐怖名单信息
	 * @param list
	 * 
	 * @author yinxiong
	 * @date 2017年5月26日 下午9:34:57
	 */
	public void batchInsertBb11_aml_list(List<AmlWarnDto> list);
	
	/**
	 * 任务查询
	 * @param task
	 * @return
	 * 
	 * @author yinxiong
	 * @date 2017年6月1日 下午7:52:05
	 */
	public Bb11_data_task selectBb11_data_task(Bb11_data_task task); 
	
	/**
	 * 根据ID更新任务状态
	 * @param task
	 * 
	 * @author yinxiong
	 * @date 2017年6月1日 下午7:52:30
	 */
	public void updateBb11_data_taskByID(Bb11_data_task task);
	
	/**
	 * 根据任务ID更新任务时间
	 * @param task
	 * 
	 * @author yinxiong
	 * @date 2017年6月1日 下午7:53:03
	 */
	public void updateBb11_data_taskTimeByID(Bb11_data_task task);
	
	/**
	 * 统计待发送的名单总数
	 * @return
	 * 
	 * @author yinxiong
	 * @date 2017年6月14日 下午9:43:11
	 */
	public int selectBb11_aml_listCountByFlag();
	
	/**
	 * 修改中间态数据为写入态（9--》8）
	 * 
	 * 
	 * @author yinxiong
	 * @date 2017年6月14日 下午10:40:11
	 */
	public void updateBb11_aml_listSendFlagToEight();
	
}

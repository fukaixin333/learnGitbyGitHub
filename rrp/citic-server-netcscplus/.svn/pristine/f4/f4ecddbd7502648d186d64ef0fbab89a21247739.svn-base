package com.citic.server.service.task;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.task.taskBo.AML503Bo;
import com.citic.server.utils.DbFuncUtils;
/**
 * 与模型匹配生成案件
 * 
 * @author liangchunyu
 * @version 1.0
 */
public class TK_AML503  extends BaseTask{

	private JdbcTemplate jdbcTemplate;
	public TK_AML503(ApplicationContext _ac, MC00_task_fact mC00_task_fact) {
		super(_ac, mC00_task_fact);
		jdbcTemplate = (JdbcTemplate) this.getAc().getBean("jdbcTemplate");
	}

	@Override
	public boolean calTask() throws Exception {
		/***
		 * 初始化函数
		 */
		AML503Bo bo = new AML503Bo();
		ArrayList initSqlList = new ArrayList();
		bo.init(initSqlList);
		
		this.syncToDatabase(initSqlList);
	
		/**
		 * 清理临时表
		 */
//		ArrayList clearSqlList = new ArrayList();
//		bo.clear(clearSqlList);
//		this.syncToDatabase(clearSqlList);
		DbFuncUtils dbfunc = new DbFuncUtils();
		String sql_1 = dbfunc.getTruncateSql("BH13_EVENT_SCORE");
		jdbcTemplate.update(sql_1);
		sql_1 = dbfunc.getTruncateSql("BH13_LINK_SCORE");
		jdbcTemplate.update(sql_1);
		sql_1 = dbfunc.getTruncateSql("BH13_EVENT_ALERT");
		jdbcTemplate.update(sql_1);
		 
		sql_1 = dbfunc.getTruncateSql("BH13_LINK_ALERT");
		jdbcTemplate.update(sql_1);
		 
		sql_1 = dbfunc.getTruncateSql("BH13_MODEL_ALERT");
		jdbcTemplate.update(sql_1);
		
		
		
		/**
		 * 逻辑计算
		 */
		//第一步：取得预警信息
		ArrayList sqlList = new ArrayList();
		//if(this.getMC00_task_fact().getSubtaskid().equalsIgnoreCase("net")){
//			bo.cal01_getEventScore_net(sqlList);
		//}
		//else if(this.getMC00_task_fact().getSubtaskid().equalsIgnoreCase("event")){
			bo.cal01_getEventScore_event(sqlList);
		//}
		//第二步:取得环节预警
		bo.cal02_getLinkScore(sqlList);
		bo.cal02_getEventAlert(sqlList);
		bo.cal03_getLinkAlert(sqlList);
		//第三步：取得模型预警
		bo.cal04_getModelAlert(sqlList);
		this.syncToDatabase(sqlList);

		return true;
	}

	public static  void main(String[] args){
		
		//TK_AML503 TK_AML503 = new TK_AML503();
		
	}
	
}

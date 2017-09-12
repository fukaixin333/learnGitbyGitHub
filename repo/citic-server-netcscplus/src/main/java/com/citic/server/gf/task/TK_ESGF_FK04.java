package com.citic.server.gf.task;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.gf.task.taskBo.PacketBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;


/**
 *生成控制数据包
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESGF_FK04 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_FK04.class);
	private JdbcTemplate jdbcTemplate = null;
	

	public TK_ESGF_FK04(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
	}

	public boolean calTask() throws Exception {
		boolean isSucc = true;
		  PacketBo packetBo=new PacketBo(this.getAc());

		 try {
	
			  MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
			  String newpacketkey=mc21_task_fact.getBdhm(); // 数据包编码_请求单号
				ArrayList<String>   sqlList=new  	ArrayList<String>();
				  //1.删除数据包
				packetBo.delBR32_packet(newpacketkey, sqlList);
				//删除回退的xml
				packetBo.delMsg(newpacketkey, sqlList);				
		     //2.生成回退的xml
				String bdhm=newpacketkey.substring(newpacketkey.lastIndexOf("_")+1);
				 sqlList=packetBo.dealHTMsg_kz(bdhm, newpacketkey,sqlList);
				 isSucc = this.syncToDatabase(sqlList);		       
			    	sqlList.clear(); 
			 //3.生成数据包
			packetBo.makePacket(newpacketkey, jdbcTemplate);
	
			 
		} catch (Exception e) {
			
			
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
		return isSucc;
	}

	 

}
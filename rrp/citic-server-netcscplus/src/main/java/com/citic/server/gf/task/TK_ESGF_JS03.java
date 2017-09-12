package com.citic.server.gf.task;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.gf.domain.Br32_packet;
import com.citic.server.gf.task.taskBo.PacketBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;


/**
 *解析失败反馈报文
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESGF_JS03 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_JS03.class);
	private JdbcTemplate jdbcTemplate = null;
	

	public TK_ESGF_JS03(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
	}

	public boolean calTask() throws Exception {
		boolean isSucc = true;
		  PacketBo packetBo=new PacketBo(this.getAc());
		  String packetkey="";
		 try {

			  MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
			    packetkey=mc21_task_fact.getBdhm(); //数据包编码
				ArrayList<String>   sqlList=new  	ArrayList<String>();
			  //1.查询数据包
			   Br32_packet  br32_packet=packetBo.getPacket(packetkey);
		  
			 //3.解析数据包
				 sqlList=packetBo.dealPacket(br32_packet, sqlList);
				 isSucc = this.syncToDatabase(sqlList);		       
			    	sqlList.clear(); 
			 
		} catch (Exception e) {
			   //2.删除回执表改数据包下的数据
			packetBo.delBR32_receipt(packetkey, jdbcTemplate);
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
		return isSucc;
	}

	 

}
package com.citic.server.service;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_data_time;
import com.citic.server.mapper.MC00_data_timeMapper;
/**
 * @author hubq
 *
 */

@Service
public class DatatimeService {
	private static final Logger logger = LoggerFactory.getLogger(DatatimeService.class);

	@Autowired
    private ApplicationContext ac;
	
	@Autowired
	MC00_data_timeMapper mC00_data_timeMapper ;
	
	public DatatimeService(){
		
	}
	
	/**
	 * 维护待扫描的数据日期
	 * 从数据表中取得待扫描的数据日期，如果不存在或者缺少，就自动补齐；
	 * @return
	 */
	public ArrayList maintainDatatimeForScan() throws Exception{
		
		int standLength = 3;
		int scanLength = 0;
		
		try{
			ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
			
			if(applicationCFG.getServer_scan_ds_datatime_length()!=0){
				standLength = applicationCFG.getServer_scan_ds_datatime_length();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//
		ArrayList dtList = mC00_data_timeMapper.getDatatimeForScan();
		String maxDt = mC00_data_timeMapper.getDatatimeForMaxDt();
		
		//
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime();
		DateTime currDatatime = new DateTime(); //当前计算数据日期最大值；
		
		if(maxDt==null || maxDt.equals("")){
			currDatatime = dt.minusDays(1);//默认设置昨天的任务
			
		}else{
			//第一个是最大的数据日期 + 1
			currDatatime = ( new DateTime(maxDt) ).plusDays(1);
			
		}
		
		scanLength = dtList.size();
		
		//
		if(scanLength < standLength){
			
			int loopLenght = standLength-scanLength;
			while(loopLenght>0){
				
				/**
				 * 如果数据日期大于系统日期，就不再设置（最多判断到当日）；
				 */
				if(   new DateTime( fmt.print( dt ) ).isBefore(currDatatime) ){
					break;
				}
				
				MC00_data_time mC00_data_time = new MC00_data_time();
				mC00_data_time.setDatatime( fmt.print( currDatatime ) );
				mC00_data_time.setFlag("0");//等待扫描
				
				mC00_data_timeMapper.insertMC00_data_time(mC00_data_time);
				
				currDatatime = currDatatime.plusDays(1);//日期+1；
				loopLenght--;
			}
			
			dtList = mC00_data_timeMapper.getDatatimeForScan();
			
		}
		
		return dtList;
	}

}

package com.citic.server.service.triger;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.service.UtilsService;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_S2 extends BaseTrigerCondition {

	private static final Logger logger = LoggerFactory.getLogger(TrigerCondition_S2.class);
	
	public TrigerCondition_S2(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}
	
	/**
	 * S2：计算频度，PARAM1=1，2，3，4，5，6，7；在指定频度时可以执行
	 * 参数说明：
	 * 参数1：频度分为：日，周，旬，月，季，半年，年 ，频度编码，逗号分隔，可多位
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		
		if(this.getMC00_triger_sub().getParam1()==null || this.getMC00_triger_sub().getParam1().trim().equals("")){
			throw new Exception("参数1：频度参数没有设置！");
		}
		
		String freqs = this.getMC00_triger_sub().getParam1();
		String[] _freqs;
		ArrayList freqList = new ArrayList();
		if(freqs.indexOf(",")>-1){
			_freqs = freqs.split(",");
		}else{
			_freqs = new String[1];
			_freqs[0] = freqs;
		}
		
		UtilsService ut = new UtilsService();
		String freqs_rst = "";
		for(int i=0;i<_freqs.length;i++){
			String freq = _freqs[i];
			
			if(!this.validFreq(freq)){
				logger.error("计算频度参数设置错误："+freq);
				throw new Exception("计算频度参数设置错误："+freq);
			}
			
			boolean isFreq = ut.isFreqEnd(this.getDatatime(), freq);
			
			if(!isFreq){//不满足频度计算要求
				continue;
			}
			freqs_rst = freqs_rst + "," +freq;//满足的频度，就是当日数据计算可以执行的频度
		}
		if(freqs_rst.startsWith(",")){
			freqs_rst = freqs_rst.substring(1);
		}
		
		MC00_triger_fact mC00_triger_fact= new MC00_triger_fact();
		mC00_triger_fact.setTrigerid( this.getMC00_triger_sub().getTrigerid());
		mC00_triger_fact.setTrigercondid(this.getMC00_triger_sub().getTrigercondid());
		mC00_triger_fact.setDatatime(this.getDatatime());	
		mC00_triger_fact.setFreq(freqs_rst);
		mC00_triger_fact.setDsid("0");
		
		if(freqs_rst.length()>0)//有频度满足
			tfList.add( mC00_triger_fact );
		
		return tfList;
	}
	
}
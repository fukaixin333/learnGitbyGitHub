/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年5月21日 下午4:37:27
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.citic.server.domain.MP01_func_param;
import com.citic.server.domain.Mp01_busiparm_val;
import com.citic.server.mapper.MP01_func_paramMapper;
import com.citic.server.mapper.Mp01_busiparm_valMapper;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Maps;

/**
 * @author gaojianxin 参数服务，获取后台指标或规则定义的参数值，用于SQL替换
 */
@Component
public class ParameterService {
	private final Logger logger = LoggerFactory.getLogger(ParameterService.class);

	@Autowired
	private MP01_func_paramMapper mp01_func_paramMapper;

	@Autowired
	private SqlParseService sqlParseService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Mp01_busiparm_valMapper mp01_busiparm_valMapper;

	/**
	 * 获取默认参数值
	 * 
	 * @param statisticdate
	 * @return
	 * @throws ParseException
	 */
	public Map<String, String> getDefaultVal(String statisticdate) throws ParseException {
		Map<String, String> parmValMap = Maps.newHashMap();

		parmValMap.put("data_date", statisticdate);
		parmValMap.put("curr_date", DtUtils.getNowDate());

		// 数据日期的月初 data_date_mon_begin
		parmValMap.put("data_date_mon_begin", DtUtils.getBeginDt(statisticdate, DtUtils.MONTH));
		// 数据日期的月末 data_date_mon_end
		parmValMap.put("data_date_mon_end", DtUtils.getEndDt(statisticdate, DtUtils.MONTH));
		parmValMap.put("data_date_lastmon_end", DtUtils.getPreDt(statisticdate, DtUtils.MONTH));
		// 数据日期的季初 data_date_sea_begin
		parmValMap.put("data_date_sea_begin", DtUtils.getBeginDt(statisticdate, DtUtils.SEASON));
		// 数据日期的季末 data_date_sea_end
		parmValMap.put("data_date_sea_end", DtUtils.getEndDt(statisticdate, DtUtils.SEASON));
		// 数据日期的半年初 data_date_halfyear_begin
		parmValMap.put("data_date_halfyear_begin", DtUtils.getBeginDt(statisticdate, DtUtils.HALFYEAR));
		// 数据日期的半年末 data_date_halfyear_end
		parmValMap.put("data_date_halfyear_end", DtUtils.getEndDt(statisticdate, DtUtils.HALFYEAR));
		// 数据日期的年初 data_date_year_begin
		parmValMap.put("data_date_year_begin", DtUtils.getBeginDt(statisticdate, DtUtils.YEAR));
		// 数据日期的年末 data_date_year_end
		parmValMap.put("data_date_year_end", DtUtils.getEndDt(statisticdate, DtUtils.YEAR));

		// 数据日期的11个月前的月末日期，
		parmValMap.put("data_date_11mon_end", DtUtils.getEndDt(DtUtils.add(statisticdate, DtUtils.MONTH, -11), DtUtils.MONTH));
		//5个月前的月末日期
		parmValMap.put("data_date_6mon_before", DtUtils.getEndDt(DtUtils.add(statisticdate, DtUtils.MONTH, -5), DtUtils.MONTH));
		
		//当前时间一小时前的时间
		parmValMap.put("citic_pre_1hour", DtUtils.addHour(DtUtils.getNowDate(),-1));

		return parmValMap;
	}

	/**
	 * 返回公共参数表中定义的参数值
	 * 
	 * @param statisticdate
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFuncParmVal(String statisticdate) throws Exception {
		Map<String, String> parmValMap = Maps.newHashMap();
		// 1、获取所有公共参数
		List<MP01_func_param> funcParmList = mp01_func_paramMapper.getMP01_func_paramList();
		String baseDateStr = "";
		String qrySql = "";
		String dateValStr = "";

		for (MP01_func_param mp01_func_param : funcParmList) {
			if (StringUtils.equals(mp01_func_param.getBasedt(), "2")) { // 以系统日期为基准
				baseDateStr = DtUtils.getNowDate();
			} else {
				baseDateStr = statisticdate;
			}
			// 正常参数
			if (mp01_func_param.getParmtype().equals("1")) {
				parmValMap.put(mp01_func_param.getParmvalkey(), mp01_func_param.getParamval());
			} else if (mp01_func_param.getParmtype().equals("2")) { // 2 自然日
				parmValMap.put(mp01_func_param.getParmvalkey(), DtUtils.add(baseDateStr, DtUtils.DAY, NumberUtils.toInt(mp01_func_param.getParamval())));
			} else if (mp01_func_param.getParmtype().equals("3")) { // 3 对公节假日
				qrySql = sqlParseService.getCalHolidaySql(baseDateStr, "C", NumberUtils.toInt(mp01_func_param.getParamval()));
				dateValStr = jdbcTemplate.queryForObject(qrySql, String.class);
				if (StringUtils.isEmpty(dateValStr)) {
					logger.error("对公节假日参数设置错误！！！");
					throw new Exception("对公节假日参数设置错误！！！");
				}
				parmValMap.put(mp01_func_param.getParmvalkey(), dateValStr);
			} else if (mp01_func_param.getParmtype().equals("4")) { // 4 对私节假日
				qrySql = sqlParseService.getCalHolidaySql(baseDateStr, "I", NumberUtils.toInt(mp01_func_param.getParamval()));
				dateValStr = jdbcTemplate.queryForObject(qrySql, String.class);
				if (StringUtils.isEmpty(dateValStr)) {
					logger.error("对私节假日参数设置错误！！！");
					throw new Exception("对私节假日参数设置错误！！！");
				}
				parmValMap.put(mp01_func_param.getParmvalkey(), dateValStr);
			}
		}
		return parmValMap;
	}

	/**
	 * 获取指定规则或事件的参数值
	 * 
	 * @param busikey
	 * @return
	 */
	public Map<String, String> getBusiParmVal(String busikey) {
		Map<String, String> parmValMap = Maps.newHashMap();
		String tmpParmvalkey = "";
		StringBuffer tmpSb = new StringBuffer();

		ArrayList<Mp01_busiparm_val> valList = mp01_busiparm_valMapper.getMp01_busiparm_valList(busikey);

		if(valList == null || valList.size()==0){
			return parmValMap;
		}
		
		for (Mp01_busiparm_val mp01_busiparm_val : valList) {
			if (!StringUtils.equals(tmpParmvalkey, mp01_busiparm_val.getParmvalkey())) {
				if (!tmpParmvalkey.equals("")) {
					parmValMap.put(tmpParmvalkey, tmpSb.substring(1).toString());
				}
				tmpParmvalkey = mp01_busiparm_val.getParmvalkey();
				tmpSb = new StringBuffer();
			}
			tmpSb.append(",").append(mp01_busiparm_val.getParam1());

		}
		parmValMap.put(tmpParmvalkey, tmpSb.substring(1).toString());

		return parmValMap;
	}
	
	
}

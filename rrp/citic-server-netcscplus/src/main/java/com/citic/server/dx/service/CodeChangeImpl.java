/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description:
 * =========================================================
 */
package com.citic.server.dx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.dx.domain.Br24_bas_dto;
import com.citic.server.dx.domain.Br24_code_change;
import com.citic.server.net.mapper.Br24_code_changeMapper;
import com.citic.server.service.domain.Mp02_organ;
import com.citic.server.utils.StrUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 电信诈骗码表转换接口
 * 
 * @author gaojx
 */
@Service("codeChangeImpl")
public class CodeChangeImpl implements CodeChange {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private HashMap<String, HashMap<String, String>> codeMap = Maps.newHashMap();
	private HashMap<String, Set<String>> colMap = Maps.newHashMap();
	private HashMap<String, String> organMap = Maps.newHashMap();
	private Boolean isNotInit = true;
	
	@Autowired
	private Br24_code_changeMapper br24_code_changeMapper;
	
	public void loadCode() {
		HashMap<String, String> valMap = null;
		Set<String> cols = null;
		
		ArrayList<Br24_code_change> br24_code_changeList = br24_code_changeMapper.getBr24_code_changeList();
		for (Br24_code_change br24_code_change : br24_code_changeList) {
			String key = br24_code_change.getDestab() + "_" + br24_code_change.getQryds();
			if (codeMap.containsKey(key)) {
				valMap = codeMap.get(key);
			} else {
				valMap = Maps.newHashMap();
				codeMap.put(key, valMap);
			}
			valMap.put(br24_code_change.getColcode() + "_" + StrUtils.null2String(br24_code_change.getSourceval()), br24_code_change.getTargetval());
			
			if (colMap.containsKey(key)) {
				cols = colMap.get(key);
			} else {
				cols = Sets.newHashSet();
				colMap.put(key, cols);
			}
			cols.add(br24_code_change.getColcode());
		}
		ArrayList<Mp02_organ> mpo2_organList = br24_code_changeMapper.getMp02_organList();
		for (Mp02_organ mp02_organ : mpo2_organList) {
			String organkey = mp02_organ.getOrgankey();
			String organname = mp02_organ.getOrganname();
			organMap.put(organkey, organname);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.citic.server.dx.service.CodeChange#chgCode(com.citic.server.dx.domain
	 * .Br24_bas_dto, java.lang.String, java.lang.String)
	 */
	@Override
	public Br24_bas_dto chgCode(Br24_bas_dto br24_bas_dto, String desTabName, String query_datasource) {
		if (isNotInit) {
			this.loadCode();
			isNotInit = false;
		}
		
		HashMap<String, String> valMap = codeMap.get(desTabName + "_" + query_datasource);
		Set<String> cols = colMap.get(desTabName + "_" + query_datasource);
		
		if (cols == null || cols.size() == 0) {
			return br24_bas_dto;
		}
		
		for (String colCode : cols) {
			try {
				String colvalue = BeanUtils.getProperty(br24_bas_dto, colCode);
				if (StringUtils.isBlank(colvalue)) {
					continue;
				}
				String key = colCode + "_" + colvalue;
				String val = valMap.get(key);
				if (StringUtils.isNoneEmpty(val)) {
					BeanUtils.setProperty(br24_bas_dto, colCode, val);
				} else {
					val = organMap.get(colvalue);
					if (StringUtils.isNoneEmpty(val)) {
						String tergetcolCode = valMap.get(colCode + "_");
						BeanUtils.setProperty(br24_bas_dto, tergetcolCode, val);
					}
				}
				
			} catch (Exception e) {
				logger.error("码表转换错误" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return br24_bas_dto;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.citic.server.dx.service.CodeChange#chgCode(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<? extends Br24_bas_dto> chgCode(List<? extends Br24_bas_dto> dtoList, String desTabName, String query_datasource) {
		if (isNotInit) {
			this.loadCode();
			isNotInit = false;
		}
		
		HashMap<String, String> valMap = codeMap.get(desTabName + "_" + query_datasource);
		Set<String> cols = colMap.get(desTabName + "_" + query_datasource);
		
		if (cols == null || cols.size() == 0 || dtoList == null || dtoList.size() == 0) {
			return dtoList;
		}
		
		for (Br24_bas_dto br24_bas_dto : dtoList) {
			for (String colCode : cols) {
				try {
					String colvalue = BeanUtils.getProperty(br24_bas_dto, colCode);
					if (StringUtils.isBlank(colvalue)) {
						continue;
					}
					String key = colCode + "_" + colvalue;
					String val = valMap.get(key);
					if (StringUtils.isNoneEmpty(val)) {
						BeanUtils.setProperty(br24_bas_dto, colCode, val);
					} else {
						val = organMap.get(colvalue);
						if (StringUtils.isNoneEmpty(val)) {
							String tergetcolCode = valMap.get(colCode + "_");
							BeanUtils.setProperty(br24_bas_dto, tergetcolCode, val);
						}
					}
					
				} catch (Exception e) {
					logger.error("码表转换错误" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return dtoList;
	}
	
}

package com.citic.server.inner.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.inner.domain.AnswerCode;
import com.citic.server.net.mapper.BB13_Err_CodeMapper;
import com.citic.server.runtime.DataOperateException;

/**
 * 应答码转换
 * 
 * @author liuxuanfei
 * @date 2016年5月5日 下午5:59:01
 */
@Service("answerCodeService")
public class AnswerCodeService {
	private static final Logger logger = LoggerFactory.getLogger(AnswerCodeService.class);
	
	private static HashMap<String, AnswerCode> cacheMap = new HashMap<String, AnswerCode>();
	
	@Autowired
	private BB13_Err_CodeMapper mapper;
	
	public DataOperateException convertDataOperateException(DataOperateException ex, String taskType) {
		String scode = ex.getCode();
		if (StringUtils.isNotBlank(scode)) {
			AnswerCode answerCode = getAnswerCode(scode, taskType);
			if (answerCode != null) {
				ex.setCode(answerCode.getTcode());
				ex.setDescr(answerCode.getDescr());
			} else {
				// throw new IllegalArgumentException("Unknown AnswerCode SCODE = '" + scode + "', TASKTYPE = '" + taskType + "'.");
				ex.setCode("9999"); // 9999-其他未定义错误
				ex.setDescr(ex.getMessage());
			}
		}
		return ex;
	}
	
	public AnswerCode getAnswerCode(String scode, String type) {
		if (StringUtils.isNotBlank(scode) && StringUtils.isNotBlank(type)) {
			// 先从缓存取
			AnswerCode answerCode = getCacheMap().get(scode + "_" + type);
			// 如果缓存中没有，再从数据库查询
			if (answerCode == null) {
				answerCode = mapper.selectErrCodeBySCode(scode, type);
			}
			
			return answerCode;
		}
		return null;
	}
	
	public String getTCode(String scode, String type) {
		AnswerCode answerCode = getAnswerCode(scode, type);
		if (answerCode != null) {
			return answerCode.getTcode();
		}
		return null;
	}
	
	private HashMap<String, AnswerCode> getCacheMap() {
		if (cacheMap.isEmpty()) {
			synchronized (cacheMap) {
				if (cacheMap.isEmpty()) {
					logger.info("初始化[银行错误码|监管应答码]缓存……");
					cacheMap = new HashMap<String, AnswerCode>();
					List<AnswerCode> answerCodeList = mapper.selectAllErrCode();
					for (AnswerCode answerCode : answerCodeList) {
						String scode = answerCode.getScode();
						String type = answerCode.getType();
						if (StringUtils.isNotBlank(scode) && StringUtils.isNotBlank(type)) {
							cacheMap.put(scode + "_" + type, answerCode);
						}
					}
				}
			}
		}
		return cacheMap;
	}
}

package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;

/**
 * Artery响应结果统一报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月19日 下午4:20:45
 */
@Data
public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = 5183107077760502780L;
	
	/** 请求目标简称 */
	private String to = "";
	
	/** 错误编码（输出） */
	private String code = "";
	
	/** 错误描述信息 */
	private String description = "";
	
	// ==========================================================================================
	//                     Paging Information
	// ==========================================================================================
	/** 总页数 */
	private Integer totalPage = 0;
	
	/** 总记录条数 */
	private Integer totalNum = 0;
	
	/** 当前页号 */
	private Integer currPage = 0;
	
	/** 当前页行数 */
	private Integer currPageRow = 0;
	
	/** 当前会计日符合条件记录数 */
	private Integer todRecNum = 0;
	
	/** 是否末页标志（Y-末页；否则为N） */
	private String lastPage = "N";
	
	/** 是否有记录（Y-有记录；N-无记录） */
	private String resFlag = "Y";
	
	// ==========================================================================================
	//                     Helper Behavior
	// ==========================================================================================
	public boolean isLastPage() {
		if ("Y".equals(lastPage) || "y".equals(lastPage)) {
			return true;
		}
		return false;
	}
	
	public boolean hasResultSet() {
		if ("N".equals(resFlag) || "n".equals(resFlag)) {
			return false;
		}
		return true;
	}
	
	public boolean hasException() {
		if (StringUtils.isBlank(code) || ServerEnvironment.getStringValue(Keys.ARTERY_CODE_OK).equals(code)) {
			return false;
		}
		return true;
	}
	
	public boolean isArteryException() {
		if (ServerEnvironment.getStringValue(Keys.ARTERY_CODE_ERROR).equals(code)) {
			return true;
		}
		return false;
	}
}

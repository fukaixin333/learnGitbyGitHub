package com.citic.server.gf.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 查询/控制结果反馈以及回退请求的处理结果信息列表
 * 
 * <pre>
 * <strong>注意</strong>
 * 根据《人民法院、银行业金融机构网络执行查控工作技术规范》所示：
 * 
 * 1、查询和控制结果反馈的处理结果使用<code> cxjglist </code>作为标签名，请求回退的处理结果以<code> htjglist </code>作为标签名，
 * 所以，此类统一使用<code> jgList </code>字段名，binding文件兼容两种情况。
 * 
 * 2、除回退请求的处理结果使用<code> errMsg </code>标注错误信息描述之外，其它结果反馈均使用<code> errorMsg </code>标注错误信息描述，
 * 所以，此类统一使用<code> errorMsg </code>字段名，binding文件兼容两种情况。
 * </pre>
 * 
 * @author Liu Xuanfei
 * @date 2016年3月9日 下午6:48:36
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 648003619537793604L;
	
	/** 错误信息描述 */
	private String errorMsg;
	private String errMsg;
	
	/** 查询/控制结果反馈以及回退请求的处理结果信息列表 */
	private List<Result_Jg> htJgList;
	private List<Result_Jg> cxJgList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	public String getErrMsg() {
		return this.errMsg;
	}
	
	public String getRealError() {
		if (errorMsg == null || errorMsg.length() == 0) {
			return errMsg;
		}
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public void setErrMsg(String errorMsg) {
		this.errMsg = errorMsg;
	}
	
	public List<Result_Jg> getRealJgList() {
		if (htJgList == null || htJgList.size() == 0) {
			return cxJgList;
		}
		return htJgList;
	}
	
	public List<Result_Jg> getHtJgList() {
		return this.htJgList;
	}
	
	public List<Result_Jg> getCxJgList() {
		return this.cxJgList;
	}
	
	public void setHtJgList(List<Result_Jg> jgList) {
		this.htJgList = jgList;
	}
	
	public void setCxJgList(List<Result_Jg> jgList) {
		this.cxJgList = jgList;
	}
}

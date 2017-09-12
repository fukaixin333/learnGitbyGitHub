package com.citic.server.cbrc.domain;

import java.io.Serializable;
import java.util.List;

import com.citic.server.cbrc.domain.response.CBRC_LiInfosResponse;

/**
 * @author Liu Xuanfei
 * @date 2016年9月14日 下午8:09:17
 */
public class CBRC_Response implements Serializable {
	private static final long serialVersionUID = 1120606058596165649L;
	
	/** 基础数据项 */
	private CBRC_BasicInfo basicInfo;
	
	/** 查询人数据项 */
	private CBRC_QueryPerson queryPerson;
	
	/** 法律文书信息 */
	private List<CBRC_LiInfosResponse> liInfos;
	
	public CBRC_BasicInfo getBasicInfo() {
		return basicInfo;
	}
	
	public void setBasicInfo(CBRC_BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	
	public CBRC_QueryPerson getQueryPerson() {
		return queryPerson;
	}
	
	public void setQueryPerson(CBRC_QueryPerson queryPerson) {
		this.queryPerson = queryPerson;
	}
	
	public List<CBRC_LiInfosResponse> getLiInfos() {
		return liInfos;
	}
	
	public void setLiInfos(List<CBRC_LiInfosResponse> liInfos) {
		this.liInfos = liInfos;
	}
}

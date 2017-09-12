package com.citic.server.jsga.domain;

import java.io.Serializable;
import java.util.List;

import com.citic.server.jsga.domain.response.JSGA_LiInfosResponse;

/**
 * @author Liu Xuanfei
 * @date 2016年9月14日 下午8:09:17
 */
public class JSGA_Response implements Serializable {
	private static final long serialVersionUID = 112060585961L;
	
	/** 基础数据项 */
	private JSGA_BasicInfo basicInfo;
	
	/** 查询人数据项 */
	private JSGA_QueryPerson queryPerson;
	
	/** 法律文书信息 */
	private List<JSGA_LiInfosResponse> liInfos;
	
	public JSGA_BasicInfo getBasicInfo() {
		return basicInfo;
	}
	
	public void setBasicInfo(JSGA_BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	
	public JSGA_QueryPerson getQueryPerson() {
		return queryPerson;
	}
	
	public void setQueryPerson(JSGA_QueryPerson queryPerson) {
		this.queryPerson = queryPerson;
	}
	
	public List<JSGA_LiInfosResponse> getLiInfos() {
		return liInfos;
	}
	
	public void setLiInfos(List<JSGA_LiInfosResponse> liInfos) {
		this.liInfos = liInfos;
	}
}

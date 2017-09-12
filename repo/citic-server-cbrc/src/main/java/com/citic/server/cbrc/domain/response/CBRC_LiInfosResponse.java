package com.citic.server.cbrc.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 文书信息项
 * <ul>
 * <li>“四川省公安厅”包含<em>[任务流水号]</em>和<em>[文书编号]</em>等字段，其它来源不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午3:06:21
 */
@Data
public class CBRC_LiInfosResponse implements Serializable {
	private static final long serialVersionUID = 2051164073577276571L;
	
	/** 文件名称 */
	private String wjmc;
	
	/** 文件类型 */
	private String wjlx;
	
	/** 文书类型 */
	private String wslx;
	
	/** 文书内容 */
	private String wsnr;
	/**  真正文书内容 （江苏）*/
	private byte[] realwsnr;
	
}

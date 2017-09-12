package com.citic.server.shpsb.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author liuxuanfei
 * @date 2016年12月27日 上午10:46:36
 */
@Data
public class ShpsbReturnReceipt implements Serializable {
	private static final long serialVersionUID = -8187670335453145464L;
	
	private String status;
	private String fileName;
	private String relativeFilePath;
	
	public ShpsbReturnReceipt() {
	}
	
	public ShpsbReturnReceipt(String fileName, String relativeFilePath, String status) {
		this.status = status;
		this.fileName = fileName;
		this.relativeFilePath = relativeFilePath;
	}
}

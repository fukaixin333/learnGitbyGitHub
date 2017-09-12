package com.citic.server.gdjg.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询类--办案人信息
 * 
 * @author wangbo
 * @date 2017年5月23日 下午9:07:14
 */
@Data
public class Gdjg_Response_PolicemanCx implements Serializable {
	private static final long serialVersionUID = 3890719301129484460L;
	/** 办案人姓名 */
	private String exename;
	/** 警察证号 */
	private String policecert;
	/** 联系电话 */
	private String policetel;
}

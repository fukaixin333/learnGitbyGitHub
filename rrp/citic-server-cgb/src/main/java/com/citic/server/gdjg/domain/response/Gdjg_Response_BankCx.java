package com.citic.server.gdjg.domain.response;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

@Data
public class Gdjg_Response_BankCx  implements Serializable{
	private static final long serialVersionUID = -133901785466431816L;

	
	/** 开户行代码 */
	private String bankcode;
	
	/** 开户行名称 */
	private String bankname;
	
	/** 账户 */
	private List<Gdjg_Response_AccCx>  accs;
}

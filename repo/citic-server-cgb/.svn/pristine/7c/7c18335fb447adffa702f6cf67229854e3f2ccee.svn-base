package com.citic.server.gdjg.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 查询类--开户行
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_BankCx  implements Serializable{
	private static final long serialVersionUID = -771781953373687034L;
	/** 开户行代码 */
	private String bankcode;
	
	/** 开户行名称 */
	private String bankname;
	
	/** 账户 */
	private List<Gdjg_Request_AccCx> accs;
	
	/**金融产品*/
	private List<Gdjg_Request_FinancialProductsCx> financialPros;
	
	/** 保险箱信息 */
	private List<Gdjg_Request_SafeCx> insurances;
}

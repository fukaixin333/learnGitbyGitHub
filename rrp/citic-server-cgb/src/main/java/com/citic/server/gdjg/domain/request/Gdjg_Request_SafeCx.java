package com.citic.server.gdjg.domain.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询类--保险箱信息
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_SafeCx  implements Serializable{
	private static final long serialVersionUID = 4822029114822566316L;
	
	/** 用户角色 */
	private String userrole;
	/** 网点号 */
	private String outletsno;
	/** 保管箱号 */
	private String insuranceno;
	/** 开户日期 */
	private String opendate;
	
	/** 授权到期日 */
	private String authorizationdatedue;
	/** 租用状态 */
	private String status;
	/** 退箱日期 */
	private String withdrawdate;
	/** 住宅电话 */
	private String hometel;
	/** 手机号码 */
	private String phone;
	
	/** 办公电话 */
	private String officetel;
	/** 通讯地址 */
	private String addr;
	/** 单位电话 */
	private String companytel;
	/** 邮箱地址 */
	private String email;
	/** 是否设置联名人  0-否，  1-是*/
	private String haspartner;
	/** 联名人姓名 */
	private String partnername;
	
	/** 联名人证件类型 */
	private String partneridtype;
	/** 联名人证件号码 */
	private String partnerid;
	/** 联名人通讯地址  */
	private String partneraddr;
	/** 联名人邮政编码 */
	private String partnerzipcode;
	/** 联名人手机号码 */
	private String partnerphone;
	/** 联名人住宅电话 */
	private String partnerhometel;
	/** 联名人办公电话 */
	private String partnerofficetel;
}

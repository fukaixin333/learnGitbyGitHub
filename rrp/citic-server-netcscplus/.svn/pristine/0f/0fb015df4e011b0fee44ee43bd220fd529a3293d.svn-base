package com.citic.server.dx.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 客户证件类型证件号码查询客户卡(主账户)信息，子账号信息，优选权信息 ，冻结措辞信息
 * 
 * @author dingke
 * @date 2016年5月21日 下午2:09:41
 */
public class PartyQueryResult implements Serializable {
	
	private static final long serialVersionUID = 2149228092271221920L;
	
	/** 卡或主账户信息 */
	private List<Br24_card_info> card_infoList;
	
	/** 账户信息 */
	private List<Br24_account_info> accountInfoList;
	
	/** 权利人信息 */
	private List<Br24_account_right> rightList;
	
	/** 措施信息 */
	private List<Br24_account_freeze> freezeList;
	
	public PartyQueryResult() {
	}
	
	public PartyQueryResult(List<Br24_card_info> cardInfoList, List<Br24_account_info> accountInfoList, List<Br24_account_right> rightsList, List<Br24_account_freeze> freezeList) {
		this.card_infoList = cardInfoList;
		this.accountInfoList = accountInfoList;
		this.rightList = rightsList;
		this.freezeList = freezeList;
	}
	
	public List<Br24_card_info> getCard_infoList() {
		return card_infoList;
	}
	
	public void setCard_infoList(List<Br24_card_info> card_infoList) {
		this.card_infoList = card_infoList;
	}
	
	public List<Br24_account_info> getAccountInfoList() {
		return accountInfoList;
	}
	
	public void setAccountInfoList(List<Br24_account_info> accountInfoList) {
		this.accountInfoList = accountInfoList;
	}
	
	public List<Br24_account_right> getRightList() {
		return rightList;
	}
	
	public void setRightList(List<Br24_account_right> rightList) {
		this.rightList = rightList;
	}
	
	public List<Br24_account_freeze> getFreezeList() {
		return freezeList;
	}
	
	public void setFreezeList(List<Br24_account_freeze> freezeList) {
		this.freezeList = freezeList;
	}
}

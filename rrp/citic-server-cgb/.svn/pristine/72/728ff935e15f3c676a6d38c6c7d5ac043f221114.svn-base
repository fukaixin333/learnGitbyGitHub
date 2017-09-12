package com.citic.server.inner.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 质押和授信类保证金查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OwnershipInfo extends ResponseMessage {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 保证金属性标识
	 * <ul>
	 * <li>1-授信类保证金
	 * <li>2-非授信类保证金
	 * <li>3-保证金主账户下有授信类保证金
	 * <li>4-保证金主账户下无授信类保证金
	 * <li>5-不是保证金
	 * </ul>
	 */
	private String marginType; // 保证金属性标识
	
	/**
	 * 质押情况标识
	 * <ul>
	 * <li>1-已质押
	 * <li>2-未质押
	 * <li>3-主账户下有已质押子账户
	 * <li>4-主账户下无已质押子账户
	 * </ul>
	 */
	private String pledgeType; // 质押情况标识
	
	public boolean isMargin() {
		return !"5".equals(marginType);
	}
	
	public boolean isPledge() {
		return !"2".equals(pledgeType) && !"4".equals(pledgeType);
	}
	
	public boolean hasSubOwnership() {
		return "3".equals(marginType) || "3".equals(pledgeType);
	}
}

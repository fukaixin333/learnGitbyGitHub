package com.citic.server.test;

import org.junit.Assert;
import org.junit.Test;

import com.citic.server.runtime.FormatTools;
import com.citic.server.runtime.Utility;

/**
 * {@link com.citic.server.runtime.Utility}
 * 
 * @author liuxuanfei
 * @date 2016年12月9日 下午1:18:14
 */
public class TestUtility extends Assert {
	
	@Test
	public void precisionOfCent() {
		assertEquals(FormatTools.precisionOfCent("+1000"), "+1000");
		assertEquals(FormatTools.precisionOfCent("-1000"), "-1000");
		assertEquals(FormatTools.precisionOfCent(".1000"), ".1000");
		
		assertEquals(FormatTools.precisionOfCent("1000"), "10.00");
		assertEquals(FormatTools.precisionOfCent("10.00"), "10.00");
		assertEquals(FormatTools.precisionOfCent("100}"), "-10.00");
		assertEquals(FormatTools.precisionOfCent("100J"), "-10.01");
		assertEquals(FormatTools.precisionOfCent("100K"), "-10.02");
		assertEquals(FormatTools.precisionOfCent("100L"), "-10.03");
		assertEquals(FormatTools.precisionOfCent("100M"), "-10.04");
		assertEquals(FormatTools.precisionOfCent("100N"), "-10.05");
		assertEquals(FormatTools.precisionOfCent("100O"), "-10.06");
		assertEquals(FormatTools.precisionOfCent("100P"), "-10.07");
		assertEquals(FormatTools.precisionOfCent("100Q"), "-10.08");
		assertEquals(FormatTools.precisionOfCent("100R"), "-10.09");
	}
	
	@Test
	public void isNumeric() {
		// 整数
		assertTrue(Utility.isNumeric("1000", true));
		assertTrue(Utility.isNumeric("1000", false));
		assertTrue(Utility.isNumeric("-1000", true));
		assertTrue(Utility.isNumeric("-1000", false));
		assertTrue(Utility.isNumeric("+1000", true));
		assertTrue(Utility.isNumeric("+1000", false));
		// 小数
		assertTrue(Utility.isNumeric("10.00", true));
		assertFalse(Utility.isNumeric("10.00", false));
		assertTrue(Utility.isNumeric("-10.00", true));
		assertFalse(Utility.isNumeric("-10.00", false));
		assertTrue(Utility.isNumeric("+10.00", true));
		assertFalse(Utility.isNumeric("+10.00", false));
		// 小数（无整数部分）
		assertTrue(Utility.isNumeric(".01", true));
		assertFalse(Utility.isNumeric(".01", false));
		assertTrue(Utility.isNumeric("-.01", true));
		assertFalse(Utility.isNumeric("-.01", false));
		assertTrue(Utility.isNumeric("+.01", true));
		assertFalse(Utility.isNumeric("+.01", false));
		// 小数（无小数部分）
		assertTrue(Utility.isNumeric("1000.", true));
		assertFalse(Utility.isNumeric("1000.", false));
		assertTrue(Utility.isNumeric("-1000.", true));
		assertFalse(Utility.isNumeric("-1000.", false));
		assertTrue(Utility.isNumeric("+1000.", true));
		assertFalse(Utility.isNumeric("+1000.", false));
		// +、-位置不对
		assertFalse(Utility.isNumeric("1-000", true));
		assertFalse(Utility.isNumeric("1-000", false));
		assertFalse(Utility.isNumeric("1+000", true));
		assertFalse(Utility.isNumeric("1+000", false));
		// 多个-、+、.
		assertFalse(Utility.isNumeric("--1000", true));
		assertFalse(Utility.isNumeric("--1000", false));
		assertFalse(Utility.isNumeric("++1000", true));
		assertFalse(Utility.isNumeric("++1000", false));
		assertFalse(Utility.isNumeric("1.0.00", true));
		assertFalse(Utility.isNumeric("1.0.00", false));
		assertFalse(Utility.isNumeric("1..000", true));
		assertFalse(Utility.isNumeric("1..000", false));
		// 非数字字符
		assertFalse(Utility.isNumeric("1000a", true));
		assertFalse(Utility.isNumeric("1000a", false));
		assertFalse(Utility.isNumeric("#1000", true));
		assertFalse(Utility.isNumeric("#1000", false));
	}
}

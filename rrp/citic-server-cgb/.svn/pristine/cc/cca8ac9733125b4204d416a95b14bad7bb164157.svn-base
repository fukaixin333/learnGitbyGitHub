package com.citic.server.shpsb;

/**
 * 上海市公安局（公安银行间破案追赃协查系统）请求代号
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午2:48:53
 */
public enum ShpsbCode {
	
	/** 个人账户信息查询 */
	GR_ZHXX("grzhxxcx", "grzhxxfk", true),
	
	/** 单位账户信息查询 */
	DW_ZHXX("dwzhxxcx", "dwzhxxfk", false),
	
	/** 个人开户资料查询 */
	GR_KHZL("grkhzlcx", "grkhzlfk", true),
	
	/** 单位开户资料查询 */
	DW_KHZL("dwkhzlcx", "dwkhzlfk", false),
	
	/** 个人交易明细查询 */
	GR_JYMX("grjymxcx", "grjymxfk", true),
	
	/** 单位交易明细查询 */
	DW_JYMX("dwjymxcx", "dwjymxfk", false),
	
	/** 个人账户持有人资料查询 */
	GR_ZHCYR("grzhcyrcx", "grzhcyrfk", true),
	
	/** 单位账户持有人资料查询 */
	DW_ZHCYR("dwzhcyrcx", "dwzhcyrfk", false),
	
	/** 个人操作日志查询 */
	GR_CZRZ("grczrzcx", "grczrzfk", true),
	
	/** 单位操作日志查询 */
	DW_CZRZ("dwczrzcx", "dwczrzfk", false),
	
	/** 个人交易关联号查询 */
	GR_JYGLH("grjyglhcx", "grjyglhfk", true),
	
	/** 单位交易关联号查询 */
	DW_JYGLH("dwjyglhcx", "dwjyglhfk", false),
	
	/** 个人对端账号查询 */
	GR_DDZH("grddzhcx", "grddzhfk", true),
	
	/** 单位对端账号查询 */
	DW_DDZH("dwddzhcx", "dwddzhfk", false);
	
	private String _cx;
	private String _fk;
	private boolean _personal;
	
	private ShpsbCode(String cxcode, String fkcode, boolean personal) {
		this._cx = cxcode;
		this._fk = fkcode;
		this._personal = personal;
	}
	
	public static ShpsbCode valueOf(String cxcode, ShpsbCode nval) {
		ShpsbCode code = enumOf(cxcode);
		return code == null ? nval : code;
	}
	
	public static boolean contains(String cxcode) {
		if (cxcode == null) {
			return false;
		}
		return enumOf(cxcode) == null ? false : true;
	}
	
	public static ShpsbCode enumOf(String cxcode) {
		ShpsbCode[] codes = ShpsbCode.values();
		for (ShpsbCode code : codes) {
			if (code.equalsTo(cxcode)) {
				return code;
			}
		}
		if (cxcode == null) {
			throw new NullPointerException("The 'cxcode' is null");
		}
		return null;
	}
	
	public static String fkOf(String cxcode) {
		ShpsbCode code = ShpsbCode.enumOf(cxcode);
		if (code == null) {
			throw new IllegalArgumentException("No enum const com.citic.server.shpsb.ShpsbCode._cx = " + cxcode);
		}
		return code.fk();
	}
	
	public boolean isPersonal() {
		return _personal;
	}
	
	public String cx() {
		return _cx;
	}
	
	public String fk() {
		return _fk;
	}
	
	public boolean equalsTo(String cxcode) {
		if (_cx.equals(cxcode)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + _cx + ", " + _fk + ")";
	}
}

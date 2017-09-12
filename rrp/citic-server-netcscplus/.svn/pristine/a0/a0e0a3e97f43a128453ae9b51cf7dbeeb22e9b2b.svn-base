package com.citic.server.gf.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询/控制请求涉及的文书信息
 * 
 * <pre>
 * <strong>警告</strong>
 * 根据《人民法院、银行业金融机构网络执行查控工作技术规范》所示：
 * 控制请求涉及的文书信息主键使用“请求单号（BDHM）”，而查询请求涉及的文书信息主键使用“文书编号（WSBH）”，
 * 所以，此类冗余了<code> wsbh </code>和<code> bdhm </code>两个字段，以适应两种情况。
 * 
 * 另外，文中给出的数据字典表中没有<code> wjmc </code>字段，而示例代码中却使用了该字段，
 * 所以，此类冗余了<code> wjmc </code>字段，以便于后续灵活调整。
 * </pre>
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 下午1:35:54
 */
@Data
public class WritResponse_WsInfo implements Serializable {
	private static final long serialVersionUID = -665446045293348465L;
	
	/** 文书编号，默认值为<code>null</code> */
	private String wsbh;
	
	/** 查询/控制请求单号，默认值为<code>null</code> */
	private String bdhm;
	
	/** 序号 */
	private String xh = "";
	
	/** 文件名称 */
	private String wjmc = "";
	
	/** 文件类型 */
	private String wjlx = "";
	
	/** 文书类别 */
	private String wslb = "";
	
	/** 文书MD5 */
	private String md5 = "";
	
	/** 文书内容 */
	private byte[] wsnr;
	
	// ==========================================================================================
	//                     Helper Field
	// ==========================================================================================
	/** 文书存储路径 */
	private String wspath = "";
}

package com.citic.server.gf.task.taskBo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.citic.server.gf.domain.Br31_kzcl_info;
import com.citic.server.gf.domain.Br31_yxq_info;
import com.citic.server.gf.domain.request.ControlRequest_Hzxx;
import com.citic.server.net.mapper.MM31_kzqqMapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;
import com.itextpdf.text.pdf.BaseFont;

public class HzwsBo extends BaseBo {
	
	private MM31_kzqqMapper mm31_kzqqMapper;
	
	public HzwsBo(MM31_kzqqMapper mm31_kzqqMapper) {
		this.mm31_kzqqMapper = mm31_kzqqMapper;
	}
	
	public void writeBr31_kzqq_hzws(String bdhm, String packetkey) throws Exception {
		
		String reportkey_r = "";
		if (packetkey.length() >= 21) {
			reportkey_r = packetkey.substring(4, 21);
		}
		
		// 1.查询控制请求单号下的账户处理信息
		List<Br31_kzcl_info> kzclList = this.getBr31_kzcl_infoList(bdhm);
		for (Br31_kzcl_info br31_kzcl_info : kzclList) {
			String csStr01 = "";// 冻结措施信息
			String csStr02 = "";// 续冻措施信息
			String csStr04 = ""; // 解除冻结措施信息
			String csStr06 = "";// 扣划措施信息
			String yxqStr01 = "";// 冻结权利人信息
			String yxqStr02 = "";// 续冻权利人信息
			
			String noKzStr01 = "";// 冻结未能控制原因
			String noKzStr02 = "";// 续冻未能控制原因
			String noKzStr04 = "";// 解除冻结未能控制原因
			String noKzStr06 = "";// 扣划未能控制原因
			
			String zcsStr01 = "";// 资产冻结措施信息
			String zcsStr02 = "";// 资产续冻措施信息
			String zcsStr04 = ""; // 资产解除冻结措施信息
			
			String zyxqStr01 = "";// 冻结权利人信息
			String zyxqStr02 = "";// 续冻权利人信息
			
			String znoKzStr01 = "";// 资产冻结未能控制原因
			String znoKzStr02 = "";// 资产续冻未能控制原因
			String znoKzStr04 = "";// 资产解除冻结未能控制原因
			
			String fymc = "";
			String ckh = "";
			String ckwh = "";
			String kzcs = br31_kzcl_info.getKzcs();
			String kzlx = br31_kzcl_info.getKzlx();
			String kzzt = br31_kzcl_info.getKzzt();
			fymc = br31_kzcl_info.getFymc();
			ckh = br31_kzcl_info.getCkh();
			ckwh = br31_kzcl_info.getCkwh();
			if (kzlx.equals("1")) { // 存款
				String csStr = "";
				String nocsStr = "";
				if (kzzt.equals("1")) { // 已控
					csStr = this.getCsStr01(br31_kzcl_info);
				} else {
					nocsStr = this.getNoKzStr01(br31_kzcl_info);
				}
				if (kzcs.equals("01")) {// 冻结存款
					csStr01 = csStr01 + csStr;
					noKzStr01 = noKzStr01 + nocsStr;
				}
				if (kzcs.equals("02")) {// 续冻存款
					csStr02 = csStr02 + csStr;
					noKzStr02 = noKzStr02 + nocsStr;
				}
				if (kzcs.equals("04")) {// 解除冻结存款
					csStr04 = csStr04 + csStr;
					noKzStr04 = noKzStr04 + nocsStr;
				}
				if (kzcs.equals("06")) {// 扣划
					csStr06 = csStr06 + csStr;
					noKzStr06 = noKzStr06 + nocsStr;
				}
				
			} else { // 金融资产
				String csStr = "";
				String nocsStr = "";
				if (kzzt.equals("1")) { // 已控
					csStr = this.getZCsStr01(br31_kzcl_info);
				} else {
					nocsStr = this.getZNoKzStr01(br31_kzcl_info);
				}
				if (kzcs.equals("01")) {// 冻结存款
					zcsStr01 = zcsStr01 + csStr;
					znoKzStr01 = znoKzStr01 + nocsStr;
				}
				if (kzcs.equals("02")) {// 续冻存款
					zcsStr02 = zcsStr02 + csStr;
					znoKzStr02 = znoKzStr02 + nocsStr;
				}
				if (kzcs.equals("04")) {// 解除冻结存款
					zcsStr04 = zcsStr04 + csStr;
					znoKzStr04 = znoKzStr04 + nocsStr;
				}
			}
			
			// 2.查询控制请求单号下的优选权信息
			List<Br31_yxq_info> list = mm31_kzqqMapper.selectBr31_yxq_DetailByBdhm(br31_kzcl_info);
			if (list.size() > 0) {
				for (Br31_yxq_info br31_yxq_info : list) {
					
					String csStr = this.getYxqStr(br31_yxq_info);
					
					if (kzlx.equals("1")) { // 存款
						if (kzcs.equals("01")) {// 冻结存款
							yxqStr01 = yxqStr01 + csStr;
						}
						if (kzcs.equals("02")) {// 续冻存款
							yxqStr02 = yxqStr02 + csStr;
						}
					} else { // 金融资产
					
						if (kzcs.equals("01")) {// 冻结存款
							zyxqStr01 = zyxqStr01 + csStr;
						}
						if (kzcs.equals("02")) {// 续冻存款
							zyxqStr02 = zyxqStr02 + csStr;
						}
					}
				}
			}
			// 3.组织文书串
			/*
			 * Br31_kzcl_info br31_kzcl_info = new Br31_kzcl_info();
			 * br31_kzcl_info.setFymc(fymc);
			 * br31_kzcl_info.setBdhm(bdhm);
			 * br31_kzcl_info.setCkh(ckh);
			 * br31_kzcl_info.setCkwh(ckwh);
			 */
			br31_kzcl_info.setReportkey_r(reportkey_r);
			// 冻结执行
			this.InsertStr("01", br31_kzcl_info, csStr01 + yxqStr01 + noKzStr01, zcsStr01 + zyxqStr01 + znoKzStr01);
			// 续冻执行
			this.InsertStr("02", br31_kzcl_info, csStr02 + yxqStr02 + noKzStr02, zcsStr02 + zyxqStr02 + znoKzStr02);
			// 解除冻结执行
			this.InsertStr("04", br31_kzcl_info, csStr04 + noKzStr04, zcsStr04 + znoKzStr04);
			// 扣划执行
			this.InsertStr("06", br31_kzcl_info, csStr06 + noKzStr06, "");
		}
		
	}
	
	public List<Br31_kzcl_info> getBr31_kzcl_infoList(String bdhm) throws Exception {
		
		List<Br31_kzcl_info> list = mm31_kzqqMapper.selectBr31_kzcl_DetailByBdhm(bdhm);
		
		return list;
	}
	
	public String getCsStr01(Br31_kzcl_info br31_kzcl_info) throws Exception {
		//币种转换
		HashMap sysParaMap = (HashMap) cacheService.getCache("BB13_pbc_crtpMap", HashMap.class);
		if (sysParaMap != null) {
			String bzdisp = StrUtils.null2String((String) sysParaMap.get(br31_kzcl_info.getBz()));
			br31_kzcl_info.setBz_disp(bzdisp);
		}
		
		String enddt = br31_kzcl_info.getCsjsrq();
		String startdt = br31_kzcl_info.getCsksrq();
		if (startdt == null || startdt.equals("")) {
			startdt = DtUtils.getNowTime();
		}
		if (enddt == null || enddt.equals("")) {
			enddt = DtUtils.getNowTime();
		}
		String kzcs = br31_kzcl_info.getKzcs();
		String str = "";
		String nullStr = "&nbsp;&nbsp;";
		String ceskje = br31_kzcl_info.getJe();
		if (ceskje == null || ceskje.equals("")) {
			ceskje = "0";
		}
		String khzh = br31_kzcl_info.getKhzh();
		String glzhhm = br31_kzcl_info.getGlzhhm();
		if (glzhhm != null && !glzhhm.equals("")) {
			khzh = khzh + "_" + glzhhm;
		}
		if (kzcs.equals("01")) {// 冻结
			str = nullStr + "被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的存款已被冻结，已冻结可用金额(" + br31_kzcl_info.getBz_disp() + ")" + br31_kzcl_info.getSkje()
					+ "元，额度冻结金额(" + br31_kzcl_info.getBz_disp() + ") " + ceskje + "元，冻结期限自" + startdt.substring(0, 4) + "年" + startdt.substring(5, 7) + "月"
					+ startdt.substring(8, 10) + "日" + startdt.substring(11, 13) + "时" + startdt.substring(14, 16) + "分" + " 至 " + enddt.substring(0, 4) + "年"
					+ enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日" + enddt.substring(11, 13) + "时" + enddt.substring(14, 16) + "分  ";
		}
		if (kzcs.equals("02")) {// 续冻
			str = nullStr + "被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的存款已成功续冻，续冻可用金额(" + br31_kzcl_info.getBz_disp() + ")" + br31_kzcl_info.getSkje()
					+ "元，续冻额度(" + br31_kzcl_info.getBz_disp() + ")" + ceskje + "元，续冻期限自" + startdt.substring(0, 4) + "年" + startdt.substring(5, 7) + "月" + startdt.substring(8, 10)
					+ "日" + startdt.substring(11, 13) + "时" + startdt.substring(14, 16) + "分" + " 至 " + enddt.substring(0, 4) + "年" + enddt.substring(5, 7) + "月"
					+ enddt.substring(8, 10) + "日" + enddt.substring(11, 13) + "时" + enddt.substring(14, 16) + "分  ";
		}
		if (kzcs.equals("04")) {// 解除冻结
			str = nullStr + "本案对被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的存款已解除冻结，解除冻结可用金额(" + br31_kzcl_info.getBz_disp() + ")" + br31_kzcl_info.getSkje()
					+ "元，解除额度冻结金额(" + br31_kzcl_info.getBz_disp() + ")" + ceskje + "元";
		}
		if (kzcs.equals("06")) {// 扣划
			str = nullStr + "本案对被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的存款已扣划(" + br31_kzcl_info.getBz_disp() + ")" + br31_kzcl_info.getSkje() + "元至你院执行款专户";
		}
		
		return str;
	}
	
	public String getNoKzStr01(Br31_kzcl_info br31_kzcl_info) throws Exception {
		String kzcs = br31_kzcl_info.getKzcs();
		String str = "";
		String khzh = br31_kzcl_info.getKhzh();
		String glzhhm = br31_kzcl_info.getGlzhhm();
		if (glzhhm != null && !glzhhm.equals("")) {
			khzh = khzh + "_" + glzhhm;
		}
		String nullStr = "&nbsp;&nbsp;";
		if (kzcs.equals("01")) {// 冻结
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的存款未能冻结。";
		}
		if (kzcs.equals("02")) {// 续冻
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的存款未能续冻。";
		}
		if (kzcs.equals("04")) {// 解除冻结
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内存款的冻结措施未能解除。";
		}
		if (kzcs.equals("06")) {// 扣划
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的存款未能扣划。";
		}
		
		return str;
	}
	
	// 资产操作
	public String getZCsStr01(Br31_kzcl_info br31_kzcl_info) throws Exception {
		String enddt = br31_kzcl_info.getCsjsrq();
		String startdt = br31_kzcl_info.getCsksrq();
		String kzcs = br31_kzcl_info.getKzcs();
		if (startdt == null || startdt.equals("")) {
			startdt = DtUtils.getNowTime();
		}
		if (enddt == null || enddt.equals("")) {
			enddt = DtUtils.getNowTime();
		}
		String khzh = br31_kzcl_info.getKhzh();
		String glzhhm = br31_kzcl_info.getGlzhhm();
		if (glzhhm != null && !glzhhm.equals("")) {
			khzh = khzh + "_" + glzhhm;
		}
		String djse = br31_kzcl_info.getSkse();
		String cedjse = br31_kzcl_info.getCeskje();
		if (cedjse != null && !cedjse.equals("")) {
			if (Integer.parseInt(cedjse) > 0) {
				djse = cedjse;
			}
		}
		
		String str = "";
		String nullStr = "&nbsp;&nbsp;";
		if (kzcs.equals("01")) {// 冻结
			str = nullStr + "被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的金额资产" + djse + " 份（计量单位）已被冻结，" + " 冻结期限自" + startdt.substring(0, 4) + "年"
					+ startdt.substring(5, 7) + "月" + startdt.substring(8, 10) + "日" + startdt.substring(11, 13) + "时" + startdt.substring(14, 16) + "分" + " 至 "
					+ enddt.substring(0, 4) + "年" + enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日" + enddt.substring(11, 13) + "时" + enddt.substring(14, 16) + "分 "
					+ "该金融资产交易限制将于" + enddt.substring(0, 4) + "年" + enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日解除" + " ";
		}
		if (kzcs.equals("02")) {// 续冻
			str = nullStr + "被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的金额资产" + djse + " 份（计量单位）已被继续冻结，" + " 续冻期限自" + startdt.substring(0, 4) + "年"
					+ startdt.substring(5, 7) + "月" + startdt.substring(8, 10) + "日" + startdt.substring(11, 13) + "时" + startdt.substring(14, 16) + "分" + " 至 "
					+ enddt.substring(0, 4) + "年" + enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日" + enddt.substring(11, 13) + "时" + enddt.substring(14, 16) + "分 "
					+ "该金融资产交易限制将于" + enddt.substring(0, 4) + "年" + enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日解除" + " ";
		}
		if (kzcs.equals("04")) {// 解除冻结
			str = nullStr + "本案对被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + "在我行" + khzh + "账户内的" + br31_kzcl_info.getZcmc() + "金融资产" + djse + " 份（计量单位）的冻结措施已经解除";
		}
		
		return str;
	}
	
	public String getZNoKzStr01(Br31_kzcl_info br31_kzcl_info) throws Exception {
		String kzcs = br31_kzcl_info.getKzcs();
		String str = "";
		String khzh = br31_kzcl_info.getKhzh();
		String glzhhm = br31_kzcl_info.getGlzhhm();
		if (glzhhm != null && !glzhhm.equals("")) {
			khzh = khzh + "_" + glzhhm;
		}
		String nullStr = "&nbsp;&nbsp;";
		if (kzcs.equals("01")) {// 冻结
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的" + br31_kzcl_info.getZcmc() + "金融资产未能冻结。";
		}
		if (kzcs.equals("02")) {// 续冻
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的" + br31_kzcl_info.getDjxe() + " 份（计量单位）未能续冻。";
		}
		if (kzcs.equals("04")) {// 解除冻结
			str = nullStr + "因" + br31_kzcl_info.getWnkzyy() + " ，被执行人（或其他法律地位）" + br31_kzcl_info.getXm() + " 在我行" + khzh + "账户内的" + br31_kzcl_info.getZcmc() + "金融资产的冻结措施未能解除。";
		}
		
		return str;
	}
	
	public String getYxqStr(Br31_yxq_info br31_yxq_info) throws Exception {
		String nullStr = "&nbsp;&nbsp;";
		String kzlx = br31_yxq_info.getKzlx();
		String kzlx_disp = "存款" + br31_yxq_info.getQlje() + "元享有" + br31_yxq_info.getQllx() + "权";
		if (kzlx.equals("2")) {
			kzlx_disp = "资产" + br31_yxq_info.getQlje() + "份享有" + br31_yxq_info.getQllx() + "权";
		}
		String str = "";
		str = "<br>" + nullStr + "上述账户中，被执行人（或其他法律地位）" + br31_yxq_info.getXm() + "的" + br31_yxq_info.getKhzh() + "账户为" + br31_yxq_info.getQllx() + "账户，权利人（/监管机关)"
				+ br31_yxq_info.getQlr() + "对该账户中的" + kzlx_disp + "";
		
		return str;
	}
	
	public ControlRequest_Hzxx InsertStr(String kzcs, Br31_kzcl_info br31_kzcl_info, String djStr, String zdjStr) throws Exception {
		ControlRequest_Hzxx br31_kzqq_hzws = new ControlRequest_Hzxx();
		String fymc = br31_kzcl_info.getFymc();
		String xh = br31_kzcl_info.getCcxh();
		br31_kzqq_hzws.setBdhm(br31_kzcl_info.getBdhm());
		String headStr = " <p align='center' style='text-align:center'><span                                         " + " style='font-size:22.0pt;color:black'>" + fymc
							+ "</span></p>             " + "                                                                                           "
							+ " <p align='center' style='text-align:center'><b><span                                      "
							+ " style='font-size:22.0pt;color:black'>协助执行通知书</span></b></p>       "
							+ "                                                                                           "
							+ " <p align='center' style='text-align:center'><span                                         "
							+ " style='font-size:14.0pt;color:black'>（回执）</span></p>                 "
							+ "                                                                                           " + " <p><span style='font-size:14.0pt;color:black'>"
							+ fymc + "：</span></p>  ";
		headStr = headStr + "<table summary=\"tid\"><tr><td>&nbsp;&nbsp;你院" + br31_kzcl_info.getCkwh() + "、" + br31_kzcl_info.getCkh() + "协助执行通知书收悉，我行处理结果如下：</td></tr><tr><td>";
		String endStr = "</td></tr></table>";
		String name = "冻结";
		if (kzcs.equals("02")) {
			name = "冻结";
		}
		if (kzcs.equals("04")) {
			name = "解结";
		}
		if (kzcs.equals("06")) {
			name = "扣划";
		}
		br31_kzqq_hzws.setWjmc(name + "协助执行通知书(回执)");
		String insertStr = headStr;
		// 插入文书
		if (!djStr.equals("") || !zdjStr.equals("")) {
			String htmlStr = insertStr + djStr + zdjStr + endStr;
			
			br31_kzqq_hzws.setXh(xh);
			br31_kzqq_hzws.setKzcs(kzcs);
			br31_kzqq_hzws.setKzlx(br31_kzcl_info.getKzlx());
			//生成pdf
			br31_kzqq_hzws.setWjlx("pdf");
			br31_kzqq_hzws.setDjrq(DtUtils.getNowTime());
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String root = StrUtils.null2String((String) sysParaMap.get("1"));
			String outputPath = "/fyck/hzws";
			String wjmc = br31_kzqq_hzws.getBdhm() + kzcs + ".pdf";
			br31_kzqq_hzws.setWjlx("pdf");
			// 生成pdf		
			String wjpath = outputPath + "/" + wjmc;
			//htmlStr = this.ReplaceHtmlStr(htmlStr); // 特殊处理字符串
			String reportkey_r = br31_kzcl_info.getReportkey_r();
			this.exportPdfFile(htmlStr, root + outputPath, wjmc, reportkey_r);
			br31_kzqq_hzws.setWjpath(wjpath);
			br31_kzqq_hzws.setQrydt(DtUtils.getNowDate());
			//插入回执文书表
			mm31_kzqqMapper.insertBr31_kzqq_hzws(br31_kzqq_hzws);
			
		}
		
		return br31_kzqq_hzws;
	}
	
	public String ReplaceHtmlStr(String str) {
		for (int i = 1; i < 6; i++) {
			str = this.strReplace(str, "h" + i);
		}
		return str;
	}
	
	public String strReplace(String s, String str) {
		if (s == null || s.length() == 0)
			return s;
		
		String replacestr = "<" + str + " align=";
		String sql = s;
		while (sql.indexOf(replacestr, 0) != -1) {
			int index = sql.indexOf(replacestr, 0);
			String bf1 = sql.substring(0, index);
			int strlength = replacestr.length();
			String bf2 = sql.substring(index + strlength);
			int index1 = 0;
			if (bf2.indexOf(">", 0) != -1) {
				index1 = bf2.indexOf(">", 0);
			}
			String classStr = bf2.substring(0, index1 + 1);
			int index2 = 0;
			if (bf2.indexOf("</" + str + ">", 0) != -1) {
				index2 = bf2.indexOf("</" + str + ">", 0);
			}
			String rnStr = bf2.substring(index1 + 1, index2);
			int endlength = index + strlength + classStr.length() + rnStr.length() + 5;
			sql = bf1 + "<div align=" + classStr + "<" + str + ">" + rnStr + "</" + str + "></div>" + sql.substring(endlength);
		}
		
		return sql;
	}
	
	/**
	 * 导出pdf
	 * urlStr:html串
	 * outputPth：文件路径
	 * wjmc：文件名称
	 */
	public void exportPdfFile(String htmlStr, String outputPth, String wjmc, String report_r) throws Exception {
		OutputStream os = null;
		try {
			File newFile = new File(outputPth);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			os = new FileOutputStream(outputPth + "/" + wjmc);
			
			ITextRenderer renderer = new ITextRenderer();
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String imgPath = StrUtils.null2String((String) sysParaMap.get("F2"));
			String imgname = "";
			String imgBasePath = "";
			if (imgPath != null) {
				int index = imgPath.lastIndexOf("/");
				imgname = imgPath.substring(index + 1);
				imgBasePath = imgPath.substring(0, index + 1);
				boolean isMultiCorpType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE); // 是否多法人 1为多法人
				if (isMultiCorpType) {
					int index1 = imgname.indexOf(".");
					imgname = report_r + "." + imgname.substring(index1 + 1);
				}
			}
			
			String str = getHtmlFile(htmlStr, imgname);
			renderer.setDocumentFromString(str);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String root = StrUtils.null2String((String) sysParaMap.get("F1"));
			fontResolver.addFont(root + "/SimSun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字  
			//  fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字  
			renderer.getSharedContext().setBaseURL(imgBasePath);
			renderer.layout();
			
			renderer.createPDF(os);
			
			System.out.println("转换成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (os != null) {
				os.flush();
				os.close();
			}
		}
		
	}
	
	public String getHtmlFile(String urlStr, String imgname) throws Exception {
		String enddt = DtUtils.getNowTime();
		String str = "<!DOCTYPE html>                                                                                    "
						+ "<html lang=\"en\">                                                                                "
						+ "<head>                                                                                            "
						+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />                          "
						+ "    <meta charset=\"utf-8\" />                                                                    "
						+ "    <title>Dashboard - Ace Admin</title>                                                          "
						+ "    <style type=\"text/css\">                                                                                       "
						+ "        body{                                                                                     "
						+ "            font-family:SimSun;                                                                   "
						+ "            font-size:12.0pt;                                                                       "
						+ "            }                                                                                     " + " table {                         "
						+ "	border: 0px solid;             " + "	border-collapse: collapse;     " + "	table-layout: fixed;           " + "	word-break:break-all;          "
						+ "	font-size: 12.0pt;             " + "	width: 95%;                   " + "	text-align: center;            " + "	margin-top: 10px;              "
						+ "	}                              "
						+ "                                 "
						+ "	td {                           "
						+ "	border: 0px solid;             "
						+ "	text-align: left;              "
						//  +"	text-indent:2em;               "
						+ "	width:90px;                    " + "	word-break:break-all;          " + "	word-wrap : break-word;        " + "	}                              "
						+ "    </style>                                                                                      "
						+ "                                                                                                  "
						+ "</head>                                                                                           "
						+ "<body>                                                                                            ";
		str = str + urlStr + "<table summary=\"t2\"  background=\"" + imgname + "\"><tr><td  height=\"135px\" style='font-size:14.0pt;	text-align: right'>" + enddt.substring(0, 4)
				+ "年" + enddt.substring(5, 7) + "月" + enddt.substring(8, 10) + "日" + enddt.substring(11, 13) + "时" + enddt.substring(14, 16) + "分 "
				+ "</td></tr></table></body></html>  ";
		System.out.println("str::::::::::::::::::::" + str);
		StringBuffer sb = new StringBuffer();
		InputStream is = new ByteArrayInputStream(str.getBytes());
		Tidy tidy = new Tidy();
		OutputStream os2 = new ByteArrayOutputStream();
		tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)  
		tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文  
		tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息  
		tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>  
		tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些  
		tidy.parse(is, os2);
		is.close();
		// 解决乱码 --将转换后的输出流重新读取改变编码  
		String temp;
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(((ByteArrayOutputStream) os2).toByteArray()), "utf-8"));
		while ((temp = in.readLine()) != null) {
			sb.append(temp);
		}
		
		return sb.toString();
		
	}
	
}

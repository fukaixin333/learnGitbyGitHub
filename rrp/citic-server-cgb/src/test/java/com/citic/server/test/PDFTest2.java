package com.citic.server.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.citic.server.gf.domain.request.ControlRequest_Djxx;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.ControlRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.utils.PDFUtils;

public class PDFTest2 {
	
	@Test
	public void pdfTest() throws Exception {
		ControlResponse_Kzqq kzqq = new ControlResponse_Kzqq();//请求信息
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();//控制信息
		ControlRequest_Qlxx qlxx = new ControlRequest_Qlxx();//权力信息
		ControlRequest_Qlxx qlxx2 = new ControlRequest_Qlxx();//权力信息
		ControlRequest_Qlxx qlxx3 = new ControlRequest_Qlxx();//权力信息
		ControlRequest_Qlxx qlxx4 = new ControlRequest_Qlxx();//权力信息
		
		ControlRequest_Djxx djxx = new ControlRequest_Djxx();
		ControlRequest_Djxx djxx2 = new ControlRequest_Djxx();
		
		List<ControlRequest_Kzxx> kzxxList = new ArrayList<ControlRequest_Kzxx>();
		
		List<ControlRequest_Qlxx> qlxxList = new ArrayList<ControlRequest_Qlxx>();
		List<ControlRequest_Djxx> djxxList = new ArrayList<ControlRequest_Djxx>();
		//公用头内容
		kzqq.setWjmc1("2017京0712（字号）");
		kzqq.setWjmc2("2017京0712（字号）控制通知书");
		kzqq.setFymc("最高人民法院");
		kzqq.setXm("朱大憨");
		
		//kzxx.setCeskje("22");//超额冻结
		kzxx.setKhzh("62212345678900008");//开户账号
		kzxx.setSkje("1000");//实控金额
		//kzxx.setYe("900000000000000");//账户余额
		kzxx.setKyye("88888888888888");
		//可用余额
		kzxx.setCsksrq_cn("2017-06-06");
		kzxx.setCsjsrq_cn("2017-06-06");
		kzxx.setSkse("666666");//实控数额
		kzxx.setDjxe("77777");
		kzxx.setJrcpbh("精品理财产品");
		//权力信息
		qlxx.setQllx("优先受偿权");
		qlxx.setQlr("朱二楞");
		qlxx.setJrcpbh("精品理财产品");
		qlxx.setQlje("6666666666");
		
		qlxx2.setQllx("次优权");
		qlxx2.setQlr("朱三傻");
		qlxx2.setQlje("6666666666");
		
		qlxx3.setQllx("次优权");
		qlxx3.setQlr("朱四杰");
		qlxx3.setQlje("6666666666");
		
		qlxx3.setQllx("次优权");
		qlxx3.setQlr("朱无视");
		qlxx3.setQlje("6666666666");
		
		qlxxList.add(qlxx);
		
		//		qlxxList.add(qlxx2);
		//		qlxxList.add(qlxx3);
		//		qlxxList.add(qlxx4);
		kzxx.setQlxxList(qlxxList);
		kzxx.setDjxxList(djxxList);
		kzxxList.add(kzxx);
		
		djxx.setDjjg("人行");//在先冻结机关
		djxx.setDjje("1000");//在先冻结金额
		
		djxx2.setDjjg("");
		djxx2.setDjje("10000");
		
		djxxList.add(djxx);
		djxxList.add(djxx2);
		
		//失败时
		kzxx.setWnkzyy("他爸是李刚");
		
		
		//生产pdf条件
		kzxx.setKzlx("1");//1-账户  2-金融资产
		kzxx.setKzcs("01");//先以控制措施 kzcs 区分 01-冻结  02-续冻  04-解冻  06-扣划
		//kzxx.setKznr("1");
		kzxx.setKzzt("1");//状态 1 成功  2 失败
		
		PDFUtils test = new PDFUtils();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kzqq", kzqq);
		map.put("kzxxList", kzxxList);
		
		
		//生成pdf的位置
		File file=new File("D:/pdf/");
		if(!file.exists()){
			file.mkdirs();
		}
		//生成pdf名称
		String name=file+"账户冻结成功.pdf";
		//模板，生成pdf位置，传入的map
		test.html2pdf("gf/hzws01.html", name, map);
		
	}
}

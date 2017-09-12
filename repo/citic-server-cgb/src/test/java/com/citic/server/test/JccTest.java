package com.citic.server.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Transaction;
import com.citic.server.gf.BatchDBTools;
import com.citic.server.gf.domain.Br31_kzcl_info;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.junit.BaseJunit4Test;
import com.citic.server.net.mapper.MM30_xzcsMapper;
import com.citic.server.net.mapper.MM31_kzqqMapper;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;

public class JccTest extends BaseJunit4Test {
	
	private Logger logger = LoggerFactory.getLogger(JccTest.class);
	
	@Autowired
	private MM30_xzcsMapper mm30_xzcsMapper;
	
	@Autowired
	private PollingTaskMapper mapper;
	
	@Test
	public void a() throws Exception {
		List<MC20_Task_Fact1> list = mapper.queryMC20_Task_Fact1("7");
		logger.debug(">>" + list.size());
	}
	
	public void dosth() throws SQLException {
		List<QueryRequest_Zhxx> zhxxList = new ArrayList<QueryRequest_Zhxx>();
		for (int i = 0; i < 5000; i++) {
			QueryRequest_Zhxx zhxx = new QueryRequest_Zhxx();
			zhxx.setBdhm(String.valueOf(i));
			zhxx.setCcxh(String.valueOf(i));
			zhxx.setKhzh(String.valueOf(i));
			zhxx.setCclb(String.valueOf(i));
			zhxx.setZhzt(String.valueOf(i));
			zhxx.setKhwd(String.valueOf(i));
			zhxx.setKhwddm(String.valueOf(i));
			zhxx.setKhrq(String.valueOf(i));
			zhxx.setXhrq(String.valueOf(i));
			zhxx.setBz(String.valueOf(i));
			zhxx.setYe(String.valueOf(i));
			zhxx.setKyye(String.valueOf(i));
			zhxx.setGlzjzh(String.valueOf(i));
			zhxx.setTxdz(String.valueOf(i));
			zhxx.setYzbm(String.valueOf(i));
			zhxx.setLxdh(String.valueOf(i));
			zhxx.setBeiz(String.valueOf(i));
			zhxx.setQrydt(Utility.currDate10());
			zhxx.setOrgkey(String.valueOf(i));
			zhxx.setFksj(String.valueOf(i));
			zhxxList.add(zhxx);
		}
		
		long li1 = System.currentTimeMillis();
		BatchDBTools.batchInsertQueryRequest_Zhxx(zhxxList, 1000); // 62ms
		//		for (QueryRequest_Zhxx zhxx : zhxxList) {
		//			mm30_xzcsMapper.insertBr30_xzcs_info(zhxx); // 65945   281
		//		}
		long li2 = System.currentTimeMillis();
		
		System.out.println(li2 - li1);
	}
	
	public void test() {
		MM31_kzqqMapper mm31_kzqqMapper = SpringContextHolder.getBean("MM31_kzqqMapper");
		Br31_kzcl_info kzclInfo = mm31_kzqqMapper.getMM31_kzcl_info("A220170608110101200002", "1");
		System.out.println(kzclInfo);
	}
	
	public void testBatch() throws Exception {
		
		SqlSessionFactory factory = (SqlSessionFactory) SpringContextHolder.getBean(SqlSessionFactory.class);
		SqlSession session = null;
		
		boolean goon = true;
		int count = 32770; // 728  32767
		while (goon) {
			try {
				goon = false;
				List<CBRC_QueryRequest_Transaction> transactionList = new ArrayList<CBRC_QueryRequest_Transaction>();
				for (int i = 0; i < count; i++) {
					CBRC_QueryRequest_Transaction trans = new CBRC_QueryRequest_Transaction();
					trans.setTasktype("3");
					trans.setQqdbs("1"); // 0100dz0220161125135323
					trans.setRwlsh("1"); // 0100dz022016112513532300017
					trans.setTransseq("1"); // 20161227001398210002
					trans.setCxfkjg(""); // 01
					trans.setCxfkjgyy(""); // 成功
					trans.setCxkh(""); // 101001501010000881
					trans.setZh(""); // V1156101001501010000881
					trans.setJdbz(""); // 出
					trans.setBz(""); // CNY
					trans.setJe(""); // 0.01
					trans.setYe(""); // 317412828377.06
					trans.setJysj(""); // 2016-12-27 09:14:35
					trans.setJylsh(""); // 20161227001398210002
					trans.setJydfxm(""); // 胆乱酝鑫务酝废宪金淑渊剃
					trans.setJydfzh(""); // 9550802190000013452
					trans.setJydfkh(""); // 9550802190000013452
					trans.setJywdmc(""); // 广发银行广州分行广发大厦支行（网点级）
					trans.setJywddm(""); // 101001
					trans.setRzh(""); // 139821
					trans.setCph(""); // 000000139821
					trans.setXjbz(""); // 01
					trans.setJysfcg(""); // 01
					trans.setJygyh(""); // 08340327
					trans.setQrydt(""); // 2016-12-30
					transactionList.add(trans);
				}
				
				session = factory.openSession(ExecutorType.BATCH, false);
				session.insert("com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper.insertBr40_cxqq_back_trans", transactionList);
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				goon = true;
				count = count - 1;
			} finally {
				if (session != null) {
					session.clearCache();
					session.close();
				}
			}
		}
		
		System.out.println(">>>>>" + count);
	}
}

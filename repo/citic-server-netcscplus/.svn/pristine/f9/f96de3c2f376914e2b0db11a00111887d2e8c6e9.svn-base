package com.citic.server.gf.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.Br32_packet;
import com.citic.server.gf.domain.request.RollbackRequest;
import com.citic.server.gf.domain.request.RollbackRequest_Htxx;
import com.citic.server.net.mapper.BR32_packetMapper;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.utils.ZipUtils;

public class PacketBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(PacketBo.class);
	
	private BR32_packetMapper br32_packetmapper = (BR32_packetMapper) this.ac.getBean("BR32_packetMapper");
	private String root = "";
	
	public PacketBo(ApplicationContext ac) {
		super(ac);
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		root = StrUtils.null2String((String) sysParaMap.get("2"));
	}
	
	public void delPacket(String packetkey, JdbcTemplate jdbcTemplate) {
		String delsql = "delete from  BR32_PACKET where packetkey='" + packetkey + "'";
		jdbcTemplate.update(delsql);
	}
	
	public void delMsg(String packetkey, ArrayList<String> sqlList) {
		String delsql = "delete from br32_msg where  msg_type_cd in('NCT','NKT','RCT','RKT') and packetkey='" + packetkey + "'";
		sqlList.add(delsql);
	}
	
	public Br32_packet getPacket(String packetkey) throws Exception {
		Br32_packet br32_packet = br32_packetmapper.getBr32_packet(packetkey);
		if (br32_packet == null) {
			br32_packet = new Br32_packet();
		}
		return br32_packet;
	}
	
	public Br32_packet getBr32_msg(String packetkey) throws Exception {
		Br32_packet br32_packet = br32_packetmapper.getBr32_packet(packetkey);
		if (br32_packet == null) {
			br32_packet = new Br32_packet();
		}
		return br32_packet;
	}
	
	public void delBR32_receipt(String packetkey, JdbcTemplate jdbcTemplate) throws Exception {
		String delsql = "delete from br32_receipt where packetkey='" + packetkey + "'";
		jdbcTemplate.update(delsql);
	}
	
	public void delBR32_packet(String packetkey, ArrayList<String> sqlList) throws Exception {
		String delsql = "delete from br32_packet where   PACKETKEY='" + packetkey + "' and status_cd='0'";
		sqlList.add(delsql);
		
	}
	
	public ArrayList<String> dealPacket(Br32_packet br32_packet, ArrayList<String> sqlList) throws Exception {
		String packepath = root + br32_packet.getFilepath();
		String packetkey = br32_packet.getPacketkey();
		String pack_type_cd = br32_packet.getPack_type_cd();
		String tablename = "BR30_XZCS";
		String packettype = "RC";
		if (pack_type_cd.equals("KZHZ")) {
			tablename = "BR31_KZQQ";
			packettype = "RK";
		}
		//生成新的数据包key
		String newPacketKey = packetkey.substring(0, 30) + DtUtils.getNowDate().replaceAll("-", "") + this.geSequenceNumber("SEQ_BR32_PACKET") + "_" + packettype;
		//1.解除数据包到临时目录		
		String path = "/gf/back/temp/" + DtUtils.getNowDate();
		String file_path = root + path; //临时目录
		File storeFile = new File(file_path);
		if (storeFile.isDirectory()) {
			File[] files = storeFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName().toUpperCase();
				if (name.endsWith(".XML") || name.endsWith(".ZIP")) {
					files[i].delete(); //循环遍历删除zipf
				}
			}
		}
		//将数据包加压到该路径中
		ZipUtils zipUtils = new ZipUtils();
		zipUtils.unzip(packepath, file_path);
		if (storeFile.isDirectory()) {
			File[] FDXMLfiles = storeFile.listFiles();
			for (int m = 0; m < FDXMLfiles.length; m++) {
				// 取得该XML文件的绝对路径
				String FDXMLfileName = FDXMLfiles[m].getName();
				if (FDXMLfileName.toUpperCase().endsWith(".XML")) { //解析xml
					RollbackRequest bs_htxxlist = (RollbackRequest) CommonUtils.unmarshallContext(RollbackRequest.class, "binding_rollback_req", file_path + "/" + FDXMLfileName);
					List<RollbackRequest_Htxx> htxxList = bs_htxxlist.getHtxxList();
					
					for (int i = 0; i < htxxList.size(); i++) {
						RollbackRequest_Htxx bs_htxx = htxxList.get(i);
						String sql = "insert into BR32_RECEIPT(RECEIPTKEY,bdhm,HZTYPE,PACKETKEY,RECEIPT_FILE,RECEIPT_FILEPATH,XCHTYY,XCHTBZ,XCHTR,XCHTDH,CREATE_DT,RECEIPT_STATUS_CD)";
						sql = sql + "values('" + this.geSequenceNumber("SEQ_BR32_RECEIPT") + "',";
						sql = sql + "'" + bs_htxx.getBdhm() + "',";
						sql = sql + "'" + pack_type_cd + "','" + packetkey + "','" + FDXMLfileName + "',";
						sql = sql + "'" + path + "/" + FDXMLfileName + "',";
						sql = sql + "'" + bs_htxx.getXchtyy() + "',";
						sql = sql + "'" + bs_htxx.getXchtbz() + "',";
						sql = sql + "'" + bs_htxx.getXchtr() + "',";
						sql = sql + "'" + bs_htxx.getXchtdh() + "',";
						sql = sql + "'" + DtUtils.getNowTime() + "','0')";
						sqlList.add(sql);
						//修改请求表中的状态
						sql = "update  " + tablename + "  set STATUS='9' ,MSG_TYPE_CD='R',PACKETKEY='" + newPacketKey + "'" + " where bdhm='" + bs_htxx.getBdhm() + "'";
						
					}
				}
			}
		}
		return sqlList;
		
	}
	
	public ArrayList<String> dealHTMsg(String packetkey, ArrayList<String> sqlList) throws Exception {
		String packettype = packetkey.substring(packetkey.length() - 2);
		//查询回退信息
		List<RollbackRequest_Htxx> htxxList = br32_packetmapper.getBr30_Htxx(packetkey);
		if (htxxList.size() > 0) {
			RollbackRequest rollbackrequest = new RollbackRequest();
			rollbackrequest.setHtxxList(htxxList);
			//String orgkey=StrUtils.null2String(htxxList.get(0).getOrgkey());
			//生成xml文件
			String path = "/gf/htxml_" + packettype + "/" + DtUtils.getNowDate();
			//MP02_rep_org_map  mp02_rep_org_map=this.getMp02_organPerson("1",orgkey);
			//String organkey_r=mp02_rep_org_map.getReport_organkey();
			//String filename="QNA"+organkey_r+packetkey.substring(0,30)+".xml";
			String organkey_r = packetkey.substring(4, 21);
			String filename = "QNA" + packetkey.substring(0, 51) + ".xml";
			CommonUtils.marshallUTF8Document(rollbackrequest, "binding_rollback_req", root + path, filename);
			String currtime = DtUtils.getNowDate("yyyymmddhhmmss");
			
			String delsql = "delete from br32_msg where bdhm='A' and packetkey='" + packetkey + "'";
			sqlList.add(delsql);
			//插入br32_msg
			String sql = "insert into br32_msg(MSGKEY,BDHM,MSG_TYPE_CD,PACKETKEY,ORGANKEY_R,SENDDATE,MSG_FILENAME,MSG_FILEPATH,STATUS_CD,CREATE_DT)";
			sql = sql + "values('" + currtime + this.geSequenceNumber("SEQ_BR32_MSG") + "','A',";
			sql = sql + "'" + packettype + "',";
			sql = sql + "'" + packetkey + "','" + organkey_r + "','" + DtUtils.getNowDate() + "',";
			sql = sql + "'" + filename + "',";
			sql = sql + "'" + path + "/" + filename + "','0',";
			sql = sql + "'" + DtUtils.getNowTime() + "')";
			sqlList.add(sql);
		}
		return sqlList;
		
	}
	
	public ArrayList<String> dealHTMsg_kz(String bdhm, String newPacketkey, ArrayList<String> sqlList) throws Exception {
		int index1 = newPacketkey.indexOf("_");
		int index2 = newPacketkey.lastIndexOf("_");
		String packettype = newPacketkey.substring(index1 + 1, index2);
		//查询回退信息
		List<RollbackRequest_Htxx> htxxList = br32_packetmapper.getBr31_Htxx(bdhm);
		if (htxxList != null && htxxList.size() > 0) {
			RollbackRequest rollbackrequest = new RollbackRequest();
			rollbackrequest.setHtxxList(htxxList);
			//生成xml文件
			String path = "/gf/htxml_" + packettype + "/" + DtUtils.getNowDate();
			/*
			 * MP02_rep_org_map mp02_rep_org_map=this.getMp02_organPerson("1","");
			 * String organkey_r=mp02_rep_org_map.getReport_organkey();
			 * String filename="QNA"+organkey_r+newPacketkey.substring(0,30)+bdhm+".xml";
			 */
			String organkey_r = newPacketkey.substring(4, 21);
			String filename = "QNA" + newPacketkey.substring(0, 51) + bdhm + ".xml";
			CommonUtils.marshallUTF8Document(rollbackrequest, "binding_rollback_req", root + path, filename);
			
			String currtime = DtUtils.getNowDate("yyyymmddhhmmss");
			
			//插入br32_msg
			String sql = "insert into br32_msg(MSGKEY,BDHM,MSG_TYPE_CD,PACKETKEY,ORGANKEY_R,SENDDATE,MSG_FILENAME,MSG_FILEPATH,STATUS_CD,CREATE_DT)";
			sql = sql + "values('" + currtime + this.geSequenceNumber("SEQ_BR32_MSG") + "','" + bdhm + "',";
			sql = sql + "'" + packettype + "',";
			sql = sql + "'" + newPacketkey + "','" + organkey_r + "','" + DtUtils.getNowDate() + "',";
			sql = sql + "'" + filename + "',";
			sql = sql + "'" + path + "/" + filename + "','0',";
			sql = sql + "'" + DtUtils.getNowTime() + "')";
			sqlList.add(sql);
		}
		
		return sqlList;
	}
	
	public void makePacket(String packetkey, JdbcTemplate jdbcTemplate) throws Exception {
		
		//查询数据包下的xml信息打包
		List<Br32_msg> msgList = br32_packetmapper.getBr32_msg(packetkey);
		if (msgList.size() > 0) {
			String packetsqm = packetkey.substring(21, 51);
			String organkey_r = packetkey.substring(4, 21);
			
			ArrayList fileList = new ArrayList();
			String partytype = "";
			String qingqiudanhao = "";
			for (Br32_msg br32_msg : msgList) {
				partytype = br32_msg.getMsg_type_cd();
				qingqiudanhao = br32_msg.getBdhm();
				String filename = root + br32_msg.getMsg_filepath();
				fileList.add(filename);
			}
			
			//打包
			String packettype = partytype;
			String pack_type_cd = "KZFK";
			qingqiudanhao = StrUtils.fillStr(30, qingqiudanhao);
			String packetname = pack_type_cd + organkey_r + packetsqm + qingqiudanhao + ".zip";
			if (packettype.equals("NC") || packettype.equals("RC")) { //查询
				pack_type_cd = "CXFK";
				packetname = pack_type_cd + organkey_r + packetsqm + ".zip";
			}
			String zipPath = "/gf/" + DtUtils.getNowDate() + "/packet_" + packettype;
			String zipFileName = zipPath + "/" + packetname;
			String root_q = StrUtils.null2String((String) sysParaMap.get("1"));
			File file = new File(root_q + zipPath);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			
			ZipUtils zipUtils = new ZipUtils();
			zipUtils.zip(root_q + zipFileName, fileList, "");
			
			//插入br32_packet
			String sql = "insert into br32_packet(PACKETKEY,PACK_TYPE_CD,ORGANKEY_R,SENDDATE_DT,FILENAME,FILEPATH,STATUS_CD,CREATE_DT,QRYDT)";
			sql = sql + "values('" + packetkey + "',";
			sql = sql + "'" + pack_type_cd + "',";
			sql = sql + "'" + organkey_r + "','" + DtUtils.getNowDate() + "',";
			sql = sql + "'" + packetname + "',";
			sql = sql + "'" + zipFileName + "','0',";
			sql = sql + "'" + DtUtils.getNowTime() + "',";
			sql = sql + "'" + DtUtils.getNowDate() + "')";
			jdbcTemplate.update(sql);
		}
	}
}

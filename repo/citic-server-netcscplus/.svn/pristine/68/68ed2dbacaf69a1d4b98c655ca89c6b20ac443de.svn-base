package com.citic.server.dx.inner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citic.server.basic.IPollingTask;
import com.citic.server.dict.DictCoder;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.net.mapper.Br20_md_infoMapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.SftpUtils;
import com.google.common.collect.Lists;

public abstract class AbstractSuspiciousPollingTask implements IPollingTask {
	
	public static final Logger logger = LoggerFactory.getLogger(AbstractSuspiciousPollingTask.class);
	
	@Autowired
	private DictCoder dictCoder;
	@Autowired
	public Br20_md_infoMapper br20_md_infoMapper;
	@Getter@Setter
	public volatile String nextsendtime = "";
	/**
	 * 更新发送状态［增量］
	 * 
	 * @param keys
	 * @param send_time
	 */
	public void updateSend_flag(String send_flag, String send_time) {
		Br20_md_info mdkey = new Br20_md_info();
		mdkey.setSend_time(send_time);
		mdkey.setSend_flag(send_flag);//0:未发送 1:已发送 9:中间状态［待发送］
		br20_md_infoMapper.updateSend_flagBykeys(mdkey);
	}
	
	/**
	 * 更新发送状态［全量］
	 * 将发送点之前接收的数据更新
	 * 
	 * @param keys
	 * @param send_time
	 */
	public void updateSend_flagByAll(String send_time) {
		Br20_md_info mdkey = new Br20_md_info();
		mdkey.setAccept_time(send_time);
		mdkey.setSend_time(send_time);
		mdkey.setSend_flag("1");//0:未发送 1:已发送
		br20_md_infoMapper.updateSend_flagByTime(mdkey);
	}
	
	/**
	 * 更新发送时间
	 * 增量每2小时发送一次 全量每天发送一次
	 * 
	 * @param br20_md_info
	 */
	public void updateSendTime(Br20_md_info br20_md_info) {
		try {
			String nex_time = br20_md_info.getNext_time();
			int amount = Integer.valueOf(br20_md_info.getFrequency());//频率
			int gran = Integer.valueOf(br20_md_info.getUnit());//频率单位
			String new_next_time = "";
			if ("".equals(nex_time)) {// 下次发送时间为空
				nex_time = DtUtils.getNowTime();
				new_next_time = DtUtils.getTimeByGran(nex_time, amount, gran, "yyyy-MM-dd HH:mm:ss");
			} else {
				new_next_time = DtUtils.getTimeByGran(nex_time, amount, gran, "yyyy-MM-dd HH:mm:ss");
			}
			br20_md_info.setLast_time(nex_time);
			br20_md_info.setNext_time(new_next_time);
			
			br20_md_infoMapper.updateBr_md_taskByVo(br20_md_info);
			
		} catch (ParseException e) {
			logger.info("生成发送文件时，时间转换失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理数据list
	 * 
	 * @param datalist 数据list
	 * @param mode 数据存放方式
	 * @param Separate 分隔符号
	 * @return
	 */
	public List<String> dealDataList(List<Br20_md_info> datalist, String mode, String Separate) {
		if ("fixedLength".equals(mode)) {//定长
			return getFixedLengthList(datalist);
		} else {
			return getSeparationList(datalist, Separate);
		}
	}
	
	/**
	 * 定长处理
	 * 对每个object，将核心需要的字断做定长处理，然后按指定顺序拼接到一起
	 * 顺序：操作类型 实体编号 实体类型 名单种类 关联客户号 账户名称 加入原因 其他信息 备注
	 * 操作类型：长度 1 A增加 D删除
	 * 实体类型：长度1 1-账号 2-证件
	 * 名单种类：长度3 FBA黑名单 FGA灰名单
	 * 关联客户号：长度12 空
	 * 实体编号：长度80
	 * 实体名称：长度180
	 * 证件类型：长度 5
	 * 加入原因：长度120对应 案件类型 即电信诈骗
	 * 其他信息：长度120
	 * 来源 备注：长度200
	 * 
	 * @param datalist
	 *        从库中查询的结果
	 * @return
	 */
	public List<String> getFixedLengthList(List<Br20_md_info> datalist) {
		List<String> list = null;
		if (datalist != null && datalist.size() > 0) {
			list = Lists.newArrayList();
			for (Br20_md_info md : datalist) {
				//将字段进行转吗
				dictCoder.transcode(md,null);
				
				StringBuffer sb = new StringBuffer();
				sb.append(md.getOperate_flag());//操作符号 
				sb.append(md.getMd_kind());//数据类型 
				sb.append(md.getMd_type());//名单类型
				sb.append("            ");//关联客户号
				sb.append(rightPad(md.getMd_value(), 80));//实体编号
				sb.append(rightPad(md.getP_name(), 200));//实体名称
				sb.append(rightPad(md.getCardtype(), 5));//证件类型
				sb.append(rightPad(md.getBank_id(), 14));//银行编码
				sb.append(rightPad(md.getPolice(), 122));//公安联系人
				sb.append(rightPad(md.getPolice_phone(), 50));//公安联系人电话
				sb.append(rightPad(StringUtils.replaceChars(md.getPolice_check_dt(), "-: ", ""), 14));//公安审核通过时间戳
				sb.append(rightPad(md.getOpen_card_dt(), 14));//开卡时间(名单冗余项)
				sb.append(rightPad(StringUtils.replaceChars(md.getStart_dt(), "-: ", ""), 14));//有效期开始时间
				sb.append(rightPad(StringUtils.replaceChars(md.getEnd_dt(), "-: ", ""), 14));//有效期结束时间
				sb.append(rightPad(md.getRemark(), 240));//名单说明
				sb.append(rightPad(md.getCase_type(), 120));//加入原因－－目前只有电信诈骗
				sb.append(rightPad(md.getMd_source(), 120));//其他信息－－目前只有公安
				sb.append("                                                                                                    ");//备注100
				sb.append("                                                                                                    ");//备注100
				list.add(sb.toString());
			}
		}
		return list;
	}
	
	/**
	 * 分隔符号处理
	 * 顺序：操作类型 实体编号 实体类型 名单种类 关联客户号 账户名称 加入原因 其他信息 备注
	 * 
	 * @param datalist
	 * @param Separate 分隔符号
	 * @return
	 */
	public List<String> getSeparationList(List<Br20_md_info> datalist, String Separate) {
		List<String> list = null;
		if (datalist != null && datalist.size() > 0) {
			list = Lists.newArrayList();
			for (Br20_md_info md : datalist) {
				//将字段进行转吗
				dictCoder.transcode(md,null);
				
				StringBuilder sb = new StringBuilder();
                
//              sb.append(StringUtils.equals("+", md.getOperate_flag()) ? "A" : "D").append(Separate);
//				sb.append(StringUtils.equals("IDType_IDNumber", md.getMd_kind()) ? "2" : "1").append(Separate);
//				sb.append(StringUtils.equals("1", md.getMd_type()) ? "FBA" : "FGA").append(Separate);
				sb.append(md.getOperate_flag()).append(Separate);//操作符号 
				sb.append(md.getMd_kind()).append(Separate);//数据类型 
				sb.append(md.getMd_type()).append(Separate);//名单类型
				sb.append(md.getP_name()).append(Separate);//账户名称
				sb.append(md.getCardtype()).append(Separate);//证件类型
				sb.append(md.getMd_value()).append(Separate);//账户名称 
				sb.append(md.getCase_type()).append(Separate);//其他信息(名单细类 0001/0002...) 
				sb.append(md.getMd_source()).append(Separate);//加入原因(发送机构 200501/300501...)
				sb.append("").append(Separate);//备注 
				sb.append(md.getBank_id()).append(Separate);//银行编号   
				sb.append(md.getPolice()).append(Separate);//公安联系人
				sb.append(md.getPolice_phone()).append(Separate);//公安联系人电话  
				sb.append(StringUtils.replaceChars(md.getPolice_check_dt(), "-: ", "")).append(Separate);//公安审核通过时间戳
				sb.append(md.getOpen_card_dt()).append(Separate);//开卡时间 (名单冗余项)
				sb.append(StringUtils.replaceChars(md.getStart_dt(), "-: ", "")).append(Separate);//有效期开始时间 
				sb.append(StringUtils.replaceChars(md.getEnd_dt(), "-: ", "")).append(Separate);//有效期结束时间 
				sb.append(md.getRemark());//名单说明
				list.add(sb.toString());
			}
		}
		return list;
	}
	
	/**
	 * 生成txt文本文件并插入一条发送记录
	 * 
	 * @param localpath 本地存放路劲
	 * @param br20_md_info
	 * @param list 库中数据定长处理后的list
	 * @return
	 */
	public abstract String getTxt(String localpath, Br20_md_info br20_md_info, List<String> list);
	
	/**
	 *  是否发送
	 *  1.当前日期大于或等于下次发送时间，就可以发送了 两个日期字串格式必须一致
	 *   2.下次发送时间＋频率 小于 当前时间，说明下次发送时间不正确，纠正下次发送时间为当前时间 
	 * @param nextsendtime
	 * @param amount
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public boolean getSendFlag(String nextsendtime,int amount,int gran) throws ParseException {
		boolean flag = false;
		boolean bzw = false;
		String nowtime = DtUtils.getNowTime();
		String next_send_time = "";
		if ("".equals(nextsendtime)) {// 下次发送时间为空
			this.setNextsendtime(nowtime);
			flag = true;
		}else{
			next_send_time = DtUtils.getTimeByGran(nextsendtime, amount, gran, "yyyy-MM-dd HH:mm:ss");
			bzw = nowtime.compareTo(next_send_time)>0?true:false;
			if(bzw){//下次发送时间错误［过小］，纠正时间，发送标识为true
				this.setNextsendtime(nowtime);
				flag = true;
			}else{//判断是否到达发送时间
				flag = nowtime.compareTo(nextsendtime)>=0?true:false;
			}
		}
		return flag;
	}
	
	/**
	 * ftp上传文件到服务器
	 * 
	 * @param localpath 本地存放路径
	 * @param filename 文件名
	 * @param ftp
	 */
	public boolean ftpToServer(String localpath, String filename, FtpUtils ftp) {
		boolean isSucc = true;
		try {
			
			String server = ServerEnvironment.getStringValue(Keys.HX_FTP_SERVER); // FTP服务器的IP地址
			String user = ServerEnvironment.getStringValue(Keys.HX_FTP_USER); //登录FTP服务器的用户名
			String password = ServerEnvironment.getStringValue(Keys.HX_FTP_PASSWORD); // 登录FTP服务器的用户名的口令
			String remotepath = ServerEnvironment.getStringValue(Keys.HX_FTP_SEND_ADDRESS);//FTP文件存放目录
			
			ftp.setServer(server);
			ftp.setUser(user);
			ftp.setPassword(password);
			ftp.setRemotepath(remotepath);
			ftp.setLocalpath(localpath);
			
			String filepath = localpath + File.separator + filename;
			logger.debug("文件路径＝＝" + filepath);
			ftp.uploadDirectory(filepath);
		} catch (Exception e) {
			logger.info("文件ftp上传失败！");
			e.printStackTrace();
			isSucc = false;
		}
		
		return isSucc;
	}
	
	/**
	 * sftp上传文件到服务器
	 * 
	 * @param localpath 本地存放路径
	 * @param filename 文件名
	 * @param sftp 
	 * @return
	 */
	public boolean sftpToServer(String localpath, String filename, SftpUtils sftp) {
		boolean isSucc = true;
		try {
			
			String host = ServerEnvironment.getStringValue(Keys.HX_SFTP_SERVER); // SFTP服务器的IP地址
			String username = ServerEnvironment.getStringValue(Keys.HX_SFTP_USER); //登录SFTP服务器的用户名
			String password = ServerEnvironment.getStringValue(Keys.HX_SFTP_PASSWORD); // 登录SFTP服务器的用户名的口令
			String remotepath = ServerEnvironment.getStringValue(Keys.HX_SFTP_SEND_ADDRESS);//SFTP文件存放目录
			
			sftp.setHost(host);
			sftp.setUsername(username);
			sftp.setPassword(password);
			
			String filepath = localpath + File.separator + filename;
			logger.debug("文件路径＝＝" + filepath);
			sftp.put(localpath, filename, remotepath, filename);
		} catch (Exception e) {
			logger.info("文件ftp上传失败！");
			e.printStackTrace();
			isSucc = false;
		}
		
		return isSucc;
	}
	
	/**
	 * 发送数据文件
	 * 广发独有--其他地方按实际修改
	 * 
	 * @param srcName 数据文件名
	 * @return
	 */
	public abstract boolean fileToServer(String srcName);
	
	/**
	 * 生成.end文件并发送
	 * 广发独有--其他地方按实际修改
	 * 
	 * @param srcName 数据文件名
	 */
	public abstract boolean fileEndToServer(String srcName);
	
	/**
	 * 处理字节长度问题
	 * 
	 * @param str
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String rightPad(String str, int length) {
		int len = 0;
		try {
			len = str.getBytes("GBK").length;
			if (len >= length) {
				return str;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str + StringUtils.rightPad("", length - len, ' ');
	}
	
}

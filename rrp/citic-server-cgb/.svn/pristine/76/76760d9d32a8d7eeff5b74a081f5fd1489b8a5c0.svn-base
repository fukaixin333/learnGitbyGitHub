package com.citic.server.cgb.outer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.dx.mapper.Br20_md_info_cgbMapper;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.StrUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author yinxiong
 * @date 2016年11月30日
 */
@Component("outerPollingTaskLSS")
public class OuterPollingTaskLSS extends AbstractPollingTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final int BATCH_NUM = 1000;// 批处理最大数量
	
	@Autowired
	public Br20_md_info_cgbMapper br20_md_info_cgbMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private FtpUtils ftp = new FtpUtils();
	
	@Getter
	@Setter
	private volatile String max_all_black_file_name = ""; //最近日期的黑名单全量文件
	@Getter
	@Setter
	private volatile String max_all_gray_file_name = ""; //最近日期的灰名单全量文件
	@Getter
	@Setter
	private volatile String max_black_file_name = ""; //最近日期的黑名单增量文件
	@Getter
	@Setter
	private volatile String max_gray_file_name = ""; //最近日期的灰名单增量文件
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return this;
	}
	
	/**
	 * 执行方法入口
	 */
	@Override
	public void executeAction() {
		try {
			String blackAllFilePath = ServerEnvironment.getStringValue(Keys.FILE_PATH_BLACK_ALL);// 全量黑名单目录
			String grayAllFilePath = ServerEnvironment.getStringValue(Keys.FILE_PATH_GRAY_ALL);// 全量灰名单目录
			String blackFilePath = ServerEnvironment.getStringValue(Keys.FILE_PATH_BLACK_INCREMENT);// 增量黑名单目录
			String grayFilePath = ServerEnvironment.getStringValue(Keys.FILE_PATH_GRAY_INCREMENT);// 增量灰名单目录
			String localpath = ServerEnvironment.getFileRootPath() + File.separator + ServerEnvironment.getStringValue(Keys.FILE_PATH_TEMP) + File.separator + "md"; // 本地名单文件存放目录
			Set<String> set = getWhiteListSet();// 白名单
			
			if (StringUtils.equals("", max_all_black_file_name)) {//最近一个黑名单全量文件
				Br20_md_info md_info = new Br20_md_info();
				md_info.setMd_type("1");// 1黑／2灰
				md_info.setFile_type("2");// 1增量／2全量
				String all_black_name = StrUtils.null2String(br20_md_info_cgbMapper.getMaxFileName(md_info));
				this.setMax_all_black_file_name(all_black_name);
			}
			if (StringUtils.equals("", max_all_gray_file_name)) {//最近一个灰名单全量文件
				Br20_md_info md_info = new Br20_md_info();
				md_info.setMd_type("2");// 1黑／2灰
				md_info.setFile_type("2");// 1增量／2全量
				String all_gray_name = StrUtils.null2String(br20_md_info_cgbMapper.getMaxFileName(md_info));
				this.setMax_all_gray_file_name(all_gray_name);
			}
			if (StringUtils.equals("", max_black_file_name)) {//最近一个黑名单增量文件
				Br20_md_info md_info = new Br20_md_info();
				md_info.setMd_type("1");// 1黑／2灰
				md_info.setFile_type("1");// 1增量／2全量
				String black_name = StrUtils.null2String(br20_md_info_cgbMapper.getMaxFileName(md_info));
				this.setMax_black_file_name(black_name);
			}
			if (StringUtils.equals("", max_gray_file_name)) {//最近一个灰名单增量文件
				Br20_md_info md_info = new Br20_md_info();
				md_info.setMd_type("2");// 1黑／2灰
				md_info.setFile_type("1");// 1增量／2全量
				String gray_name = StrUtils.null2String(br20_md_info_cgbMapper.getMaxFileName(md_info));
				this.setMax_gray_file_name(gray_name);
			}
			// 全量黑名单
			dealAllBlackFile(ftp, blackAllFilePath, localpath, set, max_all_black_file_name);
			// 全量灰名单
			dealAllGrayFile(ftp, grayAllFilePath, localpath, set, max_all_gray_file_name);
			// 增量黑名单
			dealIncrementBlackFile(ftp, blackFilePath, localpath, set, max_black_file_name, max_all_black_file_name);
			// 增量灰名单
			dealIncrementGrayFile(ftp, grayFilePath, localpath, set, max_gray_file_name, max_all_gray_file_name);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 全量黑名单文件接收
	 * 
	 * @param ftp
	 * @param remotepath
	 * @param localpath
	 * @param set
	 * @throws Exception
	 */
	private void dealAllBlackFile(FtpUtils ftp, String remotepath, String localpath, Set<String> set, String alldbafilename) throws Exception {
		logger.info("==[全量黑名单轮询开始]==");
		String flag = br20_md_info_cgbMapper.getAllLoadFlag();// 0：全量加载关闭 1:全量加载开启
		if (StringUtils.equals("1", flag)) {
			// 1.获取远程ftp目录下的所有文件列表
			List<String> ftpfilenamelist = getFtpFileNameList(remotepath, localpath, ftp);
			// 2、与本地已下载入库的文件，进行比较，找出日期最近的一个待处理文件
			String dealname = getDealFileNameListAll(ftpfilenamelist, alldbafilename);
			// 3、处理文件
			if (!StringUtils.equals("", dealname)) {
				int m = 0;//记录处理成功数
				int n = 0;//记录处理失败数
				List<String> deallist = Lists.newArrayList();
				deallist.add(dealname);
				ftp.getfileMoreChk(deallist);// 下载文件到本地
				// 解析本地文件
				Br20_md_info filelog = getFileDataAll(localpath, deallist.get(0), "1", "2", set);// 批量处理全量数据文件
				if ("3".equals(filelog.getFlag())) {
					m = 1;
					n = 0;
				} else {
					m = 0;
					n = 1;
				}
				br20_md_info_cgbMapper.insertBr20_md_accept(filelog);// 插入一条文件接收日志
				this.setMax_all_black_file_name(dealname);//更改最近日期文件名
				logger.info("==[本次全量黑名单轮询结果]==下载文件数：1" + "==处理成功数：" + m + "==处理失败数：" + n);
				
			} else {
				logger.info("==[本次全量黑名单轮询结果]==没有需要下载处理的文件");
			}
		} else {
			logger.info("==[black全量名单加载未开启]==将不会下载处理全量黑名单文件");
		}
		
	}
	
	/**
	 * 全量灰名单接收
	 * 
	 * @param ftp
	 * @param remotepath
	 * @param localpath
	 * @param set
	 * @throws Exception
	 */
	private void dealAllGrayFile(FtpUtils ftp, String remotepath, String localpath, Set<String> set, String alldbafilename) throws Exception {
		logger.info("==[全量灰名单轮询开始]===");
		String flag = br20_md_info_cgbMapper.getAllLoadFlag();// 0：全量加载关闭 1:全量加载开启
		if (StringUtils.equals("1", flag)) {
			// 1.获取远程ftp目录下的所有文件列表
			List<String> ftpfilenamelist = getFtpFileNameList(remotepath, localpath, ftp);
			//2、与本地已处理完成的文件，进行比较，找出待处理文件
			String dealname = getDealFileNameListAll(ftpfilenamelist, alldbafilename);
			if (!StringUtils.equals("", dealname)) {
				int m = 0;//记录处理成功数
				int n = 0;//记录处理失败数
				List<String> deallist = Lists.newArrayList();
				deallist.add(dealname);
				ftp.getfileMoreChk(deallist);// 下载文件到本地
				// 解析本地文件
				Br20_md_info filelog = getFileDataAll(localpath, deallist.get(0), "2", "2", set);// 批量处理全量数据文件
				if ("3".equals(filelog.getFlag())) {
					m = 1;
					n = 0;
				} else {
					m = 0;
					n = 1;
				}
				br20_md_info_cgbMapper.insertBr20_md_accept(filelog);// 插入一条文件接收日志
				this.setMax_all_gray_file_name(dealname);//更改最近日期文件名
				logger.info("==[本次全量灰名单轮询结果]==下载文件数：1" + "==处理成功数：" + m + "==处理失败数：" + n);
				
			} else {
				logger.info("==[本次全量灰名单轮询结果]==没有需要下载处理的文件");
			}
		} else {
			logger.info("==[gray全量名单加载未开启]==将不会下载处理全量灰名单文件");
		}
	}
	
	/**
	 * 增量黑名单接收
	 * 
	 * @param ftp
	 * @param remotepath
	 * @param localpath
	 * @param set
	 * @throws Exception
	 */
	private void dealIncrementBlackFile(FtpUtils ftp, String remotepath, String localpath, Set<String> set, String dbafilename, String alldbafilename) throws Exception {
		logger.info("==[增量黑名单轮询开始]==");
		// 1.获取远程ftp目录下的所有文件列表
		List<String> ftpfilenamelist = getFtpFileNameList(remotepath, localpath, ftp);
		// 2、与本地已处理完成的文件，进行比较，找出待处理文件
		String filename = getMaxFileName(alldbafilename, dbafilename);
		List<String> deallist = getDealFileNameList(ftpfilenamelist, filename);
		
		if (deallist != null && deallist.size() > 0) {
			int m = 0;//记录有内容，且解析成功的文件个数
			int n = 0;//记录解析异常＋内容为空的文件个数
			ftp.getfileMoreChk(deallist);// 下载文件到本地
			for (int i = 0; i < deallist.size(); i++) {// 逐个解析本地文件
				Br20_md_info filelog = getFileData(localpath, deallist.get(i), "1", "1");
				if ("3".equals(filelog.getFlag())) {
					m++;
				} else {
					n++;
				}
				br20_md_info_cgbMapper.insertBr20_md_accept(filelog);// 插入一条文件接收日志
			}
			//更新 最近增量黑名单文件的名称
			String max_black_filename = getNearFileName(deallist);
			this.setMax_black_file_name(max_black_filename);
			logger.info("==[本次增量黑名单轮询结果]==下载文件数：" + deallist.size() + "==内容存在且处理成功文件数：" + m + "==解析异常文件数：" + n);
		} else {
			logger.info("==[本次增量黑名单轮询结果]==没有需要下载处理的文件");
		}
		
	}
	
	/**
	 * 增量灰名单接收
	 * 
	 * @param ftp
	 * @param remotepath
	 * @param localpath
	 * @param set
	 * @throws Exception
	 */
	private void dealIncrementGrayFile(FtpUtils ftp, String remotepath, String localpath, Set<String> set, String dbafilename, String alldbafilename) throws Exception {
		logger.info("==[增量灰名单单轮询开始]==");
		// 1.获取远程ftp目录下的所有文件列表
		List<String> ftpfilenamelist = getFtpFileNameList(remotepath, localpath, ftp);
		// 2、与本地已处理完成的文件，进行比较，找出待处理文件
		String filename = getMaxFileName(alldbafilename, dbafilename);
		List<String> deallist = getDealFileNameList(ftpfilenamelist, filename);
		if (deallist != null && deallist.size() > 0) {
			int m = 0;//记录有内容，且解析成功的文件个数
			int n = 0;//记录解析异常＋内容为空的文件个数
			ftp.getfileMoreChk(deallist);// 下载文件到本地
			for (int i = 0; i < deallist.size(); i++) {// 逐个解析本地文件
				Br20_md_info filelog = getFileData(localpath, deallist.get(i), "2", "1");
				if ("3".equals(filelog.getFlag())) {
					m++;
				} else {
					n++;
				}
				br20_md_info_cgbMapper.insertBr20_md_accept(filelog);// 插入一条文件接收日志
			}
			//更新 最近增量灰名单文件的名称
			String max_gray_filename = getNearFileName(deallist);
			this.setMax_gray_file_name(max_gray_filename);
			logger.info("==[本次增量灰名单轮询结果]==下载文件数：" + deallist.size() + "==内容存在且处理成功文件数：" + m + "==解析异常文件数：" + n);
		} else {
			logger.info("==[本次增量灰名单轮询结果]==没有需要下载处理的文件");
		}
	}
	
	/**
	 * ftp获取远程服务器上的指定目录下的文件名称list
	 * 
	 * @param remotepath
	 *        FTP服务器上的路径
	 * @param localpath
	 *        本地路径
	 * @return
	 * @throws Exception
	 */
	private List<String> getFtpFileNameList(String remotepath, String localpath, FtpUtils ftp) throws Exception {
		String server = ServerEnvironment.getStringValue(Keys.FTP_REMOTE_SERVER); // FTP服务器的IP地址
		String user = ServerEnvironment.getStringValue(Keys.FTP_REMOTE_USER); // 登录FTP服务器的用户名
		String password = ServerEnvironment.getStringValue(Keys.FTP_REMOTE_PASSWORD); // 登录FTP服务器的用户名的口令
		
		ftp.setServer(server);
		ftp.setUser(user);
		ftp.setPassword(password);
		ftp.setRemotepath(remotepath);
		ftp.setLocalpath(localpath);
		List<String> ftpfilenamelist = Lists.newArrayList();
		ftpfilenamelist = ftp.getFileNameList(remotepath);
		
		return ftpfilenamelist;
	}
	
	/**
	 * 找出库中黑／灰名单最近日期文件
	 * 
	 * @param max_all_file_name
	 *        库中最近全量文件名
	 * @param max_file_name
	 *        库中最近增量文件名
	 * @return
	 */
	private String getMaxFileName(String max_all_file_name, String max_file_name) {
		String file_name = max_file_name;
		if (!StringUtils.equals("", max_all_file_name)) {
			String all = max_all_file_name.replace("all_", "").replace(".txt", "") + "000000.txt";
			if (getDealFlag(all, max_file_name)) {// all>max_file_name
				file_name = all;
			}
		}
		return file_name;
	}
	
	/**
	 * 获取待ftp处理文件名list
	 * 
	 * @param dba_filename
	 *        库存最近日期的文件名
	 * @param filenamelist
	 *        ftp获取的文件名list
	 * @return
	 */
	private List<String> getDealFileNameList(List<String> ftpfilenamelist, String dba_filename) {
		List<String> newList = null;
		if (dba_filename != null && !StringUtils.equals(dba_filename, "")) {
			if (ftpfilenamelist != null && ftpfilenamelist.size() > 0) {
				newList = Lists.newArrayList();
				for (int i = 0; i < ftpfilenamelist.size(); i++) {
					if (getDealFlag(ftpfilenamelist.get(i), dba_filename)) {// 文件日期大于数据库中最近文件日期。即待ftp处理文件
						newList.add(ftpfilenamelist.get(i));
					}
				}
			}
		} else {
			newList = ftpfilenamelist;
		}
		
		return newList;
	}
	
	/**
	 * 获取待ftp处理的最近日期的全量文件名
	 * 
	 * @param dba_filenamelist
	 *        库存文件名list
	 * @param filenamelist
	 *        ftp获取的文件名list
	 * @return
	 */
	private String getDealFileNameListAll(List<String> ftpfilenamelist, String dba_filename) {
		String max_date_filename = "";// 最近的一个全量文件名
		
		if (ftpfilenamelist != null && ftpfilenamelist.size() > 0) {
			String[] a = new String[ftpfilenamelist.size()];
			ftpfilenamelist.toArray(a);
			Arrays.sort(a);
			max_date_filename = a[a.length - 1];
			if (!getDealFlag(max_date_filename, dba_filename)) {// 文件已经处理过，不再处理
				max_date_filename = "";
			}
		}
		return max_date_filename;
	}
	
	/**
	 * 找出list中最大日期的文件名
	 * 
	 * @param ftpfilenamelist
	 * @return
	 */
	private String getNearFileName(List<String> ftpfilenamelist) {
		String max_filename = "";// 最近的一个全量文件名
		
		if (ftpfilenamelist != null && ftpfilenamelist.size() > 0) {
			String[] a = new String[ftpfilenamelist.size()];
			ftpfilenamelist.toArray(a);
			Arrays.sort(a);
			max_filename = a[a.length - 1];
		}
		return max_filename;
	}
	
	/**
	 * 解析增量文件数据<br>
	 * 先插入log表，然后根据log表的该批次数据，删除data表中的重复且非白名单数据<br>
	 * 然后向data插入该批次且不存在于白名单的数据
	 * @param localpath
	 * @param filename
	 * @param md_type 1:黑名单 2:灰名单
	 * @param file_type
	 * @return
	 */
	private Br20_md_info getFileData(String localpath, String filename, String md_type, String file_type) {
		long a = System.currentTimeMillis();
		String filepath = localpath + File.separator + filename;// 本地文件路径
		String file_code = UUID.randomUUID().toString().replaceAll("-", "");// 文件编号
		String accept_time = DtUtils.getNowTime();
		
		InputStreamReader read = null;
		BufferedReader br = null;
		Br20_md_info filelog = null;
		List<Br20_md_info> list = Lists.newArrayList();
		int count = 0;// 待处理数据总数
		int n = 0;// 待处理数据总数／BATCH_NUM 后的余数
		try {
			//信用卡的卡bin
			String creditbin = ServerEnvironment.getStringValue(CgbKeys.MD_CREDIT_BIN);
			String[] arr = creditbin.split(",");
			Set<String> creditSet = new HashSet<String>(Arrays.asList(arr));
			//接收文件日志信息
			filelog = new Br20_md_info();
			filelog.setFile_code(file_code);// 暂时用uuid
			filelog.setFile_name(filename);
			filelog.setAccept_time(accept_time);
			filelog.setMd_type(md_type);
			filelog.setFile_type(file_type);
			filelog.setMd_source("公安");
			filelog.setFlag("1");// 1:接收成功 2:处理失败 3：处理成功
			//数据读取
			read = new InputStreamReader(new FileInputStream(filepath), "utf-8");// 处理中文乱码
			br = new BufferedReader(read);
			String r = br.readLine();
			while (r != null) {
				if (!StringUtils.equals("", r)) {// 保证数据存在
					String str[] = r.split("\\$\\$", -1);
					//字断缺失的数据不处理
					if (str.length > 12) {
						count++;
						n = count % BATCH_NUM;
						Br20_md_info t = new Br20_md_info();
						// 文件中的数据
						t.setMd_kind(str[0]);// 种类
						if ("IDType_IDNumber".equals(str[0])) {
							int index = str[1].indexOf("_");
							if (index > 0 && str[1].length() > (index + 1)) {// "_"前面存在证件类型，后面存在证件号码
								t.setCardtype(str[1].substring(0, index));
								t.setMd_value(str[1].substring(index + 1));// 数据值
							} else {// 错误的数据不存，直接读取下一行
								r = br.readLine();
								continue;
							}
						} else {
							t.setMd_value(str[1]);// 数据值
						}
						t.setOperate_flag(str[2]);// 操作标志
						t.setMd_source(str[3]);// 名单来源
						t.setCase_type(str[4]);// 案件类型
						t.setP_name(str[5]);// 姓名
						if (str[6].length() > 12) {// 银行编号 12  
							t.setBank_id(str[6].substring(0, 12));//解决人工数据字段超长的问题
						} else {
							t.setBank_id(str[6]);
						}
						t.setPolice(str[7]);// 公安联系人 60
						t.setPolice_phone(str[8]);// 公安联系人电话 30
						if (!StringUtils.equals("", str[9])) {// 公安审核通过黑名单时间戳 19
							//解决人工造数时时间长度非法的问题
							if (str[9].length() == 14) {
								t.setPolice_check_dt(DtUtils.toStrTimeStamp(str[9]));
							} else {
								logger.warn("==公安审核通过黑名单时间戳:" + str[9] + "==约定长度14，长度非法,将默认为当前时间==");
								t.setPolice_check_dt(DtUtils.getNowTime());
							}
						}else{
							t.setPolice_check_dt(DtUtils.getNowTime());
						}
						if (!StringUtils.equals("", str[10])) {//名单类型冗余项(原先的开卡时间)
							//解决人工造数长度非法的问题
							if (str[10].length() <= 14) {
								t.setOpen_card_dt(str[10]);
							} else {
								logger.warn("==名单类型冗余项:" + str[10] + "==约定长度14，长度非法,将取默认值空字符串==");
							}
						}
						if (!StringUtils.equals("", str[11])) {// 名单有效期＝开始时间＋“-”＋结束时间
							if (str[11].length() == 29) {
								t.setStart_dt(DtUtils.toStrTimeStamp(str[11].substring(0, 14)));
								t.setEnd_dt(DtUtils.toStrTimeStamp(str[11].substring(15)));
							} else {
								logger.warn("==名单有效期:" + str[11] + "==约定长度29，长度非法,将取默认值空字符串==");
							}
						}
						t.setRemark(str[12]);// 名单说明225
						// =========其他需要的数据===========
						t.setIs_inner("0");//是否行内名单 0:否 1:是
						t.setInterface_type("0"); //调用接口类型 0:不调用接口1:核心接口2:信用卡接口
						if (str[6].startsWith("306")) {//306开头的是广发银行
							t.setIs_inner("1");
							//行内名单&&发送机构为200501&&黑名单
							if ("AccountNumber".equals(str[0]) && "1".equals(md_type) && "200501".equals(str[3])) {
								if ("0001".equals(str[4])) {//名单类型名为0001时，用creditset区分信用卡,名单值的前6位符合卡bin的规则
									if (creditSet.contains(str[1].substring(0, 6))) {
										t.setInterface_type("2");
									} else {
										t.setInterface_type("1");
									}
								} else if ("0002".equals(str[4])) {
									t.setInterface_type("1");
								}
							}
						}
						t.setSend_type("0");//发送类型 0:都发送1:发送核心 2:发送信用卡
						t.setCredit_send_flag("0");//信用卡发送标志 0:未发送 1:已发送
						t.setCredit_failed_num(0);//信用卡发送失败次数
						
						t.setMd_code(UUID.randomUUID().toString().replaceAll("-", ""));// 名单编号
						t.setMd_type(md_type);// 名单类型
						t.setSend_flag("0");// 发送标志 0:为发送 1:已发送
						t.setAccept_time(accept_time);// 文件接收时间
						t.setMd_flag("1");// 名单状态 0:无效 1:有效 2:白名单
						t.setLast_update_time(accept_time);// 更新时间
						t.setFile_code(file_code);// 文件编码
						t.setFile_name(filename);// 文件名
						list.add(t);
						if (n == 0) {
							doBatchSqlByLog(list);
							list.clear();
						}
					} else {
						logger.warn("==数据内容：" + r + "==约定字段个数13，字段个数非法==该条数据将会被跳过");
					}
				}
				r = br.readLine();
			}
			if (r == null && list != null && list.size() > 0) {// 文件最后一次读取，不满足BATCH_NUM的条数
				doBatchSqlByLog(list);// 批量处理
				list.clear();
			}
			filelog.setFlag("3");
		} catch (Exception e) {
			filelog.setFlag("2");//设置文件状态为处理失败
			list.clear();//清空list
			logger.error("==[文件解析异常]==文件名称：" + filename + "==当前读取有效数据行：" + count,e);
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(read!=null){
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				//删除Br20_md_data关于该批次的数据
				long x = System.currentTimeMillis();
				br20_md_info_cgbMapper.deleteBr20_md_dataByFlagAndBatch(file_code);
				logger.info("========删除耗时："+(System.currentTimeMillis()-x)+"毫秒");
				//插入最新的有效记录
				br20_md_info_cgbMapper.insertBr20_md_dataByNewAndBatch(filelog);
			} catch (Exception e) {
				logger.error("==br20_md_data入库异常："+e.getMessage(),e);
			}
		}
		logger.info("========增量耗时："+(System.currentTimeMillis()-a)+"毫秒");
		return filelog;
	}
	
	
	/**
	 * 获取白名单的数据set
	 * 
	 * @return
	 */
	private Set<String> getWhiteListSet() {
		Set<String> s = null;
		List<Br20_md_info> whitelist = br20_md_info_cgbMapper.getWhiteList("");
		if (whitelist != null && whitelist.size() > 0) {
			s = Sets.newHashSet();
			for (int i = 0; i < whitelist.size(); i++) {
				s.add(whitelist.get(i).getMd_type() + whitelist.get(i).getMd_value());// 名单的类型[黑／灰]＋值
				
			}
		}
		return s;
	}
	
	/**
	 * 全量数据加载<br>
	 * 先插入log表(br20_md_data_log),然后根据log表去重插入到
	 * data表(br20_md_data)中，保证data表为最新的数据
	 * 
	 * @param localpath
	 * @param filename
	 * @param md_type 1:黑名单 2:灰名单
	 * @param file_type
	 * @param set
	 * @return
	 */
	private Br20_md_info getFileDataAll(String localpath, String filename, String md_type, String file_type, Set<String> set) {
		
		String filepath = localpath + File.separator + filename;// 本地文件路径
		String file_code = UUID.randomUUID().toString().replaceAll("-", "");// 文件编号
		String accept_time = DtUtils.getNowTime();
		
		InputStreamReader read = null;
		BufferedReader br = null;
		List<Br20_md_info> list = Lists.newArrayList();
		Br20_md_info filelog = null;
		int count = 0;// 待处理数据总数
		int n = 0;// 待处理数据总数／BATCH_NUM 后的余数
		try {
			//信用卡的卡bin
			String creditbin = ServerEnvironment.getStringValue(CgbKeys.MD_CREDIT_BIN);
			String[] arr = creditbin.split(",");
			Set<String> creditSet = new HashSet<String>(Arrays.asList(arr));
			// 接收文件日志信息
			filelog = new Br20_md_info();// 接受的文件信息
			filelog.setFile_code(file_code);// 暂时用uuid
			filelog.setFile_name(filename);
			filelog.setAccept_time(accept_time);
			filelog.setMd_type(md_type);
			filelog.setFile_type(file_type);
			filelog.setMd_source("公安");
			filelog.setFlag("1");// 1:接收成功 2:处理失败 3：处理成功
			//数据读取
			read = new InputStreamReader(new FileInputStream(filepath), "utf-8");// 处理中文乱码
			br = new BufferedReader(read);
			String r = br.readLine();
			boolean whiteflag = false;
			if (set != null && !set.isEmpty()) {
				whiteflag = true;
			}
			// 清理数据日志表和数据表中的数据
			br20_md_info_cgbMapper.deleteBr20_md_data_log(md_type);
			br20_md_info_cgbMapper.deleteBr20_md_dataByVo(md_type);
			
			while (r != null) {
				if (!StringUtils.equals("", r)) {// 保证数据存在
					String str[] = r.split("\\$\\$", -1);// 文件中的数据
					//字断缺失的数据不处理
					if (str.length > 12) {
						String key = md_type + str[1];
						if (whiteflag && set.contains(key)) {// 数据存在于白名单中，不入库
							logger.error("==白名单数据==名单类型：" + md_type + "==数据值:" + str[1]);
						} else {
							count++;
							n = count % BATCH_NUM;
							Br20_md_info t = new Br20_md_info();
							t.setMd_kind(str[0]);// 种类
							if ("IDType_IDNumber".equals(str[0])) {// 身份类型的名单
								int index = str[1].indexOf("_");
								if (index > 0 && str[1].length() > (index + 1)) {// "_"前面存在证件类型，后面存在证件号码
									t.setCardtype(str[1].substring(0, index));
									t.setMd_value(str[1].substring(index + 1));// 数据值
								} else {
									r = br.readLine();
									continue;
								}
								
							} else {
								t.setMd_value(str[1]);// 数据值
							}
							t.setOperate_flag(str[2]);// 操作标志
							t.setMd_source(str[3]);// 名单来源
							t.setCase_type(str[4]);// 案件类型--名单类型（原黑名单类型，0001/0002/...）
							t.setP_name(str[5]);// 姓名
							//当数据类型为AccountNumber且黑名单类型为0001/0002/0003/1004时必输
							if (str[6].length() > 12) {// 银行编号 12
								t.setBank_id(str[6].substring(0, 12));//解决人工数据字段超长的问题
							} else {
								t.setBank_id(str[6]);
							}
							t.setPolice(str[7]);// 公安联系人 60
							t.setPolice_phone(str[8]);// 公安联系人电话 30
							if (!StringUtils.equals("", str[9])) {// 公安审核通过黑名单时间戳 19
								//解决人工造数时时间长度非法的问题
								if (str[9].length() == 14) {
									t.setPolice_check_dt(DtUtils.toStrTimeStamp(str[9]));
								} else {
									logger.warn("==公安审核通过黑名单时间戳:" + str[9] + "==约定长度14，长度非法,将默认当前时间==");
									t.setPolice_check_dt(DtUtils.getNowTime());
								}
							}else{
								t.setPolice_check_dt(DtUtils.getNowTime());
							}
							if (!StringUtils.equals("", str[10])) {//名单类型冗余项(原先的开卡时间)
								//解决人工造数长度非法的问题
								if (str[10].length() <= 14) {
									t.setOpen_card_dt(str[10]);
								} else {
									logger.warn("==名单类型冗余项:" + str[10] + "==约定长度14，长度非法,将取默认值空字符串==");
								}
							}
							if (!StringUtils.equals("", str[11])) {// 名单有效期＝开始时间＋“-”＋结束时间
								if (str[11].length() == 29) {
									t.setStart_dt(DtUtils.toStrTimeStamp(str[11].substring(0, 14)));
									t.setEnd_dt(DtUtils.toStrTimeStamp(str[11].substring(15)));
								} else {
									logger.warn("==名单有效期:" + str[11] + "==约定长度29，长度非法,将取默认值空字符串==");
								}
							}
							t.setRemark(str[12]);// 名单说明225
							// =========其他需要的数据===========
							t.setIs_inner("0");//是否行内名单 0:否 1:是
							t.setInterface_type("0"); //调用接口类型 0:不调用接口1:核心接口2:信用卡接口
							if (str[6].startsWith("306")) {//306开头的是广发银行
								t.setIs_inner("1");
								//行内名单&&发送机构为200501&&黑名单
								if ("AccountNumber".equals(str[0]) && "1".equals(md_type) && "200501".equals(str[3])) {
									if ("0001".equals(str[4])) {//名单类型名为0001时，用creditset区分信用卡,名单值的前6位符合卡bin的规则
										if (creditSet.contains(str[1].substring(0, 6))) {
											t.setInterface_type("2");
										} else {
											t.setInterface_type("1");
										}
									} else if ("0002".equals(str[4])) {
										t.setInterface_type("1");
									}
								}
							}
							t.setSend_type("0");//发送类型 0:都发送1:发送核心 2:发送信用卡
							t.setCredit_send_flag("0");//行用卡发送标志 0:为发送 1:已发送
							t.setCredit_failed_num(0);//行用卡发送失败次数
							
							t.setMd_code(UUID.randomUUID().toString().replaceAll("-", ""));// 名单编号
							t.setMd_type(md_type);// 名单类型
							t.setSend_flag("0");// 发送标志 0:为发送 1:已发送
							t.setAccept_time(accept_time);// 文件接收时间
							t.setMd_flag("1");// 名单状态 0:无效 1:有效 2:白名单
							t.setLast_update_time(accept_time);// 更新时间
							t.setFile_code(file_code);// 文件编码
							t.setFile_name(filename);// 文件名
							list.add(t);
							if (n == 0) {
								doBatchSqlByLog(list);// 批量处理
								list.clear();
							}
						}
					} else {
						logger.warn("==数据内容：" + r + "==约定字段个数13，字段个数非法==该条数据将会被跳过");
					}
				}
				r = br.readLine();
			}
			
			if (r == null && list != null && list.size() > 0) {// 文件最后一次读取，不满足BATCH_NUM的条数
				doBatchSqlByLog(list);// 批量处理
				list.clear();
			}
			filelog.setFlag("3");
		} catch (Exception e) {
			filelog.setFlag("2");// 1:接收成功 2:处理失败 3：处理成功
			logger.error("==全量文件处理异常，请检查数据内容合理性==文件名称：" + filename + "==当前读取有效数据行" + count,e);
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(read!=null){
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				//执行入库sql
				br20_md_info_cgbMapper.insertBr20_md_dataByNewAndBatch(filelog);
			} catch (Exception e) {
				logger.error("==br20_md_data入库异常："+e.getMessage(),e);
			}
		}
		
		return filelog;
	}
	
	/**
	 * br20_md_data表批量插入
	 * 
	 * @param list
	 * @author yinxiong
	 * @date 2017年2月15日
	 */
//	private void doBatchSqlByData(List<Br20_md_info> list) {
//		List<Object[]> batch_data = new ArrayList<Object[]>(list.size());// 插入数据表
//		for (Br20_md_info md : list) {
//			Object[] values_data = new Object[] { md.getMd_code(), md.getMd_type(), md.getMd_kind(), md.getCardtype(), md.getMd_value(), md.getP_name(), md.getMd_source(), md.getCase_type(),
//					md.getMd_flag(), md.getLast_update_time(), md.getLast_update_user(), md.getBank_id(), md.getPolice(), md.getPolice_phone(), md.getPolice_check_dt(), md.getOpen_card_dt(),
//					md.getStart_dt(), md.getEnd_dt(), md.getRemark() };
//			batch_data.add(values_data);
//		}
//		String sql_data = "insert into br20_md_data(md_code,md_type,md_kind,cardtype,md_value,p_name,md_source,case_type,md_flag,last_update_time,last_update_user,bank_id,police,police_phone,police_check_dt,open_card_dt,start_dt,end_dt,remark)"
//				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		jdbcTemplate.batchUpdate(sql_data, batch_data);
//
//		batch_data.clear();
//	}

	/**
	 * br20_md_data_log表批量提交
	 * 
	 * @param list
	 * @author yinxiong
	 * @date 2017年2月15日
	 */
	private void doBatchSqlByLog(List<Br20_md_info> list) {
		List<Object[]> batch_log = new ArrayList<Object[]>(list.size());// 插入数据日志表
		for (Br20_md_info md : list) {
			Object[] values_log = new Object[] { md.getFile_code(), md.getMd_code(), md.getMd_type(), md.getMd_kind(), md.getCardtype(), md.getMd_value(), md.getP_name(), md.getMd_source(),
					md.getCase_type(), md.getOperate_flag(), md.getSend_flag(), md.getAccept_time(), md.getBank_id(), md.getPolice(), md.getPolice_phone(), md.getPolice_check_dt(),
					md.getOpen_card_dt(), md.getStart_dt(), md.getEnd_dt(), md.getRemark(), md.getInterface_type(), md.getSend_type(), md.getCredit_send_flag(), md.getCredit_send_time(),
					md.getCredit_status(), md.getCredit_sbyy(), md.getCredit_failed_num(), md.getIs_inner() };
			batch_log.add(values_log);
		}
		String sql_log = "insert into br20_md_data_log(file_code,md_code,md_type,md_kind,cardtype,md_value,p_name,md_source,case_type,operate_flag,send_flag,accept_time,bank_id,police,police_phone,police_check_dt,open_card_dt,start_dt,end_dt,remark,interface_type,send_type,credit_send_flag,credit_send_time,credit_status,credit_sbyy,credit_failed_num,is_inner)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql_log, batch_log);

		batch_log.clear();
	}
	/**
	 * 文件是否处理 文件名格式必须一致，比较才会准确
	 * filename比dbname，说明文件没有处理过
	 * 
	 * @param filename
	 *        待处理文件
	 * @param dbname
	 *        数据库中最近日期文件
	 * @return
	 */
	public boolean getDealFlag(String filename, String dbname) {
		boolean flag = false;
		if (dbname != null && !StringUtils.equals(dbname, "")) {
			int result = filename.compareTo(dbname);
			if (result > 0) {
				flag = true;
			}
		} else {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 缓存加载配置
	 * 不需要缓存，返回null
	 */
	@Override
	protected String getTaskType() {
		return null;
	}
	
	/**
	 * 轮询名单时间间隔
	 * cfca每5分钟左右生成一个文件，此处设置为5分钟
	 */
	@Override
	protected String getExecutePeriodExpression() {
		return "5";
	}
}

package com.citic.server.service.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.utils.ExecmdUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.UnZipUtils;

/**
 * 从数据文件装在数据到ODS中 根据数据源表，实现分组并行处理
 * 
 * @author hubaiqing
 * @version 1.0
 */
@Component("TK_ETL201")
public class TK_ETL201 extends BaseTask {

	private static final Logger logger = LoggerFactory
			.getLogger(TK_ETL201.class);

	private MC00_datasource mC00_datasource;
	private FileUtils fileUtils = new FileUtils();

	
	public TK_ETL201() {
		super();
	}

	public TK_ETL201(ApplicationContext ac, MC00_task_fact mC00_task_fact) {
		super(ac, mC00_task_fact);
	}

	public boolean calTask() throws Exception {

		boolean isSuccess = false;

		String dsid = this.getMC00_task_fact().getDsid();
		String datatime = this.getMC00_task_fact().getDatatime();
		String taskid = this.getMC00_task_fact().getTaskid();
		String subtaskid = this.getMC00_task_fact().getSubtaskid();
		//
		HashMap datasourceMap = (HashMap) this.getCacheService().getCache(
				"datasource", HashMap.class);
		mC00_datasource = (MC00_datasource) datasourceMap.get(dsid);
		
		//=== 部分数据获取方式，不需要数据文件加载过程执行，直接返回正确即可
		String dgetmedthod = this.getMC00_datasource().getDgetmethod();
		String dputlocation = this.getMC00_datasource().getDputlocation();
		String dgetlocation = this.getMC00_datasource().getDgetlocation();
		boolean needExec = false;
		if(
			(dgetmedthod.equalsIgnoreCase("get") && dgetlocation.equalsIgnoreCase("jdbc2localfile"))
			|| (dgetmedthod.equalsIgnoreCase("get") && dgetlocation.equalsIgnoreCase("ftp2localfile"))
			|| (dgetmedthod.equalsIgnoreCase("get") && dgetlocation.equalsIgnoreCase("cmd2localfile"))
			|| (dgetmedthod.equalsIgnoreCase("put") && dputlocation.equalsIgnoreCase("localfile"))
			){
			needExec = true;
			//
		}
		
		if(!needExec){//不需要装载数据，直接进入表格
			return true;
		}
		//===

		//String mysubtaskid = dsid + "-" + subtaskid;// 子任务编码，带数据源属性；

		MC00_ds_tables mC00_ds_tables = new MC00_ds_tables();

		HashMap dstMap = (HashMap) this.getCacheService().getCache(
				"ds_tables", HashMap.class);
		HashMap filterCodeMap = (HashMap) this.getCacheService().getCache(
				"code_filter", HashMap.class);

		if (dstMap.containsKey(taskid)) {

			HashMap subtaskMap = (HashMap) dstMap.get(taskid);
			UnZipUtils unZipUtils = new UnZipUtils();
			//远程文件扩展名
			String fileext = this.getMC00_datasource().getFtpfileext();
			FileUtils fileUtils = new FileUtils();
			fileext = fileUtils.replateDTpath(fileext,datatime);
			
			
			if (subtaskMap.containsKey(subtaskid)) {
				
				ArrayList subList = (ArrayList)subtaskMap.get(subtaskid);
				Iterator subIter = subList.iterator();
				while(subIter.hasNext()){
					mC00_ds_tables = (MC00_ds_tables) subIter.next();
					//文件解压缩
					String zip = mC00_ds_tables.getZip();
					
					boolean unzip = true;
					if(zip!=null && !zip.equals("")){//需要解压缩
						
						String tablename = mC00_ds_tables.getTablename();
						String filter = mC00_ds_tables.getFilter();//add by jiangdm in 20150921
						String inEncoding = "GBK";
						if(mC00_ds_tables.getSencode()!=null && !mC00_ds_tables.getSencode().equals("")){
							inEncoding = mC00_ds_tables.getSencode();
						}
						String outEncoding = "UTF-8";
						if(mC00_ds_tables.getTencode()!=null && !mC00_ds_tables.getTencode().equals("")){
							outEncoding = mC00_ds_tables.getTencode();
						}
						boolean isFilter = false;
						if(mC00_ds_tables.getZipfilter()==null || mC00_ds_tables.getZipfilter().equals("false")){
							isFilter = false;
						}
						else if(mC00_ds_tables.getZipfilter().equals("true")){
							isFilter = true;
						}
						
		            	String filename = tablename;
		            	if(!fileext.equals("")){
		            		filename = tablename + fileext ;
		            	} 
		            	
		            	//====================================
		                //本地路径：FTP过来的直接进入工作目录：在目录“YYYY-MM-DD_DSKEY”下
		                //====================================
		                //String localpath = this.t18_datasource.getDspath()+File.separator+ftppath;
		                String workpath = this.getMC00_datasource().getLworkpath();
		                if (!workpath.endsWith(File.separator)) {
		                	workpath += File.separator;
		                }
		                //localpath=workpath/yyyy-mm-dd_dsid
		                String localpath = workpath + datatime +"_"+ this.getMC00_datasource().getDsid() + File.separator;
		            	
						try{
							logger.info("开始解压缩文件："+filename+"  -->  "+tablename);
							if(zip.equalsIgnoreCase("zip")){
								unZipUtils.unCompZ(localpath+filename, localpath+tablename, inEncoding, outEncoding, isFilter, filter, filterCodeMap);
							}
							else if(zip.equalsIgnoreCase("gzip")){
								unZipUtils.unCompGzip(localpath+filename, localpath+tablename, inEncoding, outEncoding, isFilter, filter, filterCodeMap);
							}
							else{
								unZipUtils.unCompGzip(localpath+filename, localpath+tablename, inEncoding, outEncoding, isFilter, filter, filterCodeMap);
							}
							logger.info("文件："+filename+"  -->  "+tablename +" 解压缩 OK!");
						}catch(Exception e){
							unzip = false;
							e.printStackTrace();
						}
						
					}else{
						logger.info("文件不需要解压缩，可以直接装载："+mC00_ds_tables.getTablename());
					}
					
					
					//文件装载
//					if(unzip){
						isSuccess = true;//暂时注释掉
						isSuccess = this.calTaskTable(mC00_ds_tables, datatime);
//					}
					if(!isSuccess){
						break;
					}
					
				}
				
			}

		}
		
		

		return isSuccess;
	}
	
	private boolean calTaskTable(MC00_ds_tables mC00_ds_tables,String datatime) throws Exception{
		
		boolean isSuccess;
		
		if (mC00_ds_tables.getDsid().equals("")) {
			throw new Exception("未找到装在脚本命令！请检查mC00_ds_tables表配置情况！");
		}

		String cmd = mC00_ds_tables.getEtlcmd().trim();
		FileUtils fileUtils = new FileUtils();
		cmd = fileUtils.replateDTpath(cmd, datatime);
		
		// 真正的命令路径（被处理过的，可执行命令）
		// cmdFileDirStr=lworkpath/yyyy-mm-dd_S01/bin
		String dataFileDir = this.getMC00_datasource().getLworkpath()
				+ File.separator + datatime + "_"
				+ this.getMC00_datasource().getDsid();
		String cmdFileDir = dataFileDir + File.separator + "bin";

		try {
			String cmdStr = cmdFileDir + File.separator + cmd;
			if (cmdStr == null) {
				throw new Exception("数据装在命令为空！");
			}

			File cFile = new File(cmdFileDir);
			if(!cFile.exists()){
				cFile.mkdirs();
			}
			
			ExecmdUtils exeCmd = new ExecmdUtils();

			String dbpName = this.getDatabaseProductName(this
					.getMC00_datasource().getDbconnection());

			if (dbpName.toLowerCase().indexOf("oracle") > -1) {
				String badFileDir = cmdFileDir + File.separator +"bad";
				String logFileDir = cmdFileDir + File.separator +"log";
				
				File bFile = new File(badFileDir);
				if(!bFile.exists()){
					bFile.mkdirs();
				}
				File lFile = new File(logFileDir);
				if(!lFile.exists()){
					lFile.mkdirs();
				}
				
				isSuccess = exeCmd.exec_oracle(cmdStr);

				// 客户数据库为oracle，load失败:解析错误日志信息
				if (!isSuccess) {
					String dataFileName = mC00_ds_tables.getTablename() + mC00_datasource.getFtpfileext();
					String badFileName  = mC00_ds_tables.getTablename().toUpperCase() + ".bad";
					String logFileName  = mC00_ds_tables.getTablename().toUpperCase()+".log";

					// 1.得到要加载总记录数
					int dataRecNum = getLoadRecordNum(dataFileDir, dataFileName);
					/**
					 *  读取日志文件，判断错误类型:1.被拒绝 -, .bad中记录没有加载的数据2.SQL*Loader-
					 *  .bad文件不生成，所有的记录均未加载
					 */
					// 2.得到加载失败记录数
					int loadFailRecNum = -1;
					// 如果是 SQL*Loader- 则加载失败记录数为总记录数 loadFalueRecNum=dataRecNum;
					// 日志文件路径workpath/YYYY-MM-DD_dskey/bin/log/businesskey.log
					
					// 添加日志文件不存在处理
					File logFileObj = new File(logFileDir + File.separator+logFileName);
					if (!logFileObj.exists()) {
						throw new Exception("数据加载失败，但找不到错误日志文件:" + logFileName);
						//logger.error("数据加载失败，但找不到错误日志文件:" + logFileName);
					}else{
						String findKeys[] = { "被拒绝 -", "SQL*Loader-" };
						if (findKeys[0].equals(this.researchStrFromFile( logFileName, findKeys))) {
							loadFailRecNum = getLoadRecordNum(badFileDir, badFileName);
						} else {// SQL*Loader- 错误,这种情况下认为全部没加载成功
							loadFailRecNum = dataRecNum;
						}
						// 3.加载成功记录数=总记录数-加载失败记录数
						int loadSuccNum = dataRecNum - loadFailRecNum;
						StringBuffer errorMsgBuf = new StringBuffer();

						errorMsgBuf.append(this.getMC00_task_fact().getDatatime() + " 加载数据文件时出错,加载详情：");
						errorMsgBuf.append("加载总记录数:");
						errorMsgBuf.append(dataRecNum + ",");
						errorMsgBuf.append("加载成功记录数:");
						errorMsgBuf.append(loadSuccNum + ",");
						errorMsgBuf.append("加载失败记录数:");
						errorMsgBuf.append(loadFailRecNum);
						throw new Exception(errorMsgBuf.toString());// 抛出带加载失败信息的异常，在AuditServer4中捕获到异常，向t18_errorlog中增加错误记录
					}
				}
			} else {// 如果客户数据源为db2数据库
				logger.info("db2cmdStr:"+cmdStr);
				isSuccess = exeCmd.exec_normal(cmdStr);
			}

		} catch (Exception e) {
			logger.error("taskid=" + this.getMC00_task_fact().getTaskid()
					+ ";subtaskid=" + this.getMC00_task_fact().getSubtaskid()
					+ ";datatime=" + this.getMC00_task_fact().getDatatime()
					+ ";任务出现异常！！！");
			throw e;
		}
		return isSuccess;
		
	}

	/**
	 * 功能：从指定路径(dataFilePath)的数据文件中统计总记录数
	 * 
	 * @param dataFilePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @return int 文件中记录总数
	 */
	public int getLoadRecordNum(String dataFileDir, String fileName) {
		int recNum = 0;
		BufferedReader fin = null;
		String lineStr = null;
		try {
			File dataFile = new File(dataFileDir, fileName);
			// 如果数据文件不存在，则直接返回0
			if (!dataFile.exists()) {
				return recNum;
			}
			// 从数据文件中统计记录总数
			fin = new BufferedReader(new FileReader(dataFile));
			// 当前读的记录不是空串，也不是空对象，才视为一条数据记录
			lineStr = fin.readLine();
			while (lineStr!=null && lineStr.trim()!="") {
				++recNum;
				lineStr = fin.readLine();
			}
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
			recNum = 0;
		}
		return recNum;
	}

	/**
	 * 功能：文件搜索指定字符
	 * 
	 * @param filedirstr
	 *            文件路径
	 * @param srt
	 *            搜索关键字:被拒绝 - 、SQL*Loader- return findStr 找到的错误关键字
	 * 
	 */
	public String researchStrFromFile(String filedirstr, String[] srts) {
		BufferedReader fin;
		String linestr = null;
		int ishave = -1;
		String retStr = null;

		try {
			fin = new BufferedReader(new FileReader(filedirstr));

			out_loop: while ((linestr = fin.readLine()) != null) { 
				if (!linestr.equals(null)) {
					for (int i = 0; i < srts.length; i++) {
						ishave = linestr.indexOf(srts[i]);
						if (ishave >= 0) {
							retStr = srts[i];
							break out_loop;
						}
					}

				}
			}
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;

	}

	public MC00_datasource getMC00_datasource() {
		return mC00_datasource;
	}

	public void setMC00_datasource(MC00_datasource mC00_datasource) {
		this.mC00_datasource = mC00_datasource;
	}

}
package com.citic.server.service.task.tk_etl101;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;
import com.citic.server.service.base.Base;
import com.citic.server.utils.Base64Utils;
import com.citic.server.utils.FileUtils;

public abstract class BaseFile extends Base {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseFile.class);

	private MC00_datasource mC00_datasource;
	private MC00_task_fact mC00_task_fact;

	private JdbcTemplate jdbcTemplate;

	private CacheService cacheService;

	private ApplicationContext ac;
	
	private FileUtils fileUtils = new FileUtils();

	public BaseFile(ApplicationContext ac, MC00_datasource ds, MC00_task_fact tf) {
		this.ac = ac;
		this.mC00_datasource = ds;
		this.mC00_task_fact = tf;
		jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	/**
	 * 取得数据库类型名称
	 * 
	 * @param jdbcTemplateName
	 * @return
	 * @throws Exception
	 */
	public String getDatabaseProductName(String jdbcTemplateName)
			throws Exception {

		JdbcTemplate jdbcTemplate = this.getJdbcTemplate(jdbcTemplateName);

		//DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();

		//String dbpName = md.getDatabaseProductName();

		String dbpName = "";
		
		Connection conn = null;
		
		try{
			
			conn = jdbcTemplate.getDataSource().getConnection();
			
			DatabaseMetaData md = conn.getMetaData();

			dbpName = md.getDatabaseProductName();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			conn.close();
			}catch(Exception ee){
				
			}
		}
		
		return dbpName;
	}

	public abstract boolean run() throws Exception;

	
	public boolean initDataFileForLoad() throws Exception {
		boolean isSucc = false;

		/**
		 * 1、如果是客户送数据到本地，数据先进入LDSPATH，计算时改目录名字，放入LWORKPATH下带日期的子目录内（YYYY-MM-DD_DSID）
		 * 2、自己抽取的数据，会直接进入工作目录下，带时间的子目录内（YYYY-MM-DD_DSID）这可以有效避免多日数据互相覆盖
		 * 3、装在命令放入每日数据目录下（BIN）目录下，系统自动更改装载脚本内的路径，并分别拷贝到各个数据目录下
		 * 4、系统保留数据及装载实际命令的切片
		 */
		String datatime = this.getMC00_task_fact().getDatatime();

		// 本地实际工作路径
		String lworkpath = this.getMC00_datasource().getLworkpath();
		if (!lworkpath.endsWith(File.separator)) {
			lworkpath += File.separator;
		}
		// 例子：lworkpath/20150101_DS01，lworkpath=绝对路径
		String ldatapathroot = lworkpath + datatime + "_"
				+ this.getMC00_datasource().getDsid();

		String dgetmethod = this.getMC00_datasource().getDgetmethod();
		String dputlocation = this.getMC00_datasource().getDputlocation();

		if (dgetmethod.equalsIgnoreCase("put")
				&& dputlocation.equalsIgnoreCase("localfile")) {// 客户送到本地文件

			// 数据文件路径：路径可能带时间（YYYY-MM-DD），实际使用时，需要根据数据日期替换
			String ldspath = this.getMC00_datasource().getLdspath();
			
			ldspath = fileUtils.replateDTpath(ldspath, datatime);

			// 将原来数据文件临时目录保存 ,重命名后，再创建
			String tempDataDir = ldspath;
			if (!ldspath.equalsIgnoreCase(ldatapathroot)) {// 可能重名

				FileUtils.renameFile(ldspath, ldatapathroot);
				
				//如果标志文件路径消失，自动创建
				CacheService cacheService = (CacheService)this.getAc().getBean("cacheService");
				HashMap flagfileMap = (HashMap)cacheService.getCache("flagfile", HashMap.class);
				if(flagfileMap.containsKey( this.getMC00_datasource().getDsid() )){
					MC00_flagfile ff = (MC00_flagfile)flagfileMap.get( this.getMC00_datasource().getDsid() );
					
					String flagPath = ff.getLocalfilepath();
					flagPath = fileUtils.replateDTpath(flagPath, datatime);
					File flagFile = new File(flagPath);
					if (!flagFile.exists()) {
						flagFile.mkdirs();
					}
					
				}
				//
				//如果本地数据文件目录消失，自动创建
				File tempFile = new File(tempDataDir);
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
			}

		}

		// 需要生成装载命令（将命令模板进行替换，并放置指定位置
		String localCmdPath = ldatapathroot + File.separator + "bin";
		// 命令脚本中，需要替换的路径信息
		LinkedHashMap strHash = new LinkedHashMap();
		strHash.put(this.getMC00_datasource().getLworkpath(), ldatapathroot);
		strHash.put(this.getMC00_datasource().getLloadcmdpath(), localCmdPath);
		
		/**
		 * 脚本中如果有日期变量，需要替换成实际日期
		 */
		String dtStr = datatime;
		strHash.put("YYYY-MM-DD", dtStr);
		strHash.put("yyyy-mm-dd", dtStr);
		dtStr = dtStr.replace("-", "");
		strHash.put("YYYYMMDD", dtStr);
		strHash.put("yyyymmdd", dtStr);
		
		/**
		 * 当数据库装在脚本中的数据库连接信息是通过：tool-config.xml统一配置时，如下生效
		 * 如果密码被Base64编码，此处需要解开
		 */
		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
		String back_dataload_database_dbname = applicationCFG.getBack_dataload_database_dbname();
		String back_dataload_database_username = applicationCFG.getBack_dataload_database_username();
		String back_dataload_database_password = applicationCFG.getBack_dataload_database_password();
		String back_dataload_flag_dbname = applicationCFG.getBack_dataload_flag_dbname();
		String back_dataload_flag_username = applicationCFG.getBack_dataload_flag_username();
		String back_dataload_flag_password = applicationCFG.getBack_dataload_flag_password();
		if(back_dataload_database_dbname!=null && !back_dataload_database_dbname.equals("")
				&& back_dataload_database_username!=null && !back_dataload_database_username.equals("")
				&& back_dataload_database_password!=null && !back_dataload_database_password.equals("")
				&& back_dataload_flag_dbname!=null && !back_dataload_flag_dbname.equals("")
				&& back_dataload_flag_username!=null && !back_dataload_flag_username.equals("")
				&& back_dataload_flag_password!=null && !back_dataload_flag_password.equals("")
				
				){
			
//			if(back_dataload_database_password.lastIndexOf("==")>-1){
//				//数据库密码解密
//				back_dataload_database_password = Base64Utils.decodeBase64(back_dataload_database_password);
//			}
//			if(back_dataload_database_dbname.lastIndexOf("==")>-1){
//				//数据库密码解密
//				back_dataload_database_dbname = Base64Utils.decodeBase64(back_dataload_database_dbname);
//			}
			
			if(Base64Utils.isEncryption( back_dataload_database_password) ){
				//数据库密码解密
				back_dataload_database_password = Base64Utils.unmakeEncryption(back_dataload_database_password);
			}
			if(Base64Utils.isEncryption(back_dataload_database_dbname)){
				//数据库密码解密
				back_dataload_database_dbname = Base64Utils.unmakeEncryption(back_dataload_database_dbname);
			}
			
			strHash.put(back_dataload_flag_dbname,back_dataload_database_dbname);
			strHash.put(back_dataload_flag_username,back_dataload_database_username);
			strHash.put(back_dataload_flag_password,back_dataload_database_password);
		}
		
		//
		fileUtils.copyFolderAndReplaceCmd(this.getMC00_datasource().getLloadcmdpath(), localCmdPath, strHash);

		return isSucc;

	}

	/**
	 * 根据配置文件，保存数据时间，自动将数据文件清理(从当前时间往前追溯N天)
	 * @throws Exception
	 */
	public void deleteHistoryDate() throws Exception {
		
		String dsid = this.getMC00_task_fact().getDsid();
		
		String datatime = this.getMC00_task_fact().getDatatime();

		// 本地实际工作路径
		String lworkpath = this.getMC00_datasource().getLworkpath();
		if (!lworkpath.endsWith(File.separator)) {
			lworkpath += File.separator;
		}
		
		// 读取数据文件保留天数
		ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean(
				"applicationCFG");

		int backdays = applicationCFG.getDatafile_backup_days();

		if (backdays <= 0)
			throw new Exception("参数：datafile_backup_days配置错误！");

		DateTime dt = new DateTime(datatime);

		DateTime currDatatime = dt.minusDays(-1 * backdays);
		// 将currDatatime 之前的所有数据清理掉，清理是连续进行，如果某日数据缺失，之前的不能清理
		File file = new File(lworkpath + currDatatime + "_" + dsid);
		while (true) {
			if (!file.exists()) {
				break;
			}
			logger.info("清理历史数据:" + file.getName());
			// 清理当前目录

			deleteHistoryDate(file);

		}
	}

	/**
	 * 递归删除
	 * @param file
	 * @return
	 */
	private boolean deleteHistoryDate(File file) {

		if (!file.exists()) {
			logger.error("未找到正在清理的历史数据文件：" + file.getName() + "！");
			return false;
		}
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteHistoryDate(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return file.delete();
	}
	
	
	public MC00_datasource getMC00_datasource() {
		return mC00_datasource;
	}

	public MC00_task_fact getMC00_task_fact() {
		return mC00_task_fact;
	}

	public JdbcTemplate getJdbcTemplate(String jdbcTemplateName) {
		return jdbcTemplate;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}

	public static void main(String[] args) {
	}

}

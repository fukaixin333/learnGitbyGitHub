package com.citic.server.service.task.tk_ds101;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.UtilsService;
import com.citic.server.service.base.NeetReCalException;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.FtpUtils;
/**
 * FTP 取文件到本地，进行判断;
 * @author hubq
 */
public class RemoteFile extends BaseDS {

	private static final Logger logger = LoggerFactory
			.getLogger(RemoteFile.class);

	public RemoteFile(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}
 

    /**
     * 标识文件规范：prefix_yyyy-mm-dd.suffix(10位日期)或者prefix_yyyymmdd.suffix(8位日期)
     * 
     * 取出全部准备完毕的数据标识（包括已经导入的）
     */
	public boolean run() throws Exception {
        
		UtilsService utilsService = (UtilsService)this.getAc().getBean("utilsService");
		
        //首先FTP同步远程标志文件
        
        //待扩展
		boolean isSuccess = false;
		
		try {
			HashMap flagFileHash = (HashMap) this.getCacheService().getCache(
					"flagfile", HashMap.class);
	
			MC00_flagfile ff = (MC00_flagfile) flagFileHash.get(this
					.getMC00_datasource().getDsid());
	        
	        FtpUtils ftpUtils = new FtpUtils();
	        FileUtils fileUtils = new FileUtils();
	        
	        /**
	         * remoteFilePath = ftp://ipaddress:port||username||password||datafilepath||flagfilepath
	         * 
	         * datasource - ftpconnection : FTP://ipaddress:port||useranme||password||datafilepath
	         * 
	         * flagfile - remotfilepath : 远程服务器的文件路径
	         * flagfile - localfilepath : 本地服务器的文件路径
	         */
	
	        String remoteFilePath= this.getMC00_datasource().getFtpconnection()+"||"+ff.getRemotefilepath();
	        String localFiletpah = ff.getLocalfilepath();
	        if(ff.getLocalfilepath().equals("")){
	        	ff.setLocalfilepath( this.getMC00_datasource().getLdspath() );//如果本地标识文件路径为空，默认使用本地数据文件路径；
	        }
	        
	        String []strlist=remoteFilePath.split("\\|\\|");
	        logger.debug("strlist.length:"+strlist.length+" ip:"+strlist[0]+" username:"+strlist[1]+" password:"+strlist[2]+" datafilepath:"+strlist[3]+" flagfilepath:"+strlist[4]);
	        
	        if(strlist.length==5){
	        	ftpUtils.setServer(strlist[0]);  	//ftpserver  ip : port
	        	ftpUtils.setUser(strlist[1]);    	//user   用户名
	        	ftpUtils.setPassword(strlist[2]);   //password   密码
	        	
	        	//**远端标识文件路径**/
	            String ftppath=strlist[4]; //ftppath(远程标识文件路径)
	            //如果标志文件路径中含有数据日期变量，替换
	            String datatime = this.getMC00_task_fact().getDatatime();
	            ftppath = fileUtils.replateDTpath(ftppath, datatime);
	            ftpUtils.setRemotepath(ftppath);	//path
	            
	            //** 本地标识文件路径**/
	            ff.setLocalfilepath( fileUtils.replateDTpath(ff.getLocalfilepath(),datatime) );
	            ftpUtils.setLocalpath(ff.getLocalfilepath());
	            fileUtils.mkDirs(ftpUtils.getLocalpath());
	            
	            
	            String hasflag = "0";
	            // 如果本地标志文件目录==数据文件目录，判断本地是否有标志文件
	            if ((fileUtils.replateDTpath(this.getMC00_datasource().getLdspath(),datatime)).equalsIgnoreCase(ff.getLocalfilepath())) {
	           
	                
	                File directory = new File(ff.getLocalfilepath() + File.separator);
	                File[] files = directory.listFiles();
	                String filename=new String();
	                for (int i = 0; i < files.length; i++) {
	                    filename=files[i].getName();
	                    if (filename.indexOf(ff.getFilepre()) >= 0
	                            && filename.indexOf(ff.getFileend()) > 0) {
	                        hasflag = "1";
	                    }
	                }
	            }
	           
	            if (hasflag.equalsIgnoreCase("0")) {      
	            	 try{
	            		// 将ftp目录下的标志文件下载到本地（标志文件目录==数据文件目录&&本地没有标志文件或标志文件目录<>数据文件目录）
	            		 ftpUtils.ftpflagfileList(ff.getFilepre(), ff.getFileend());
	                 }catch(FileNotFoundException ex){
	                	 ex.printStackTrace();
	                	 throw new Exception("FTP远程目录 "+ftpUtils.getRemotepath()+" 不存在！！！");
	                 }      	
	            }else{
	                logger.info("本地标志文件目录与数据文件目录相同且本地已有标志文件，不下载标志文件");
	            }
	                       
	        }else{
	            logger.error(this.getMC00_datasource().getDsid()+"数据源探测错误，标识文件的ftp路径格式不对！！！");
	        }
	        
			// 获取路径下文件
			String FilePath = ff.getLocalfilepath(); // 标识文件路径
			String preStr = ff.getFilepre();// 标识文件前缀
			String endStr = ff.getFileend();// 标识文件后缀

		

			File file = new File(FilePath);
			// 文件数组
			if (file != null) {
				File[] tmpfiles = file.listFiles();
				for (int i = 0; i < tmpfiles.length; i++) {
					if (tmpfiles[i].isFile()) {
						String datatime = "";
						String filename = tmpfiles[i].getName();
						logger.debug("文件名称：" + filename + ";文件长度" + filename.length());

						if (filename.indexOf(preStr) < 0
								|| filename.indexOf(endStr) < 0) {
							logger.debug(filename + ";前缀不对，此文件不是标识文件！");
							continue;// 前缀不对，此文件不是标识文件
						}

						if (filename.indexOf(".") > 0) {
							datatime = filename.substring(0,filename.indexOf("."));
						}
						datatime = datatime.substring(preStr.length());

						if (datatime.length() == 10) {// 10位日期 yyyy-mm-dd
							//
						} else if (datatime.length() == 8) {// 8位日期
							datatime = utilsService.date8to10(datatime);
						} 
						//判断日期格式合法性
						if(!utilsService.isDateStr(datatime)){// 在文件的第一行记录的数据时间

							datatime = utilsService.getFirstLineFromFile(
									FilePath, filename);

							if (datatime == null
									|| datatime.equals("")) {
								logger.error("标识文件非法：" + filename);
							}

							if (datatime.length() == 8) {
								datatime = utilsService.date8to10(datatime);
							}
							
							if(!utilsService.isDateStr(datatime)){
								logger.error("未找到标识文件日期标识位置,请检查标识文件位置！");
								continue;
							}
							
						}

						if (datatime.equals(this.getMC00_task_fact().getDatatime())) {
							isSuccess = true;
							break;
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("数据源探时候出现异常。。。");
			
			isSuccess = false;
		}finally{
      
			if(!isSuccess){//没有找到符合的数据标识文件，本任务需要循环探测
				throw new NeetReCalException();
			}
		
		}
		
        return isSuccess;
    }

   
    public static void main(String[] args) {
        
    }

}

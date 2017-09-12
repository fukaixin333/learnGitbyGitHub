package com.citic.server.service.task.tk_etl101;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.FtpUtils;


/**
 * 将数据源文件从远端服务器抽取到本地
 * @author hubaiqing
 */
public class GetFileByFTP extends BaseFile{

	private static final Logger logger = LoggerFactory.getLogger(GetFileByFTP.class);
	
	public GetFileByFTP(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}

    @Override
	public  boolean run() throws Exception{
    	boolean isSucc = false;
    	
    	isSucc = this.getFileByFtp();
    	
        this.initDataFileForLoad();

		// 清理历史数据文件
		this.deleteHistoryDate();
        
    	return isSucc;
    	
    }
    
    private boolean getFileByFtp() throws Exception{
    	
    	boolean isSucc = false;
    	
    	FtpUtils ftpUtils = new FtpUtils();
    	FileUtils fileUtils = new FileUtils();
    	
    	String[] strlist = this.getMC00_datasource().getFtpconnection().split("\\|\\|");
        
        if(strlist.length==4){
        	ftpUtils.setServer(strlist[0]);  		//ftpserver
        	ftpUtils.setUser(strlist[1]);    		//user
        	ftpUtils.setPassword(strlist[2]);    	//password
          
            String ftppath=strlist[3];				//ftp服务器上的数据文件路径
            
            //如果标志文件路径中含有数据日期变量，替换
            String datatime = this.getMC00_task_fact().getDatatime();   
            ftppath = fileUtils.replateDTpath(ftppath,datatime);
            ftpUtils.setRemotepath(ftppath);		//远程服务器端的路径
            
            //====================================
            //本地路径：FTP过来的直接进入工作目录：在目录“YYYY-MM-DD_DSKEY”下
            //====================================
            //String localpath = this.t18_datasource.getDspath()+File.separator+ftppath;
            String workpath = this.getMC00_datasource().getLworkpath();
            if (!workpath.endsWith(File.separator)) {
            	workpath += File.separator;
            }
            //localpath=workpath/yyyy-mm-dd_dsid
            String localpath = workpath + datatime +"_"+ this.getMC00_datasource().getDsid();
            ftpUtils.setLocalpath(localpath); 
            
            fileUtils.mkDirs( ftpUtils.getLocalpath() );
            //====================================
            
            String fileext = this.getMC00_datasource().getFtpfileext();
            //日期配置
            fileext = fileUtils.replateDTpath(fileext, this.getMC00_task_fact().getDatatime());
            
            ArrayList tableList = this.getMC00_task_fact().getTableList();
            Iterator iter = tableList.iterator();
            while(iter.hasNext()){
            	
            	/**
            	 * 按照ds_table定义的表进行抽取
            	 */
            	MC00_ds_tables mC00_ds_tables = (MC00_ds_tables)iter.next();
            	
            	String tablename = mC00_ds_tables.getTablename();
            	String filename = tablename;
            	if(!fileext.equals("")){
            		filename = tablename + fileext ;
            	}
            	logger.info("开始FTP下载数据文件:"+filename +" TASKID="+this.getMC00_task_fact().getTaskid()+"； SUBTASKID="+this.getMC00_task_fact().getSubtaskid());
            	ftpUtils.getfile(filename);
            	logger.info("数据文件:"+filename +" 下载 OK!");
            }
            
        	isSucc = true;
           
            
        }else{
        
            throw new Exception("数据源："+this.getMC00_datasource().getDsid()+"，ftp路径格式不正确！");
        }
        
        return isSucc;
    	
    }
    
    
}

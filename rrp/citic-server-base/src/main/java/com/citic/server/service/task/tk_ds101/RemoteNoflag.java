package com.citic.server.service.task.tk_ds101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.UtilsService;
import com.citic.server.service.base.NeetReCalException;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.FtpUtils;
/**
 * =====
 * 没有数据标示文件(到远端判断数据文件）
 * =====
 * 邯郸现场标志文件判断逻辑
 * 客户数据源：核心，信贷，个贷，银联前置，POS，网间互联等
 * 核心、对公有的有标志文件（按单表生成XXX.ok）,其余数据源没有
 * 对私一定在核心之前
 * 数据文件都为.z的压缩文件
 * 数据文件为各个系统共用，因此数据表会比实际需要的多，而且，核心，信贷是混在一起的。
 * 
 * 数据文件格式：tablename.yyyymmdd.unl.00.z 其中00为币种，暂时只有00；unl为固定值
 * 
 * *************************************************
 * 思路：
 * 1、远程获取对应文件目录下得文件列表，与 mc00_ds_tables表的记录进行对比，判断是否都以机构存在
 * 2、如果数据文件都以已经存在，休眠10秒，再查一次，比较各个文件大小（或者时间）如果一致，则没有问题（说明没有正在写入的文件）
 * 
 * 系统数据源准备可以限定扫描时间：例如：凌晨一点~上午8点，其余时间无意义
 * 
 * 
 * 
 * @author hubq
 */
public class RemoteNoflag extends BaseDS {

	private static final Logger logger = LoggerFactory
			.getLogger(RemoteNoflag.class);

	public RemoteNoflag(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}
 
	
	private ArrayList getDsFiles(){
		String dsid = this.getMC00_datasource().getDsid();
		String datatime = this.getMC00_task_fact().getDatatime();
		String freq = this.getMC00_task_fact().getFreq();
		
		/** 通过取数据的任务编码，取得待抽取任务的列表，再与FTP服务器文件列表进行比较 */
		//String taskid = this.getMC00_task_fact().getTaskid();
		String taskid = "TK_ETL101";
		//String subtaskid = this.getMC00_task_fact().getSubtaskid();
		
		//远程文件扩展名
		String fileext = this.getMC00_datasource().getFtpfileext();
		FileUtils fileUtils = new FileUtils();
		fileext = fileUtils.replateDTpath(fileext,datatime);
		
		
		//
		ArrayList dsfileList = new ArrayList();
		HashMap map = (HashMap)this.getCacheService().getCache("ds_tables", HashMap.class);
		HashMap submap = new HashMap();
		
		if(map.containsKey(taskid))
			submap = (HashMap)map.get(taskid);
		
		/**
		ArrayList list = new ArrayList();
		
		if(submap.containsKey(subtaskid))
			list = (ArrayList)submap.get(subtaskid);
		**/
		
		Iterator iter = submap.keySet().iterator();
		while(iter.hasNext()){
			
			String tablename = (String)iter.next();
			
			ArrayList subList = (ArrayList)submap.get(tablename);
			
			Iterator subIter = subList.iterator();
			
			while(subIter.hasNext()){
				
				MC00_ds_tables mC00_ds_tables = (MC00_ds_tables)subIter.next();
				
				if(mC00_ds_tables.getFreq() == null || mC00_ds_tables.getFreq().equals("")){
					mC00_ds_tables.setFreq("1");
				}
				
				if(mC00_ds_tables.getDsid().equals(dsid)  //同数据源下的表才拿出来比较
				   && mC00_ds_tables.getFreq().equals(freq)
						){
					
					/**
					 * 如果存在多币种情况，请在mc00_datasource 表中把可能用到的币种信息传入，
					 * 在此处循环，生成多个文件名，用于文件个数的比较
					 */
	            	String filename = tablename;
	            	if(!fileext.equals("")){
	            		filename = tablename + fileext ;
	            	} 
					
					dsfileList.add(filename);
							
				}
				
			}
			
		}
		
		return dsfileList;
	}

	public HashMap getRemoteFiles() throws Exception{
		HashMap map = new HashMap();

		
		FtpUtils ftpUtils = this.getFtpUtilsObj();
		if(ftpUtils!=null)
			map = ftpUtils.getRemoteFileList();
		
		return map;
	}
	
	 public HashMap getRemoteFileSize(ArrayList _filenameList) throws Exception {
		 
		 HashMap map = new HashMap();
		 
		 FtpUtils ftpUtils = this.getFtpUtilsObj();
		 if(ftpUtils!=null)
			 map = ftpUtils.getRemoteFileSize(_filenameList);
		 
	     return map;
		 
	 }
	
	 
	 public FtpUtils getFtpUtilsObj(){
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
			
	        String remoteFilePath= this.getMC00_datasource().getFtpconnection();
	        
	        //logger.debug("remoteFilePath="+remoteFilePath);
	        String []strlist=remoteFilePath.split("\\|\\|");
	        
	        logger.debug("strlist.length:"+strlist.length+" ip:"+strlist[0]+" username:"+strlist[1]+" password:"+strlist[2]+" datafilepath:"+strlist[3]);
	        
	        if(strlist.length==4){
	        	ftpUtils.setServer(strlist[0]);  	//ftpserver  ip : port
	        	ftpUtils.setUser(strlist[1]);    	//user   用户名
	        	ftpUtils.setPassword(strlist[2]);   //password   密码
	        	
	        	//**远端标识文件路径**/
	            String ftppath=strlist[3]; //ftppath(远程标识文件路径)
	            //如果标志文件路径中含有数据日期变量，替换
	            String datatime = this.getMC00_task_fact().getDatatime();
	            ftppath = fileUtils.replateDTpath(ftppath, datatime);
	            ftpUtils.setRemotepath(ftppath);	//path
	            
	        }else{
	            logger.error(this.getMC00_datasource().getDsid()+"数据源探测错误，标识文件的ftp路径格式不对！！！");
	            ftpUtils = null;
	        }
	        
	        return ftpUtils;
	 }
	 
    /**
     * 标识文件规范：prefix_yyyy-mm-dd.suffix(10位日期)或者prefix_yyyymmdd.suffix(8位日期)
     * 
     * 取出全部准备完毕的数据标识（包括已经导入的）
     */
	public boolean run() throws Exception {
        
		boolean isSuccess = true;
		
		try{
		UtilsService utilsService = (UtilsService)this.getAc().getBean("utilsService");
		//本数据源需要扫描的全部数据文件列表
		ArrayList dsfileList = this.getDsFiles();
		//服务器端全部文件名及对应的文件大小
		
		//HashMap prefilesizeMap = this.getRemoteFileSize(dsfileList);
		//
		HashMap remoteFileMap = this.getRemoteFiles();
		
		/**
		 * 比较文件个数
		 */
		boolean getAll = true;
		Iterator iter = dsfileList.iterator();
		while(iter.hasNext()){
			String dsfilename = (String)iter.next();
			
			if(!remoteFileMap.containsKey(dsfilename)){
				logger.debug("MissingFile:"+dsfilename);
				getAll = false;
				break;
			}else{
				logger.debug("HaveFile:"+dsfilename);
			}
			
		}
		
		if(!getAll){
			isSuccess = false;
			
		}/*else{
			logger.debug("DSID="+this.getMC00_datasource().getDsid()+";文件数量正确！");
			*//**
			 * 比较文件的大小
			 *//*
			//HashMap remoteFileMap1 = this.getRemoteFiles();
			HashMap nxtfilesizeMap = this.getRemoteFileSize(dsfileList);
			
			iter = dsfileList.iterator();
			while(iter.hasNext()){
				String dsfilename = (String)iter.next();
				
				int filesize = (Integer)prefilesizeMap.get( dsfilename );
				int filesize1= (Integer)nxtfilesizeMap.get( dsfilename );
				
				if(filesize != filesize1){
					logger.debug("filename="+dsfilename+"filesize="+filesize+";"+filesize1+",文件还在服务器端写入中，稍后再判断！");
					
					isSuccess = false;
					break;
				}
				
			}
		}*/
		
		}catch(Exception e){
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

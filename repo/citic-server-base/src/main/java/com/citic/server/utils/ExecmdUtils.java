package com.citic.server.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExecmdUtils {
   
	private static final Logger logger = LoggerFactory
			.getLogger(ExecmdUtils.class);
	
    /**
     * 构造函数
     */
    public ExecmdUtils() {
    }

    /**
     * 调用系统命令
     */
    public static void exec(String execStr){
    	
    	execStr = ExecmdUtils.getCMDStr(execStr);
    	
    	Runtime runtime = Runtime.getRuntime(); 
    	try{   		
    		Process proc = runtime.exec(execStr); 
    		proc.waitFor();
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	
    	
    } 
    
    /**
     * 调用系统命令
     */
    public static boolean exec_normal(String execStr) throws Exception {
        boolean retVal = false;
        
        execStr = ExecmdUtils.getCMDStr(execStr);
        logger.info("=====execStr:" + execStr);
        Runtime runtime = Runtime.getRuntime();// 取得当前运行期对象
        String line = "";
        String ln = "";
        try {
            Process proc = runtime.exec(execStr); // 启动另一个进程来执行命令
            // 使用缓存输入流获取屏幕输出。
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            // 读取屏幕输出
            while ((line = input.readLine()) != null) {
                logger.debug(line);
            }
            input.close();
            // 通过获取错误输出流来判断cmd命令是否正常结束
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            if ((ln = error.readLine()) != null) {
                logger.error(ln);
                while ((ln = error.readLine()) != null) {
                    logger.error(ln);
                }
                error.close();
                throw new Exception();
            }
            retVal = true;
        } catch (Exception e) {
            throw e;
        }
        return retVal;
    } 
    
    /**
     * ORACLE数据库处理错误信息的方式不同
     */
    
    public static boolean exec_oracle(String execStr){
    	boolean retVal = false;
    	
    	execStr = ExecmdUtils.getCMDStr(execStr);
    	
    	Runtime runtime = Runtime.getRuntime();//取得当前运行期对象
    	try{   		
    		StringBuffer sb=new StringBuffer();
    		Process proc = runtime.exec(execStr); //启动另一个进程来执行命令
    		
    		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");                
    		// kick off stderr     
    		errorGobbler.start();
    		StreamGobbler outGobbler = new StreamGobbler(proc.getInputStream(), "STDOUT");    
    		// kick off stdout
    		outGobbler.start();
    		
//    		java.io.InputStream istr=proc.getErrorStream();
//    		BufferedReader br=new BufferedReader(new InputStreamReader(istr));
//    		String line;
//    		while((line=br.readLine())!=null){
//    			sb.append(line+"\n");
//    		}
    		proc.waitFor();//等待执行结束
    		retVal = true;
    		
    		if(proc.exitValue()==0){
    			proc.destroy();
    			return true;
    		}else{
    			logger.error(sb.toString());
    			proc.destroy();
    			return false;
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		retVal = false;    		
    	}    	
    	return retVal;
    	
    } 
    
    /**
    public static boolean exec_oracle(String execStr){
    	boolean retVal = false;
    	
    	execStr = ExecmdUtils.getCMDStr(execStr);
    	
    	Runtime runtime = Runtime.getRuntime();//取得当前运行期对象   
    	try{   		
    		StringBuffer sb=new StringBuffer();
    		Process proc = runtime.exec(execStr); //启动另一个进程来执行命令
    		java.io.InputStream istr=proc.getErrorStream();
    		BufferedReader br=new BufferedReader(new InputStreamReader(istr));
    		String line;
    		while((line=br.readLine())!=null){
    			sb.append(line+"\n");
    		}
    		proc.waitFor();//等待执行结束
    		retVal = true;
    		
    		if(proc.exitValue()==0){
    			proc.destroy();
    			return true;
    		}else{
    			logger.error(sb.toString());
    			proc.destroy();
    			return false;
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		retVal = false;    		
    	}    	
    	return retVal;
    	
    } 
    */
    private static String getCMDStr(String cmd){
		String osName = System.getProperty("os.name");
		String cmdStr = "";
		if(cmd.indexOf(".bat")<0 && cmd.indexOf(".sh")<0 ){
			logger.error("当前文件类型不可知，可识别的文件扩展名为.bat,.sh");
			return null;
		}
		if(osName.toLowerCase().indexOf("win")>=0){
			cmdStr = cmd;
		}else{
			if(!cmd.startsWith("sh")){
				cmdStr = "sh "+cmd;
			}
		}	

		return cmdStr;
	}
    
    
   public static void main(String[] args) {
    	
    	ExecmdUtils cmd = new ExecmdUtils();
    	
    	//String cmdstr = "sh h.sh/c cd /Users/hubaiqing/Desktop/PROJECTS/cmd";
    	
    	String cmdstr = "/Users/hubaiqing/Desktop/PROJECTS/cmd/h.sh";
    	
    	//String cmdstr = "ls -la";
    	try{
    		cmd.exec_normal(cmdstr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    }
    
}
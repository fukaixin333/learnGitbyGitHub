package com.citic.server.utils;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * @author hubq
 * 
 */
public class FileCodeRevertUtils {

	public FileCodeRevertUtils() {
	}

	public static void main(String[] args) {
		FileCodeRevertUtils fcr = new FileCodeRevertUtils();
		
		String rootdir = "";
		
		//GBK编码格式源码路径
	    String srcDirPath = "/Users/hubaiqing/Desktop/SVNROOT/projects/afplus/src/main/java/com";
	    //转为UTF-8编码格式源码路径
	    String utf8DirPath = "/Users/hubaiqing/Desktop/tmpdir";
		try {
			fcr.GBK2UTF8(srcDirPath, utf8DirPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void GBK2UTF8(String srcDirPath,String utf8DirPath) throws Exception {
	       
	    //获取所有java文件
	    Collection<File> javaGbkFileCol =  FileUtils.listFiles(new File(srcDirPath), new String[]{"java"}, true);
	    for (File javaGbkFile : javaGbkFileCol) {
	    //UTF8格式文件路径
	    String utf8FilePath = utf8DirPath+javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
	    //使用GBK读取数据，然后用UTF-8写入数据
	    FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));     
	    }
	}

}

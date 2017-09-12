package com.citic.server.runtime;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class FileTools {
	
	/**
	 * 获取指定目录下的文件数组
	 * 
	 * @param dir 目标目录
	 * @param fileStartStr 待获取的目标文件名开头
	 * @return
	 * @author yinxiong
	 * @date 2017年5月19日 上午10:37:48
	 */
	public static File[] getFileListByByStartStr(File dir, final String fileStartStr) {
		// 获取目录
		File directory = dir;
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (directory.isFile() || !directory.exists()) {
			return new File[0];
		}
		// 生成过滤规则
		FileFilter filter = null;
		filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().startsWith(fileStartStr);//获取指定开头的文件
			}
		};
		// 获取过滤后的文件数组
		File[] files = directory.listFiles(filter);
		
		return files;
	}
	
	/**
	 * 获取指定目录下的文件名数组
	 * 
	 * @param dir
	 * @param fileStartStr待获取的目标文件名开头
	 * @return
	 * @author yinxiong
	 * @date 2017年5月26日 下午8:57:49
	 */
	public static String[] getFileNameListByStartStr(File dir, final String fileStartStr) {
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (dir.isFile() || !dir.exists()) {
			return new String[0];
		}
		// 生成过滤规则
		FilenameFilter filter = null;
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileStartStr);//获取指定开头的文件名
			}
		};
		// 获取过滤后的文件名数组
		String fieleName[] = dir.list(filter);
		
		return fieleName;
	}
	
	/**
	 * 获取指定目录下的文件名数组
	 * 
	 * @param dir 目标目录
	 * @param fileStartStr 待获取的目标文件名结尾
	 * @return
	 * @author yinxiong
	 * @date 2017年5月19日 上午10:38:36
	 */
	public static String[] getFileNameListByEndStr(File dir, final String endStr) {
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (dir.isFile() || !dir.exists()) {
			return new String[0];
		}
		// 生成过滤规则
		FilenameFilter filter = null;
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(endStr);
			}
		};
		// 获取过滤后的文件名数组
		String fieleName[] = dir.list(filter);
		
		return fieleName;
	}
	
	/**
	 * 获取指定目录下的文件名数组(通过文服推送的)
	 * 
	 * @param dir 目标目录
	 * @param fileStartStr 待获取的目标文件名
	 * @return
	 * @author yinxiong
	 * @date 2017年5月19日 上午10:38:36
	 */
	public static String[] getFileNameListByWfSaveEnd(File dir) {
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (dir.isFile() || !dir.exists()) {
			return new String[0];
		}
		// 生成过滤规则
		FilenameFilter filter = null;
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".end");//获取.end文件
			}
		};
		// 获取过滤后的文件名数组
		String fieleName[] = dir.list(filter);
		
		return fieleName;
	}
	
	
	/**
	 * 判断文件是否存在
	 * <br>文件名带格式匹配
	 * @param dir
	 * @param fileName
	 * @return 返回文件名
	 * 
	 * @author yinxiong
	 * @date 2017年6月3日 上午10:55:19
	 */
	public static String getFileNameByName(File dir, final String fileName) {
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (dir.isFile() || !dir.exists()) {
			return "";
		}
		// 生成过滤规则
		FilenameFilter filter = null;
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(fileName);
			}
		};
		// 获取过滤后的文件名数组
		String fieleName[] = dir.list(filter);
		String rName = "";
		if(fieleName.length>0){
			rName = fieleName[0];
		}
		return rName;
	}
}

/**
 * ========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年4月2日 上午9:40:36
 * Description:
 * =========================================================
 */
package com.citic.server.service.task.taskBo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.citic.server.utils.StrUtils;
import com.itextpdf.text.pdf.BaseFont;

public class TemplateBo {
	/** 日志记录器 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public TemplateBo(String path) {
		try {
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "file");
			properties.setProperty("file.resource.loader.path", path);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("input.encoding", "UTF-8");
			properties.setProperty("output.encoding", "UTF-8");
			Velocity.init(properties);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("Velocity init Error !!!");
		}
	}
	
	public String makeFileByTemplate(String templateFileName, Map beans, String targetFileName, String targetPathStr, String root) throws Exception {
		
		String outputFileName = "";
		try {
			
			//取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			Iterator it = beans.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				Object value = beans.get(key);
				context.put(key, value);
			}
			
			// 导出文件存放的临时目录
			
			String targetRootPath = root + targetPathStr + File.separator;
			String targetFilePath = targetRootPath + File.separator;
			// 判断是否存在该临时目录，没有就建立
			File targetPath = new File(targetFilePath);
			if (!targetPath.exists()) {
				File targetrootPath = new File(targetRootPath);
				targetrootPath.delete();
				targetPath.mkdirs();
			}
			
			outputFileName = targetPathStr + "/" + targetFileName;
			//logger.debug("outputFileName::::::::::::" + outputFileName);
			//输出流
			StringWriter writer = new StringWriter();
			
			Velocity.mergeTemplate(templateFileName, "UTF-8", context, writer);
			//  logger.debug(writer.toString());
			File f = new File(targetFilePath + File.separator + targetFileName);
			
			org.apache.commons.io.FileUtils.writeStringToFile(f, writer.toString(), "UTF-8");
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		return outputFileName;
		
	}
	
	/**
	 * 导出pdf
	 * urlStr:html串
	 * outputPth：文件路径
	 * wjmc：文件名称
	 */
	public String exportPdfFile(String inFileUrl, String outputPth, String wjmc, HashMap sysParaMap) throws Exception {
		
		OutputStream os=null;
		String returnPath = "";
		try {
			String root = StrUtils.null2String((String) sysParaMap.get("2"));
			String toFilePath = root + outputPth;
			File newFile = new File(toFilePath);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			os = new FileOutputStream(toFilePath + "/" + wjmc);
			
			String url = new File(root + inFileUrl).toURI().toURL().toString();
			//实例ITextRenderer，加载html文档
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);
			
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String path = StrUtils.null2String((String) sysParaMap.get("F1"));
			String imgPath = StrUtils.null2String((String) sysParaMap.get("F2"));
			String imgBasePath = "";
			if (imgPath != null) {
				int index = imgPath.lastIndexOf("/");
				imgBasePath = imgPath.substring(0, index + 1);
			}
			fontResolver.addFont(path + "/SimSun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字  
			//  fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字  
			renderer.getSharedContext().setBaseURL(imgBasePath);
			renderer.layout();
			
			renderer.createPDF(os);
			
			System.out.println("转换成功！");
			
			returnPath = outputPth + "/" + wjmc;
			
		} catch (Exception e) {
			e.printStackTrace();	
			logger.debug(e.getMessage());
			throw e;
		}finally{
			try{
			  if(os!=null)	{  
				    os.flush();
					os.close();
			  }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return returnPath;
	}
}

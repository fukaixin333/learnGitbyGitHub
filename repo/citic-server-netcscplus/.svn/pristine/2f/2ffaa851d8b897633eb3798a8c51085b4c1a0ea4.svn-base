package com.citic.server.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.citic.server.runtime.VelocityConstants;
import com.itextpdf.text.pdf.BaseFont;

/**
 * PDF文件处理工具
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/12 22:50:35$
 */
public class PDFUtils implements VelocityConstants {
	
	public static void html2pdf(String template, String filepath, String filename, Map<String, Object> map) throws Exception {
		html2pdf(template, filepath, filename, map, null);
	}
	
	public static void html2pdf(String template, String filepath, String filename, Map<String, Object> map, String baseURL) throws Exception {
		File file = new File(filepath);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		file = new File(filepath + File.separator + filename);
		html2pdf(template, file, map, baseURL);
	}
	
	/**
	 * 根据HTML模板生成PDF文件
	 * 
	 * @param template 模板名称
	 * @param filepath PDF文件路径
	 * @param map 上下文键值对
	 * @throws Exception
	 */
	public static void html2pdf(String template, String filepath, Map<String, Object> map) throws Exception {
		html2pdf(template, new File(filepath), map, null);
	}
	
	public static void html2pdf(String template, File file, Map<String, Object> map, String baseURL) throws Exception {
		VelocityEngine ve = new VelocityEngine();
		// ve.setProperty(RESOURCE_LOADER, "file"); // 资源的加载方式
		// ve.setProperty(FILE_RESOURCE_LOADER_CLASS, "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		ve.setProperty(RESOURCE_LOADER, "classpath");
		ve.setProperty(CLASSPATH_RESOURCE_LOADER_CLASS, "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		ve.setProperty(INPUT_ENCODING, "UTF-8");
		ve.setProperty(OUTPUT_ENCODING, "UTF-8");
		ve.init();
		
		VelocityContext ctx = new VelocityContext();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> e = it.next();
			ctx.put(e.getKey(), e.getValue());
		}
		
		StringWriter sw = new StringWriter();
		Template tpl = ve.getTemplate("templates/" + template);
		tpl.merge(ctx, sw);
		
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(sw.toString());
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			
			renderer.getFontResolver().addFont("fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.getFontResolver().addFont("fonts/FZXBSJW.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.getFontResolver().addFont("fonts/MSYH.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.getFontResolver().addFont("fonts/SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.getFontResolver().addFont("fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			
			if (baseURL != null) {
				renderer.getSharedContext().setBaseURL(StringUtils.cleanPath(baseURL)); // 仅支持"/", 注意末尾可能需要加上路径分隔符"/"
			}
			
			renderer.layout();
			renderer.createPDF(fos);
			renderer.finishPDF();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}
}

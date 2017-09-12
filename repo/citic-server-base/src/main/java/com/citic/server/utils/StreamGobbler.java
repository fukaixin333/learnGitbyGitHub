package com.citic.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于处理Runtime.getRuntime().exec产生的错误流及输出流
 * 
 * @author
 * 
 */
public class StreamGobbler extends Thread {
	private static final Logger logger = LoggerFactory
			.getLogger(StreamGobbler.class);
	
	InputStream is;
	String type;
	OutputStream os;

	StreamGobbler(InputStream is, String type) {
		this(is, type, null);
	}

	StreamGobbler(InputStream is, String type, OutputStream redirect) {
		this.is = is;
		this.type = type;
		this.os = redirect;
	}

	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			if (os != null)
				pw = new PrintWriter(os);

			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			
			while (br!=null && (line = br.readLine()) != null) {
				if (pw != null)
					pw.println(line);
				
				logger.debug(type + ">" + line);
				//System.out.println(type + ">" + line);
			}

			if (pw != null)
				pw.flush();
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			logger.error("br has close()");
		} finally {
			try {
				if(pw!=null)
					pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(br!=null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(isr!=null)
					isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

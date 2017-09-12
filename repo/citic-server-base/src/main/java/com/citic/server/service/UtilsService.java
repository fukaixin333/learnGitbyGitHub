package com.citic.server.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author hubq
 *
 */

@Service
public class UtilsService {
	private static final Logger logger = LoggerFactory.getLogger(UtilsService.class);

	public UtilsService(){
		
	}
	
	public String getPreDatatime(String datatime, String freq)
			throws Exception {

		String predatatime = "";
		// yyyy-MM-dd HH:mm:ss
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime(datatime);

		if (freq.trim().equals("1")) {// 日

			DateTime predt = dt.minusDays(1);
			predatatime = fmt.print(predt);

		} else if (freq.trim().equals("2")) {// 周

			DateTime predt = dt.minusWeeks(1);
			predatatime = fmt.print(predt);

		} else if (freq.trim().equals("3")) {// 旬

			// 暂不实现

		} else if (freq.trim().equals("4")) {// 月

			DateTime predt = dt.minusMonths(1);
			predatatime = fmt.print(predt);

		} else if (freq.trim().equals("5")) {// 季

			DateTime predt = dt.minusMonths(3);
			predatatime = fmt.print(predt);

		} else if (freq.trim().equals("6")) {// 半年

			DateTime predt = dt.minusMonths(6);
			predatatime = fmt.print(predt);

		} else if (freq.trim().equals("7")) {// 年

			DateTime predt = dt.minusYears(1);
			predatatime = fmt.print(predt);

		} else {
			throw new Exception("任务计算频度（频度＝" + freq + "）非法，必须为1～7的数字");
		}

		return predatatime;

	}

	public boolean isFreqEnd(String datatime, String freq) throws Exception {
		boolean isFreqEnd = false;
		// yyyy-MM-dd HH:mm:ss

		DateTime dt = new DateTime(datatime);

		if (freq.trim().equals("1")) {// 日

			isFreqEnd = true;

		} else if (freq.trim().equals("2")) {// 周
			int iWeek = dt.getDayOfWeek();
			if (iWeek == DateTimeConstants.SUNDAY) {
				isFreqEnd = true;
			}

		} else if (freq.trim().equals("3")) {// 旬
			// 咱不实现

		} else if (freq.trim().equals("4")) {// 月
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
			if (dt.equals(lastdt)) {
				isFreqEnd = true;
			}
		} else if (freq.trim().equals("5")) {// 季
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd");
			String md = fmt.print(lastdt);
			if (dt.equals(lastdt)
					&& (md.equals("03-31") || md.equals("06-30")
							|| md.equals("09-30") || md.equals("12-31"))) {
				isFreqEnd = true;
			}

		} else if (freq.trim().equals("6")) {// 半年
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd");
			String md = fmt.print(lastdt);
			if (dt.equals(lastdt) && (md.equals("06-30") || md.equals("12-31"))) {
				isFreqEnd = true;
			}

		} else if (freq.trim().equals("7")) {// 年
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd");
			String md = fmt.print(lastdt);
			if (dt.equals(lastdt) && md.equals("12-31")) {
				isFreqEnd = true;
			}

		} else {
			throw new Exception("任务计算频度（频度＝" + freq + "）非法，必须为1～7的数字");
		}

		return isFreqEnd;
	}
	
	public String getFreqEnd(String datatime, String freq) throws Exception {
		String freqEnd = "";
		// yyyy-MM-dd

		DateTime dt = new DateTime(datatime);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		if (freq.trim().equals("1")) {// 日

			freqEnd = fmt.print(dt);

		} else if (freq.trim().equals("2")) {// 周
			// 暂不实现

		} else if (freq.trim().equals("3")) {// 旬
			// 咱不实现

		} else if (freq.trim().equals("4")) {// 月
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
		} else if (freq.trim().equals("5")) {// 季
			DateTime lastdt = dt.dayOfMonth().withMaximumValue();
			
			DateTimeFormatter fmt1 = DateTimeFormat.forPattern("YYYY");
			DateTimeFormatter fmt2 = DateTimeFormat.forPattern("MM");
			String yyyy = fmt1.print(dt);
			String mm = fmt2.print(dt);
			
			if(mm.equals("01") || mm.equals("02") || mm.equals("03") ){
				freqEnd = yyyy+"-03-31";
			}
			else if(mm.equals("04") || mm.equals("05") || mm.equals("06") ){
				freqEnd = yyyy+"-06-30";
			}
			else if(mm.equals("07") || mm.equals("08") || mm.equals("09") ){
				freqEnd = yyyy+"-09-30";
			}
			else if(mm.equals("10") || mm.equals("11") || mm.equals("12") ){
				freqEnd = yyyy+"-12-31";
			}
		} else if (freq.trim().equals("6")) {// 半年
			DateTimeFormatter fmt1 = DateTimeFormat.forPattern("YYYY");
			String yyyy = fmt1.print(dt);
			
			DateTimeFormatter fmt2 = DateTimeFormat.forPattern("MM");
			String mm = fmt1.print(dt);
			
			if(mm.equals("01") || mm.equals("02") || mm.equals("03") || mm.equals("04") || mm.equals("05") || mm.equals("06")){
				freqEnd = yyyy+"-06-30";
			}else{
				freqEnd = yyyy+"-12-31";
			}
		} else if (freq.trim().equals("7")) {// 年
			DateTimeFormatter fmt1 = DateTimeFormat.forPattern("YYYY");
			String yyyy = fmt1.print(dt);
			
			freqEnd = yyyy+"-12-31";

		} else {
			throw new Exception("任务计算频度（频度＝" + freq + "）非法，必须为1～7的数字");
		}

		return freqEnd;
	}
	
	public String getFirstLineFromFile(String filepath, String filename) {
		String str = "";

		FileReader fr = null;
		LineNumberReader lr = null;

		try {
			fr = new FileReader(filepath + File.separator + filename);
			lr = new LineNumberReader(fr, 512);
			str = lr.readLine().trim();
			fr.close();
			fr = null;
			lr.close();
			lr = null;
		} catch (FileNotFoundException e) {
			logger.error("FILENAME:" + filename);
		} catch (IOException e) {
			logger.error("IO error");
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (lr != null) {
					lr.close();
				}
			} catch (Exception ex) {
			}
		}

		return str;
	}

	public String date8to10(String dateStr8) {
		String dateStr10 = "";

		dateStr10 = dateStr8.substring(0, 4) + "-" + dateStr8.substring(4, 6) + "-" + dateStr8.substring(6,8);

		return dateStr10;
	}
	
	public boolean isDateStr(String datatime){
		boolean isDate = false;
		try{
			DateTime dt = new DateTime(datatime);
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
			
			if(fmt.print(dt).equals( datatime )){
				isDate = true;
			}
			
		}catch(Exception e){
			
		}
		return isDate;
	}

	public static void main(String[] args){
		UtilsService ut = new UtilsService();
		try{
			//boolean isFreqend = ut.isFreqEnd("2015-03-31", "6");
			
			//System.out.print("isFreqend="+isFreqend);
			
			String a = "";
			String[] as = a.split(",");
			
			System.out.print(as);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

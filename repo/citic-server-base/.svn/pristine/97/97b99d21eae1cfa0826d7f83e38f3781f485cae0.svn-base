
package com.citic.server.utils;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;


/**
 * 先解压缩，再对编码进行过滤，解决文件中乱码、半个中文等问题
 * @author gaojianxin
 *
 */
public class UnZipUtils {

	/**
	 * 
	 * @param gzSrcFile 压缩文件源文件
	 * @param desFile 解压缩的目标文件(去掉.Z)
	 * @param inEncoding GBK
	 * @param outEncoding UTF-8
	 * @param isFilter 是否过滤：解决乱码 默认都是 true
	 * @throws IOException
	 */
	public void unCompGzip(String gzSrcFile, String desFile,String inEncoding,String outEncoding,boolean isFilter, String filter, HashMap codeMap) throws IOException {
		FileInputStream fin = new FileInputStream(gzSrcFile);
		BufferedInputStream in = new BufferedInputStream(fin);
		final char[] buffer = new char[80960];
		
		String tmpEncoding =  StringUtils.isEmpty(inEncoding) ? Charset.defaultCharset().name() :inEncoding;
		String outTmpEncoding =  StringUtils.isEmpty(outEncoding) ? Charset.defaultCharset().name() :outEncoding;
		
		FileOutputStream out = new FileOutputStream(desFile);
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
		InputStreamReader reader = new InputStreamReader(gzIn,tmpEncoding);
		
		BufferedReader br = new BufferedReader(reader);
		OutputStreamWriter writer = new OutputStreamWriter(out,outTmpEncoding);
		
		String str1 = "";
		String str = "";
		int len = 0;
		while((str = br.readLine()) != null) {
			//判断是否以|结尾，不是，则表明改行没有结束，将下一行数据拼接到本行中
			if(!str.endsWith("|")) {
				str1 += str;
				continue;
			}
			str1 += str;
			
			//根据码表过滤
			if (null != filter && filter.equals("1")) {
				//遍历map
				Set<String> key = codeMap.keySet();
		        for (Iterator it = key.iterator(); it.hasNext();) {
		            String s = (String) it.next();
		            String val = codeMap.get(s).toString();
		            if (str1.contains(s)) {
						str1 = str1.replace(s, val);
					}
		        }
			}
			str1 = str1 + "\n";
			
			InputStream is = new ByteArrayInputStream(str1.getBytes());
			InputStreamReader reader1 = new InputStreamReader(is);
			
			while (-1 != (len = reader1.read(buffer))) {
				if (isFilter) {
					for (int i = 0; i < buffer.length; i++) {
						if(       (buffer[i] >= '\u0001' && buffer[i] <= '\u007f') 
								||(buffer[i] >= '\uFF00' && buffer[i] <= '\uFFEF') 
								||(buffer[i] >= '\u2000' && buffer[i] <= '\u206F') 
								||(buffer[i] >= '\u2460' && buffer[i] <= '\u24FF') 
								||(buffer[i] >= '\u2500' && buffer[i] <= '\u257F') 
								||(buffer[i] >= '\u2A00' && buffer[i] <= '\u2AFF') 
								||(buffer[i] >= '\u3000' && buffer[i] <= '\u303F') 
								||(buffer[i] >= '\u3200' && buffer[i] <= '\u32FF') 
								||(buffer[i] >= '\uF900' && buffer[i] <= '\uFAFF') 
								||(buffer[i] >= '\uFE50' && buffer[i] <= '\uFE6F') 
								||(buffer[i] >= '\u3400' && buffer[i] <= '\u4DBF') 
								||(buffer[i] >= '\u4E00' && buffer[i] <= '\u9fff') 
								) { 
							     continue;
							 } else {
								 buffer[i] = ' ';
							}
						}
				}

				writer.write(buffer, 0, len);
			}
			str1 = "";
		}
		
		/*int n = 0;
		while (-1 != (n = reader.read(buffer))) {
			if (isFilter) {
				for (int i = 0; i < buffer.length; i++) {
					if(       (buffer[i] >= '\u0001' && buffer[i] <= '\u007f')
							||(buffer[i] >= '\uFF00' && buffer[i] <= '\uFFEF')
							||(buffer[i] >= '\u2000' && buffer[i] <= '\u206F')
							||(buffer[i] >= '\u2460' && buffer[i] <= '\u24FF')
							||(buffer[i] >= '\u2500' && buffer[i] <= '\u257F')
							||(buffer[i] >= '\u2A00' && buffer[i] <= '\u2AFF')
							||(buffer[i] >= '\u3000' && buffer[i] <= '\u303F')
							||(buffer[i] >= '\u3200' && buffer[i] <= '\u32FF')
							||(buffer[i] >= '\uF900' && buffer[i] <= '\uFAFF')
							||(buffer[i] >= '\uFE50' && buffer[i] <= '\uFE6F')
							||(buffer[i] >= '\u3400' && buffer[i] <= '\u4DBF')
							||(buffer[i] >= '\u4E00' && buffer[i] <= '\u9fff')
							) { 
						     continue;
						 }else{
							 buffer[i] = ' ';
						}
					}
			}
			writer.write(buffer, 0, n);
		}*/
		
		writer.close();
		reader.close();
	}
	
	/**
	 * 
	 * @param gzSrcFile 压缩文件源文件
	 * @param desFile 解压缩的目标文件(去掉.Z)
	 * @param inEncoding GBK
	 * @param outEncoding UTF-8
	 * @param isFilter 是否过滤：解决乱码 默认都是 true
	 * @throws IOException
	 */
	public void unCompZ(String srcFile, String desFile, String inEncoding,String outEncoding, boolean isFilter, String filter, HashMap codeMap) throws FileNotFoundException, IOException {
		String inTmpEncoding =  StringUtils.isEmpty(inEncoding) ? Charset.defaultCharset().name() :inEncoding;
		String outTmpEncoding =  StringUtils.isEmpty(outEncoding) ? Charset.defaultCharset().name() :outEncoding;
		final char[] buffer = new char[80960];
		byte[] buf = new byte[10240];
		
		System.out.println("系统编码："+Charset.defaultCharset().name());
		
		UncompressInputStream uc = new UncompressInputStream(new FileInputStream(srcFile));
		
		BufferedInputStream in = new BufferedInputStream(uc);
		InputStreamReader reader = new InputStreamReader(in,inTmpEncoding);

		BufferedReader br = new BufferedReader(reader);
		FileOutputStream out = new FileOutputStream(desFile);
		
		OutputStreamWriter writer = new OutputStreamWriter(out,outTmpEncoding);
		String str1 = "";
		String str = "";
		int len = 0;
		while((str = br.readLine()) != null) {
			//判断是否以|结尾，不是，则表明改行没有结束，将下一行数据拼接到本行中
			if(!str.endsWith("|")) {
				str1 += str;
				continue;
			}
			str1 += str;
			
			//根据码表过滤
			if (null != filter && filter.equals("1")) {
				//遍历map
				Set<String> key = codeMap.keySet();
		        for (Iterator it = key.iterator(); it.hasNext();) {
		            String s = (String) it.next();
		            String val = codeMap.get(s).toString();
		            if (str1.contains(s)) {
						str1 = str1.replace(s, val);
					}
		        }
			}
			str1 = str1 + "\n";
			
			InputStream is = new ByteArrayInputStream(str1.getBytes());
			InputStreamReader reader1 = new InputStreamReader(is);
			
			while (-1 != (len = reader1.read(buffer))) {
				if (isFilter) {
					for (int i = 0; i < buffer.length; i++) {
						if(       (buffer[i] >= '\u0001' && buffer[i] <= '\u007f') 
								||(buffer[i] >= '\uFF00' && buffer[i] <= '\uFFEF') 
								||(buffer[i] >= '\u2000' && buffer[i] <= '\u206F') 
								||(buffer[i] >= '\u2460' && buffer[i] <= '\u24FF') 
								||(buffer[i] >= '\u2500' && buffer[i] <= '\u257F') 
								||(buffer[i] >= '\u2A00' && buffer[i] <= '\u2AFF') 
								||(buffer[i] >= '\u3000' && buffer[i] <= '\u303F') 
								||(buffer[i] >= '\u3200' && buffer[i] <= '\u32FF') 
								||(buffer[i] >= '\uF900' && buffer[i] <= '\uFAFF') 
								||(buffer[i] >= '\uFE50' && buffer[i] <= '\uFE6F') 
								||(buffer[i] >= '\u3400' && buffer[i] <= '\u4DBF') 
								||(buffer[i] >= '\u4E00' && buffer[i] <= '\u9fff') 
								) { 
							     continue;
							 } else {
								 buffer[i] = ' ';
							}
						}
				}

				writer.write(buffer, 0, len);
			}
			str1 = "";
		}
		
		/*int n = 0;
		while (-1 != (n = reader.read(buffer))) {
			if (isFilter) {
				for (int i = 0; i < buffer.length; i++) {
					System.out.println(buffer[i]);
					if(       (buffer[i] >= '\u0001' && buffer[i] <= '\u007f') 
							||(buffer[i] >= '\uFF00' && buffer[i] <= '\uFFEF') 
							||(buffer[i] >= '\u2000' && buffer[i] <= '\u206F') 
							||(buffer[i] >= '\u2460' && buffer[i] <= '\u24FF') 
							||(buffer[i] >= '\u2500' && buffer[i] <= '\u257F') 
							||(buffer[i] >= '\u2A00' && buffer[i] <= '\u2AFF') 
							||(buffer[i] >= '\u3000' && buffer[i] <= '\u303F') 
							||(buffer[i] >= '\u3200' && buffer[i] <= '\u32FF') 
							||(buffer[i] >= '\uF900' && buffer[i] <= '\uFAFF') 
							||(buffer[i] >= '\uFE50' && buffer[i] <= '\uFE6F') 
							||(buffer[i] >= '\u3400' && buffer[i] <= '\u4DBF') 
							||(buffer[i] >= '\u4E00' && buffer[i] <= '\u9fff') 
							) { 
						     continue;
						 }else{
							 buffer[i] = ' ';
						}
					}
			}
			writer.write(buffer, 0, n);
		}*/

		writer.close();
		reader.close();
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		 if(args.length <2){
			 System.out.println("input error:");
			 System.out.println("input: srcFile desFile  inEncoding outEncoding,");
			
		 }

		 	String srcFile = args[0];
		 	String desFile = args[1];
		 	String inEncoding = args[2];
		 	String outEncoding =args[3];
		 	HashMap codeMap = new HashMap();
		 	codeMap.put("穦","|");
		 	codeMap.put("璡","|");
		 	codeMap.put("昞","|");
		 	codeMap.put("昞","|");
		 	codeMap.put("脇","|");
		 	codeMap.put("聕","|");
		 	codeMap.put("磡","|");
		 	codeMap.put("簗","|");
		 	codeMap.put("蕓","|");
		 	codeMap.put("詜","|");
		 	codeMap.put("碶","|");
		 	codeMap.put("紎","|");
		 	codeMap.put("泶","|");
		 	codeMap.put("謡","|");
		 	codeMap.put("祙","|");
		 	codeMap.put("衸","|");
		 	codeMap.put("襹","|");
		 	codeMap.put("縷","|");
		 	codeMap.put("皘","|");
		 	codeMap.put("膢","|");
		 	codeMap.put("蟶","|");
		 	codeMap.put("蛗","|");
		 	codeMap.put("莬","|");
		 	codeMap.put("蝲","|");
		 	codeMap.put("硘","|");
		 	codeMap.put("羭","|");
		 	codeMap.put("僜","|");
		 	codeMap.put("褆","|");
		 	codeMap.put("藎","|");
		 	codeMap.put("纜","|");
		 	codeMap.put("苵","|");
		 	codeMap.put("蓔","|");
		 	codeMap.put("觸","|");
		 	codeMap.put("鄚","|");
		 	codeMap.put("秥","|");
		 	codeMap.put("粅","|");
		 	codeMap.put("讄","|");
		 	codeMap.put("鴟","|");
		 	codeMap.put("箌","|");
		 	codeMap.put("絴","|");
		 	codeMap.put("瞸","|");
		 	codeMap.put("葇","|");
		 	codeMap.put("緗","|");
		 	codeMap.put("蘾","|");
		 	codeMap.put("諀","|");
		 	codeMap.put("碶","|");
		 	codeMap.put("郳","|");
		 	
		 	UnZipUtils ts = new UnZipUtils();
		 	//ts.unCompGzip(srcFile, desFile, inEncoding, outEncoding, true);
		 	ts.unCompZ("/Users/muffy/jklswj.20141214.unl.00.Z", "/Users/muffy/jklswj.20141214.unl.00", inEncoding, outEncoding, true, "1", codeMap);
	}

}

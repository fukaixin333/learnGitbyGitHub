package com.citic.server.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;

/**
 * 通用工具类：JiBX数据编组和解组、物理文件读写等常用函数。
 * 
 * @author Ding Ke, Liu Xuanfei
 * @date 2016年3月7日 上午9:47:50
 */
public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	
	protected static final String OS_NAME = getSystemProperty("os.name"); // 操作系统名称
	
	public static final boolean IS_OS_LINUX = startsWith(OS_NAME, "Linux") || startsWith(OS_NAME, "LINUX");
	public static final boolean IS_OS_MAC = startsWith(OS_NAME, "Mac OS");
	public static final boolean IS_OS_WINDOWS = startsWith(OS_NAME, "Windows");
	
	private static CommonUtils commonUtils;
	
	private CommonUtils() {
	}
	
	public static CommonUtils getInstance() {
		if (commonUtils == null) {
			commonUtils = new CommonUtils();
		}
		return commonUtils;
	}
	
	// ==========================================================================================
	//                     JiBX 数据编组与解组
	// ==========================================================================================
	
	/**
	 * 数据编组（生成对象的 XML 表示），返回XML表示字符串。
	 * 
	 * @param root 数据编组结构中的根对象，即绑定(binding)中的顶层映射(top-level mapping)
	 * @return XML表示字符串
	 * @throws Exception on any error in finding or accessing factory
	 */
	public static String marshallContext(Object root) throws JiBXException {
		return marshallContext(root, false, null);
	}
	
	public static String marshallContext(Object root, boolean indent, String encoding) throws JiBXException {
		return marshallContext(root, null, indent, encoding);
	}
	
	public static String marshallContext(Object root, String bname) throws JiBXException {
		return marshallContext(root, bname, false);
	}
	
	public static String marshallContext(Object root, String bname, boolean indent) throws JiBXException {
		return marshallContext(root, bname, indent, null);
	}
	
	public static String marshallContext(Object root, String bname, boolean indent, String encoding) throws JiBXException {
		StringWriter out = new StringWriter();
		marshallWriter(root, bname, out, indent, encoding);
		return out.toString();
	}
	
	/**
	 * 数据编组（生成对象的 XML 表示），并写入流。
	 * 
	 * @param root 数据编组结构中的根对象，即绑定(binding)中的顶层映射(top-level mapping)
	 * @param out 输出流
	 * @throws Exception on any error in finding or accessing factory
	 */
	public static void marshallWriter(Object root, Writer out) throws JiBXException {
		marshallWriter(root, null, out, false, null);
	}
	
	public static void marshallWriter(Object root, Writer out, boolean indent) throws JiBXException {
		marshallWriter(root, null, out, indent, null);
	}
	
	public static void marshallWriter(Object root, Writer out, boolean indent, String encoding) throws JiBXException {
		marshallWriter(root, null, out, indent, encoding);
	}
	
	public static void marshallWriter(Object root, String bname, Writer out, boolean indent) throws JiBXException {
		marshallWriter(root, bname, out, indent, null);
	}
	
	public static void marshallWriter(Object root, String bname, Writer out, boolean indent, String encoding) throws JiBXException {
		IBindingFactory bfact = null;
		if (bname == null || bname.length() == 0) {
			bfact = BindingDirectory.getFactory(root.getClass());
		} else {
			bfact = BindingDirectory.getFactory(bname, root.getClass());
		}
		
		IMarshallingContext mctx = bfact.createMarshallingContext();
		mctx.setOutput(out); // UTF8Escaper
		
		if (indent) {
			mctx.setIndent(2, localLineSeparator(), ' ');
		}
		
		if (encoding == null) {
			mctx.marshalDocument(root);
		} else {
			mctx.marshalDocument(root, encoding, null); // No standalone 
		}
	}
	
	/**
	 * 数据编组：生成对象的 XML 表示，并写入物理文件。
	 * 
	 * @param root 数据编组结构中的根对象，即绑定(binding)中的顶层映射(top-level mapping)
	 * @param path XML文件存储目录
	 * @param filename XML文件名
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String marshallUTF8Document(Object root, String path, String filename) throws JiBXException, FileNotFoundException {
		return marshallDocument(root, null, path, filename, "UTF-8");
	}
	
	public static String marshallUTF8Document(Object root, String bname, String path, String filename) throws JiBXException, FileNotFoundException {
		return marshallDocument(root, bname, path, filename, "UTF-8");
	}
	
	/**
	 * 数据编组：生成对象的 XML 表示，并写入物理文件。
	 * 
	 * @param root 数据编组结构中的根对象，即绑定(binding)中的顶层映射(top-level mapping)
	 * @param path XML文件存储目录
	 * @param filename XML文件名
	 * @param enc 文档编码
	 * @return 全路径文件名
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String marshallDocument(Object root, String path, String filename, String enc) throws JiBXException, FileNotFoundException {
		return marshallDocument(root, null, path, filename, enc);
	}
	
	public static String marshallDocument(Object root, String bname, String path, String filename, String enc) throws JiBXException, FileNotFoundException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		String pathname = path + File.separator + filename;
		file = new File(pathname);
		FileOutputStream out = new FileOutputStream(file, false);
		try {
			marshallOutputStream(root, bname, out, enc);
			return pathname;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * 数据编组：生成对象的 XML 表示，并写入流。
	 * 
	 * @param root 数据编组结构中的根对象，即绑定(binding)中的顶层映射(top-level mapping)
	 * @param out 输出流
	 * @param enc 文档编码
	 * @throws Exception
	 */
	public static void marshallOutputStream(Object root, OutputStream out, String enc) throws JiBXException {
		marshallOutputStream(root, null, out, enc);
	}
	
	public static void marshallOutputStream(Object root, String bname, OutputStream out, String enc) throws JiBXException {
		IBindingFactory bfact;
		if (bname == null || bname.length() == 0) {
			bfact = BindingDirectory.getFactory(root.getClass());
		} else {
			bfact = BindingDirectory.getFactory(bname, root.getClass());
		}
		
		IMarshallingContext mctx = bfact.createMarshallingContext();
		mctx.setIndent(2, localLineSeparator(), ' ');
		mctx.setOutput(out, enc);
		mctx.marshalDocument(root, enc, null); // 不指定standalone文档标识
	}
	
	/**
	 * 数据解组：根据 XML 表示字符串构建对象
	 * 
	 * @param clazz 绑定的目标类
	 * @param xml XML 表示字符串
	 * @return 目标类对象
	 * @throws Exception if error creating parser or unmarshalling not supported by binding
	 */
	public static <T> T unmarshallContext(Class<T> clazz, String xml) throws JiBXException {
		return unmarshallContext(clazz, null, xml);
	}
	
	public static <T> T unmarshallContext(Class<T> clazz, String bname, String xml) throws JiBXException {
		StringReader reader = new StringReader(xml);
		return unmarshallReader(clazz, bname, reader);
	}
	
	/**
	 * 数据解组：根据 XML 表示流构建对象
	 * 
	 * @param clazz 绑定的目标类
	 * @param reander 写入流
	 * @return 目标类对象
	 * @throws Exception if error creating parser or unmarshalling not supported by binding
	 */
	public static <T> T unmarshallReader(Class<T> clazz, Reader reader) throws JiBXException {
		return unmarshallReader(clazz, null, reader);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unmarshallReader(Class<T> clazz, String bname, Reader reader) throws JiBXException {
		IBindingFactory bfact;
		if (bname == null || bname.length() == 0) {
			bfact = BindingDirectory.getFactory(clazz);
		} else {
			bfact = BindingDirectory.getFactory(bname, clazz);
		}
		
		IUnmarshallingContext umctx = bfact.createUnmarshallingContext();
		return (T) umctx.unmarshalDocument(reader);
	}
	
	/**
	 * 数据解组：根据 XML 表示文件构建对象
	 * 
	 * @param clazz 绑定的目标类
	 * @param pathname XML 表示文件路径
	 * @return 目标类对象
	 * @throws FileNotFoundException
	 * @throws Exception if error creating parser or unmarshalling not supported by binding
	 */
	public static <T> T unmarshallUTF8Document(Class<T> clazz, String pathname) throws JiBXException, FileNotFoundException {
		return unmarshallDocument(clazz, pathname, "UTF-8");
	}
	
	public static <T> T unmarshallUTF8Document(Class<T> clazz, String bname, String pathname) throws JiBXException, FileNotFoundException {
		return unmarshallDocument(clazz, bname, pathname, "UTF-8");
	}
	
	/**
	 * 数据解组：根据 XML 表示文件构建对象
	 * 
	 * @param clazz 绑定的目标类
	 * @param pathname XML 表示文件路径
	 * @param enc 文档编码。如果为 <code>null</code>，则默认为UTF-8
	 * @return 目标类对象
	 * @throws FileNotFoundException
	 * @throws Exception if error creating parser or unmarshalling not supported by binding
	 */
	public static <T> T unmarshallDocument(Class<T> clazz, String pathname, String enc) throws JiBXException, FileNotFoundException {
		FileInputStream ins = new FileInputStream(new File(pathname));
		try {
			return unmarshallInputStream(clazz, ins, enc);
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static <T> T unmarshallDocument(Class<T> clazz, String bname, String pathname, String enc) throws JiBXException, FileNotFoundException {
		FileInputStream ins = new FileInputStream(new File(pathname));
		try {
			return unmarshallInputStream(clazz, bname, ins, enc);
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static <T> T unmarshallInputStream(Class<T> clazz, InputStream ins, String enc) throws JiBXException {
		return unmarshallInputStream(clazz, null, ins, enc);
	}
	
	/**
	 * 数据解组：根据 XML 表示流构建对象
	 * 
	 * @param clazz 绑定的目标类
	 * @param bname 绑定文件别名
	 * @param ins 写入流
	 * @param enc 文档编码。如果为 <code>null</code>，则由解析器(parser)决定
	 * @return 目标类对象
	 * @throws Exception if error creating parser or unmarshalling not supported by binding
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshallInputStream(Class<T> clazz, String bname, InputStream ins, String enc) throws JiBXException {
		IBindingFactory bfact = null;
		
		if (bname == null || bname.length() == 0) {
			bfact = BindingDirectory.getFactory(clazz);
		} else {
			bfact = BindingDirectory.getFactory(bname, clazz);
		}
		
		IUnmarshallingContext umctx = bfact.createUnmarshallingContext();
		return (T) umctx.unmarshalDocument(ins, enc);
	}
	
	// ==========================================================================================
	//                     文件读写（I/O）
	// ==========================================================================================
	
	public static String writeTextFile(String text, String path, String filename) throws IOException {
		return writeBinaryFile(text.getBytes(), path, filename);
	}
	
	public static String writeTextFile(String text, String path, String filename, Charset charset) throws IOException {
		return writeBinaryFile(text.getBytes(charset), path, filename);
	}
	
	public static String writeTextFile(String text, String path, String filename, Charset charset, boolean append) throws IOException {
		return writeBinaryFile(text.getBytes(charset), path, filename, append);
	}
	
	/**
	 * 输出物理文件
	 * 
	 * @param bts 需输出物理文件的字节数组
	 * @param path 输出文件目录
	 * @param filename 输出文件名
	 * @return 输出文件路径
	 * @throws IOException if an I/O error occurs.
	 */
	public static String writeBinaryFile(byte[] bts, String path, String filename) throws IOException {
		return writeBinaryFile(bts, path, filename, false);
	}
	
	public static String writeBinaryFile(byte[] bts, String path, String filename, boolean append) throws IOException {
		String pathname = null;
		BufferedOutputStream bout = null;
		try {
			File file = new File(path);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			pathname = path + File.separator + filename;
			file = new File(pathname);
			bout = new BufferedOutputStream(new FileOutputStream(file, append));
			bout.write(bts);
		} finally {
			if (bout != null) {
				bout.flush();
				bout.close();
			}
		}
		return pathname;
	}
	
	public static byte[] readBinaryFile(String pathname) throws IOException {
		BufferedInputStream bins = null;
		ByteArrayOutputStream byout = null;
		try {
			FileInputStream fins = new FileInputStream(new File(pathname));
			bins = new BufferedInputStream(fins);
			byout = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int len = 0;
			while ((len = bins.read(buffer)) != -1) {
				byout.write(buffer, 0, len);
			}
			return byout.toByteArray();
		} finally {
			if (byout != null) {
				byout.flush();
				byout.close();
			}
			if (bins != null) {
				bins.close();
			}
		}
	}
	
	/**
	 * 此方法待实现
	 */
	public static void writeObjectFile() throws IOException {
		//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//		ObjectOutputStream objOut = new ObjectOutputStream(baos);
		//		objOut.writeObject(record);
		//		CommonUtils.writeBinaryFile(baos.toByteArray(), "E:\\pdftest", bdhm + ".bt");
		//		
		//		if (true) {
		//			throw new IllegalArgumentException("exit");
		//		}
	}
	
	/**
	 * 此方法待实现
	 */
	public static Object readObjectFile() throws IOException {
		return null;
	}
	
	/**
	 * 此方法已过时。
	 * <p>
	 * 使用{@link com.citic.server.crypto.MD5Coder#encodeHex(byte[], boolean)}。
	 * 
	 * @param strTemp
	 * @return
	 */
	@Deprecated
	public static String EncryptToMD5(byte[] strTemp) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	// ==========================================================================================
	//                     System Utility
	// ==========================================================================================
	
	/**
	 * 试图查找给定键的系统属性。在不可信的环境中可能会抛出{@link SecurityException}异常，在这种情况下捕获异常并返回一个空字符串。
	 * 
	 * @param key 系统属性的名称
	 * @return 系统属性的值。如果没有这样的值返回<code>null</code>；如果捕获{@link SecurityException}异常返回空字符串。
	 */
	public static String getSystemProperty(String key) {
		try {
			return System.getProperty(key);
		} catch (SecurityException e) {
			logger.warn("Can't access the System property " + key + ".");
		}
		return null;
	}
	
	/**
	 * 本地操作系统的换行符
	 * 
	 * @return LINUX='\n'，OS_MAC='\r'，WINDOWS='\r\n'；默认返回'\r\n'。
	 */
	public static String localLineSeparator() {
		if (IS_OS_LINUX) {
			return "\n";
		} else if (IS_OS_MAC) {
			return "\r";
		} else if (IS_OS_WINDOWS) {
			return "\r\n";
		} else {
			return "\r\n";
		}
	}
	
	// ==========================================================================================
	//                     Normal Utility
	// ==========================================================================================
	
	protected static boolean startsWith(String str, String prefix) {
		return str != null && str.startsWith(prefix);
	}
	
	// ==========================================================================================
	//                     Directory Utility
	// ==========================================================================================
	
	/**
	 * 获取以当前日期为分目录的相对路径
	 * <p>
	 * 键值参考{@link com.citic.server.runtime.Keys}
	 * 
	 * @param key_path 获取路径的键值
	 * @param key_directory 获取子目录的键值
	 * @return 相对路径
	 */
	public static String createRelativePath(String key_path, String key_directory) {
		return File.separator + ServerEnvironment.getStringValue(key_path, "temp") + File.separator + Utility.currDate8() + File.separator
				+ ServerEnvironment.getStringValue(key_directory, "attach");
	}
	
	/**
	 * 获取以当前日期为分目录的绝对路径
	 * <p>
	 * 键值参考{@link com.citic.server.runtime.Keys}
	 * 
	 * @param key_path 获取路径的键值
	 * @param key_directory 获取子目录的键值
	 * @return 绝对路径
	 */
	public static String createAbsolutePath(String key_path, String key_directory) {
		return ServerEnvironment.getFileRootPath() + createRelativePath(key_path, key_directory);
	}
}

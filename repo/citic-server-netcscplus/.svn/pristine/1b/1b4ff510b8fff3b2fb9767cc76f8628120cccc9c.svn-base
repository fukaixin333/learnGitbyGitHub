package com.citic.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * ZIP文件工具类
 * <p>
 * 使用 net.lingala.zip4j包实现相关操作，相对于JDK自带的zip操作类，其不仅支持密码操作，更加简单方便，且不依赖其他类库。
 * <p>
 * <strong>预定义的压缩级别</strong>
 * <ul>
 * <li>DEFLATE_LEVEL_FASTEST -
 * <li>DEFLATE_LEVEL_FAST -
 * <li>DEFLATE_LEVEL_NORMAL -
 * <li>DEFLATE_LEVEL_MAXIMUM -
 * <li>DEFLATE_LEVEL_ULTRA -
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年9月10日 下午2:45:42
 */
public final class Zip4jUtils {
	
	/**
	 * 以压缩方式更新并添加文件到压缩文件
	 * 
	 * @param zipPath 压缩文件路径
	 * @param filePaths 待添加文件路径
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件
	 * @see #addFilesWithDeflateComp(String, ArrayList)
	 */
	public static void addFilesWithDeflateComp(String zipPath, String[] filePaths) throws Exception {
		// 创建文件清单
		ArrayList<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		addFilesWithDeflateComp(zipPath, fileList);
	}
	
	/**
	 * 以压缩方式更新并添加文件到压缩文件<br />
	 * <strong>压缩方法</strong>：无损压缩，同时使用LZ77算法与哈夫曼编码（Huffman Coding）的一种无损数据压缩算法。<br />
	 * <strong>压缩等级</strong>：标准压缩
	 * <p>
	 * 如果压缩文件存在，所有文件将更新并添加到压缩文件；如果压缩文件不存在，将创建一个新的压缩文件。
	 * 
	 * @param zipPath 压缩文件路径
	 * @param fileList 待添加文件清单
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addFilesWithDeflateComp(String zipPath, ArrayList<File> fileList) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方法（M）
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩等级（L）
		// 更新并添加所有文件到压缩文件
		zipFile.addFiles(fileList, parameters);
	}
	
	/**
	 * 以仅存储方式更新并添加文件到压缩文件<br />
	 * <strong>压缩方法</strong>：仅存储<br />
	 * <strong>压缩等级</strong>：标准压缩
	 * <p>
	 * 如果压缩文件存在，所有文件将更新并添加到压缩文件；如果压缩文件不存在，将创建一个新的压缩文件。
	 * 
	 * @param zipPath 压缩文件路径
	 * @param filePaths 待添加文件路径
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addFilesWithStoreComp(String zipPath, String[] filePaths) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 创建文件清单
		ArrayList<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
		// 更新并添加所有文件到压缩文件
		zipFile.addFiles(fileList, parameters);
	}
	
	/**
	 * 以压缩方式更新并添加文件到压缩文件中的指定目录下<br />
	 * <strong>压缩方法</strong>：无损压缩<br />
	 * <strong>压缩等级</strong>：标准压缩
	 * <p>
	 * 如果压缩文件存在，所有文件将更新并添加到压缩文件；如果压缩文件不存在，将创建一个新的压缩文件。
	 * 
	 * @param zipPath 压缩文件路径
	 * @param folder 目录名称，例如：dir、dir1/dir2
	 * @param filePaths 待添加文件路径
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addFilesToFolderInZip(String zipPath, String folder, String[] filePaths) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 创建文件清单
		ArrayList<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setRootFolderInZip(folder);
		// 更新并添加所有文件到压缩文件
		zipFile.addFiles(fileList, parameters);
	}
	
	/**
	 * 以加密压缩方式更新并添加文件到压缩文件<br />
	 * <strong>压缩方法</strong>：无损压缩<br />
	 * <strong>压缩等级</strong>：标准压缩<br />
	 * <strong>加密算法</strong>：AES-256
	 * <p>
	 * 如果压缩文件存在，所有文件将更新并添加到压缩文件；如果压缩文件不存在，将创建一个新的压缩文件。
	 * 
	 * @param zipPath 压缩文件路径
	 * @param filePaths 待添加文件路径
	 * @param password 密码
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addFilesWithAESEncryption(String zipPath, String[] filePaths, String password) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 创建文件清单
		ArrayList<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES); // 加密算法
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256); // AES密钥长度
		parameters.setPassword(password.toCharArray()); // 密码
		// 更新并添加所有文件到压缩文件
		zipFile.addFiles(fileList, parameters);
	}
	
	/**
	 * 以加密压缩方式更新并添加文件到压缩文件
	 * 
	 * @param zipPath 压缩文件路径
	 * @param filePaths 待添加文件路径
	 * @param password 密码
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 * @see #addFilesWithStandardEncryption(String, ArrayList, String)
	 */
	public static void addFilesWithStandardEncryption(String zipPath, String[] filePaths, String password) throws Exception {
		// 创建文件清单
		ArrayList<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		addFilesWithStandardEncryption(zipPath, fileList, password);
	}
	
	/**
	 * 以加密压缩方式更新并添加文件到压缩文件<br />
	 * <strong>压缩方法</strong>：无损压缩<br />
	 * <strong>压缩等级</strong>：标准压缩<br />
	 * <strong>加密算法</strong>：标准ZIP加密
	 * <p>
	 * 如果压缩文件存在，所有文件将更新并添加到压缩文件；如果压缩文件不存在，将创建一个新的压缩文件。
	 * 
	 * @param zipPath 压缩文件路径
	 * @param fileList 待添加文件清单
	 * @param password 密码
	 * @return 压缩文件对象
	 * @throws ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addFilesWithStandardEncryption(String zipPath, ArrayList<File> fileList, String password) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密算法
		parameters.setPassword(password.toCharArray()); // 密码
		// 更新并添加所有文件到压缩文件
		zipFile.addFiles(fileList, parameters);
	}
	
	/**
	 * 直接将流更新并添加到压缩文件<br />
	 * <strong>压缩方法</strong>：无损压缩<br />
	 * <strong>压缩等级</strong>：标准压缩<br />
	 * 
	 * @param zipPath 压缩文件路径
	 * @param fileName 文件名
	 * @param in 待添加的流
	 * @return 压缩文件对象
	 * @throws Exception ZipException 如果<code>zipFile</code>为<code>null</code>，或者压缩文件存在且是分割文件。
	 */
	public static void addStreamToZip(String zipPath, String fileName, InputStream in) throws Exception {
		// 初始化ZipFile对象
		ZipFile zipFile = new ZipFile(zipPath);
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setFileNameInZip(fileName);
		parameters.setSourceExternalStream(true); // 指定直接从流中获取数据
		// 以流的方式直接更新并添加文件到压缩文件
		zipFile.addStream(in, parameters);
	}
	
	public static void createZipFile(String zipPath, String[] filePaths) throws Exception {
		List<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		createZipFile(zipPath, fileList);
	}
	
	public static void createZipFile(String zipPath, List<File> fileList) throws Exception {
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		// 创建压缩文件
		createZipFile0(zipPath, fileList, parameters);
	}
	
	public static void createZipFileWithAESEnc(String zipPath, String[] filePaths, String password) throws Exception {
		List<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		createZipFileWithAESEnc(zipPath, fileList, password);
	}
	
	public static void createZipFileWithAESEnc(String zipPath, List<File> fileList, String password) throws Exception {
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		parameters.setPassword(password.toCharArray());
		// 创建压缩文件
		createZipFile0(zipPath, fileList, parameters);
	}
	
	public static void createZipFileWithStandardEnc(String zipPath, String[] filePaths, String password) throws Exception {
		List<File> fileList = new ArrayList<File>();
		for (String filePath : filePaths) {
			fileList.add(new File(filePath));
		}
		createZipFileWithStandardEnc(zipPath, fileList, password);
	}
	
	public static void createZipFileWithStandardEnc(String zipPath, List<File> fileList, String password) throws Exception {
		// 初始化压缩属性
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
		parameters.setPassword(password.toCharArray());
		// 创建压缩文件
		createZipFile0(zipPath, fileList, parameters);
	}
	
	private static void createZipFile0(String zipPath, List<File> fileList, ZipParameters parameters) throws Exception {
		ZipOutputStream out = null;
		InputStream in = null;
		try {
			// 初始化压缩文件输出流
			out = new ZipOutputStream(new FileOutputStream(new File(zipPath)));
			// 循环处理文件
			for (File file : fileList) {
				out.putNextEntry(file, parameters);
				// 如果是目录，则不需要进一步处理
				if (file.isDirectory()) {
					out.closeEntry();
					continue;
				}
				// 初始化读入流
				in = new FileInputStream(file);
				// 读文件并写入到输出流
				byte[] buf = new byte[4096];
				int len = -1;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				// 关闭已复制（写入）到压缩文件的内容，并更新头部信息
				out.closeEntry();
				// 关闭文件输入流
				in.close();
			}
			// 处理头部信息并写入到压缩文件
			out.finish();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void progressInformation(ZipFile zipFile) throws ZipException {
		// When this variable is set, Zip4j will run any task in a new thread
		// If this variable is not set, Zip4j will run all tasks in the current
		// thread. 
		if (!zipFile.isRunInThread()) {
			return;
		}
		
		ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
		while (progressMonitor.getState() == ProgressMonitor.STATE_BUSY) {
			System.out.println("Percent Done: " + progressMonitor.getPercentDone());
			System.out.println("File: " + progressMonitor.getFileName());
			
			// ProgressMonitor.OPERATION_NONE - no operation being performed
			// ProgressMonitor.OPERATION_ADD - files are being added to the zip file
			// ProgressMonitor.OPERATION_EXTRACT - files are being extracted from the zip file
			// ProgressMonitor.OPERATION_REMOVE - files are being removed from zip file
			// ProgressMonitor.OPERATION_CALC_CRC - CRC of the file is being calculated
			// ProgressMonitor.OPERATION_MERGE - Split zip files are being merged
			switch (progressMonitor.getCurrentOperation()) {
			case ProgressMonitor.OPERATION_NONE:
				System.out.println("no operation being performed");
				break;
			case ProgressMonitor.OPERATION_ADD:
				System.out.println("Add operation");
				break;
			case ProgressMonitor.OPERATION_EXTRACT:
				System.out.println("Extract operation");
				break;
			case ProgressMonitor.OPERATION_REMOVE:
				System.out.println("Remove operation");
				break;
			case ProgressMonitor.OPERATION_CALC_CRC:
				System.out.println("Calcualting CRC");
				break;
			case ProgressMonitor.OPERATION_MERGE:
				System.out.println("Merge operation");
				break;
			default:
				System.out.println("invalid operation");
				break;
			}
		}
		
		// ProgressMonitor.RESULT_SUCCESS - Operation was successful
		// ProgressMonitor.RESULT_WORKING - Zip4j is still working and is not yet done with the current operation
		// ProgressMonitor.RESULT_ERROR - An error occurred during processing
		System.out.println("Result: " + progressMonitor.getResult());
		
		if (progressMonitor.getResult() == ProgressMonitor.RESULT_ERROR) {
			if (progressMonitor.getException() != null) {
				progressMonitor.getException().printStackTrace();
			} else {
				System.out.println("An error occurred without any exception");
			}
		}
	}
	
	/////////////////////// createSplitZipFile ///////////////////////////
	// zipFile.createZipFile(fileList, parameters);                     //
	// zipFile.createZipFile(fileList, parameters, true, 10485760);     //
	// zipFile.createZipFileFromFolder("", parameters, true, 10485760); //
	//////////////////////////////////////////////////////////////////////
	
	public static void extractAllFiles(String zipPath, String destPath, String password) throws Exception {
		extractAllFiles(new File(zipPath), destPath, password);
	}
	
	public static void extractAllFiles(File srcFile, String destPath, String password) throws Exception {
		ZipFile zipFile = new ZipFile(srcFile);
		if (zipFile.isEncrypted()) {
			zipFile.setPassword(password.toCharArray());
		}
		zipFile.extractAll(destPath);
	}
	
	public static void main(String[] args) throws Exception {
		String zipPath = "E:\\create.zip";
		String[] filePaths = {"E:\\a.txt", "E:\\b.txt", "E:\\c.txt", "E:\\misc"};
		// Zip4jUtils.progressInformation(zipPath, filePaths);
		
		//		Zip4jUtils.extractAllFiles(zipPath, "E:\\create", null);
		
		//		zipPath = "E:\\deflate.zip";
		//		Zip4jUtils.addFilesWithDeflateComp(zipPath, filePaths);
		
	}
}

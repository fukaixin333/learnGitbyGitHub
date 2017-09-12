package com.citic.server.shpsb.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.citic.server.crypto.DESCoder;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.ShpsbKeys;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.shpsb.ShpsbCode;
import com.citic.server.shpsb.domain.ShpsbReturnReceipt;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyls;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.SftpUtils;

/**
 * 结果反馈接口
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午5:45:14
 */
@Component("requestMessageServiceShpsb")
public class RequestMessageServiceShpsb {
	
	private final String rootPath = ServerEnvironment.getFileRootPath();
	private final String remoteUploadPath = ServerEnvironment.getStringValue(ShpsbKeys.REMOTE_UPLOAD_PATH, "upload");
	private final byte[] cryptoKey = ServerEnvironment.getStringValue(ShpsbKeys.CRYPTO_KEY).getBytes(StandardCharsets.UTF_8); //
	private SftpUtils sftpUtils;
	
	private SftpUtils getSFTPUtils() {
		if (sftpUtils == null) {
			sftpUtils = new SftpUtils();
			sftpUtils.setHost(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_HOST));
			sftpUtils.setUsername(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_USERNAME));
			sftpUtils.setPassword(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_PASSWORD));
		}
		return sftpUtils;
	}
	
	public ShpsbReturnReceipt sendResultMessage(String target, Object obj) throws Exception {
		int point = target.length() - 13;
		String prefix = ShpsbCode.fkOf(target.substring(0, point));
		String fileName = prefix + target.substring(point) + ".xml";
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, ShpsbKeys.FILE_DIRECTORY_SHPSB);
		String absolutePath = rootPath + relativePath;
		String absoluteFilePath = CommonUtils.marshallUTF8Document(obj, "binding_shpsb_" + prefix, absolutePath, fileName);
		
		String tmpAbsolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_TEMP, ShpsbKeys.FILE_DIRECTORY_SHPSB);
		byte[] xmlData = CommonUtils.readBinaryFile(absoluteFilePath);
		String data = DESCoder.encryptHex(xmlData, cryptoKey, false);
		CommonUtils.writeBinaryFile(data.getBytes(StandardCharsets.UTF_8), tmpAbsolutePath, fileName);
		getSFTPUtils().putWith(tmpAbsolutePath, fileName, remoteUploadPath, fileName + ".tmp");
		
		return new ShpsbReturnReceipt(fileName, relativePath, "3");
	}
	
	public List<ShpsbReturnReceipt> sendResultMessage(String target, ShpsbRequestJyls jyls, List<ShpsbRequestJylsMx> jylsmx) throws Exception {
		int point = target.length() - 13;
		String prefix = ShpsbCode.fkOf(target.substring(0, point));
		String realFileName = prefix + target.substring(point);
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, ShpsbKeys.FILE_DIRECTORY_SHPSB);
		String tmpAbsolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_TEMP, ShpsbKeys.FILE_DIRECTORY_SHPSB);
		String absolutePath = rootPath + relativePath;
		
		List<ShpsbReturnReceipt> rrs = new ArrayList<ShpsbReturnReceipt>();
		// xml
		String fileName = realFileName + ".xml";
		String absoluteFilePath = CommonUtils.marshallUTF8Document(jyls, "binding_shpsb_" + prefix, absolutePath, fileName);
		byte[] data = CommonUtils.readBinaryFile(absoluteFilePath);
		String mdata = DESCoder.encryptHex(data, cryptoKey, false);
		CommonUtils.writeBinaryFile(mdata.getBytes(StandardCharsets.UTF_8), tmpAbsolutePath, fileName);
		getSFTPUtils().putWith(tmpAbsolutePath, fileName, remoteUploadPath, fileName + ".tmp");
		rrs.add(new ShpsbReturnReceipt(fileName, relativePath, "3"));
		
		// txt
		if (jylsmx != null) {
			int seq = 1;
			int i = 0;
			StringBuilder builder = new StringBuilder();
			for (ShpsbRequestJylsMx mx : jylsmx) {
				builder.append(marshallTxt(mx));
				i++;
				if (i % 10000 == 0 || i == jylsmx.size()) { // 10000
					fileName = realFileName + "_" + seq + ".txt";
					absoluteFilePath = absolutePath + File.separator + fileName;
					// 写TXT
					CommonUtils.writeBinaryFile(builder.toString().getBytes(StandardCharsets.UTF_8), absolutePath, fileName);
					// 加密并发送
					data = CommonUtils.readBinaryFile(absoluteFilePath);
					mdata = DESCoder.encryptHex(data, cryptoKey, false);
					CommonUtils.writeBinaryFile(mdata.getBytes(StandardCharsets.UTF_8), tmpAbsolutePath, fileName);
					getSFTPUtils().putWith(tmpAbsolutePath, fileName, remoteUploadPath, fileName + ".tmp");
					rrs.add(new ShpsbReturnReceipt(fileName, relativePath, "3"));
					
					seq++;
					builder.delete(0, builder.length()); // clear
				}
			}
		}
		
		return rrs;
	}
	
	private String marshallTxt(ShpsbRequestJylsMx mx) throws Exception {
		StringBuilder buffer = new StringBuilder();
		buffer.append(mx.getAh() == null ? "" : mx.getAh()); // 案号
		buffer.append("┇");
		buffer.append(mx.getZh() == null ? "" : mx.getZh()); // 账（卡）号
		buffer.append("┇");
		buffer.append(mx.getJyrq() == null ? "" : mx.getJyrq()); // 交易日期
		buffer.append("┇");
		buffer.append(mx.getJysj() == null ? "" : mx.getJysj()); // 交易时间
		buffer.append("┇");
		buffer.append(mx.getDfzh() == null ? "" : mx.getDfzh()); // 对方账号
		buffer.append("┇");
		buffer.append(mx.getDfhm() == null ? "" : mx.getDfhm()); // 对方行名
		buffer.append("┇");
		buffer.append(mx.getDfhm() == null ? "" : mx.getDfhm());// 对方户名
		buffer.append("┇");
		buffer.append(mx.getBz() == null ? "" : mx.getBz()); // 币种
		buffer.append("┇");
		buffer.append(mx.getJyje() == null ? "" : mx.getJyje()); // 交易金额
		buffer.append("┇");
		buffer.append(mx.getJdbz() == null ? "" : mx.getJdbz()); // 借贷标记
		buffer.append("┇");
		buffer.append(mx.getJyqd() == null ? "" : mx.getJyqd()); // 交易渠道
		buffer.append("┇");
		buffer.append(mx.getJywd() == null ? "" : mx.getJywd()); // 交易网点
		buffer.append("┇");
		buffer.append(mx.getIp() == null ? "" : mx.getIp()); // IP地址
		buffer.append("┇");
		buffer.append(mx.getMac() == null ? "" : mx.getMac()); // MAC地址
		buffer.append("┇");
		buffer.append(mx.getBeiz() == null ? "" : mx.getBeiz()); // 备注
		buffer.append("┇");
		buffer.append(mx.getJyglh() == null ? "" : mx.getJyglh()); // 交易关联号
		buffer.append("┇");
		buffer.append(mx.getJyye() == null ? "" : mx.getJyye()); // 交易后余额
		buffer.append("┇");
		buffer.append(mx.getCph() == null ? "" : mx.getCph()); // 传票号
		buffer.append("┇");
		buffer.append(mx.getDfzzlx() == null ? "" : mx.getDfzzlx()); // 交易对手证照类型
		buffer.append("┇");
		buffer.append(mx.getDfzzhm() == null ? "" : mx.getDfzzhm()); // 交易对手证照号
		buffer.append("┇");
		buffer.append(mx.getJylsh() == null ? "" : mx.getJylsh()); // 系统交易流水号
		
		buffer.append(CommonUtils.localLineSeparator()); // 
		return buffer.toString();
	}
}

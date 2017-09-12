package com.citic.server.cbrc.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br42_msg;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.domain.BR42_sequences;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.utils.Zip4jUtils;

public class PacketBo extends BaseBo {
	// private static final Logger logger = (Logger) LoggerFactory.getLogger(PacketBo.class);
	
	private MM40_cxqq_cbrcMapper br40_cxqqMapper;
	
	public PacketBo(ApplicationContext ac) {
		super(ac);
		this.br40_cxqqMapper = (MM40_cxqq_cbrcMapper) ac.getBean("MM40_cxqq_cbrcMapper");
	}
	
	public Br42_packet makePacket(MC21_task_fact mc21_task_fact, ArrayList<File> xmlFileList) throws Exception {
		Br42_packet packet = new Br42_packet();
		
		String taskType = mc21_task_fact.getTasktype();
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String organkey = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		String sqjgdm = mc21_task_fact.getTaskobj();
		
		String tgroupid = null;
		String rwlsh = null;
		if (mc21_task_fact.getTgroupid() == null || mc21_task_fact.getTgroupid().length() == 0) {
		} else {
			tgroupid = StringUtils.substringBefore(mc21_task_fact.getTgroupid(), "$");
			rwlsh = StringUtils.substringAfter(mc21_task_fact.getTgroupid(), "$");
		}
		
		packet.setTasktype(taskType);
		packet.setPacketkey(qqdbs);
		packet.setRwlsh(rwlsh); // 任务流水号
		//查询数据包下的xml信息打包
		List<Br42_msg> msgList = br40_cxqqMapper.getBr42_msg(packet);
		if (msgList.size() > 0) {
			if (xmlFileList == null) {
				xmlFileList = new ArrayList<File>(msgList.size());
			}
			
			for (Br42_msg _msg : msgList) {
				xmlFileList.add(new File(_msg.getMsg_filepath()));
			}
			
			//打包
			String rootPath = ServerEnvironment.getFileRootPath();
			String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, CBRCKeys.getFileDirectoryKey(taskType)) + File.separator + qqdbs;
			File file = new File(rootPath + relativePath);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			
			// 执行打包
			String xmlZipFileSEQ = "000000000002"; // 接收回执文书已经使用了000000000001
			String zipFileName;
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) {
				// 深圳公安局命名规范：银行代码【8位】+请求单标识【22位】+序号【12位，000000000001-999999999999】.ZIP
				zipFileName = organkey + qqdbs + xmlZipFileSEQ + ".zip";
				String absoluteZipFilePath = rootPath + relativePath + File.separator + zipFileName;
				// 先删除可能已经存在的同名压缩包文件
				FileUtils.deleteFile(absoluteZipFilePath);
				// 以普通压缩模式将文件添加到压缩包（打包）
				Zip4jUtils.addFilesWithDeflateComp(absoluteZipFilePath, xmlFileList);
			} else {
				// 反馈结果报文包
				if (tgroupid == null || tgroupid.length() == 0) {
				} else {
					xmlZipFileSEQ = StringUtils.leftPad(tgroupid, 12, '0');
				}
				
				// CBRC命名规范：查控机构代码【6/8位】 +银行代码【17位】+请求单标识【22位】+序号【12位，000000000001-999999999999】.ZIP
				String realZipFileName = sqjgdm + organkey + qqdbs + xmlZipFileSEQ;
				
				zipFileName = realZipFileName + ".zip";
				String absoluteZipFilePath = rootPath + relativePath + File.separator + zipFileName;
				// 先删除可能已经存在的同名压缩包文件
				FileUtils.deleteFile(absoluteZipFilePath);
				
				if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GAOJIAN.equals(taskType)) { // 国家安全机关
					Zip4jUtils.addFilesWithDeflateComp(absoluteZipFilePath, xmlFileList); // 无密码压缩方式
				} else {
					// 以标准加密模式将文件添加到压缩包（打包）
					Zip4jUtils.addFilesWithStandardEncryption(absoluteZipFilePath, xmlFileList, realZipFileName); // 设置密码的压缩方式
				}
			}
			
			// 反馈结果报文包信息
			if (packet.getRwlsh() == null || packet.getRwlsh().length() == 0) {
				packet.setPacketkey(qqdbs);
			} else {
				packet.setPacketkey(packet.getRwlsh());
			}
			
			packet.setPack_type_cd("");
			packet.setOrgankey_r(organkey);
			packet.setSenddate_dt(DtUtils.getNowTime());
			packet.setStatus_cd("0");
			packet.setCreate_dt(DtUtils.getNowTime());
			// 先删除数据库中数据
			br40_cxqqMapper.delBr42_packet(packet);
			// 插入数据库
			packet.setFilename(zipFileName);
			packet.setFilepath(relativePath + File.separator + zipFileName);
			br40_cxqqMapper.insertBr42_packet(packet);
			
			// 清理XML物理文件
			for (File xmlFile : xmlFileList) {
				if (xmlFile.exists()) {
					xmlFile.delete();
				}
			}
		}
		
		return packet;
	}
	
	protected String getDyPacketSeqCBRC(String qqdbs, String organkey, String oldseq) throws Exception {
		String packetSeq = "";
		if (oldseq == null || (oldseq != null && oldseq.equals(""))) {
			oldseq = "0";
		}
		BR42_sequences br42_sequences = new BR42_sequences();
		br42_sequences.setOrgankey_r(organkey);
		br42_sequences.setPacket_seq(qqdbs + "_D");
		BR42_sequences br42_sequences1 = common_Mapper.getBr42_sequences(br42_sequences);
		if (br42_sequences1 != null && !br42_sequences1.getOrgankey_r().equals("")) {
			Integer msgseq = br42_sequences1.getMsg_num();
			br42_sequences1.setMsg_num(new Integer(msgseq.intValue() + 1));
			common_Mapper.updateBr42_sequences(br42_sequences1);
			packetSeq = StrUtils.fillStr(12, msgseq.intValue() + 1 + Integer.parseInt(oldseq) + "");
		} else {
			common_Mapper.insertBr42_sequences(br42_sequences);
			packetSeq = StrUtils.fillStr(12, 1 + Integer.parseInt(oldseq) + "");
		}
		
		return packetSeq;
	}
}

package com.citic.server.service.task.tk_ds101;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.mapper.MC00_ds_tablesMapper;
import com.citic.server.service.base.NeetReCalException;

/**
 * 数据源标识在本地，以文件形式存在
 * 
 * @author hubq
 */
public class LocalFile extends BaseDS {
	private static final Logger logger = LoggerFactory.getLogger(LocalFile.class);

	public LocalFile(ApplicationContext ac, MC00_datasource ds, MC00_task_fact tf) {
		super(ac, ds, tf);
	}

	/**
	 * 广发银行客户化开发，标志文件以ok结尾，通过标志文件个数，判断是否文件已全部送到 目前抽取文件个数为5 龙久优化：
	 * 1、标志文件个数从，从MC00_Ds_TABLES取taskid=TK_ETL101的记录总数 2、过滤文件不要写死，从表中取endStr
	 * 
	 * 宏程：发给梁春雨、雷洪量 说明： 1.不允许放到基础工程，只允许放到现场工程 2、类名称、路径不允许修改 3、标志文件不支持前缀
	 * 4、数据日期在目录中，目前必须为yyyymmdd 判断当前任务是否可以被设置
	 */
	@Override
	public boolean run() throws Exception {
		boolean isSuccess = false;
		HashMap flagFileHash = (HashMap) super.getCacheService().getCache("flagfile", HashMap.class);
		MC00_flagfile ff = (MC00_flagfile) flagFileHash.get(this.getMC00_datasource().getDsid());
		String caldate = StringUtils.remove(super.getMC00_task_fact().getDatatime(), "-");
		// 获取路径下文件
		String filePathStr = ff.getLocalfilepath(); // 标识文件路径
		// String preStr = ff.getFilepre();// 标识文件前缀
		String endStr = ff.getFileend().substring(1);// 标识文件后缀
		String realPath = StringUtils.replace(filePathStr, "yyyymmdd", caldate);
		// 获取标志文件的个数
		MC00_ds_tablesMapper mC00_ds_tablesMapper = (MC00_ds_tablesMapper) this.getAc().getBean("MC00_ds_tablesMapper");
		int flag_File_Num = mC00_ds_tablesMapper.get_Flagfile_Count();// 标志文件个数
		File filePath = new File(realPath);
		if (filePath.isDirectory()) {
			Collection<?> flagFiles = FileUtils.listFiles(filePath, new String[] { endStr }, false);
			if (flagFiles.size() == flag_File_Num) {
				isSuccess = true;
			} else {
				logger.debug("标志文件路径："+filePath);
				logger.debug("flagFiles.size() "+flagFiles.size() );
				logger.debug("标志文件个数"+flag_File_Num);
				logger.debug("标志文件个数不足" + flag_File_Num + "个......");
			}
		} else {
			logger.debug("标志文件路径[" + filePath + "]不存在.....");
		}
		if (!isSuccess) {// 没有找到符合的数据标识文件，本任务需要循环探测
			throw new NeetReCalException();
		}
		return isSuccess;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

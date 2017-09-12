package com.citic.server.service.task.tk_ds101;

import java.io.File;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.UtilsService;
import com.citic.server.service.base.NeetReCalException;
import com.citic.server.utils.FileUtils;

/**
 * 数据源标识在本地，以文件形式存在
 * 
 * @author hubq
 */
public class LocalFile extends BaseDS {

	private static final Logger logger = LoggerFactory
			.getLogger(LocalFile.class);

	public LocalFile(ApplicationContext ac, MC00_datasource ds, MC00_task_fact tf) {
		super(ac, ds, tf);
	}

	/**
	 * 标志文件规范示例: prefix_yyyy-mm-dd.suffix(10位日期)或者prefix_yyyymmdd.suffix(8位日期)
	 * 
	 * 判断当前任务是否可以被设置
	 */
	@Override
	public boolean run() throws Exception {

		boolean isSuccess = false;
		try{
			
		
		UtilsService utilsService = new UtilsService();
		HashMap flagFileHash = (HashMap) this.getCacheService().getCache(
				"flagfile", HashMap.class);

		MC00_flagfile ff = (MC00_flagfile) flagFileHash.get(this
				.getMC00_datasource().getDsid());

		// 获取路径下文件
		String FilePath = ff.getLocalfilepath(); // 标识文件路径
		/**
		 * 替换标识文件所在的路径中的 日期标识
		 */
		String currtsk_datatime = this.getMC00_task_fact().getDatatime();
		FileUtils fileUtils = new FileUtils();
		FilePath = fileUtils.replateDTpath(FilePath, currtsk_datatime);
		//
		String preStr = ff.getFilepre();// 标识文件前缀
		String endStr = ff.getFileend();// 标识文件后缀

		File file = new File(FilePath);
		// 文件数组
		if (file != null) {
			File[] tmpfiles = file.listFiles();
			if(tmpfiles!=null){//当时间不到，目录不存在的情况（客户自己建目录）时
				
				for (int i = 0; i < tmpfiles.length; i++) {
					if (tmpfiles[i].isFile()) {
						String datatime = "";
						String filename = tmpfiles[i].getName();
						logger.debug("文件名称：" + filename + ";文件长度"
								+ filename.length());

						if (filename.indexOf(preStr) < 0
								|| filename.indexOf(endStr) < 0) {
							logger.debug(filename + ";前缀不对，此文件不是标识文件！");
							continue;// 前缀不对，此文件不是标识文件
						}

						if (filename.indexOf(".") > 0) {
							datatime = filename.substring(0, filename.indexOf("."));
						}
						datatime = datatime.substring(preStr.length());

						if (datatime.length() == 10) {// 10位日期 yyyy-mm-dd
							//
						} else if (datatime.length() == 8) {// 8位日期
							datatime = utilsService.date8to10(datatime);
						}
						// 判断日期格式合法性
						if (!utilsService.isDateStr(datatime)) {// 在文件的第一行记录的数据时间

							datatime = utilsService.getFirstLineFromFile(FilePath,
									filename);

							if (datatime == null || datatime.equals("")) {
								logger.error("标识文件非法：" + filename);
							}

							if (datatime.length() == 8) {
								datatime = utilsService.date8to10(datatime);
							}

							if (!utilsService.isDateStr(datatime)) {
								logger.error("未找到标识文件日期标识位置,请检查标识文件位置！");
								continue;
							}

						}

						if (datatime.equals(this.getMC00_task_fact().getDatatime())) {
							isSuccess = true;
							break;
						}

					}
				}
				//==
				
			}

		}

		}catch(Exception e){
			isSuccess = false;
		}finally{
			if(!isSuccess){//没有找到符合的数据标识文件，本任务需要循环探测
				throw new NeetReCalException();
			}	
		}
		
		
		return isSuccess;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

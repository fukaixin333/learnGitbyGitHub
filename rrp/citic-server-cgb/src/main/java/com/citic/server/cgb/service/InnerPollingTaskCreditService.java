/**
 * 
 */
package com.citic.server.cgb.service;


import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.cgb.domain.GatewayHeader;
import com.citic.server.cgb.domain.request.AfpSuspicious;
import com.citic.server.dict.DictCoder;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.inner.domain.CustomerIDInfo;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.dx.mapper.Br20_md_info_cgbMapper;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.google.common.collect.Lists;


/**
 * @author yinxiong
 * @date 2016年11月29日
 */
@Service
public class InnerPollingTaskCreditService {
	private static final Logger logger = LoggerFactory.getLogger(InnerPollingTaskCreditService.class);

	@Autowired
	public Br20_md_info_cgbMapper br20_md_info_cgbMapper;
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService messageService;
	@Autowired
	private DictCoder dictCoder;
	// 信用卡任务锁
	private String UNLOCK = "0";

	/**
	 * 信用卡核心文件推送入口 <br>
	 * 1.查询行内的黑名单数据，调用核心接口，获取内部认定名单信息（客户名+证件号码+证件类型用接口返回的，其它沿用黑名单的数据）<br>
	 * 2.内部名单入库<br>
	 * 3.修改待发送到核心的名单数据为中间状态9【避免发送过程中有新的数据过来】，查询出这些数据<br>
	 * 4.用发送模块发送数据<br>
	 * 5.更新发送核心状态为已发送<br>
	 * 
	 * @param br20_md_info
	 */
	public void pushFile() {
		// 锁状态检测判断是否有正在进行的任务
		String lockStatus = br20_md_info_cgbMapper.selectLockStatusByCredit();
		if (UNLOCK.equals(lockStatus)) {
			try {
				// 加锁
				br20_md_info_cgbMapper.updateLockStatusByCredit("1");
				// 查询待发送到核心的内部认定名单
				ArrayList<Br20_md_info> intefacelist = br20_md_info_cgbMapper.getInBankDataByCore();
				// 调用核心接口，获取内部认定名单信息
				ArrayList<Br20_md_info> innerlist = this.getPartyInfoByCoreInterface(intefacelist);
				// 内部认定名单信息入库
				this.insertPartyInfo(innerlist);
				// 释放资源
				innerlist.clear();
				// 获取发送条数限制
				String topSize = br20_md_info_cgbMapper.selectTop_sizeByCredit();
				// 将待发送的数据状态改为中间状态
				br20_md_info_cgbMapper.updateSend_flagByMiddleCredit(topSize);
				// 获取br20_md_data_log中待发送核心的名单
				ArrayList<Br20_md_info> incrementlist = br20_md_info_cgbMapper.getIncrementSendDataCredit();
				// 开始推送数据给卡核心
				if (incrementlist != null && incrementlist.size() > 0) {
					ArrayList<Br20_md_info> err_list = this.httpSendDataToCredit(incrementlist);
					// 释放资源
					incrementlist.clear();
					updateCredit_send_flag("9", Utility.currDateTime19());// 更新名单数据发送状态为已发送
					// 重置发送失败的数据
					if (err_list != null && err_list.size() > 0) {
						for (Br20_md_info br20_info : err_list) {
							br20_md_info_cgbMapper.updateCredit_send_flagByID(br20_info);
						}
					} else {
						logger.info("==数据推送信用卡成功==");
					}
				} else {
					logger.info("==没有待推送给行用卡的名单数据==");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			} finally {
				// 解锁 0:未上锁 1：已上锁
				br20_md_info_cgbMapper.updateLockStatusByCredit("0");
			}
		} else {
			logger.info("==已存在推送名单数据到信用卡核心的任务==");
		}

	}

	/**
	 * HTTP推送数据
	 * 
	 * @param incrementlist
	 * @return
	 * @author yinxiong
	 * @date 2016年12月2日 下午4:41:05
	 */
	private ArrayList<Br20_md_info> httpSendDataToCredit(ArrayList<Br20_md_info> incrementlist) {

		ArrayList<Br20_md_info> err_list = new ArrayList<Br20_md_info>();// 记录发送失败的数据
		String flag = br20_md_info_cgbMapper.getAllLoadFlag();// 0：全量加载关闭 1:全量加载开启
		for (Br20_md_info br20_md : incrementlist) {
			try {
				// 将字段进行转吗
				dictCoder.transcode(br20_md, null);
				// 生成xml字符串
				String xml = this.getSoapXml(br20_md);

				// 全量文件时不打印该信息（太多了）
				if ("0".equals(flag)) {
					logger.info("生成的xml信息\r\n" + xml);
				}

				// http发送给信用卡
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(ServerEnvironment.getStringValue(CgbKeys.MD_REMOTE_ACCESS_URL));
				StringEntity strEnt = new StringEntity(xml, "gb18030");
				httppost.setEntity(strEnt);
				CloseableHttpResponse response = httpclient.execute(httppost);

				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					logger.info("正在解析信用卡回执报文...");
					HttpEntity entity = response.getEntity();
					try {
						AfpSuspicious partyDto = new AfpSuspicious();
						partyDto = (AfpSuspicious) CommonUtils.unmarshallInputStream(AfpSuspicious.class, "binding_cgb_suspicious", entity.getContent(), "gb18030");

						if ("01".equals(partyDto.getGatewayHeader().getGwErrorCode())) {// 网关错误标识（01-成功；00-错误）
							if (StringUtils.isBlank(partyDto.getResult())) {// 信用卡处理标识 Y - 处理成功、N -
																			// 处理失败（正常） Z -
																			// 正常不处理（正常）
								br20_md.setCredit_sbyy("信用卡端处理异常,流水号：" + partyDto.getGatewayHeader().getSenderSN());
								err_list.add(br20_md);
							}
						} else {
							br20_md.setCredit_send_flag("0");// 重发设置
							br20_md.setCredit_sbyy("网关返回错误消息：" + partyDto.getGatewayHeader().getGwErrorMessage());
							err_list.add(br20_md);
						}
					} catch (Exception e) {
						logger.info("响应的xml信息\r\n" + IOUtils.toString(entity.getContent(), "gb18030"));
						logger.error("通过JiBX解析信用卡回执报文失败：" + e.getMessage());
					} finally {
						EntityUtils.consume(entity);
					}
				} else {
					br20_md.setCredit_send_flag("0");// 重发设置
					br20_md.setCredit_sbyy("与网关通讯异常");
					err_list.add(br20_md);
					logger.info("与网关通讯异常，http状态码：" + statusCode);
				}
			} catch (Exception e) {
				br20_md.setCredit_sbyy("XML生成发生异常或HTTP发送异常");
				err_list.add(br20_md);
				logger.error(e.getMessage(), e);
			}

		}
		return err_list;
	}

	/**
	 * 调用核心的个人客户信息（025317）接口，获取个人客户信息<br>
	 * 根据规则个人的才需要调用
	 * 
	 * @param intefacelist
	 * @return
	 */
	private ArrayList<Br20_md_info> getPartyInfoByCoreInterface(ArrayList<Br20_md_info> intefacelist) {

		ArrayList<Br20_md_info> credit_list = Lists.newArrayList();// 信用卡客户信
		if (intefacelist != null && intefacelist.size() > 0) {
			for (Br20_md_info br20_md_info : intefacelist) {
				try {
					IndividualCustomer info = messageService.queryIndividualCustomerInfo(br20_md_info.getMd_value());
					if (info != null && info.getOpenIDInfo() != null) {
						CustomerIDInfo openIDInfo = info.getOpenIDInfo();//开户信息
						if (!"".equals(openIDInfo.getIdNumber())) {
							// 更新记录的接口调用状态,避免重复查询
							br20_md_info.setInterface_type("3");
							br20_md_info_cgbMapper.updateInterface_typeByID(br20_md_info);
							// 设置内部认定名单数据
							br20_md_info.setMd_kind("IDType_IDNumber");// 修改实体类型为证件类
							br20_md_info.setMd_code(UUID.randomUUID().toString().replaceAll("-", ""));// 设置名单编码
							br20_md_info.setMd_type("3");// 设置名单为内部认定名单
							br20_md_info.setCardtype(openIDInfo.getIdType());// 证件类型
							br20_md_info.setMd_value(openIDInfo.getIdNumber());// 证件号码
							br20_md_info.setP_name(info.getChineseName());
							br20_md_info.setInterface_type("0");// 0:不调用接口 1：调用核心接口 2：调用信用卡接口
																// 3：已调用核心接口 4：已调用信用卡接口
							br20_md_info.setSend_type("2");// 0:都发 1:发送核心 2:发送信用卡
							br20_md_info.setMd_flag("1");// 名单状态 0:无效 1:有效 2:白名单
							br20_md_info.setSend_flag("0");// 重置核心发送状态
							br20_md_info.setLast_update_time(Utility.currDateTime19());// 设置更新时间

							// 将核心证件类型转换为监管证件类型
							dictCoder.reverse(br20_md_info, null);

							credit_list.add(br20_md_info);
						} else {
							logger.error("个人客户信息-证件号码缺失");
						}
					} else {
						logger.error("获取个人客户信息失败");
					}
				} catch (Exception e) {
					logger.error("调用个人客户信息（025317）接口失败：" + e.getMessage(),e);
				}
			}
		}
		return credit_list;
	}

	/**
	 * 客户信息入库
	 * 
	 * @param map
	 */
	private void insertPartyInfo(ArrayList<Br20_md_info> list) {
		try {
			if (list != null && list.size() > 0) {
				// 插入Br20_md_data_log
				br20_md_info_cgbMapper.insertBr20_md_data_logByBatch(list);
				// 插入Br20_md_data
				for (int i = 0; i < list.size(); i++) {
					Br20_md_info md = list.get(i);
					if("-".equals(md.getOperate_flag())){
						//根据三证删除数据
						br20_md_info_cgbMapper.deleteBr20_md_dataByKeys(md);
					}else{
						//根据三证更新数据
						br20_md_info_cgbMapper.updateBr20_md_dataByMergeOne(md);
					}
				}
			} else {
				logger.info("没有需要推送信用卡核心的客户名单信息");
			}
		} catch (Exception e) {
			logger.error("客户信息入库发生异常:" + e.getMessage(),e);
		}
	}

	/**
	 * 对象转换为soapxml
	 * 
	 * @param md
	 * @return
	 * @throws JiBXException
	 */
	private String getSoapXml(Br20_md_info md) throws JiBXException {
		AfpSuspicious xmlDto = new AfpSuspicious();
		String time = Utility.currDateTime14() + (int) ((Math.random() * 9 + 1) * 10000000);// 日期＋随机数的22位流水号
		// 通用报文头处理
		GatewayHeader header = new GatewayHeader();
		header.setReceiverId("SWT3");// 接收方系统标识
		header.setSenderId("AFPN");// 发起放系统标识
		header.setCommCode(GatewayHeader.COMMCODE_REQUEST);// 请求：500001 响应：510001
		header.setSenderSN(time);
		header.setSenderDate(time.substring(0, 8));
		header.setSenderTime(time.substring(8, 14));
		header.setTradeCode("T00089");// 交易代码
		xmlDto.setGatewayHeader(header);
		// header128处理
		xmlDto.setAcqDate(time.substring(0, 8));
		xmlDto.setAcqTime(time.substring(8, 14));
		xmlDto.setAcqSeiNumber(time);
		// body处理 以下字段已经转码过了
		xmlDto.setRequestType(md.getOperate_flag());// 请求类型
		xmlDto.setInfoType(md.getMd_kind());// 信息类型 此处只有这两种
		xmlDto.setListType(md.getMd_type());// 此处只有这两种
		xmlDto.setRelatedCusNumber(""); // 关联客户号
		xmlDto.setCusNumber(md.getMd_value()); // 证件号/卡账号
		xmlDto.setCusDesc(md.getP_name());// 姓名描述
		xmlDto.setIdType(md.getCardtype());// 证件类型[核心的证件类型]
		xmlDto.setBankNumber(md.getBank_id());// 机构编号
		xmlDto.setContacts(md.getPolice());// 联系人
		xmlDto.setPhone(md.getPolice_phone()); // 联系人电话
		String checkTime = StringUtils.replaceChars(md.getPolice_check_dt(), "-: ", "").trim();
		if (checkTime.length() == 14) {
			xmlDto.setBlkListDate(checkTime.substring(0, 8));// 审核通过黑名单日期
			xmlDto.setBlkListTime(checkTime.substring(8));// 审核通过黑名单时间
		}
		xmlDto.setOpenDate("");// 开户日期
		xmlDto.setOpenTime(""); // 开户时间
		String startTime = StringUtils.replaceChars(md.getStart_dt(), "-: ", "");
		if (startTime.length() == 14) {
			xmlDto.setEffDate(startTime.substring(0, 8));// 生效日期
			xmlDto.setEffTime(startTime.substring(8));// 生效时间
		}
		String endTime = StringUtils.replaceChars(md.getEnd_dt(), "-: ", "");
		if (endTime.length() == 14) {
			xmlDto.setExpiredDate(endTime.substring(0, 8));// 失效日期
			xmlDto.setExpiredTime(endTime.substring(8));// 失效时间
		}
		xmlDto.setListMemo(md.getRemark());// 名单说明
		xmlDto.setReason(md.getMd_source());// 加入原因（一般为名单来源机构）
		xmlDto.setListDetail(md.getCase_type());// 其它信息（一般为名单细类）
		xmlDto.setMemo(md.getOpen_card_dt()); // 备注 存放的是【名单类型冗余项】

		String xml = CommonUtils.marshallContext(xmlDto, "binding_cgb_suspicious", true);

		return xml;
	}

	/**
	 * 更新发送状态［增量］
	 * 
	 * @param keys
	 * @param send_time
	 */
	public void updateCredit_send_flag(String send_flag, String send_time) {
		Br20_md_info mdkey = new Br20_md_info();
		mdkey.setCredit_send_time(send_time);
		mdkey.setCredit_send_flag(send_flag);// 0:未发送 1:已发送 9:中间状态［待发送］
		br20_md_info_cgbMapper.updateCredit_send_flagBykeys(mdkey);
	}

}

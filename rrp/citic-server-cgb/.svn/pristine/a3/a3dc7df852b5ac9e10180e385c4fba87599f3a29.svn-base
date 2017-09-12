package com.citic.server.gdjg.mapper;

import java.util.List;

import com.citic.server.gdjg.domain.Br57_kzqq;
import com.citic.server.gdjg.domain.Br57_kzqq_back;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_mx_policeman;
import com.citic.server.gdjg.domain.Br57_receipt;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.service.domain.MC21_task_fact;

public interface MM57_kzqqMapper {
	/** 删除Br57_kzqq查询请求单表 */
	public void delBr57_kzqq(Br57_kzqq br57_kzqq);
	
	/** 删除Br57_kzqq_mx查询请求单表 */
	public void delBr57_kzqq_mx(Br57_kzqq br57_kzqq);
	
	/** 删除Br50_kzqq_mx 查询请求主体信息 */
	public void delBr57_kzqq_mx_policeman(Br57_kzqq br57_kzqq);
	
	/** 删除控制请求反馈主体表 */
	public void delBr57_kzqq_back(Br57_kzqq br57_kzqq);
	
	/** 删除控制请求输入项主体表 */
	public void delBr57_kzqq_input(Br57_kzqq br57_kzqq);
	
	/** 插入Br57_KZQQ查询请求主表 */
	public void insertBr57_kzqq(Br57_kzqq br57_kzqq);
	
	/** 插入查询请求办案人主体表 */
	public void insertBr57_kzqq_mx_policeman(Br57_kzqq_mx_policeman mxpolice);
	
	/** 请求单主体信息 */
	public void insertBr57_kzqq_mx(Br57_kzqq_mx qqmx);
	
	/** 插入查询请求反馈主体表 */
	public void insertBr57_kzqq_back(Br57_kzqq_back br57_kzqq_back);
	
	/** 插入控制请求输入项主体表 */
	public void insertBr57_kzqq_input(Br57_kzqq_input br57_kzqq_input);
	
	
	/** 删除查询信息 */
	public void delBr57_kzqq_back_freezeresult(MC21_task_fact mc21_task_fact);
    public void delBr57_kzqq_back_unfreezeresult(MC21_task_fact mc21_task_fact);
    public void delBr57_kzqq_back_stoppayment(MC21_task_fact mc21_task_fact);
	
	
	
	/** 按条件 查询请求主体条件表 */
	public Br57_kzqq_mx selectBr57_kzqq_mxByVo(Br57_kzqq br57_kzqq);
	public List<Br57_kzqq_mx_policeman> selectBr57_kzqq_mx_policemanByVo(Br57_kzqq br57_kzqq);
	public Br57_kzqq_back selectBr57_kzqq_backByVo(Br57_kzqq_input br57_kzqq_input);
	public List<Br57_kzqq_input> selectBr57_kzqq_inputByVo(Br57_kzqq_mx br57_kzqq_mx);

	/** 批量插入反馈信息：基本信息，辅助信息 */
	public void batchInsertBr57_kzqq_back_FreezeResult(List<Gdjg_Request_AccCx> accList);
	public void batchInsertBr57_kzqq_back_UnfreezeResult(List<Gdjg_Request_AccCx> accList);
	public void batchInsertBr57_kzqq_back_StopPaymentResult(List<Gdjg_Request_AccCx> accList);
	
	/** 更新Br57_kzqq_back部分状态 */
	public void updateBr57_kzqq_back(Br57_kzqq_back br57_kzqq_back);
    /** 获取Br57_kzqq信息 */
	public List<Br57_kzqq> getBr57_kzqqList(String bdhm);
	/** 获取Br57_kzqq_mxx信息 */
	public List<Br57_kzqq_mx> getBr57_kzqq_mxList(Br57_kzqq br57_kzqq);
	
	
	/** 获取冻结结果账户信息 */
	public Gdjg_Request_AccCx getBr57_kzqq_back_FreezeAcc(Br57_kzqq_input kzqq_input);
	/** 获取解冻结果账户信息 */
	public Gdjg_Request_AccCx getBr57_kzqq_back_UnfreezeAcc(Br57_kzqq_input kzqq_input);
	/** 获取紧急止付结果账户信息 */
	public Gdjg_Request_AccCx getBr57_kzqq_back_StoppaymentAcc(Br57_kzqq_input kzqq_input);
	
	/** 获取Br57_kzqq_back信息 */
	public Br57_kzqq_back getBr57_kzqq_back(Br57_kzqq_mx mx);
	/** 插入回执信息 */
	public void insertBr57_receipt(Br57_receipt receipt);
	/** 更新Br57_kzqq信息等辅助处理 */
	public void updateBr57_kzqq(Br57_kzqq br57_kzqq);
    public int getBr57_kzqq_backCount(String docno);
    /** 将冻结编号（Artery）插入Br57_kzqq_input */
    public String getFroNumberByFreeze(Br57_kzqq_input br57_kzqq_input);
	public void updateBr57_kzqq_inputByFreeze(Gdjg_Request_AccCx account);
   
    public String getFroNumberByUnfreeze(Br57_kzqq_input br57_kzqq_input);
    public void updateBr57_kzqq_inputByUnfreeze(Gdjg_Request_AccCx account);

	public String getFroNumberByJjzf(Br57_kzqq_input br57_kzqq_input);
    public void updateBr57_kzqq_inputByJjzf(Gdjg_Request_AccCx account);

	
	/** 插入冻结解冻回执接口文书信息 */
	public void updateBr57_kzqq_back_freezeResult(Gdjg_Request_AccCx acc);
    public void updateBr57_kzqq_back_unfreezeResult(Gdjg_Request_AccCx acc);

    /** 获取冻结结果账户信息 */
	public List<Gdjg_Request_AccCx> getBr57_kzqq_back_FreezeAccByMx(Br57_kzqq_mx mx);
	/** 获取解冻结果账户信息 */
	public List<Gdjg_Request_AccCx> getBr57_kzqq_back_UnFreezeAccByMx(Br57_kzqq_mx mx);

	
	/** 冻结/续冻任务查重 */
	public int selectNum_FreezeTask(Br57_kzqq_input br57_kzqq_input);
	/** 解冻任务查重 */
	public int selectNum_UnFreezeTask(Br57_kzqq_input br57_kzqq_input);

	/** 查询原重复任务的状态*/
	public List<String> selectStatus_PreFreezeTask(Br57_kzqq_input br57_kzqq_input);
	public List<String> selectStatus_PreUnfreezeTask(Br57_kzqq_input br57_kzqq_input);

	/** 查询是否有与原冻结流水号匹配的位于Br57_kzqq_back成功项*/
	public String getSuccessBackDocnoByFreeze(Br57_kzqq_input br57_kzqq_input);
	public String getSuccessBackDocnoByUnfreeze(Br57_kzqq_input br57_kzqq_input);
	
}

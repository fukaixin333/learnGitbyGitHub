/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.net.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;

import com.citic.server.dx.domain.Br24_code_change;
import com.citic.server.service.domain.Mp02_organ;

/**
 * @author gaojx
 * 码表转换映射
 */
public interface Br24_code_changeMapper {
	
	@Select("select t1.colcode,t1.qryds,t.targetval,t.sourceval,t1.destab from br24_txcode t1 left join br24_code_change t  on ( t.qryds= t1.qryds and t.colcode = t1.colcode)")
	ArrayList<Br24_code_change> getBr24_code_changeList();
	
	@Select("select organkey,organname  from mp02_organ")
	ArrayList<Mp02_organ> getMp02_organList();
}

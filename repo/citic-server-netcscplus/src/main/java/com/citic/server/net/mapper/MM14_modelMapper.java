package com.citic.server.net.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;

import com.citic.server.service.domain.BB13_sys_para;
import com.citic.server.service.domain.MM14_link;
import com.citic.server.service.domain.MM14_link_event;
import com.citic.server.service.domain.MM14_model;

public interface MM14_modelMapper {
	
	ArrayList<MM14_model> getMM14_modelList();
	
	MM14_model getMM14_model(String modelkey);
	
	ArrayList<MM14_link> getMM14_linkList(String modelkey);
	ArrayList<MM14_link_event> getMM14_link_eventList(String modelkey);
	ArrayList<BB13_sys_para> getsysPara();	
 
}

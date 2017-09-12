package com.citic.server.net.mapper;

import java.util.ArrayList;

import com.citic.server.service.domain.MM11_indic;
import com.citic.server.service.domain.MM12_event;

public interface MM11_indicMapper {
	
	ArrayList<MM11_indic> getMM11_indicList(MM11_indic mm11_indic);
	
	ArrayList<MM11_indic> getMM11_indicListForLab(MM11_indic mm11_indic);

	MM11_indic getMM11_indicDisp(String indickey);
	
}


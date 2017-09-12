package com.citic.server.mapper;

import java.util.ArrayList;

import com.citic.server.domain.MC00_triger_fact;

public interface MC00_triger_factMapper {
	
	
	ArrayList<MC00_triger_fact> getMC00_triger_factList(String datatime);
	
	void insertMC00_triger_fact(MC00_triger_fact mC00_triger_fact);
	
	void insertMC00_triger_fact_his(String datatime);

	void deleteMC00_triger_factByDatatime(String datatime);
	void deleteMC00_triger_fact_hisByDatatime(String datatime);
	
}


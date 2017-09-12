package com.citic.server;


/**
 * @author hubq
 *
 */
public class ApplicationCFG {
	
	private String server_name_mainserver = "mainserver";
	private String server_name_taskserver = "taskserver";
	private String server_name_labserver = "labserver";
	private String server_name_rtserver = "rtserver";
	

	/**
	 * @return the server_name_rtserver
	 */
	public String getServer_name_rtserver() {
		return server_name_rtserver;
	}

	/**
	 * @param server_name_rtserver the server_name_rtserver to set
	 */
	public void setServer_name_rtserver(String server_name_rtserver) {
		this.server_name_rtserver = server_name_rtserver;
	}

	private int server_scan_timespace = 60;
	
	private int server_scan_ds_datatime_length = 3;
	
	private int datafile_backup_days = 3;
	
	private String server_cachename = "serverCache";

	private String taskid_ds_property = "TK_DTS101";
	
	private String taskid_getdata_tolocal = "TK_ETL101";
	
	private String jdbctemplate_ods = "jdbcTemplate";
	
	private String jdbctemplate_manager = "jdbcTemplate";
	
	private String jdbctemplate_business = "jdbcTemplate";
	
	private String jdbctemplate_history = "jdbcTemplate";
	
	private String save_cal_sqllog = "1";
	
	private String save_cal_bigsql_split = "2000";
	
	private String global_temp_table_created = "db";
	
	private String global_temp_table_prestr = "session";
	
	private String lab_calmodel_maxdate_length = "30";

	private String back_dataload_flag_username = "";
	private String back_dataload_flag_password = "";
	private String back_dataload_flag_dbname = "";
	private String back_dataload_database_username = "";
	private String back_dataload_database_password = "";
	private String back_dataload_database_dbname = "";
	
	public int getServer_scan_ds_datatime_length() {
		return server_scan_ds_datatime_length;
	}

	public void setServer_scan_ds_datatime_length(int server_scan_ds_datatime_length) {
		this.server_scan_ds_datatime_length = server_scan_ds_datatime_length;
	}

	public String getServer_name_mainserver() {
		return server_name_mainserver;
	}

	public void setServer_name_mainserver(String server_name_mainserver) {
		this.server_name_mainserver = server_name_mainserver;
	}

	public String getServer_name_taskserver() {
		return server_name_taskserver;
	}

	public void setServer_name_taskserver(String server_name_taskserver) {
		this.server_name_taskserver = server_name_taskserver;
	}

	public String getServer_name_labserver() {
		return server_name_labserver;
	}

	public void setServer_name_labserver(String server_name_labserver) {
		this.server_name_labserver = server_name_labserver;
	}

	public int getServer_scan_timespace() {
		return server_scan_timespace;
	}

	public void setServer_scan_timespace(int server_scan_timespace) {
		this.server_scan_timespace = server_scan_timespace;
	}

	public int getDatafile_backup_days() {
		return datafile_backup_days;
	}

	public void setDatafile_backup_days(int datafile_backup_days) {
		this.datafile_backup_days = datafile_backup_days;
	}

	public String getServer_cachename() {
		return server_cachename;
	}

	public void setServer_cachename(String server_cachename) {
		this.server_cachename = server_cachename;
	}

	public String getTaskid_ds_property() {
		return taskid_ds_property;
	}

	public void setTaskid_ds_property(String taskid_ds_property) {
		this.taskid_ds_property = taskid_ds_property;
	}

	public String getTaskid_getdata_tolocal() {
		return taskid_getdata_tolocal;
	}

	public void setTaskid_getdata_tolocal(String taskid_getdata_tolocal) {
		this.taskid_getdata_tolocal = taskid_getdata_tolocal;
	}

	public String getJdbctemplate_ods() {
		return jdbctemplate_ods;
	}

	public void setJdbctemplate_ods(String jdbctemplate_ods) {
		this.jdbctemplate_ods = jdbctemplate_ods;
	}

	public String getJdbctemplate_manager() {
		return jdbctemplate_manager;
	}

	public void setJdbctemplate_manager(String jdbctemplate_manager) {
		this.jdbctemplate_manager = jdbctemplate_manager;
	}

	public String getJdbctemplate_business() {
		return jdbctemplate_business;
	}

	public void setJdbctemplate_business(String jdbctemplate_business) {
		this.jdbctemplate_business = jdbctemplate_business;
	}

	public String getJdbctemplate_history() {
		return jdbctemplate_history;
	}

	public void setJdbctemplate_history(String jdbctemplate_history) {
		this.jdbctemplate_history = jdbctemplate_history;
	}

	public String getSave_cal_sqllog() {
		return save_cal_sqllog;
	}

	public void setSave_cal_sqllog(String save_cal_sqllog) {
		this.save_cal_sqllog = save_cal_sqllog;
	}

	public String getSave_cal_bigsql_split() {
		return save_cal_bigsql_split;
	}

	public void setSave_cal_bigsql_split(String save_cal_bigsql_split) {
		this.save_cal_bigsql_split = save_cal_bigsql_split;
	}

	public String getGlobal_temp_table_created() {
		return global_temp_table_created;
	}

	public void setGlobal_temp_table_created(String global_temp_table_created) {
		this.global_temp_table_created = global_temp_table_created;
	}

	public String getGlobal_temp_table_prestr() {
		return global_temp_table_prestr;
	}

	public void setGlobal_temp_table_prestr(String global_temp_table_prestr) {
		this.global_temp_table_prestr = global_temp_table_prestr;
	}
	
	public String getLab_calmodel_maxdate_length() {
		return lab_calmodel_maxdate_length;
	}

	public void setLab_calmodel_maxdate_length(String lab_calmodel_maxdate_length) {
		this.lab_calmodel_maxdate_length = lab_calmodel_maxdate_length;
	}

	public String getBack_dataload_flag_username() {
		return back_dataload_flag_username;
	}

	public void setBack_dataload_flag_username(String back_dataload_flag_username) {
		this.back_dataload_flag_username = back_dataload_flag_username;
	}

	public String getBack_dataload_flag_password() {
		return back_dataload_flag_password;
	}

	public void setBack_dataload_flag_password(String back_dataload_flag_password) {
		this.back_dataload_flag_password = back_dataload_flag_password;
	}

	public String getBack_dataload_database_username() {
		return back_dataload_database_username;
	}

	public void setBack_dataload_database_username(
			String back_dataload_database_username) {
		this.back_dataload_database_username = back_dataload_database_username;
	}

	public String getBack_dataload_database_password() {
		return back_dataload_database_password;
	}

	public void setBack_dataload_database_password(
			String back_dataload_database_password) {
		this.back_dataload_database_password = back_dataload_database_password;
	}

	public String getBack_dataload_flag_dbname() {
		return back_dataload_flag_dbname;
	}

	public void setBack_dataload_flag_dbname(String back_dataload_flag_dbname) {
		this.back_dataload_flag_dbname = back_dataload_flag_dbname;
	}

	public String getBack_dataload_database_dbname() {
		return back_dataload_database_dbname;
	}

	public void setBack_dataload_database_dbname(
			String back_dataload_database_dbname) {
		this.back_dataload_database_dbname = back_dataload_database_dbname;
	}
	
	
	
}

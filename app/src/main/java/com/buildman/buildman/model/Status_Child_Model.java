package com.buildman.buildman.model;

public class Status_Child_Model {
	private int site_id_Child;
	private int prj_est_workmaster_id_Child;
	private String prj_est_work_location_name_Child = null;
	private String prj_work_est_start_date_Child = null;
	private String prj_work_est_end_date_Child = null;
	private String prj_est_work_tot_qty_Child = null;
	private String prj_est_work_tot_qty_units_Child = null;
	private String prj_est_work_com_qty_Child = null;
	private String balance_work_qty_Child = null;
	
	
	public void setSite_ID_Child(int site_id) {
	    this.site_id_Child = site_id;
	}
	public int getSite_ID_Child() {
	    return this.site_id_Child;
	}
	public void setPRJ_EST_WorkMaster_ID_Child(int prj_est_workmaster_id_Child) {
	    this.prj_est_workmaster_id_Child = prj_est_workmaster_id_Child;
	}
	public int getPRJ_EST_WorkMaster_ID_Child() {
	    return this.prj_est_workmaster_id_Child;
	}
	
	public void setPRJ_EST_WorkLocation_Name_Child(String prj_est_worklocation_name_Child) {
	    this.prj_est_work_location_name_Child = prj_est_worklocation_name_Child;
	}
	public String getPRJ_EST_WorkLocation_Name() {
	    return this.prj_est_work_location_name_Child;
	}
	
	public void setPRJ_Work_EST_Start_Date_Child(String prj_work_est_start_date_Child) {
	    this.prj_work_est_start_date_Child = prj_work_est_start_date_Child;
	}
	public String getPRJ_WorK_EST_Start_Date_Child() {
	    return this.prj_work_est_start_date_Child;
	}
	
	public void setPRJ_Work_EST_End_Date_Child(String prj_work_est_end_date_Child) {
	    this.prj_work_est_end_date_Child = prj_work_est_end_date_Child;
	}
	public String getPRJ_Work_EST_End_Date_Child() {
	    return this.prj_work_est_end_date_Child;
	}
	
	public void setPRJ_EST_Work_TOT_QTY_Child(String prj_est_work_tot_qty_Child) {
	    this.prj_est_work_tot_qty_Child = prj_est_work_tot_qty_Child;
	}
	public String getPRJ_EST_Work_TOT_QTY_Child() {
	    return this.prj_est_work_tot_qty_Child;
	}
	
	public void setPRJ_EST_Work_TOT_QTY_Units_Child(String prj_est_work_tot_qty_units_Child) {
	    this.prj_est_work_tot_qty_units_Child = prj_est_work_tot_qty_units_Child;
	}
	public String getPRJ_EST_Work_TOT_QTY_Units_Child() {
	    return this.prj_est_work_tot_qty_units_Child;
	}
	
	public void setPRJ_EST_Work_COM_QTY_Child(String prj_est_work_com_qty_Child) {
	    this.prj_est_work_com_qty_Child = prj_est_work_com_qty_Child;
	}
	public String getPRJ_EST_Work_COM_QTY_Child() {
	    return this.prj_est_work_com_qty_Child;
	}
	
	public void setBalance_Work_QTY_Child(String mBalance_work_qty_Child) {
	    this.balance_work_qty_Child = mBalance_work_qty_Child;
	}
	public String getBalance_Work_QTY_Child() {
	    return this.balance_work_qty_Child;
	}
	
}

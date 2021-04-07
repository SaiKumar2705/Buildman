package com.buildman.buildman.model;

public class Dashboard_Model {
	private int dashboard_id;
	int site_id;
	String sitename = null;
	String site_stockvalue = null;
	String testingcolumn = null;
	String date = null;
	
	public Dashboard_Model(int mSite_id_result, String stock_value,
			String site_Name, String date) {
		// TODO Auto-generated constructor stub
		this.site_id=mSite_id_result;
		this.site_stockvalue=stock_value;
		this.sitename=site_Name;
		this.date=date;
	}
	public Dashboard_Model() {
		// TODO Auto-generated constructor stub
	}
	public void setDashboard_ID(int dashboard_id) {
	    this.dashboard_id = dashboard_id;
	}
	public int getDashboard_ID() {
	    return this.dashboard_id;
	}
	public void setSite_ID(int site_id) {
	    this.site_id = site_id;
	}
	public int getSite_ID() {
	    return this.site_id;
	}

	public void setSiteName(String sitename) {
	    this.sitename = sitename;
	}

	public String getSiteName() {
	    return this.sitename;
	}

	public void setSiteStockValue(String site_stockvalue) {
	    this.site_stockvalue = site_stockvalue;
	}

	public String getSiteStockValue() {
	    return this.site_stockvalue;
	}
	
	public void setDate(String date) {
	    this.date = date;
	}

	public String getDate() {
	    return this.date;
	}
}

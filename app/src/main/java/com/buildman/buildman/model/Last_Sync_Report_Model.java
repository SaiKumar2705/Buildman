package com.buildman.buildman.model;

import java.util.Comparator;


public class Last_Sync_Report_Model {
	// Last_Sync_Report variables
	private int lastsynreport_id_LastSynReport;
	private int site_id_LastSynReport;
	private String site_name_LastSynReport = null;
	private int module_id_LastSynReport;
	private String module_name_LastSynReport = null;
	private int numb_of_days_LastSynReport ;
	private int party_id_LastSynReport;
	private String mobile_Numb_LastSynReport = null;
	private int identifier_LastSynReport;


	public Last_Sync_Report_Model(int mSite_Id_result, String site_name, String mobile_numb, int mIdentifier_result,
			int mModule_id, String mModule_name, int mNumb_of_days	) {
		// TODO Auto-generated constructor stub
		this.site_id_LastSynReport=mSite_Id_result;
		this.site_name_LastSynReport=site_name;
		this.mobile_Numb_LastSynReport=mobile_numb;
		this.identifier_LastSynReport=mIdentifier_result;	
		this.module_id_LastSynReport=mModule_id;
		this.module_name_LastSynReport=mModule_name;
		this.numb_of_days_LastSynReport=mNumb_of_days;
	}
	public Last_Sync_Report_Model(String site_name,int mModule_Id_result, String module_name,
			int number_of_days, int mParty_Id_result) {
		// TODO Auto-generated constructor stub
		this.site_name_LastSynReport=site_name;
		this.module_id_LastSynReport=mModule_Id_result;
		this.module_name_LastSynReport=module_name;
		this.numb_of_days_LastSynReport=number_of_days;
		this.party_id_LastSynReport=mParty_Id_result;
			
	}
	
	public Last_Sync_Report_Model(int mModule_Id_DropDown, int mNo_of_days) {
		// TODO Auto-generated constructor stub
		this.module_id_LastSynReport=mModule_Id_DropDown;
		this.numb_of_days_LastSynReport=mNo_of_days;
	}
	public Last_Sync_Report_Model() {
		// TODO Auto-generated constructor stub
	}
	
	public Last_Sync_Report_Model(String site_name) {
		// TODO Auto-generated constructor stub
		this.site_name_LastSynReport=site_name;
		
		
	}
	public void setLastSynReport_ID_LastSynReport(int mLastSynReportID_LastSynReport) {
	    this.lastsynreport_id_LastSynReport = mLastSynReportID_LastSynReport;
	}
	public int getLastSynReport_ID_LastSynReport() {
	    return this.lastsynreport_id_LastSynReport;
	}
	public void setSite_ID_LastSynReport(int mSite_id_LastSynReport) {
	    this.site_id_LastSynReport = mSite_id_LastSynReport;
	}
	public int getSite_ID_LastSynReport() {
	    return this.site_id_LastSynReport;
	}
	
	public void setSite_Name_LastSynReport(String mSite_name_LastSynReport) {
	    this.site_name_LastSynReport = mSite_name_LastSynReport;
	}
	public String getSite_Name_LastSynReport() {
	    return this.site_name_LastSynReport;
	}
	
	public void setModule_ID_LastSynReport(int mModule_id_LastSynReport) {
	    this.module_id_LastSynReport = mModule_id_LastSynReport;
	}
	public int getModule_ID_LastSynReport() {
	    return this.module_id_LastSynReport;
	}
	
	public void setModule_Name_LastSynReport(String mModule_name_LastSynReport) {
	    this.module_name_LastSynReport = mModule_name_LastSynReport;
	}
	public String getModule_Name_LastSynReport() {
	    return this.module_name_LastSynReport;
	}
	
	public void setNumber_Of_Days_LastSynReport(int mNumber_of_days_LastSynReport) {
	    this.numb_of_days_LastSynReport = mNumber_of_days_LastSynReport;
	}
	public int getNumber_Of_Days_LastSynReport() {
	    return this.numb_of_days_LastSynReport;
	}
	
	public void setParty_ID_LastSynReport(int mParty_id_LastSynReport) {
	    this.party_id_LastSynReport = mParty_id_LastSynReport;
	}
	public int getParty_ID_LastSynReport() {
	    return this.party_id_LastSynReport;
	}
	
	public void setMobile_Numb_LastSynReport(String mMobile_Numb_LastSynReport) {
	    this.mobile_Numb_LastSynReport = mMobile_Numb_LastSynReport;
	}
	public String getMobile_Numb_LastSynReport() {
	    return this.mobile_Numb_LastSynReport;
	}
	
	public void setIdentifier_LastSynReport(int mIdentifier_LastSynReport) {
	    this.identifier_LastSynReport = mIdentifier_LastSynReport;
	}
	public int getIdentifier_LastSynReport() {
	    return this.identifier_LastSynReport;
	}
	
	/*Comparator for sorting the list by Student Name*/
    public static Comparator<Last_Sync_Report_Model> SiteDetailsList_SiteNameComparator = new Comparator<Last_Sync_Report_Model>() {

	public int compare(Last_Sync_Report_Model s1, Last_Sync_Report_Model s2) {
	   String SiteName1 = s1.getSite_Name_LastSynReport().toUpperCase();
	   String SiteName2 = s2.getSite_Name_LastSynReport().toUpperCase();

	   //ascending order
	   return SiteName1.compareTo(SiteName2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};
    
    /*Comparator for sorting the list by Student Name*/
    public static Comparator<Last_Sync_Report_Model> SynDetailsList_SiteNameComparator = new Comparator<Last_Sync_Report_Model>() {

	public int compare(Last_Sync_Report_Model s1, Last_Sync_Report_Model s2) {
	   String SiteName1 = s1.getSite_Name_LastSynReport().toUpperCase();
	   String SiteName2 = s2.getSite_Name_LastSynReport().toUpperCase();

	   //ascending order
	   return SiteName1.compareTo(SiteName2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};
	
	
    
    /**
	 * Two users are equal if their firstName, lastName and email address is same.
	 */
	/*@Override
	public boolean equals(Object obj) {
		return (this.site_name_LastSynReport.equals(((Last_Sync_Report_Model) obj).site_name_LastSynReport)
				&& this.module_name_LastSynReport.equals(((Last_Sync_Report_Model) obj).module_name_LastSynReport) );
	}*/
    
    @Override
	public boolean equals(Object obj) {
		return (this.site_name_LastSynReport.equals(((Last_Sync_Report_Model) obj).site_name_LastSynReport)
				 );
	}
}

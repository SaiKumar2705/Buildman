package com.buildman.buildman.model;

import java.util.ArrayList;

public class LastSyncReport_Parent_Model {
	// Last_Sync_Report variables
		private int site_id_Parent_LastSynReport;
		private String site_name_Parent_LastSynReport = null;	
		private String mobile_number_Parent_LastSynReport = null;	
		private int identifier_Parent_LastSynReport;
		private ArrayList<LastSyncReport_Child_Model> mArrayChildren_List;
		
		public LastSyncReport_Parent_Model(int mSite_Id_result, String site_name) {
			// TODO Auto-generated constructor stub
			this.site_id_Parent_LastSynReport=mSite_Id_result;
			this.site_name_Parent_LastSynReport=site_name;
			
		}
		
		public LastSyncReport_Parent_Model(String site_names,String mobile_numb,int identifier_value,
				ArrayList<LastSyncReport_Child_Model> arrayChildren) {
			// TODO Auto-generated constructor stub
			this.site_name_Parent_LastSynReport=site_names;
			this.mobile_number_Parent_LastSynReport=mobile_numb;
			this.identifier_Parent_LastSynReport=identifier_value;
			this.mArrayChildren_List=arrayChildren;
		}

		public void setSite_ID_Parent_LastSynReport(int mSite_id_Parent_LastSynReport) {
		    this.site_id_Parent_LastSynReport = mSite_id_Parent_LastSynReport;
		}
		public int getSite_ID_Parent_LastSynReport() {
		    return this.site_id_Parent_LastSynReport;
		}
		
		public void setSite_Name_Parent_LastSynReport(String mSite_name_Parent_LastSynReport) {
		    this.site_name_Parent_LastSynReport = mSite_name_Parent_LastSynReport;
		}
		public String getSite_Name_Parent_LastSynReport() {
		    return this.site_name_Parent_LastSynReport;
		}
		
		public void setMobileNumber_Parent_LastSynReport(String mMobileNumber_Parent_LastSynReport) {
		    this.mobile_number_Parent_LastSynReport = mMobileNumber_Parent_LastSynReport;
		}
		public String getMobileNumber_Parent_LastSynReport() {
		    return this.mobile_number_Parent_LastSynReport;
		}
		
		public void setIdentifier_Parent_LastSynReport(int mIdentifier_Parent_LastSynReport) {
		    this.identifier_Parent_LastSynReport = mIdentifier_Parent_LastSynReport;
		}
		public int getIdentifier_Parent_LastSynReport() {
		    return this.identifier_Parent_LastSynReport;
		}
		
		 public ArrayList<LastSyncReport_Child_Model> getArrayChildren() {
		        return mArrayChildren_List;
		    } 
		    public void setArrayChildren(ArrayList<LastSyncReport_Child_Model> arrayChildren) {
		    	mArrayChildren_List = arrayChildren;
		    }
		
		
	}

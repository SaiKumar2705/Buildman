package com.buildman.buildman.model;

import java.util.ArrayList;

import android.support.annotation.MainThread;

public class Status_Model {
	private int project_status_id;
	private int project_id;
	private int site_id;
	private int prj_est_workmaster_id;
	private String prj_est_work_location_name = null;
	private String percentage_completion = null;
	private String prj_est_work_tot_qty = null;
	private String prj_est_work_tot_qty_units = null;
	private String prj_est_work_com_qty = null;
	private String prj_work_est_start_date = null;
	private String prj_work_est_end_date = null;
	private String start_date = null;
	private String end_date = null;
	private String syn_status = null;
	private String created_date = null;
	private String transaction_date = null;
	private String display_flag = null;
	private String remarks = null;
	private int work_task_type_id;
	private int main_task_Id;
	private String main_task_name = null;
	private String link_toBill = null;
	private String sum_Entered_qty = null;
	 private ArrayList<Status_Child_Model> mArrayChildren;
	
//	Status_Issues
	private int project_issues_id_ISSUES;
	private int project_id_ISSUES;
	private int site_id_ISSUES;

	public int getProtal_ISSUES_id() {
		return protal_ISSUES_id;
	}

	public void setProtal_ISSUES_id(int protal_ISSUES_id) {
		this.protal_ISSUES_id = protal_ISSUES_id;
	}

	private int protal_ISSUES_id;
	private int prj_est_workmaster_id_ISSUES;
	private String prj_est_work_location_name_ISSUES = null;	
	private String issues_title_ISSUES = null;
	private String issues_details_ISSUES = null;
	private String syn_flag_ISSUES = null;
	private String issues_creation_datetime_ISSUES = null;
	private String display_flag_ISSUES = null;

	public String getDisplay_flag_ISSUES() {
		return display_flag_ISSUES;
	}

	public void setDisplay_flag_ISSUES(String display_flag_ISSUES) {
		this.display_flag_ISSUES = display_flag_ISSUES;
	}



	//	Status_Issues_Images
	private int PROJECT_ISSUES_IMAGES_ID;
	private String issues_id;
	private String PROJECTID_ISSUES_IMAGES_ID;
	private String SITEID_ISSUES_IMAGES;
	private String TASKID_ISSUES_IMAGES;
	private String DISPLAY_FLAG_PRJ_ISSUES_IMAGES = null;
	private String PATH_PRJ_ISSUES_IMAGES;
	private String SYN_FLAG_PRJ_ISSUES_IMAGES = null;
	private String IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES = null;

	public String getPROJECTID_ISSUES_IMAGES_ID() {
		return PROJECTID_ISSUES_IMAGES_ID;
	}

	public void setPROJECTID_ISSUES_IMAGES_ID(String PROJECTID_ISSUES_IMAGES_ID) {
		this.PROJECTID_ISSUES_IMAGES_ID = PROJECTID_ISSUES_IMAGES_ID;
	}

	public String getSITEID_ISSUES_IMAGES() {
		return SITEID_ISSUES_IMAGES;
	}

	public void setSITEID_ISSUES_IMAGES(String SITEID_ISSUES_IMAGES) {
		this.SITEID_ISSUES_IMAGES = SITEID_ISSUES_IMAGES;
	}

	public String getTASKID_ISSUES_IMAGES() {
		return TASKID_ISSUES_IMAGES;
	}

	public void setTASKID_ISSUES_IMAGES(String TASKID_ISSUES_IMAGES) {
		this.TASKID_ISSUES_IMAGES = TASKID_ISSUES_IMAGES;
	}

	public String getDISPLAY_FLAG_PRJ_ISSUES_IMAGES() {
		return DISPLAY_FLAG_PRJ_ISSUES_IMAGES;
	}

	public void setDISPLAY_FLAG_PRJ_ISSUES_IMAGES(String DISPLAY_FLAG_PRJ_ISSUES_IMAGES) {
		this.DISPLAY_FLAG_PRJ_ISSUES_IMAGES = DISPLAY_FLAG_PRJ_ISSUES_IMAGES;
	}




	public int getPROJECT_ISSUES_IMAGES_ID() {
		return PROJECT_ISSUES_IMAGES_ID;
	}

	public void setPROJECT_ISSUES_IMAGES_ID(int PROJECT_ISSUES_IMAGES_ID) {
		this.PROJECT_ISSUES_IMAGES_ID = PROJECT_ISSUES_IMAGES_ID;
	}

	public String getIssues_id() {
		return issues_id;
	}

	public void setIssues_id(String issues_id) {
		this.issues_id = issues_id;
	}

	public String getPATH_PRJ_ISSUES_IMAGES() {
		return PATH_PRJ_ISSUES_IMAGES;
	}

	public void setPATH_PRJ_ISSUES_IMAGES(String PATH_PRJ_ISSUES_IMAGES) {
		this.PATH_PRJ_ISSUES_IMAGES = PATH_PRJ_ISSUES_IMAGES;
	}

	public String getSYN_FLAG_PRJ_ISSUES_IMAGES() {
		return SYN_FLAG_PRJ_ISSUES_IMAGES;
	}

	public void setSYN_FLAG_PRJ_ISSUES_IMAGES(String SYN_FLAG_PRJ_ISSUES_IMAGES) {
		this.SYN_FLAG_PRJ_ISSUES_IMAGES = SYN_FLAG_PRJ_ISSUES_IMAGES;
	}

	public String getIMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES() {
		return IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES;
	}

	public void setIMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES(String IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES) {
		this.IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES = IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES;
	}

//	Status_Images
	private int project_images_id_IMAGES;
	private int project_id_IMAGES;
	private int site_id_IMAGES;
	private int prj_est_workmaster_id_IMAGES;
	private String prj_est_work_location_name_IMAGES = null;	
	private String path_IMAGES; 	
	private String syn_flag_IMAGES = null;
	private String images_creation_datetime_IMAGES = null;
	
	public Status_Model(int project_id, int site_id,
			int prj_EST_WorkMaster_id,
			String prj_EST_Work_Location_Name,
			String percentage_Completion,
			String prj_EST_Work_TOT_Qty,
			String prj_EST_Work_TOT_Qty_Units,
			String prj_EST_Work_COM_Qty,
			String prj_Work_EST_Start_Date,
			String prj_Work_EST_End_Date,
			String start_date, 
			String end_date, 
			String syn_status,
			String created_date, 
			String transaction_date, 
			String display_flag,
			String remarks,
			int work_taskType_id,
			int main_task_id,
			String main_Task_Name,
			String link_toBill,
			String sum_entered_Qty) {
		// TODO Auto-generated constructor stub
		this.project_id=project_id;
		this.site_id=site_id;
		this.prj_est_workmaster_id=prj_EST_WorkMaster_id;
		this.prj_est_work_location_name=prj_EST_Work_Location_Name;
		this.percentage_completion=percentage_Completion;
		this.prj_est_work_tot_qty=prj_EST_Work_TOT_Qty;
		this.prj_est_work_tot_qty_units=prj_EST_Work_TOT_Qty_Units;
		this.prj_est_work_com_qty=prj_EST_Work_COM_Qty;
		this.prj_work_est_start_date=prj_Work_EST_Start_Date;
		this.prj_work_est_end_date=prj_Work_EST_End_Date;
		this.start_date=start_date;
		this.end_date=end_date;
		this.syn_status=syn_status;
		this.created_date=created_date;
		this.transaction_date=transaction_date;
		this.display_flag=display_flag;
		this.remarks=remarks;
		this.work_task_type_id=work_taskType_id;
		this.main_task_Id=main_task_id;
		this.main_task_name=main_Task_Name;
		this.link_toBill=link_toBill;
		this.sum_Entered_qty=sum_entered_Qty;
	}
	public Status_Model() {
		// TODO Auto-generated constructor stub
	}
//	status_status
	public Status_Model(String mMain_Task_Name, String mDate ,String mSum_Entered_qty,
			String units,String prj_work_est_end_date ,String prj_est_work_tot_qty,ArrayList<Status_Child_Model> children_list) {
		// TODO Auto-generated constructor stub
		
		this.main_task_name=mMain_Task_Name;
		this.start_date=mDate;
		this.prj_work_est_end_date=prj_work_est_end_date;
		this.prj_est_work_tot_qty=prj_est_work_tot_qty;

		this.sum_Entered_qty=mSum_Entered_qty;
		this.prj_est_work_tot_qty_units=units;
		mArrayChildren = children_list;
	}	
	public Status_Model(int selected_SiteID, int mWork_Master_id,
			String mTask_Name, String mDate, String mQty_Completed,
			String mUnits) {
		// TODO Auto-generated constructor stub
		
		this.prj_est_workmaster_id=mWork_Master_id;
		this.prj_est_work_location_name=mTask_Name;
		this.start_date=mDate;
		this.prj_est_work_com_qty=mQty_Completed;
		this.prj_est_work_tot_qty_units=mUnits;
	}
//	issues_status
	public Status_Model(int project_id_issues, int SiteID_issues,
			int work_master_id_issues, String task_name_issues, String issues_title_issues,
			String issues_details_issues, String syn_flag_issues, String issue_CreationDateTime_Now, String displayflg_issue) {
		// TODO Auto-generated constructor stub
		this.project_id_ISSUES=project_id_issues;
		this.site_id_ISSUES=SiteID_issues;
		this.prj_est_workmaster_id_ISSUES=work_master_id_issues;
		this.prj_est_work_location_name_ISSUES=task_name_issues;
		this.issues_title_ISSUES=issues_title_issues;
		this.issues_details_ISSUES=issues_details_issues;
		this.syn_flag_ISSUES=syn_flag_issues;
		this.issues_creation_datetime_ISSUES=issue_CreationDateTime_Now;
		this.display_flag_ISSUES=displayflg_issue;
	}
	public Status_Model(int project_id_issues, int SiteID_issues,
			int work_master_id_issues, String task_name_issues, String issues_title_issues,
			String issues_details_issues, String syn_flag_issues, String issue_CreationDateTime_Now,int portal_issue_id,String display_flag) {
		// TODO Auto-generated constructor stub
		this.project_id_ISSUES=project_id_issues;
		this.site_id_ISSUES=SiteID_issues;
		this.prj_est_workmaster_id_ISSUES=work_master_id_issues;
		this.prj_est_work_location_name_ISSUES=task_name_issues;
		this.issues_title_ISSUES=issues_title_issues;
		this.issues_details_ISSUES=issues_details_issues;
		this.syn_flag_ISSUES=syn_flag_issues;
		this.issues_creation_datetime_ISSUES=issue_CreationDateTime_Now;
		this.protal_ISSUES_id=portal_issue_id;
		this.display_flag_ISSUES=display_flag;
	}
	public Status_Model(int issue_id,String issue_creation_date, String issue_title,String issue_details) {
		// TODO Auto-generated constructor stub
		this.issues_creation_datetime_ISSUES=issue_creation_date;
		this.issues_title_ISSUES=issue_title;
		this.issues_details_ISSUES=issue_details;
		this.project_issues_id_ISSUES=issue_id;

	}
	public Status_Model(String issue_id, String projectid,String siteid,String taskid,String imgpath,String syn_flag_IMAGES,String issue_creation_date,String display_flag) {
		// TODO Auto-generated constructor stub
		this.issues_id=issue_id;
		this.PROJECTID_ISSUES_IMAGES_ID=projectid;
		this.SITEID_ISSUES_IMAGES=siteid;
		this.TASKID_ISSUES_IMAGES=taskid;
		this.PATH_PRJ_ISSUES_IMAGES=imgpath;
		this.SYN_FLAG_PRJ_ISSUES_IMAGES=syn_flag_IMAGES;
		this.IMAGES_CRAETION_DATE_PRJ_ISSUES_IMAGES=issue_creation_date;
		this.DISPLAY_FLAG_PRJ_ISSUES_IMAGES=display_flag;
	}
	
//	Images_status
	public Status_Model(int project_id_images, int selected_SiteID_images,
			int work_master_id_images, String task_name_images, String path_images,
			String syn_flag_images, String mCurrent_DateTime_Now) {
		// TODO Auto-generated constructor stub
		this.project_id_IMAGES=project_id_images;
		this.site_id_IMAGES=selected_SiteID_images;
		this.prj_est_workmaster_id_IMAGES=work_master_id_images;
		this.prj_est_work_location_name_IMAGES=task_name_images;
		this.path_IMAGES=path_images;		
		this.syn_flag_IMAGES=syn_flag_images;
		this.images_creation_datetime_IMAGES=mCurrent_DateTime_Now;
	}
	public void setProject_Status_ID(int project_status_id) {
	    this.project_status_id = project_status_id;
	}
	public int getProject_Status_ID() {
	    return this.project_status_id;
	}
	public void setProject_ID(int project_id) {
	    this.project_id = project_id;
	}
	public int getProject_ID() {
	    return this.project_id;
	}
	public void setSite_ID(int site_id) {
	    this.site_id = site_id;
	}
	public int getSite_ID() {
	    return this.site_id;
	}
	public void setPRJ_EST_WorkMaster_ID(int prj_est_workmaster_id) {
	    this.prj_est_workmaster_id = prj_est_workmaster_id;
	}
	public int getPRJ_EST_WorkMaster_ID() {
	    return this.prj_est_workmaster_id;
	}
	
	public void setPRJ_EST_WorkLocation_Name(String prj_est_worklocation_name) {
	    this.prj_est_work_location_name = prj_est_worklocation_name;
	}
	public String getPRJ_EST_WorkLocation_Name() {
	    return this.prj_est_work_location_name;
	}
	
	public void setPercentage_Completion(String percentage_completion) {
	    this.percentage_completion = percentage_completion;
	}
	public String getPercentage_Completion() {
	    return this.percentage_completion;
	}
	
	public void setPRJ_EST_Work_TOT_QTY(String prj_est_work_tot_qty) {
	    this.prj_est_work_tot_qty = prj_est_work_tot_qty;
	}
	public String getPRJ_EST_Work_TOT_QTY() {
	    return this.prj_est_work_tot_qty;
	}
	
	public void setPRJ_EST_Work_TOT_QTY_Units(String prj_est_work_tot_qty_units) {
	    this.prj_est_work_tot_qty_units = prj_est_work_tot_qty_units;
	}
	public String getPRJ_EST_Work_TOT_QTY_Units() {
	    return this.prj_est_work_tot_qty_units;
	}
	
	public void setPRJ_EST_Work_COM_QTY(String prj_est_work_com_qty) {
	    this.prj_est_work_com_qty = prj_est_work_com_qty;
	}
	public String getPRJ_EST_Work_COM_QTY() {
	    return this.prj_est_work_com_qty;
	}
	
	public void setPRJ_Work_EST_Start_Date(String prj_work_est_start_date) {
	    this.prj_work_est_start_date = prj_work_est_start_date;
	}
	public String getPRJ_WorK_EST_Start_Date() {
	    return this.prj_work_est_start_date;
	}
	
	public void setPRJ_Work_EST_End_Date(String prj_work_est_end_date) {
	    this.prj_work_est_end_date = prj_work_est_end_date;
	}
	public String getPRJ_Work_EST_End_Date() {
	    return this.prj_work_est_end_date;
	}
	
	public void setStart_Date(String start_date) {
	    this.start_date = start_date;
	}
	public String getStart_Date() {
	    return this.start_date;
	}
	public void setEnd_Date(String end_date) {
	    this.end_date = end_date;
	}
	public String getEnd_Date() {
	    return this.end_date;
	}
	
	public void setSyn_Status(String syn_status) {
	    this.syn_status = syn_status;
	}
	public String getSyn_Status() {
	    return this.syn_status;
	}
	
	public void setCreated_Date(String created_date) {
	    this.created_date = created_date;
	}
	public String getCreated_Date() {
	    return this.created_date;
	}
	
	public void setTransaction_Date(String transaction_date) {
	    this.transaction_date = transaction_date;
	}
	public String getTransaction_Date() {
	    return this.transaction_date;
	}
	
	public void setDisplay_Flag(String display_flag) {
	    this.display_flag = display_flag;
	}
	public String getDisplay_Flag() {
	    return this.display_flag;
	}
	
	public void setRemarks(String remarks) {
	    this.remarks = remarks;
	}
	public String getRemarks() {
	    return this.remarks;
	}
	public void setWork_TaskType_ID(int work_task_type_id) {
	    this.work_task_type_id = work_task_type_id;
	}
	public int getWork_TaskType_ID() {
	    return this.work_task_type_id;
	}
	public void setMain_Task_ID(int mMain_Task_ID) {
	    this.main_task_Id = mMain_Task_ID;
	}
	public int getMain_Task_ID() {
	    return this.main_task_Id;
	}
	public void setMain_Task_Name(String mMain_Task_Name) {
	    this.main_task_name = mMain_Task_Name;
	}
	public String getMain_Task_Name() {
	    return this.main_task_name;
	}
	public void setLink_ToBill(String mLink_ToBill) {
	    this.link_toBill = mLink_ToBill;
	}
	public String getLink_ToBill() {
	    return this.link_toBill;
	}
	public void setSum_Entered_Qty(String mSum_Entered_Qty) {
	    this.sum_Entered_qty = mSum_Entered_Qty;
	}
	public String getSum_Entered_Qty() {
	    return this.sum_Entered_qty;
	}
	
	 public ArrayList<Status_Child_Model> getArrayChildren() {
	        return mArrayChildren;
	    } 
	    public void setArrayChildren(ArrayList<Status_Child_Model> arrayChildren) {
	        mArrayChildren = arrayChildren;
	    }
	
//	issues_status
	public void setProject_Issues_ID(int project_issues_id) {
	    this.project_issues_id_ISSUES = project_issues_id;
	}
	public int getProject_Issues_ID() {
	    return this.project_issues_id_ISSUES;
	}
	
	public void setProject_ID_Issues(int project_id_issues) {
	    this.project_id_ISSUES = project_id_issues;
	}
	public int getProject_ID_Issues() {
	    return this.project_id_ISSUES;
	}
	
	public void setSite_ID_ISSUES(int site_id_issues) {
	    this.site_id_ISSUES = site_id_issues;
	}
	public int getSite_ID_ISSUES() {
	    return this.site_id_ISSUES;
	}
	
	public void setPRJ_EST_WorkMaster_ID_ISSUES(int prj_est_workmaster_id_issues) {
	    this.prj_est_workmaster_id_ISSUES = prj_est_workmaster_id_issues;
	}
	public int getPRJ_EST_WorkMaster_ID_ISSUES() {
	    return this.prj_est_workmaster_id_ISSUES;
	}
	
	public void setPRJ_EST_WorkLocation_Name_ISSUES(String prj_est_worklocation_name_issues) {
	    this.prj_est_work_location_name_ISSUES = prj_est_worklocation_name_issues;
	}
	public String getPRJ_EST_WorkLocation_Name_ISSUES() {
	    return this.prj_est_work_location_name_ISSUES;
	}
	
	public void setIssues_Title_ISSUES(String issues_title_issues) {
	    this.issues_title_ISSUES = issues_title_issues;
	}
	public String getIssues_Title_ISSUES() {
	    return this.issues_title_ISSUES;
	}
	
	public void setIssues_Details_ISSUES(String issues_details_issues) {
	    this.issues_details_ISSUES = issues_details_issues;
	}
	public String getIssues_Details_ISSUES() {
	    return this.issues_details_ISSUES;
	}
	
	public void setSyn_Flag_ISSUES(String syn_flag_issues) {
	    this.syn_flag_ISSUES = syn_flag_issues;
	}
	public String getSyn_Flag_ISSUES() {
	    return this.syn_flag_ISSUES;
	}
	
	public void setIssues_CreationDateTime_ISSUES(String issues_creation_datetime_issues) {
	    this.issues_creation_datetime_ISSUES = issues_creation_datetime_issues;
	}
	public String getIssues_CreationDateTime_ISSUES() {
	    return this.issues_creation_datetime_ISSUES;
	}
	
//	Images_status
	public void setProject_Images_ID(int project_Images_id) {
	    this.project_images_id_IMAGES = project_Images_id;
	}
	public int getProject_Images_ID() {
	    return this.project_images_id_IMAGES;
	}
	
	public void setProject_ID_IMAGES(int project_id_Images) {
	    this.project_id_IMAGES = project_id_Images;
	}
	public int getProject_ID_IMAGES() {
	    return this.project_id_IMAGES;
	}
	
	public void setSite_ID_IMAGES(int site_id_Images) {
	    this.site_id_IMAGES = site_id_Images;
	}
	public int getSite_ID_IMAGES() {
	    return this.site_id_IMAGES;
	}
	
	public void setPRJ_EST_WorkMaster_ID_IMAGES(int prj_est_workmaster_id_Images) {
	    this.prj_est_workmaster_id_IMAGES = prj_est_workmaster_id_Images;
	}
	public int getPRJ_EST_WorkMaster_ID_IMAGES() {
	    return this.prj_est_workmaster_id_IMAGES;
	}
	
	public void setPRJ_EST_WorkLocation_Name_IMAGES(String prj_est_worklocation_name_Images) {
	    this.prj_est_work_location_name_IMAGES = prj_est_worklocation_name_Images;
	}
	public String getPRJ_EST_WorkLocation_Name_IMAGES() {
	    return this.prj_est_work_location_name_IMAGES;
	}
	
	
	public void setPath_IMAGES(String path_Images) {
	    this.path_IMAGES = path_Images;
	}
	public String getPath_IMAGES() {
	    return this.path_IMAGES;
	}	
	
	public void setSyn_Flag_IMAGES(String syn_flag_Images) {
	    this.syn_flag_IMAGES = syn_flag_Images;
	}
	public String getSyn_Flag_IMAGES() {
	    return this.syn_flag_IMAGES;
	}
	
	public void setImages_CreationDateTime_IMAGES(String Images_creation_datetime_Images) {
	    this.images_creation_datetime_IMAGES = Images_creation_datetime_Images;
	}
	public String getImages_CreationDateTime_IMAGES() {
	    return this.images_creation_datetime_IMAGES;
	}
	
}

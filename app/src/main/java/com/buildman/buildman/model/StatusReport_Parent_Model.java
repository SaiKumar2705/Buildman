package com.buildman.buildman.model;

import java.util.ArrayList;
import java.util.Comparator;

public class StatusReport_Parent_Model implements Comparable<StatusReport_Parent_Model> {
	private int mStatusReport_id;
	private int mTitle_Site_id;
	private String mTitle_SiteName;
	private String mSum_BillableAmt;
	private int mTask_id;
	private String mTask_Name;
	private String mTotal_Qty;
	private String mCompleted_Qty;
	private String mPercentage_WorkCompleted_Qty;
	private String mBalance_Qty;
	private String mBillable_Amt;
	private String mUnits;
    private ArrayList<StatusReport_Child_Model> mArrayChildren;
  
    public StatusReport_Parent_Model(int mSite_id_result, String site_name,
			int mtask_id_result, String task_name, String total_qty,
			String completed_qty, String percentage_workcompleted, String balance_qty, String units, String billable_amt) {
		// TODO Auto-generated constructor stub
    	this.mTitle_Site_id=mSite_id_result;
    	this.mTitle_SiteName=site_name;
    	this.mTask_id=mtask_id_result;
    	this.mTask_Name=task_name;
    	this.mTotal_Qty=total_qty;
    	this.mCompleted_Qty=completed_qty;
    	this.mPercentage_WorkCompleted_Qty=percentage_workcompleted;
    	this.mBalance_Qty=balance_qty;
    	this.mUnits=units;
    	this.mBillable_Amt=billable_amt;
    	
	}
	public StatusReport_Parent_Model() {
		// TODO Auto-generated constructor stub
	}
	
	public StatusReport_Parent_Model(String site_names,String sum_total_billable_amt,
			ArrayList<StatusReport_Child_Model> children_list) {
		// TODO Auto-generated constructor stub
		this.mTitle_SiteName=site_names;
		this.mSum_BillableAmt=sum_total_billable_amt;
		mArrayChildren = children_list;
	}
	public int getStatusReport_Id() {
        return mStatusReport_id;
    }
    public void setStatusReport_Id(int status_report_id) {
        this.mStatusReport_id = status_report_id;
    }
 
    public int getTitle_SiteId() {
        return mTitle_Site_id;
    }
    public void setTitle_SiteId(int title_site_id) {
        this.mTitle_Site_id = title_site_id;
    }
    
    public String getTitle_SiteName() {
        return mTitle_SiteName;
    } 
    public void setTitle_SiteName(String title_sitename) {
    	mTitle_SiteName = title_sitename;
    }
    
    public String getSum_Total_BillableAmt() {
        return mSum_BillableAmt;
    } 
    public void setSum_Total_BillableAmt(String mSum_BillableAm) {
    	mSum_BillableAmt = mSum_BillableAm;
    }
    
    public int getTaskId() {
        return mTask_id;
    }
    public void setTaskId(int task_id) {
        this.mTask_id = task_id;
    }
    
    public String getTask_Name() {
        return mTask_Name;
    } 
    public void setTask_Name(String task_name) {
    	mTask_Name = task_name;
    }
    
    public String getTotal_Qty() {
        return mTotal_Qty;
    } 
    public void setTotal_Qty(String total_qty) {
    	mTotal_Qty = total_qty;
    }
    
    public String getCompleted_Qty() {
        return mCompleted_Qty;
    } 
    public void setCompleted_Qty(String completed_Qty) {
    	mCompleted_Qty = completed_Qty;
    }
    
    public String getPercentage_WorkCompleted_Qty() {
        return mPercentage_WorkCompleted_Qty;
    } 
    public void setPercentage_WorkCompleted_Qty(String percentagecompleted_Qty) {
    	mPercentage_WorkCompleted_Qty = percentagecompleted_Qty;
    }
    
    public String getBalance_Qty() {
        return mBalance_Qty;
    } 
    public void setBalance_Qty(String balance_Qty) {
    	mBalance_Qty = balance_Qty;
    }
    
    public String getBillable_Amt() {
        return mBillable_Amt;
    } 
    public void setBillable_Amt(String mbillable_Amt) {
    	mBillable_Amt = mbillable_Amt;
    }
    
    public String getUnits() {
        return mUnits;
    } 
    public void setUnits(String units) {
    	mUnits = units;
    }
 
    public ArrayList<StatusReport_Child_Model> getArrayChildren() {
        return mArrayChildren;
    } 
    public void setArrayChildren(ArrayList<StatusReport_Child_Model> arrayChildren) {
        mArrayChildren = arrayChildren;
    }
	@Override
	public int compareTo(StatusReport_Parent_Model another) {
		// TODO Auto-generated method stub
		float compareSalary = Float.parseFloat(((StatusReport_Parent_Model) another).getSum_Total_BillableAmt());

		// ascending order
		// return (int) (this.salary - compareSalary);

		// descending order
		return (int) (compareSalary - Float.parseFloat(this.mSum_BillableAmt));
	}
    
   
	
    
}
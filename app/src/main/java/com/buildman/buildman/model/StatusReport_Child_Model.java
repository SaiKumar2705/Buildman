package com.buildman.buildman.model;

public class StatusReport_Child_Model {
	private String mTaskName_Child;
	private String mBillable_Amt_Child;
	private String mTotal_Qty_Child;
	private String mCompleted_Qty_Child;
	private String mPercentage_WorkCompleted_Qty_Child;
	private String mBalance_Qty_Child;
	private String mUnits_Child;

    public String getTaskName_Child() {
        return mTaskName_Child;
    }

    public void setTaskName_Child(String taskName) {
        this.mTaskName_Child = taskName;
    }
    
    public String getBillable_Amt_Child() {
        return mBillable_Amt_Child;
    }
    public void setBillable_Amt_Child(String billable_amt) {
        this.mBillable_Amt_Child = billable_amt;
    }

    public String getTotal_Qty_Child() {
        return mTotal_Qty_Child;
    }
    public void setTotal_Qty_Child(String total_qty) {
        this.mTotal_Qty_Child = total_qty;
    }
    
    public String getCompleted_Qty_Child() {
        return mCompleted_Qty_Child;
    }
    public void setCompleted_Qty_Child(String completed_qty) {
        this.mCompleted_Qty_Child = completed_qty;
    }
    
    public String getPercentage_WorkCompleted_Qty_Child() {
        return mPercentage_WorkCompleted_Qty_Child;
    } 
    public void setPercentage_WorkCompleted_Qty(String percentagecompleted_Qty) {
    	mPercentage_WorkCompleted_Qty_Child = percentagecompleted_Qty;
    }
    
    
    public String getBalance_Qty_Child() {
        return mBalance_Qty_Child;
    }
    public void setBalance_Qty_Child(String balance_qty) {
        this.mBalance_Qty_Child = balance_qty;
    }
    
    public String getUnits_Child() {
        return mUnits_Child;
    }
    public void setUnits_Child(String units) {
        this.mUnits_Child = units;
    }
}

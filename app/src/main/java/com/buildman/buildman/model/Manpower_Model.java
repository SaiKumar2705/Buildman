package com.buildman.buildman.model;

public class Manpower_Model {
//  Manpower_supply   
	private int manpower_supply_contract_master_id_MP_SUP;
	private int contract_project_id_MP_SUP;	
	private int contractor_id_MP_SUP;	
	String contractor_name_MP_SUP = null;
	private int prj_id_MP_SUP;
	private int site_id_MP_SUP;
	private int labour_type_id_MP_SUP;
	String labour_type_name_MP_SUP = null;	
	String entered_qty_MP_SUP = null;
	String today_entered_qtysum_MP_SUP = null;
	String total_till_date_qty_MP_SUP = null;	
	String rate_MP_SUP = null;	
	String total_billed_amt_MP_SUP = null;	
	String total_paid_amt_MP_SUP = null;	
	String balac_MP_SUP = null;	
	String syn_flag_MP_SUP = null;
	String created_date_MP_SUP = null;
	String transaction_date_MP_SUP = null;
	String display_flag_MP_SUP = null;
	
//  Manpower_contract 
	private int manpower_labour_contract_work_master_id_MP_CONT;
	private int lab_contract_work_master_id_MP_CONT;	
	private int contract_project_id_MP_CONT;	
	private int contractor_id_MP_CONT;	
	String contractor_name_MP_CONT = null;
	private int prj_id_MP_CONT;
	private int site_id_MP_CONT;
	private int work_loc_id_MP_CONT;
	String work_loc_name_MP_CONT = null;	
	String total_qty_MP_CONT = null;
	String qty_units_MP_CONT = null;
	String start_date_MP_CONT = null;	
	String scope_of_work_MP_CONT = null;	
	String qty_completed_MP_CONT = null;	
	String rate_MP_CONT = null;	
	String paid_amt_MP_CONT = null;	
	String balac_amt_MP_CONT = null;	
	String syn_flag_MP_CONT = null;
	String created_date_MP_CONT = null;		
	String display_flag_MP_CONT = null;
	
	
	public Manpower_Model(String contractor_name, String labour_type_name,
			String total_till_date_qty, String entered_qty) {
		// TODO Auto-generated constructor stub
		this.contractor_name_MP_SUP=contractor_name;
		this.labour_type_name_MP_SUP=labour_type_name;		
		this.total_till_date_qty_MP_SUP=total_till_date_qty;
		this.entered_qty_MP_SUP=entered_qty;
	}
	public Manpower_Model() {
		// TODO Auto-generated constructor stub
	}
	public Manpower_Model(int contract_project_ID, int contractor_ID, String contractor_name, int prj_ID, int site_ID, int labour_type_ID,
			String labour_type_name, String entered_qty, String today_entered_qty_sum,String total_till_date_qty, String rate,
			String total_billed_amt, String total_paid_amt, String balance_amt, String syn_flag,
			String created_date, String transaction_date, String display_flag) {
		// TODO Auto-generated constructor stub
		this.contract_project_id_MP_SUP=contract_project_ID;
		this.contractor_id_MP_SUP=contractor_ID;
		this.contractor_name_MP_SUP=contractor_name;
		this.prj_id_MP_SUP=prj_ID;
		this.site_id_MP_SUP=site_ID;
		this.labour_type_id_MP_SUP=labour_type_ID;
		this.labour_type_name_MP_SUP=labour_type_name;
		this.entered_qty_MP_SUP=entered_qty;
		this.today_entered_qtysum_MP_SUP=today_entered_qty_sum;
		this.total_till_date_qty_MP_SUP=total_till_date_qty;
		this.rate_MP_SUP=rate;
		this.total_billed_amt_MP_SUP=total_billed_amt;
		this.total_paid_amt_MP_SUP=total_paid_amt;
		this.balac_MP_SUP=balance_amt;
		this.syn_flag_MP_SUP=syn_flag;
		this.created_date_MP_SUP=created_date;
		this.transaction_date_MP_SUP=transaction_date;
		this.display_flag_MP_SUP=display_flag;
	}
	public Manpower_Model(int mContractor_Id,String mContractor_Name, String mWork_loc_name,
			String mTotal_Qty, String mQty_Completed, String mUnits, String mStart_Date ,
			String mScope_of_work, String mRate, String mPaid_amt) {
		// TODO Auto-generated constructor stub
		this.contractor_id_MP_CONT=mContractor_Id;
		this.contractor_name_MP_CONT=mContractor_Name;
		this.work_loc_name_MP_CONT=mWork_loc_name;
		this.total_qty_MP_CONT=mTotal_Qty;
		this.qty_completed_MP_CONT=mQty_Completed;
		this.qty_units_MP_CONT=mUnits;
		this.start_date_MP_CONT=mStart_Date;
		this.scope_of_work_MP_CONT=mScope_of_work;
		this.rate_MP_CONT=mRate;
		this.paid_amt_MP_CONT=mPaid_amt;
	}
	public Manpower_Model(int lab_contract_work_master_id,int contract_project_id,int contractor_id, 
			String contractor_name, int prj_ID, int site_ID, int work_loc_ID,
			String work_loc_name, String total_qty, String qty_units, String start_date,
			String scope_of_work, String qty_completed, String rate, String paid_amt,
			String balance_amt, String syn_status, String created_date,
			 String display_flag) {
		// TODO Auto-generated constructor stub
		this.lab_contract_work_master_id_MP_CONT=lab_contract_work_master_id;
		this.contract_project_id_MP_CONT=contract_project_id;
		this.contractor_id_MP_CONT=contractor_id;
		this.contractor_name_MP_CONT=contractor_name;
		this.prj_id_MP_CONT=prj_ID;
		this.site_id_MP_CONT=site_ID;
		this.work_loc_id_MP_CONT=work_loc_ID;
		this.work_loc_name_MP_CONT=work_loc_name;
		this.total_qty_MP_CONT=total_qty;
		this.qty_units_MP_CONT=qty_units;
		this.start_date_MP_CONT=start_date;
		this.scope_of_work_MP_CONT=scope_of_work;
		this.qty_completed_MP_CONT=qty_completed;
		this.rate_MP_CONT=rate;
		this.paid_amt_MP_CONT=paid_amt;
		this.balac_amt_MP_CONT=balance_amt;
		this.syn_flag_MP_CONT=syn_status;
		this.created_date_MP_CONT=created_date;		
		this.display_flag_MP_CONT=display_flag;
	}
	public void setManpower_Supply_Contract_Master_Id_MP_SUP(int manpower_supply_master_id) {
	    this.manpower_supply_contract_master_id_MP_SUP = manpower_supply_master_id;
	}
	public int getManpower_Supply_Contract_Master_Id_MP_SUP() {
	    return this.manpower_supply_contract_master_id_MP_SUP;	    
	}
	
	public void setContract_Project_Id_MP_SUP(int contract_project_id) {
	    this.contract_project_id_MP_SUP = contract_project_id;
	}
	public int getContract_Project_Id_MP_SUP() {
	    return this.contract_project_id_MP_SUP;	    
	}
	
	public void setContractor_Id_MP_SUP(int contractor_id) {
	    this.contractor_id_MP_SUP = contractor_id;
	}
	public int getContractor_Id_MP_SUP() {
	    return this.contractor_id_MP_SUP;	    
	}
	
	public void setContractor_Name_MP_SUP(String contractor_name_MP_SUP) {
	    this.contractor_name_MP_SUP = contractor_name_MP_SUP;
	}
	public String getContractor_Name_MP_SUP() {
	    return this.contractor_name_MP_SUP;
	}
	
	public void setProj_Id_MP_SUP(int prj_id_MP_SUP) {
	    this.prj_id_MP_SUP = prj_id_MP_SUP;
	}
	public int getPrj_Id_MP_SUP() {
	    return this.prj_id_MP_SUP;	    
	}
	
	public void setSite_Id_MP_SUP(int site_id_MP_SUP) {
	    this.site_id_MP_SUP = site_id_MP_SUP;
	}
	public int getSite_Id_MP_SUP() {
	    return this.site_id_MP_SUP;	    
	}
	
	public void setLabourType_Id_MP_SUP(int labourType_id_MP_SUP) {
	    this.labour_type_id_MP_SUP = labourType_id_MP_SUP;
	}
	public int getLabourType_Id_MP_SUP() {
	    return this.labour_type_id_MP_SUP;	    
	}
		
	public void setLabourType_Name_MP_SUP(String labourType_name_MP_SUP) {
	    this.labour_type_name_MP_SUP = labourType_name_MP_SUP;
	}
	public String getLabourType_Name_MP_SUP() {
	    return this.labour_type_name_MP_SUP;
	}
	
	public void setEntered_Qty_MP_SUP(String entered_qty_MP_SUP) {
	    this.entered_qty_MP_SUP = entered_qty_MP_SUP;
	}
	public String getEntered_Qty_MP_SUP() {
	    return this.entered_qty_MP_SUP;
	}
	
	public void setTodayEntered_QtySum_MP_SUP(String today_entered_qtysum_MP_SUP) {
	    this.today_entered_qtysum_MP_SUP = today_entered_qtysum_MP_SUP;
	}
	public String getTodayEntered_QtySum_MP_SUP() {
	    return this.today_entered_qtysum_MP_SUP;
	}
	
	public void setTotalTillDate_Qty_MP_SUP(String total_till_date_qty_MP_SUP) {
	    this.total_till_date_qty_MP_SUP = total_till_date_qty_MP_SUP;
	}
	public String getTotalTillDate_Qty_MP_SUP() {
	    return this.total_till_date_qty_MP_SUP;
	}
	
	public void setRate_MP_SUP(String rate_MP_SUP) {
	    this.rate_MP_SUP = rate_MP_SUP;
	}
	public String getRate_MP_SUP() {
	    return this.rate_MP_SUP;
	}
	
	public void setTotalBilledAmt_MP_SUP(String total_billed_amt_MP_SUP) {
	    this.total_billed_amt_MP_SUP = total_billed_amt_MP_SUP;
	}
	public String getTotalBilledAmt_MP_SUP() {
	    return this.total_billed_amt_MP_SUP;
	}
	
	public void setTotalPaidAmt_MP_SUP(String total_paid_amt_MP_SUP) {
	    this.total_paid_amt_MP_SUP = total_paid_amt_MP_SUP;
	}
	public String getTotalpaidAmt_MP_SUP() {
	    return this.total_paid_amt_MP_SUP;
	}
	
	public void setBalance_Amt_MP_SUP(String balance_amt_MP_SUP) {
	    this.balac_MP_SUP = balance_amt_MP_SUP;
	}
	public String getBalance_Amt_MP_SUP() {
	    return this.balac_MP_SUP;
	}
	
	public void setSyn_Flag_MP_SUP(String syn_flag_MP_SUP) {
	    this.syn_flag_MP_SUP = syn_flag_MP_SUP;
	}
	public String getSyn_Flag_MP_SUP() {
	    return this.syn_flag_MP_SUP;
	}

	public void setCreated_Date_MP_SUP(String created_date_MP_SUP) {
	    this.created_date_MP_SUP = created_date_MP_SUP;
	}
	public String getCreated_Date_MP_SUP() {
	    return this.created_date_MP_SUP;
	}
	
	public void setTransaction_Date_MP_SUP(String transaction_date_MP_SUP) {
	    this.transaction_date_MP_SUP = transaction_date_MP_SUP;
	}
	public String getTransaction_Date_MP_SUP() {
	    return this.transaction_date_MP_SUP;
	}
	
	public void setDisplay_Flag_MP_SUP(String dispaly_flag_MP_SUP) {
	    this.display_flag_MP_SUP = dispaly_flag_MP_SUP;
	}
	public String getDisplay_Flag_MP_SUP() {
	    return this.display_flag_MP_SUP;
	}
	
//	manpower supply
	public void setManpower_Labour_Contract_Work_Master_Id_MP_CONT(int manpower_contract_master_id) {
	    this.manpower_labour_contract_work_master_id_MP_CONT = manpower_contract_master_id;
	}
	public int getManpower_Labour_Contract_Work_Master_Id_MP_CONT() {
	    return this.manpower_labour_contract_work_master_id_MP_CONT;	    
	}
	
	public void setLab_Contract_Work_MasterId_MP_CONT(int lab_contract_work_master_id) {
	    this.lab_contract_work_master_id_MP_CONT = lab_contract_work_master_id;
	}
	public int getLab_Contract_Work_MasterId_MP_CONT() {
	    return this.lab_contract_work_master_id_MP_CONT;	    
	}
	
	public void setContract_Project_Id_MP_CONT(int contract_project_id) {
	    this.contract_project_id_MP_CONT = contract_project_id;
	}
	public int getContract_Project_Id_MP_CONT() {
	    return this.contract_project_id_MP_CONT;	    
	}
	
	public void setContractor_Id_MP_CONT(int contractor_id) {
	    this.contractor_id_MP_CONT = contractor_id;
	}
	public int getContractor_Id_MP_CONT() {
	    return this.contractor_id_MP_CONT;	    
	}
	
	public void setContractor_Name_MP_CONT(String contractor_name_MP_CONT) {
	    this.contractor_name_MP_CONT = contractor_name_MP_CONT;
	}
	public String getContractor_Name_MP_CONT() {
	    return this.contractor_name_MP_CONT;
	}
	
	public void setProj_Id_MP_CONT(int prj_id_MP_CONT) {
	    this.prj_id_MP_CONT = prj_id_MP_CONT;
	}
	public int getProj_Id_MP_CONT() {
	    return this.prj_id_MP_CONT;	    
	}
	
	public void setSite_Id_MP_CONT(int site_id_MP_CONT) {
	    this.site_id_MP_CONT = site_id_MP_CONT;
	}
	public int getSite_Id_MP_CONT() {
	    return this.site_id_MP_CONT;	    
	}
	
	public void setWork_Loc_Id_MP_CONT(int work_Loc_ID_MP_CONT) {
	    this.work_loc_id_MP_CONT = work_Loc_ID_MP_CONT;
	}
	public int getWork_Loc_Id_MP_CONT() {
	    return this.work_loc_id_MP_CONT;	    
	}
	
	public void setWork_Loc_Name_MP_CONT(String work_Loc_name_MP_CONT) {
	    this.work_loc_name_MP_CONT = work_Loc_name_MP_CONT;
	}
	public String getWork_Loc_Name_MP_CONT() {
	    return this.work_loc_name_MP_CONT;	    
	}
	
	public void setTotal_Qty_MP_CONT(String total_qty_MP_CONT) {
	    this.total_qty_MP_CONT = total_qty_MP_CONT;
	}
	public String getTotal_Qty_MP_CONT() {
	    return this.total_qty_MP_CONT;	    
	}
	
	public void setQty_Units_MP_CONT(String qty_units_MP_CONT) {
	    this.qty_units_MP_CONT = qty_units_MP_CONT;
	}
	public String getQty_Units_MP_CONT() {
	    return this.qty_units_MP_CONT;	    
	}
	
	public void setStartDate_MP_CONT(String start_date_MP_CONT) {
	    this.start_date_MP_CONT= start_date_MP_CONT;
	}
	public String getStartDate_MP_CONT() {
	    return this.start_date_MP_CONT;	    
	}
	
	public void setScope_Of_Work_MP_CONT(String scope_of_work_MP_CONT) {
	    this.scope_of_work_MP_CONT= scope_of_work_MP_CONT;
	}
	public String getScope_Of_Work_MP_CONT() {
	    return this.scope_of_work_MP_CONT;	    
	}
	
	public void setQty_Completed_MP_CONT(String qty_completed_MP_CONT) {
	    this.qty_completed_MP_CONT= qty_completed_MP_CONT;
	}
	public String getQty_Completed_MP_CONT() {
	    return this.qty_completed_MP_CONT;	    
	}
	
	public void setLab_Cont_Work_Rate_MP_CONT(String rate_MP_CONT) {
	    this.rate_MP_CONT= rate_MP_CONT;
	}
	public String getLab_Cont_Work_Rate_MP_CONT() {
	    return this.rate_MP_CONT;	    
	}
	
	public void setPaidAmt_MP_CONT(String paid_amt_MP_CONT) {
	    this.paid_amt_MP_CONT= paid_amt_MP_CONT;
	}
	public String getPaidAmt_MP_CONT() {
	    return this.paid_amt_MP_CONT;
	}
	
	public void setBalance_Amt_MP_CONT(String balance_amt_MP_CONT) {
	    this.balac_amt_MP_CONT = balance_amt_MP_CONT;
	}
	public String getBalance_Amt_MP_CONT() {
	    return this.balac_amt_MP_CONT;
	}
	
	public void setSyn_Flag_MP_CONT(String syn_flag_MP_CONT) {
	    this.syn_flag_MP_CONT = syn_flag_MP_CONT;
	}
	public String getSyn_Flag_MP_CONT() {
	    return this.syn_flag_MP_CONT;
	}

	public void setCreated_Date_MP_CONT(String created_date_MP_CONT) {
	    this.created_date_MP_CONT = created_date_MP_CONT;
	}
	public String getCreated_Date_MP_CONT() {
	    return this.created_date_MP_CONT;
	}
	
	
	public void setDisplay_Flag_MP_CONT(String dispaly_flag_MP_CONT) {
	    this.display_flag_MP_CONT = dispaly_flag_MP_CONT;
	}
	public String getDisplay_Flag_MP_CONT() {
	    return this.display_flag_MP_CONT;
	}
	
}

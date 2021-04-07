package com.buildman.buildman.model;

public class Received_Material_Model implements Comparable<Received_Material_Model> {
	private int material_id;
	private int task_id;
	String material_name_matl = null;
	String units_matl = null;
	String rate_matl=null;

// material  stock transaction(MST)
	private int material_stk_trans_id;
	private int material_id_MST;
	String material_name_MST = null;
	String units_MST = null;
	String rate_MST = null;
	String received_stock_MST = null;
	String used_MST = null;
	String indent_MST = null;
	String material_req_by_date_MST = null;
	String current_stock_MST = null;
	String transaction_date_MST = null;
	String created_date_MST=null;
	String remarks_MST=null;
	String adjustments_MST=null;
	String adjustReason_MST=null;
	private int project_id_MST;
	private int site_id_MST;
	private int party_id_MST;
	private int org_id_MST;
	String sync_flag_MST = null;
	String oldornew_flag_MST=null;
	String dc_invoice_MST = null;
	String lot_number_MST = null;
	String doc_type_MST = null;
	String display_flag_MST = null;
	String indent_code_MST = null;
	
	public Received_Material_Model(String materials, String units, String currentStock,String receivedStock) {
		// TODO Auto-generated constructor stub
		 super();
		 
	       this.material_name_MST = materials;
	       this.units_MST = units;
	       this.current_stock_MST = currentStock;
	       this.received_stock_MST = receivedStock;
	      
	}

	public Received_Material_Model(String materials, String units, String currentStock,String dc_invoice,String receivedStock, String requiredStock, String rate) {
		// TODO Auto-generated constructor stub
		 super();
		 
	       this.material_name_MST = materials;
	       this.units_MST = units;
	       this.current_stock_MST = currentStock;
	       this.dc_invoice_MST = dc_invoice;
	       this.received_stock_MST = receivedStock;
	       this.indent_MST = requiredStock;
	       this.rate_MST= rate;
	}

	public Received_Material_Model() {
		// TODO Auto-generated constructor stub
	}


	public Received_Material_Model(int mMaterial_Id_result,
			String material_name, String units, String rate) {
		// TODO Auto-generated constructor stub
		   this.material_id = mMaterial_Id_result;
		   this.material_name_matl = material_name;
	       this.units_matl = units;	       
	       this.rate_matl = rate;
	}


	public Received_Material_Model(int mMaterial_Id_result,
			String material_name, String units, String rate, String received_stock,
			String used_stock, String indent, String current_stock,  String mTodayDate,
			int project_id_int, int site_id_int, int party_id_int,
			int org_id_int, String syn_status_flag, String display_flag,String material_reqd_bydate,
			String dc_invoice_number, String mlot_number,
			String doc_type_of_invoice,String created_date,String mRemarks_MST,String mAdjustments_MST,
			String mAdjustReasons_MST, int sub_TaskID) {
		// TODO Auto-generated constructor stub
		   this.material_id_MST = mMaterial_Id_result;
		   this.material_name_MST = material_name;
	       this.units_MST = units;	       
	       this.rate_MST = rate;
	       this.received_stock_MST = received_stock;
	       this.used_MST = used_stock;
	       this.indent_MST = indent;
	       this.current_stock_MST = current_stock;
	       this.transaction_date_MST = mTodayDate;
	       this.project_id_MST = project_id_int;
	       this.site_id_MST = site_id_int;
	       this.party_id_MST = party_id_int;
	       this.org_id_MST = org_id_int;
	       this.sync_flag_MST=syn_status_flag;
	       this.display_flag_MST=display_flag;
	       this.material_req_by_date_MST=material_reqd_bydate;
	       this.dc_invoice_MST=dc_invoice_number;
	       this.lot_number_MST=mlot_number;
	       this.doc_type_MST=doc_type_of_invoice;
	       this.created_date_MST=created_date;
	       this.remarks_MST=mRemarks_MST;
	       this.adjustments_MST=mAdjustments_MST;
	       this.adjustReason_MST=mAdjustReasons_MST;
	       this.task_id=sub_TaskID;
	}


	public void setMaterial_Id(int id) {
	    this.material_id = id;
	}
	public int getMaterial_ID() {
	    return this.material_id;
	}
	
	public void setMaterial_Name_Matl(String material_name) {
	    this.material_name_matl = material_name;
	}

	public String getMaterial_Name_Matl() {
	    return this.material_name_matl;
	}
	public void setUnits_Matl(String units) {
	    this.units_matl = units;
	}

	public String getUnits_Matl() {
	    return this.units_matl;
	}
	public void setRate_Matl(String rate) {
	    this.rate_matl = rate;
	}

	public String getRate_Matl() {
	    return this.rate_matl;
	}
	
	// material  stock  transaction(MST)
	public void setMaterial_Stock_Transactions_ID(int material_stock_transaction_id) {
	    this.material_stk_trans_id = material_stock_transaction_id;
	}

	public int getMaterial_Stock_Transactions_ID() {
	    return this.material_stk_trans_id;
	}
	public void setMaterial_ID_MST(int material_id_MST) {
	    this.material_id_MST = material_id_MST;
	}

	public int getMaterial_ID_MST() {
	    return this.material_id_MST;
	}
	public void setMaterial_Name_MST(String material_name_MST) {
	    this.material_name_MST = material_name_MST;
	}

	public String getMaterial_Name_MST() {
	    return this.material_name_MST;
	}
	public void setUnits_MST(String units_MST) {
	    this.units_MST = units_MST;
	}
	public String getUnits_MST() {
	    return this.units_MST;
	}
	public void setRate_MST(String rate_MST) {
	    this.rate_MST = rate_MST;
	}
	public String getRate_MST() {
	    return this.rate_MST;
	}
	public void setReceived_Stock_MST(String received_MST) {
	    this.received_stock_MST = received_MST;
	}
	public String getReceived_Stock_MST() {
	    return this.received_stock_MST;
	}
	public void setUsed_MST(String used_MST) {
	    this.used_MST = used_MST;
	}
	public String getUsed_MST() {
	    return this.used_MST;
	}
	public void setIndent_MST(String indent_MST) {
	    this.indent_MST = indent_MST;
	}
	public String getIndent_MST() {
	    return this.indent_MST;
	}
	public void setMaterial_Req_By_Date_MST(String material_req_by_date_MST) {
	    this.material_req_by_date_MST = material_req_by_date_MST;
	}
	public String getMaterial_Req_By_Date_MST() {
	    return this.material_req_by_date_MST;
	}
	
	public void setCurrent_Stock_MST(String current_stock_MST) {
	    this.current_stock_MST = current_stock_MST;
	}
	public String getCurrent_Stock_MST() {
	    return this.current_stock_MST;
	}
	
	public void setProject_ID_MST(int project_id_MST) {
	    this.project_id_MST = project_id_MST;
	}
	public int getProject_ID_MST() {
	    return this.project_id_MST;
	}
	public void setSite_ID_MST(int site_id_MST) {
	    this.site_id_MST = site_id_MST;
	}
	public int getSite_ID_MST() {
	    return this.site_id_MST;
	}
	public void setParty_ID_MST(int party_id_MST) {
	    this.party_id_MST = party_id_MST;
	}
	public int getParty_ID_MST() {
	    return this.party_id_MST;
	}
	public void setOrg_ID_MST(int org_id_MST) {
	    this.org_id_MST = org_id_MST;
	}
	public int getOrg_ID_MST() {
	    return this.org_id_MST;
	}
	public void setSync_flag_MST(String sync_flag_MST) {
	    this.sync_flag_MST = sync_flag_MST;
	}
	public String getSync_flag_MST() {
	    return this.sync_flag_MST;
	}
	public void setOldOrNew_flag_MST(String old_or_new_flag) {
	    this.oldornew_flag_MST = old_or_new_flag;
	}
	public String getOldOrNew_flag_MST() {
	    return this.oldornew_flag_MST;
	}
	public void setDC_Invoice_MST(String dc_invoice_MST) {
	    this.dc_invoice_MST = dc_invoice_MST;
	}
	public String getDC_Invoice_MST() {
	    return this.dc_invoice_MST;
	}
	public void setLOT_Number_MST(String lot_number_MST) {
	    this.lot_number_MST = lot_number_MST;
	}
	public String getLOT_Number_MST() {
	    return this.lot_number_MST;
	}
	public void setDoc_Type_MST(String doc_type_MST) {
	    this.doc_type_MST = doc_type_MST;
	}
	public String getDoc_Type_MST() {
	    return this.doc_type_MST;
	}	
	public void setTransaction_Date_MST(String transaction_date_MST) {
	    this.transaction_date_MST = transaction_date_MST;
	}
	public String getTransaction_Date_MST() {
	    return this.transaction_date_MST;
	}
	public void setCreated_Date_MST(String created_date_MST) {
	    this.created_date_MST = created_date_MST;
	}
	public String getCreated_Date_MST() {
	    return this.created_date_MST;
	}	
	public void setRemarks_MST(String mRemarks_MST) {
	    this.remarks_MST = mRemarks_MST;
	}
	public String getRemarks_MST() {
	    return this.remarks_MST;
	}
	public void setAdjustments_MST(String mAdjustments_MST) {
	    this.adjustments_MST = mAdjustments_MST;
	}
	public String getAdjustments_MST() {
	    return this.adjustments_MST;
	}
	public void setAdjustReason_MST(String mAdjustReason_MST) {
	    this.adjustReason_MST = mAdjustReason_MST;
	}
	public String getAdjustReason_MST() {
	    return this.adjustReason_MST;
	}
	public void setDisplay_Flag_MST(String display_flag_MST) {
	    this.display_flag_MST = display_flag_MST;
	}
	public String getDisplay_Flag_MST() {
	    return this.display_flag_MST;
	}
	

	public void setTask_Id(int id) {
	    this.task_id = id;
	}
	public int getTask_ID() {
	    return this.task_id;
	}
	
	public int compareTo(Received_Material_Model another) {
		// TODO Auto-generated method stub
		double compareSalary = Double.parseDouble(((Received_Material_Model) another).getDC_Invoice_MST());

		// ascending order
		// return (int) (this.salary - compareSalary);

		// descending order
		return (int) (compareSalary - Double.parseDouble(this.dc_invoice_MST));
	}
	

	}

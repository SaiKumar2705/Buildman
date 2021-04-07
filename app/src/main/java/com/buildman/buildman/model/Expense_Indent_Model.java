package com.buildman.buildman.model;

public class Expense_Indent_Model {

	int exp_indent_id;
	String category_exp_indent=null;
	String sub_category_exp_indent=null;
	String date_exp_indent=null;
	String time_exp_indent=null;
	String amount_exp_indent=null;
	String payer_exp_indent=null;
	String pay_method_exp_indent=null;
	String check_no_exp_indent=null;
	String check_status_exp_indent=null;
	String description_exp_indent=null;
	String image_exp_indent=null;
	 String tax_exp_indent=null;
	 
	public Expense_Indent_Model(String category, String sub_category, String date,
			String time, String amount, String payer, String pay_method,
			String check_no,String check_status, String description, String image, String tax) {
		// TODO Auto-generated constructor stub
		this.category_exp_indent=category;
		this.sub_category_exp_indent=sub_category;
		this.date_exp_indent=date;
		this.time_exp_indent=time;
		this.amount_exp_indent=amount;
		this.payer_exp_indent=payer;
		this.pay_method_exp_indent=pay_method;
		this.check_no_exp_indent=check_no;
		this.check_status_exp_indent=check_status;
		this.description_exp_indent=description;
		this.image_exp_indent=image;
		this.tax_exp_indent=tax;
		
	}
	public Expense_Indent_Model() {
		// TODO Auto-generated constructor stub
	}
	public Expense_Indent_Model(String date, String payer,String category, 
			String pay_method, String amt) {
		// TODO Auto-generated constructor stub
		this.date_exp_indent=date;
	
	
		this.payer_exp_indent=payer;
		this.category_exp_indent=category;
		this.pay_method_exp_indent=pay_method;
		this.amount_exp_indent=amt;
	}
	public void setExp_Indent_Id(int id) {
		this.exp_indent_id=id;
	}
	public int getExp_Indent_Id() {
		return this.exp_indent_id;
	}
	
	public void setCategory_Exp_Indent(String category) {
	    this.category_exp_indent = category;
	}

	public String getCategory_Exp_Indent() {
	    return this.category_exp_indent;
	}
	public void setSub_Category_Exp_Indent(String sub_category) {
	    this.sub_category_exp_indent = sub_category;
	}

	public String getSub_Category_Exp_Indent() {
	    return this.sub_category_exp_indent;
	}
	public void setDate_Exp_Indent(String date) {
	    this.date_exp_indent = date;
	}

	public String getDate_Exp_Indent() {
	    return this.date_exp_indent;
	}
	public void setTime_Exp_Indent(String time) {
	    this.time_exp_indent = time;
	}

	public String getTime_Exp_Indent() {
	    return this.time_exp_indent;
	}
	public void setAmount_Exp_Indent(String amount) {
	    this.amount_exp_indent = amount;
	}

	public String getAmount_Exp_Indent() {
	    return this.amount_exp_indent;
	}
	public void setPayer_Exp_Indent(String payer) {
	    this.payer_exp_indent = payer;
	}

	public String getPayer_Exp_Indent() {
	    return this.payer_exp_indent;
	}
	public void setPay_Method_Exp_Indent(String pay_method) {
	    this.pay_method_exp_indent = pay_method;
	}

	public String getPay_Method_Exp_Indent() {
	    return this.pay_method_exp_indent;
	}
	public void setCheck_No_Exp_Indent(String check_no) {
	    this.check_no_exp_indent = check_no;
	}

	public String getCheck_No_Exp_Indent() {
	    return this.check_no_exp_indent;
	}
	public void setCheck_Status_Exp_Indent(String check_status) {
	    this.check_status_exp_indent = check_status;
	}

	public String getCheck_Status_Exp_Indent() {
	    return this.check_status_exp_indent;
	}
	public void setDescription_Exp_Indent(String description) {
	    this.description_exp_indent = description;
	}

	public String getDescription_Exp_Indent() {
	    return this.description_exp_indent;
	}
	public void setImage_Exp_Indent(String image) {
	    this.image_exp_indent = image;
	}

	public String getImage_Exp_Indent() {
	    return this.image_exp_indent;
	}
	public void setTax_Exp_Indent(String tax) {
	    this.tax_exp_indent = tax;
	}

	public String getTax_Exp_Indent() {
	    return this.tax_exp_indent;
	}
}



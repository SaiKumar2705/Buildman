package com.buildman.buildman.model;

public class Expense_Expense_Model {

	int exp_expense_id;
	String category_exp_expense=null;
	String sub_category_exp_expense=null;
	String date_exp_expense=null;
	String time_exp_expense=null;
	String amount_exp_expense=null;
	String payee_exp_expense=null;
	String pay_method_exp_expense=null;
	String check_no_exp_expense=null;
	String check_status_exp_expense=null;
	String description_exp_expense=null;
	String image_exp_expense=null;
	 String tax_exp_expense=null;
	 
	public Expense_Expense_Model(String category, String sub_category, String date,
			String time, String amount, String payee, String pay_method,
			String check_no,String check_status, String description, String image, String tax) {
		// TODO Auto-generated constructor stub
		this.category_exp_expense=category;
		this.sub_category_exp_expense=sub_category;
		this.date_exp_expense=date;
		this.time_exp_expense=time;
		this.amount_exp_expense=amount;
		this.payee_exp_expense=payee;
		this.pay_method_exp_expense=pay_method;
		this.check_no_exp_expense=check_no;
		this.check_status_exp_expense=check_status;
		this.description_exp_expense=description;
		this.image_exp_expense=image;
		this.tax_exp_expense=tax;
		
	}
	public Expense_Expense_Model() {
		// TODO Auto-generated constructor stub
	}
	public Expense_Expense_Model(String date, String category, String subcategory,
			String pay_method, String amt) {
		// TODO Auto-generated constructor stub
		this.date_exp_expense=date;
	
		this.category_exp_expense=category;
		this.sub_category_exp_expense=subcategory;
		this.pay_method_exp_expense=pay_method;
		this.amount_exp_expense=amt;
	}
	public void setExp_Expense_Id(int id) {
		this.exp_expense_id=id;
	}
	public int getExp_Expense_Id() {
		return this.exp_expense_id;
	}
	
	public void setCategory_Exp_Expense(String category) {
	    this.category_exp_expense = category;
	}

	public String getCategory_Exp_Expense() {
	    return this.category_exp_expense;
	}
	public void setSub_Category_Exp_Expense(String sub_category) {
	    this.sub_category_exp_expense = sub_category;
	}

	public String getSub_Category_Exp_Expense() {
	    return this.sub_category_exp_expense;
	}
	public void setDate_Exp_Expense(String date) {
	    this.date_exp_expense = date;
	}

	public String getDate_Exp_Expense() {
	    return this.date_exp_expense;
	}
	public void setTime_Exp_Expense(String time) {
	    this.time_exp_expense = time;
	}

	public String getTime_Exp_Expense() {
	    return this.time_exp_expense;
	}
	public void setAmount_Exp_Expense(String amount) {
	    this.amount_exp_expense = amount;
	}

	public String getAmount_Exp_Expense() {
	    return this.amount_exp_expense;
	}
	public void setPayee_Exp_Expense(String payee) {
	    this.payee_exp_expense = payee;
	}

	public String getPayee_Exp_Expense() {
	    return this.payee_exp_expense;
	}
	public void setPay_Method_Exp_Expense(String pay_method) {
	    this.pay_method_exp_expense = pay_method;
	}

	public String getPay_Method_Exp_Expense() {
	    return this.pay_method_exp_expense;
	}
	public void setCheck_No_Exp_Expense(String check_no) {
	    this.check_no_exp_expense = check_no;
	}

	public String getCheck_No_Exp_Expense() {
	    return this.check_no_exp_expense;
	}
	public void setCheck_Status_Exp_Expense(String check_status) {
	    this.check_status_exp_expense = check_status;
	}

	public String getCheck_Status_Exp_Expense() {
	    return this.check_status_exp_expense;
	}
	public void setDescription_Exp_Expense(String description) {
	    this.description_exp_expense = description;
	}

	public String getDescription_Exp_Expense() {
	    return this.description_exp_expense;
	}
	public void setImage_Exp_Expense(String image) {
	    this.image_exp_expense = image;
	}

	public String getImage_Exp_Expense() {
	    return this.image_exp_expense;
	}
	public void setTax_Exp_Expense(String tax) {
	    this.tax_exp_expense = tax;
	}

	public String getTax_Exp_Expense() {
	    return this.tax_exp_expense;
	}
}


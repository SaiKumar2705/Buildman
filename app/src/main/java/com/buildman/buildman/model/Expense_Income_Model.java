package com.buildman.buildman.model;

public class Expense_Income_Model {
int exp_income_id;
String account=null;
String current_Balc=null;
String amount_type=null;
String category=null;
String sub_category=null;
String date=null;
String time=null;
String amount=null;
String payer=null;
String pay_method=null;
String check_no=null;
String check_status=null;
String description=null;
String image=null;
 String tax=null;
 
public Expense_Income_Model(String mAccount,String mCurrent_Balc,String mAmount_Type,String category,
		String sub_category, String date, String time, String amount,
		String payer, String pay_method,String check_no,String check_status, String description, 
		String image, String tax) {
	// TODO Auto-generated constructor stub
	this.account=mAccount;
	this.current_Balc=mCurrent_Balc;
	this.amount_type=mAmount_Type;
	this.category=category;
	this.sub_category=sub_category;
	this.date=date;
	this.time=time;
	this.amount=amount;
	this.payer=payer;
	this.pay_method=pay_method;
	this.check_no=check_no;
	this.check_status=check_status;
	this.description=description;
	this.image=image;
	this.tax=tax;
	
}
public Expense_Income_Model() {
	// TODO Auto-generated constructor stub
}

public void setExp_Income_Id(int id) {
	this.exp_income_id=id;
}
public int getExp_Income_Id() {
	return this.exp_income_id;
}

public void setAccount(String account) {
    this.account = account;
}
public String getAccount() {
    return this.account;
}

public void setCurrentBalance(String current_balc) {
    this.current_Balc = current_balc;
}
public String getCurrentBalance() {
    return this.current_Balc;
}

public void setAmount_Type(String amount_type) {
    this.amount_type = amount_type;
}
public String getAmount_Type() {
    return this.amount_type;
}

public void setCategory(String category) {
    this.category = category;
}

public String getCategory() {
    return this.category;
}
public void setSub_Category(String sub_category) {
    this.sub_category = sub_category;
}

public String getSub_Category() {
    return this.sub_category;
}
public void setDate(String date) {
    this.date = date;
}

public String getDate() {
    return this.date;
}
public void setTime(String time) {
    this.time = time;
}

public String getTime() {
    return this.time;
}
public void setAmount(String amount) {
    this.amount = amount;
}

public String getAmount() {
    return this.amount;
}
public void setPayer(String payer) {
    this.payer = payer;
}

public String getPayer() {
    return this.payer;
}
public void setPay_Method(String pay_method) {
    this.pay_method = pay_method;
}

public String getPay_Method() {
    return this.pay_method;
}
public void setCheck_No(String check_no) {
    this.check_no = check_no;
}

public String getCheck_No() {
    return this.check_no;
}
public void setCheck_Status(String check_status) {
    this.check_status = check_status;
}

public String getCheck_Status() {
    return this.check_status;
}
public void setDescription(String description) {
    this.description = description;
}

public String getDescription() {
    return this.description;
}
public void setImage(String image) {
    this.image = image;
}

public String getImage() {
    return this.image;
}
public void setTax(String tax) {
    this.tax = tax;
}

public String getTax() {
    return this.tax;
}
}


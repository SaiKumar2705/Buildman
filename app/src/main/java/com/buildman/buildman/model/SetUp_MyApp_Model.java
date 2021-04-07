package com.buildman.buildman.model;

public class SetUp_MyApp_Model {
	private int setup_myapp_id;
	String email = null;
	String password = null;
	String org_code;
	String date = null;
	private int org_id;	
	private int party_id;	
	String org_subscription_status = null;
	
	public SetUp_MyApp_Model(String mMy_email, String mMy_Password, String date) {
		// TODO Auto-generated constructor stub
		this.email=mMy_email;
		this.password=mMy_Password;
		this.date=date;
	}
	public SetUp_MyApp_Model(String mMy_email, String mMy_Password, String date, int party_id, int org_id) {
		// TODO Auto-generated constructor stub
		this.email=mMy_email;
		this.password=mMy_Password;
		this.date=date;
		this.party_id=party_id;
		this.org_id=org_id;
	}
	public SetUp_MyApp_Model() {
		// TODO Auto-generated constructor stub
	}
	public void setSetUp_MyApp_ID(int setup_myapp_id) {
	    this.setup_myapp_id = setup_myapp_id;
	}
	public int getSetUp_MyApp_ID() {
	    return this.setup_myapp_id;
	}

	public void setEmail(String mMy_email) {
	    this.email = mMy_email;
	}

	public String getEmail() {
	    return this.email;
	}
	public void setPassword(String mypassword) {
	    this.password = mypassword;
	}

	public String getPassword() {
	    return this.password;
	}
	
	public void setOrg_Code(String org_code) {
	    this.org_code = org_code;
	}
	public String getOrg_Code() {
	    return this.org_code;
	}
	
	public void setDate(String date) {
	    this.date = date;
	}

	public String getDate() {
	    return this.date;
	}
	
	public void setOrg_ID(int org_id) {
	    this.org_id = org_id;
	}
	public int getOrg_ID() {
	    return this.org_id;
	}
	
	public void setOrg_Subscription_Status(String subscription_status) {
	    this.org_subscription_status = subscription_status;
	}

	public String getOrg_Subscription_Status() {
	    return this.org_subscription_status;
	}
	
	public void setParty_ID(int party_Id) {
	    this.party_id = party_Id;
	}
	public int getParty_ID() {
	    return this.party_id;
	}
	
}

package com.buildman.buildman.model;

public class Login_Model {
	private int login_id;
	String email = null;
	String password = null;
	private int party_id;
	private int org_id;
	String org_name = null;	
	String login_status = null;
	String party_name = null;

	public String getRemember() {
		return Remember;
	}

	public void setRemember(String remember) {
		Remember = remember;
	}

	String Remember;
	
	public Login_Model(String mMy_Email, String mMy_Password, int mMy_Party_ID, String loginstatus) {
		// TODO Auto-generated constructor stub
		this.email=mMy_Email;
		this.password=mMy_Password;
		this.party_id=mMy_Party_ID;
		this.login_status=loginstatus;
		
	}
	public Login_Model(String mMy_Email, String mMy_Password, int mMy_Party_ID, int org_id, String party_name, String org_name) {
		// TODO Auto-generated constructor stub
		this.email=mMy_Email;
		this.password=mMy_Password;
		this.party_id=mMy_Party_ID;
		this.org_id=org_id;
		this.party_name=party_name;
		this.org_name=org_name;
		
	}
	public Login_Model(String mMy_Email, String mMy_Password, int mMy_Party_ID, int org_id, String party_name, String org_name,String remember) {
		// TODO Auto-generated constructor stub
		this.email=mMy_Email;
		this.password=mMy_Password;
		this.party_id=mMy_Party_ID;
		this.org_id=org_id;
		this.party_name=party_name;
		this.org_name=org_name;
		this.Remember=remember;

	}
	public Login_Model() {
		// TODO Auto-generated constructor stub
	}
	public void setLogin_Id(int mylogin_id) {
	    this.login_id = mylogin_id;
	}
	public int getLogin_ID() {
	    return this.login_id;
	}

	public void setEmail(String myemail) {
	    this.email = myemail;
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
	public void setParty_ID(int party_id) {
	    this.party_id = party_id;
	}

	public int getParty_ID() {
	    return this.party_id;
	}
	
	public void setOrg_ID(int org_id) {
	    this.org_id = org_id;
	}
	public int getOrg_ID() {
	    return this.org_id;
	}
	public void setOrg_Name(String org_name) {
	    this.org_name = org_name;
	}
	public String getOrg_Name() {
	    return this.org_name;
	}
	
	public void setLoginStatus(String login_status) {
	    this.login_status = login_status;
	}

	public String getLoginStatus() {
	    return this.login_status;
	}
	
	public void setParty_Name(String party_name) {
	    this.party_name = party_name;
	}
	public String getParty_Name() {
	    return this.party_name;
	}
	
	
}

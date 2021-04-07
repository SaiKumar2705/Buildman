package com.buildman.buildman.model;

public class LoginHistory_Model {
private int loginHistory_id;
String loginStatus=null;
String fromDate=null;
String toDate=null;
private int party_id;
private int org_id;

public void setLoginHistory_ID(int loginHistory_id){
	this.loginHistory_id=loginHistory_id;
}
public int getLoginHistory_ID(){
	return this.loginHistory_id ;
}

public void setLoginStatus(String loginStatus) {
    this.loginStatus = loginStatus;
}

public String getLoginStatus() {
    return this.loginStatus;
}
public void setFromDate(String fromdate) {
    this.fromDate = fromdate;
}

public String getFromDate() {
    return this.fromDate;
}
public void setToDate(String todate) {
    this.toDate = todate;
}

public String getToDate() {
    return this.toDate;
}
public void setParty_ID(int party_id){
	this.party_id=party_id;
}
public int getParty_ID(){
	return this.party_id ;
}
public void setOrg_ID(int org_id){
	this.org_id=org_id;
}
public int getOrg_ID(){
	return this.org_id ;
}
}

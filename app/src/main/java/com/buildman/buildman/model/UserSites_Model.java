package com.buildman.buildman.model;

public class UserSites_Model {
private int usersites_id;
private int user_site_link_id;
private int project_id;
String project_name;
private int site_id;
String site_name;
String site_location;
String access_status;
private int party_id;
private int org_id;
String user_type = null;	

public UserSites_Model(int project_id, String project_name,
		int mSite_Id, String mSite_name, int my_party_id, int my_org_id, String userType) {
	// TODO Auto-generated constructor stub
	this.project_id=project_id;
	this.project_name=project_name;
	this.site_id=mSite_Id;
	this.site_name=mSite_name;
	this.party_id=my_party_id;
	this.org_id=my_org_id;
	this.user_type=userType;
	
}
public UserSites_Model() {
	// TODO Auto-generated constructor stub
}
public UserSites_Model(String mSite_name, int mSite_Id) {
	// TODO Auto-generated constructor stub
	
	this.site_name=mSite_name;
	this.site_id=mSite_Id;
}
public void setUserSites_ID(int usersites_id){
	this.usersites_id=usersites_id;
}
public int getUserSites_ID(){
	return this.usersites_id;
}
public void setUser_Site_Link_ID(int user_site_link_id){
	this.user_site_link_id=user_site_link_id;
}
public int getUser_Site_Link_ID(){
	return this.user_site_link_id;
}
public void setProject_ID(int project_id) {
	this.project_id = project_id;
}

	public void deletesite_ID(int site_id) {
		this.site_id = site_id;
	}


public int getProject_ID(){
	return this.project_id;
}
	public int deletesite_ID(){
		return this.site_id;
	}
public void setProject_Name(String project_name){
	this.project_name=project_name;
}

public String getProject_Name(){
	return this.project_name;
}
public void setSite_ID(int site_id){
	this.site_id=site_id;
}
public int getSite_ID(){
	return this.site_id;
}
public void setSite_Name(String site_name){
	this.site_name=site_name;
}
public String getSite_Name(){
	return this.site_name;
}
public void setSite_Location(String site_location){
	this.site_location=site_location;
}
public String getSite_Location(){
	return this.site_location;
}
public void setAccess_Status(String access_status){
	this.access_status=access_status;
}
public String getAccess_Status(){
	return this.access_status;
}
public void setParty_ID(int party_id){
	this.party_id=party_id;
}
public int getParty_ID(){
	return this.party_id;
}
public void setOrg_ID(int org_id){
	this.org_id=org_id;
}
public int getOrg_ID(){
	return this.org_id;
}

public void setUserType(String mUsertype) {
    this.user_type = mUsertype;
}
public String getUserType() {
    return this.user_type;
}
}

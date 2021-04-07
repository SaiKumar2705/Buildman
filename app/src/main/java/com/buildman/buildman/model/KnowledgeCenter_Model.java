package com.buildman.buildman.model;

public class KnowledgeCenter_Model {
	private int mKnowledgeCenter_id;
	private int mFile_UploadMaster_id;
	private String mFile_Title;
	private int mFile_Type_id;
	private String mKeywords;
	private int mPurpose_id;	
	private String mMobile_Path;
	private int mSite_id;
	private String mDescription;
	private String mPortalPath;
	private String mCreated_Date;
	private String mUpdated_Date;
	private String mDownload_Flag;
	
	public KnowledgeCenter_Model(int mFileUploadMasterId_result,
			String mFileTittle, int mFileTypeId_result, String mKeywords2,
			int mPurposeId_result, String mobile_path, int mSite_id_result,
			String mDescription2,String mPortal_Path, String created_date,
			String updated_date, String downloaded_flag) {
		// TODO Auto-generated constructor stub
		this.mFile_UploadMaster_id=mFileUploadMasterId_result;
		this.mFile_Title=mFileTittle;
		this.mFile_Type_id=mFileTypeId_result;
		this.mKeywords=mKeywords2;
		this.mPurpose_id=mPurposeId_result;
		this.mMobile_Path=mobile_path;
		this.mSite_id=mSite_id_result;
		this.mDescription=mDescription2;
		this.mPortalPath=mPortal_Path;
		this.mCreated_Date=created_date;
		this.mUpdated_Date=updated_date;
		this.mDownload_Flag=downloaded_flag;
	}
	public KnowledgeCenter_Model() {
		// TODO Auto-generated constructor stub
	}
	public int getKnowledgeCenter_Id() {
        return mKnowledgeCenter_id;
    }
    public void setKnowledgeCenter_Id(int knowledgeCenter_id) {
        this.mKnowledgeCenter_id = knowledgeCenter_id;
    }
    
    public int getFile_UploadMaster_Id() {
        return mFile_UploadMaster_id;
    }
    public void setFile_UploadMaster_Id(int file_upload_master_id) {
        this.mFile_UploadMaster_id = file_upload_master_id;
    }
    
    public String getFile_Title() {
        return mFile_Title;
    } 
    public void setFile_Title(String file_Title) {
    	mFile_Title = file_Title;
    }
    
    public int getFileType_Id() {
        return mFile_Type_id;
    }
    public void setFileType_Id(int file_Type_id) {
        this.mFile_Type_id = file_Type_id;
    }
    
    public String getKeywords() {
        return mKeywords;
    } 
    public void setKeywords(String keywords) {
    	mKeywords = keywords;
    }
    
    public int getPurpose_Id() {
        return mPurpose_id;
    }
    public void setPurpose_Id(int purpose_id) {
        this.mPurpose_id = purpose_id;
    }
    
    public String getMobilePath() {
        return mMobile_Path;
    } 
    public void setMobilePath(String path) {
    	mMobile_Path = path;
    }
    
    public int getSite_Id() {
        return mSite_id;
    }
    public void setSite_Id(int site_id) {
        this.mSite_id = site_id;
    }
    
    public String getDescription() {
        return mDescription;
    } 
    public void setDescription(String description) {
    	mDescription = description;
    }
    
    public String getPortalPath() {
        return mPortalPath;
    } 
    public void setPortalPath(String portalPath) {
    	mPortalPath = portalPath;
    }
    
    public String getCreated_Date() {
        return mCreated_Date;
    } 
    public void setCreated_Date(String created_Date) {
    	mCreated_Date = created_Date;
    }
    
    public String getUpdated_Date() {
        return mUpdated_Date;
    } 
    public void setUpdated_Date(String updated_Date) {
    	mUpdated_Date = updated_Date;
    }
    
    public String getDownload_Flag() {
        return mDownload_Flag;
    } 
    public void setDownload_Flag(String download_Flag) {
    	mDownload_Flag = download_Flag;
    }
}

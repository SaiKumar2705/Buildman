package com.buildman.buildman.statustabs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.activity.Sample;
import com.buildman.buildman.adapter.ImageListAdapter;
import com.buildman.buildman.adapter.Images_Status_Description_Adapter;
import com.buildman.buildman.adapter.IssuesImagesAdpter;
import com.buildman.buildman.adapter.Issues_Status_Description_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;

public class Issues_Status_Description extends Activity {
	private TextView mIssues_Title_Txv,mPresentTask_Name_Txv,mIssues_Title_Dialog_Txv,mIssues_Details_Dialog_Txv;
	private ListView mListview;
	private ImageView mBackImg;
	GridView listViewDisImg;
	private Button mAdd_Btn,mCancel_btn,mConfirm_btn,mClose_btn,btnAddImg;
	private EditText mIssues_Title_Edt,mIssues_Details_Edt;
	private String mCurrent_DateTime_Now,task_name;
	private int work_master_id,selected_SiteID,org_id,party_id,project_id;
	DatabaseHandler db;
	ArrayList<Status_Model> issues_list=new ArrayList<Status_Model>();
	long val=0,imdval;
	List<String> tFileList;
	List<String> imgList;
	List<Status_Model> reverseView;


//
final int CAMERA_CAPTURE = 1;
	private Uri picUri;
	private List<String> listOfImagesPath;
	String GridViewDemo_ImagePath ;
//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issues_status_description);
        listOfImagesPath=new ArrayList<String>();
		tFileList = new ArrayList<String>();
			getIds();
			getData();
			getParameters_IDfmDB();
			current_Date_Time();
			getList_fromDB();
			backBtnClick();
			mAddBtn_Click();
	}
	@Override
	public void onBackPressed() {
		Intent i=new Intent(Issues_Status_Description.this,StatusTabs.class);
		i.putExtra("from_status", 2);
		startActivity(i);
		finish();
		return;
	}
	private void mAddBtn_Click() {
		// TODO Auto-generated method stub
		mAdd_Btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(v.getContext());
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.issues_status_dialog);
					listViewDisImg=(GridView) dialog.findViewById(R.id.listViewDisImg);
					mIssues_Title_Edt= (EditText) dialog.findViewById(R.id.issues_title_issues_status_descrp_dialog);
					mIssues_Details_Edt = (EditText) dialog.findViewById(R.id.issues_details_issues_status_descrp_dialog);
			    	mCancel_btn = (Button) dialog.findViewById(R.id.cancel_btn_issues_status_descrp_dialog);
			    	mConfirm_btn  = (Button) dialog.findViewById(R.id.confirm_btn_issues_status_descrp_dialog);
					GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/sent/";
					btnAddImg  = (Button) dialog.findViewById(R.id.btnAddImg);
					btnAddImg.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
                            selectImage();
						}
					});
			    	mCancel_btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
			    	mConfirm_btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(mIssues_Title_Edt.getText().length()==0)
								mIssues_Title_Edt.setError("Please fill the field");
					 		else if(mIssues_Details_Edt.getText().length()==0)
						 		mIssues_Details_Edt.setError("Please fill the field");
					 		else
							{
								String issues_title=mIssues_Title_Edt.getText().toString();
								String issues_details=mIssues_Details_Edt.getText().toString();
						  		val=db.add_Project_issues_Record(new Status_Model(project_id,selected_SiteID,work_master_id,task_name,issues_title,issues_details,"N",mCurrent_DateTime_Now,"Y"));
//						  		Toast.makeText(Issues_Status_Description.this,""+val,Toast.LENGTH_SHORT).show();
								if(val>0)
								{
									for(int i=0;i<listOfImagesPath.size();i++)
									{
										String imgpath=listOfImagesPath.get(i);
										imdval=db.add_Project_Issues_Images_Record(new Status_Model(String.valueOf(val),String.valueOf(project_id),String.valueOf(selected_SiteID),String.valueOf(work_master_id), imgpath, "N", mCurrent_DateTime_Now,"Y"));
									}
								}
								dialog.dismiss();
								finish();
								startActivity(getIntent());
							}
						}
					});
					dialog.show();
				}
		});
	}
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Issues_Status_Description.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
					try {
						Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(captureIntent, CAMERA_CAPTURE);
					} catch(ActivityNotFoundException anfe){
						String errorMessage = "Whoops - your device doesn't support capturing images!";
						Toast toast = Toast.makeText(Issues_Status_Description.this, errorMessage, Toast.LENGTH_SHORT);
						toast.show();
					}
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel"))
                    dialog.dismiss();
            }
        });
        builder.show();
    }

    private List<String> RetriveCapturedImagePath() {
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists())
		{
			File[] files=f.listFiles();
			Arrays.sort(files);
			File file = files[files.length-1];
			String imgpath=file.getPath();
			tFileList.add(imgpath);
		}
		return tFileList;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK)
		{
			if(requestCode == CAMERA_CAPTURE)
			{
				Bundle extras = data.getExtras();
				Bitmap thePic = extras.getParcelable("data");
				String imgcurTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
				File imageDirectory = new File(GridViewDemo_ImagePath);
				imageDirectory.mkdirs();
				String _path = GridViewDemo_ImagePath + imgcurTime+".jpg";
				try {
					FileOutputStream out = new FileOutputStream(_path);
					thePic.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.close();
				} catch (FileNotFoundException e) {
					e.getMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}
				listOfImagesPath = RetriveCapturedImagePath();
				if(listOfImagesPath!=null)
				{
					listViewDisImg.setAdapter(new ImageListAdapter(this,listOfImagesPath));
				}
			}
			else
            {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                try {
                    Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String picturePath = cursor.getString(columnIndex);
                    listOfImagesPath.add(picturePath);
                    if(listOfImagesPath!=null)
                    {
                        listViewDisImg.setAdapter(new ImageListAdapter(this,listOfImagesPath));
                    }
                    cursor.close();
                    Log.d("Picture Path", picturePath);
                }
                catch(Exception e) {
                    Log.e("Path Error", e.toString());
                }

            }

		}
	}
	private void backBtnClick() {
		// TODO Auto-generated method stub
		mBackImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Issues_Status_Description.this,StatusTabs.class);
					i.putExtra("from_status", 2);
					startActivity(i);
					finish();
						
				}
		});
			
			
	}
	private List reverseList(List myList) {
		List invertedList = new ArrayList();
		for (int i = myList.size() - 1; i >= 0; i--) {
			invertedList.add(myList.get(i));
		}
		return invertedList;
	}
	private void getList_fromDB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);
		// Reading all contacts
		Log.d("Reading: ", "Reading all contacts..");
		List<Status_Model> list = db.find_SiteID_WorkMasterId_ProjectIssuesList(selected_SiteID, work_master_id);
		System.out.println("list size with siteid"+list.size());
		LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_issues_status_description);
		if(list.size()==0)
			relative.setBackgroundResource(0);
		else{
			{
				relative.setBackgroundResource(R.drawable.box_bottom_border);
				reverseView=reverseList(list);
				System.out.println("list size with reverseList"+reverseView.size());
				for (Status_Model cn : reverseView) {
					int issue_id = cn.getProject_Issues_ID();
					Log.d("issue_id ", "issue_id " + issue_id);
					String issue_title = cn.getIssues_Title_ISSUES();
					String issue_creation_date = cn.getIssues_CreationDateTime_ISSUES();
					String issue_details = cn.getIssues_Details_ISSUES();
					Status_Model appadd = new Status_Model(issue_id, issue_creation_date, issue_title, issue_details);
					issues_list.add(appadd);
				}

			}

		}
//		for (Status_Model cn : list) {
//			int issue_id = cn.getProject_Issues_ID();
//			Log.d("issue_id ", "issue_id " + issue_id);
//			String issue_title = cn.getIssues_Title_ISSUES();
//			String issue_creation_date = cn.getIssues_CreationDateTime_ISSUES();
//			String issue_details = cn.getIssues_Details_ISSUES();
//			Status_Model appadd = new Status_Model(issue_id, issue_creation_date, issue_title, issue_details);
//			issues_list.add(appadd);
//		}
		Issues_Status_Description_Adapter adapter = new Issues_Status_Description_Adapter(Issues_Status_Description.this, R.layout.issues_status_description_row, issues_list);
		mListview.setAdapter(adapter);
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Status_Model status_model = (Status_Model) parent.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), Issuevew.class);
                intent.putExtra("ISSUE_TITLE", status_model.getIssues_Title_ISSUES());
                intent.putExtra("ISSUE_DETAIL", status_model.getIssues_Details_ISSUES());
                intent.putExtra("issueid", String.valueOf(status_model.getProject_Issues_ID()));
                startActivity(intent);
//				imgList = new ArrayList<String>();
//				Status_Model status_model = (Status_Model) parent.getItemAtPosition(position);
//				final Dialog dialog = new Dialog(view.getContext());
//				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				dialog.setContentView(R.layout.issues_status_description_dialog);
//				mIssues_Title_Dialog_Txv= (TextView) dialog.findViewById(R.id.issues_title_listview_issues_status_descrp_dialog);
//				mIssues_Details_Dialog_Txv = (TextView) dialog.findViewById(R.id.issues_details_listview_issues_status_descrp_dialog);
//			    mClose_btn = (Button) dialog.findViewById(R.id.close_btn_listview_issues_status_descrp_dialog);
//				listview_images_issues = (ListView) dialog.findViewById(R.id.listview_images_issues);
//			    mIssues_Title_Dialog_Txv.setText("ISSUE TITLE : "+status_model.getIssues_Title_ISSUES());
//			    mIssues_Details_Dialog_Txv.setText("ISSUE DETAIL : "+status_model.getIssues_Details_ISSUES());
//				int issueid=status_model.getProject_Issues_ID();
//
//				List<Status_Model> list = db.find_ProjectIssuesImgsList(issueid);
//				System.out.println("list size with issueId"+list.size());
//				if(list.size()==0)
//					listview_images_issues.setVisibility(View.GONE);
//				else
//				{
//					for (Status_Model cn : list) {
//
//						String imgpath = cn.getPATH_PRJ_ISSUES_IMAGES();
//						Toast.makeText(Issues_Status_Description.this, "imgpath : " + imgpath, Toast.LENGTH_SHORT).show();
//						imgList.add(imgpath);
//					}
//					if (imgList.size() > 0)
//						listview_images_issues.setAdapter(new ImageListAdapter(Issues_Status_Description.this, imgList));
//				}
//
//			    mClose_btn.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						dialog.dismiss();
//					}
//				});
//				dialog.show();
			}
		});
	}
	private void current_Date_Time() {
		// TODO Auto-generated method stub
		mCurrent_DateTime_Now=Utils.sdf.format(new Date());
	}
	private void getParameters_IDfmDB() {
		// TODO Auto-generated method stub
	 db=new DatabaseHandler(Issues_Status_Description.this);
	 SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
     selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
	 Login_Model login_model=db.get_FirstRow_Login(1);
     org_id=login_model.getOrg_ID();
	 party_id=login_model.getParty_ID();
	 UserSites_Model usersites_model=db.find_Site_ID_UserSitesTable(selected_SiteID);
	 project_id=usersites_model.getProject_ID();
	}
	private void getData() {
		// TODO Auto-generated method stub
		work_master_id=getIntent().getIntExtra("Send_mSubTaskID", 0); 	 	
		task_name=getIntent().getStringExtra("Send_mSubTaskName");
	    mPresentTask_Name_Txv.setText(task_name);
	}
	private void getIds() {
		// TODO Auto-generated method stub
		mIssues_Title_Txv=(TextView)findViewById(R.id.issues_title_issues_status_description);		
		mPresentTask_Name_Txv=(TextView)findViewById(R.id.task_name_header_issues_status_description);
	    mListview=(ListView)findViewById(R.id.listview_issues_status_description);
		mBackImg=(ImageView)findViewById(R.id.back_img_issues_status_description);
		mAdd_Btn=(Button)findViewById(R.id.add_btn_issues_status_description);
	}
}

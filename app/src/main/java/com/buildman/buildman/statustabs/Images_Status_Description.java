package com.buildman.buildman.statustabs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Images_Status_Description_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;

public class Images_Status_Description extends Activity {	
	private TextView mImages_Title_Txv,mPresentTask_Name_Txv;
	private ListView mListview;
	private ImageView mBackImg,mGallery_Img;
	private Button mCamera_Btn;
	private int work_master_id,selected_SiteID,org_id,party_id,project_id;
	private String mCurrent_DateTime_Now,task_name;
	int numberOfImages;
	String iname,selected_ImagePath;
	DatabaseHandler db;
	ArrayList<Integer> work_master_id_list=new ArrayList<Integer>();
	ArrayList<String>task_name_list =new ArrayList<String>();
	ArrayList<Status_Model> images_list=new ArrayList<Status_Model>();
	ArrayList<String>image_Path_List =new ArrayList<String>();
	private static final int PICK_FROM_GALLERY = 2;
    private static final int CAMERA_REQUEST = 1;
    int party_id_fmLoginDB;
	String userType_inDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images_status_description);
//			 creating methods to write code
		db = new DatabaseHandler(this);
		SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
		selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
		Login_Model login_model= db.get_FirstRow_Login(1);

		party_id_fmLoginDB=login_model.getParty_ID();
		UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
		if(model!=null)
			userType_inDB= model.getUserType();

		getIds();
		getData();
		getParameters_IDfmDB();
		current_Date_Time();
		getList_fromDB();
		backBtnClick();
		cameraBtn_Click();
		galleryBtn_Click();
			      
	}
	@Override
	public void onBackPressed() {
		// do something on back.
//		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		Intent i=new Intent(Images_Status_Description.this,StatusTabs.class);
		i.putExtra("from_status", 3);
		startActivity(i);
		finish();
		return;
	}
		 
	private void cameraBtn_Click() {
		// TODO Auto-generated method stub
		mCamera_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userType_inDB!=null&&userType_inDB.equalsIgnoreCase("PRIMRY"))
					callCamera();
				else
					Toast.makeText(getApplicationContext(),
	    	             					"User type is restricted", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void callCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// start the image capture Intent
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}
	private void galleryBtn_Click() {
		// TODO Auto-generated method stub
		mGallery_Img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(userType_inDB!=null&&userType_inDB.equalsIgnoreCase("PRIMRY"))
						callGallery();
					else
						Toast.makeText(getApplicationContext(),
								"User type is restricted", Toast.LENGTH_SHORT).show();


				}
		});
			
	}
	private void callGallery() {
		// TODO Auto-generated method stub
		Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_FROM_GALLERY);
//			 Intent intent = new Intent();
//			  intent.setType("image/*");
//			  intent.setAction(Intent.ACTION_GET_CONTENT);
//			  intent.putExtra("crop", "true");
//			  intent.putExtra("aspectX", 0);
//			  intent.putExtra("aspectY", 0);
//			  intent.putExtra("outputX", 200);
//			  intent.putExtra("outputY", 150);
//			  intent.putExtra("return-data", true);
//			  startActivityForResult(
//			    Intent.createChooser(intent, "Complete action using"),
//			    PICK_FROM_GALLERY);
					
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode)
		{
			case CAMERA_REQUEST:
				Bundle extras = data.getExtras();
	   	   		if (extras != null) {
	   	    	Bitmap yourImage = extras.getParcelable("data");
     	    	saveImage(yourImage);
	   	    	// Inserting Contacts
	   	    	Log.d("camera Pathissssss: ", selected_ImagePath);
	   	   		db.add_Project_Images_Record(new Status_Model(project_id, selected_SiteID,work_master_id,task_name,selected_ImagePath,"N",mCurrent_DateTime_Now));
	       	 	finish();
			 	startActivity(getIntent());

	   	   	}
	   	   	break;
	   	  	case PICK_FROM_GALLERY:
			   Uri selectedImage = data.getData();
			   String[] filePath = { MediaStore.Images.Media.DATA };
			   Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
			   c.moveToFirst();
			   int columnIndex = c.getColumnIndex(filePath[0]);
			   String picturePath = c.getString(columnIndex);
			   c.close();
			   if(picturePath!=null)
			   {
				   Bitmap yourImage = (BitmapFactory.decodeFile(picturePath));
				   saveImage(yourImage);
				   System.out.println("gallery Pathissssss: " + selected_ImagePath);
				   ByteArrayOutputStream stream = new ByteArrayOutputStream();
				   yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				   byte imageInByte[] = stream.toByteArray();
				   Log.e("output before", imageInByte.toString());
				   Log.d("Insert: ", "Inserting ..");
				   db.add_Project_Images_Record(new Status_Model(project_id, selected_SiteID, work_master_id, task_name, selected_ImagePath, "N", mCurrent_DateTime_Now));
				   finish();
				   startActivity(getIntent());
			   }
	   	   break;
		}
	}
	private void saveImage(Bitmap finalBitmap) {
		System.out.println("imageissss");
		String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
		System.out.println("image rootttt"+root);
		File myDir = new File(root + "/Buildman");
		if (!myDir.exists()) {
		    	myDir.mkdirs();
		}
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		iname = "Image-" + n + ".jpg";
		File file = new File(myDir, iname);
		if (file.exists())
			file.delete();
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			System.out.println("bitmap mapp");
			out.flush();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
			public void onScanCompleted(String path, Uri uri) {
				Log.i("ExternalStorage", "Scanned " + path + ":");
				Log.i("ExternalStorage", "-> uri=" + uri);
			}
		});
		selected_ImagePath=file.toString();
		System.out.println("image path ---"+selected_ImagePath);
		File[] files = myDir.listFiles();
		numberOfImages=files.length;
		System.out.println("Total images in Folder "+numberOfImages);
	}
	private void backBtnClick() {
		// TODO Auto-generated method stub
		mBackImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Images_Status_Description.this,StatusTabs.class);
				i.putExtra("from_status", 3);
				startActivity(i);
				finish();
			}
		});
	}
	private void getList_fromDB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);
		List<Status_Model> list = db.find_SiteID_WorkMasterId_ProjectImagesList(selected_SiteID, work_master_id);
		System.out.println("list size with siteid"+list.size());
		LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_images_status_description);
		if(list.size()==0){
			relative.setBackgroundResource(0);
		}else
		{
			relative.setBackgroundResource(R.drawable.box_bottom_border);
		}
		for (Status_Model cn : list)
		{
			String image_creation_date=cn.getImages_CreationDateTime_IMAGES();
			String path_of_images = cn.getPath_IMAGES();
//		    Status_Model appadd = new Status_Model(image_creation_date,images);
			images_list.add(cn);
			image_Path_List.add(path_of_images);
		          
		}
		Images_Status_Description_Adapter adapter = new Images_Status_Description_Adapter(Images_Status_Description.this, R.layout.images_status_description_row, images_list);
		mListview.setAdapter(adapter);
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Status_Model status_model = (Status_Model) parent.getItemAtPosition(position);
				String path_of_image = images_list.get(position).getPath_IMAGES();
				int imageId = images_list.get(position).getProject_Images_ID();
				File imgFile = new  File(path_of_image);
				if(imgFile.exists())
				{
					Bitmap theImage_myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
					Intent intent = new Intent(Images_Status_Description.this, Images_Status_Description_FullImage.class);
					intent.putExtra("imagename", theImage_myBitmap);
					intent.putExtra("imageid", imageId);
					int img_position=(int) parent.getItemIdAtPosition(position);
					intent.putExtra("position", img_position);
					intent.putStringArrayListExtra("image_Path_List_send", image_Path_List);
					startActivity(intent);
				}else
					Toast.makeText(getApplicationContext(), "No Image exists", Toast.LENGTH_SHORT).show();

			}
		});
	}
	private void current_Date_Time() {
		// TODO Auto-generated method stub
		mCurrent_DateTime_Now= Utils.sdf.format(new Date());
	}
	private void getParameters_IDfmDB() {
		// TODO Auto-generated method stub
		 db=new DatabaseHandler(Images_Status_Description.this);
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
//		    setting task_name to task_name field
		mPresentTask_Name_Txv.setText(task_name);
		Login_Model login_model= db.get_FirstRow_Login(1);

		party_id_fmLoginDB=login_model.getParty_ID();
		UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
		if(model!=null)
			userType_inDB= model.getUserType();
	}
	private void getIds() {
		// TODO Auto-generated method stub
		mImages_Title_Txv=(TextView)findViewById(R.id.images_title_images_status_description);
		mPresentTask_Name_Txv=(TextView)findViewById(R.id.task_name_header_images_status_description);
		mListview=(ListView)findViewById(R.id.listview_images_status_description);
		mBackImg=(ImageView)findViewById(R.id.back_img_images_status_description);
		mCamera_Btn=(Button)findViewById(R.id.cam_btn_images_status_description);
		mGallery_Img=(ImageView)findViewById(R.id.gallery_img_images_status_description);

	}
}
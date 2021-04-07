package com.buildman.buildman.statustabs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Images_Status_Description_FullImage_PagerAdapter;

public class Images_Status_Description_FullImage extends Activity {
	
	 ImageView imageDetail_Img,mBack_Img;
	 int imageId;
	 Bitmap theImage_bitmap;
	 private Images_Status_Description_FullImage_PagerAdapter adapter;
		private ViewPager viewPager;
	 ArrayList<String>image_Path_List =new ArrayList<String>();
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.images_status_description_fullimage);	
	  getIds();
	  getData();
	  back_Btn_click();
	 
	 }
	 

	private void back_Btn_click() {
		// TODO Auto-generated method stub
		  mBack_Img.setOnClickListener(new OnClickListener() {

			   @Override
			   public void onClick(View v) {
//				   Intent i=new Intent(Images_Status_Description_FullImage.this,Images_Status_Description.class);
//				   startActivity(i);	
				   finish();
			    	   }
			  });
	}

	private void getData() {
		// TODO Auto-generated method stub
		 /**
		   * getting intent data from search and previous screen
		   */
		  Intent intnt = getIntent();
		  theImage_bitmap = (Bitmap) intnt.getParcelableExtra("imagename");
		  imageId = intnt.getIntExtra("imageid", 20);
		  int position = intnt.getIntExtra("position", 0);
		  image_Path_List=getIntent().getStringArrayListExtra("image_Path_List_send"); 	
		  Log.d("Image ID:****", String.valueOf(imageId));
//		  imageDetail_Img.setImageBitmap(theImage_bitmap);
		  
		  adapter = new Images_Status_Description_FullImage_PagerAdapter(Images_Status_Description_FullImage.this,
				  image_Path_List);

			viewPager.setAdapter(adapter);

			// displaying selected image first
			viewPager.setCurrentItem(position);
		  
	}

	private void getIds() {
		// TODO Auto-generated method stub
//		 imageDetail_Img = (ImageView) findViewById(R.id.imageView_fullscreen);
	 mBack_Img=(ImageView)findViewById(R.id.back_img_fullscreen);
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	
	}



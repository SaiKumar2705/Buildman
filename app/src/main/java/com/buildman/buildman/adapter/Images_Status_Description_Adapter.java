package com.buildman.buildman.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.Status_Model;

public class Images_Status_Description_Adapter extends ArrayAdapter<Status_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Status_Model> images_list;
	    
public Images_Status_Description_Adapter(Context context, int textViewResourceId,
		ArrayList<Status_Model> buildman_list){


super(context, textViewResourceId, buildman_list);
this.context = context;
this.images_list=buildman_list;

}    

private class ViewHolder
{
TextView image_creation_date_txv;
ImageView img;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.images_status_description_row, null,true);

holder = new ViewHolder();
holder.image_creation_date_txv = (TextView) convertView.findViewById(R.id.images_creation_date_images_status_description_row);
holder.img = (ImageView) convertView.findViewById(R.id.thumbimg_images_status_description_row);

convertView.setTag(holder);
convertView.setTag(R.id.images_creation_date_images_status_description_row,     holder.image_creation_date_txv);
convertView.setTag(R.id.thumbimg_images_status_description_row,     holder.img);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Status_Model app = images_list.get(position);
System.out.println("craetion date"+app.getImages_CreationDateTime_IMAGES());

holder.image_creation_date_txv.setText( app.getImages_CreationDateTime_IMAGES());

String outImage_path=app.getPath_IMAGES();
System.out.println("image path isss"+outImage_path);
File imgFile = new  File(outImage_path);

if(imgFile.exists()){

    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    holder.img.setImageBitmap(myBitmap);
}else{
	Toast.makeText(context, "No Image Exists",Toast.LENGTH_SHORT).show();
	
}
  

		


////convert byte to bitmap take from Status_Model class
//ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage_byte);
//Bitmap theImage_bitmap = BitmapFactory.decodeStream(imageStream);


//if ( position % 2 == 0)
//{
//	convertView.setBackgroundResource(R.color.gray);
//}else{
//	convertView.setBackgroundResource(R.color.white);
//}
return convertView;
}


}
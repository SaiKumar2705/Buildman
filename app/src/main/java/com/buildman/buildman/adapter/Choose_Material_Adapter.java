package com.buildman.buildman.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.ChooseMaterial_Model;

public class Choose_Material_Adapter extends ArrayAdapter<ChooseMaterial_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<ChooseMaterial_Model> choose_material_list;
	    
public Choose_Material_Adapter(Context context, int textViewResourceId,
		ArrayList<ChooseMaterial_Model> buildman_list){


super(context, textViewResourceId, buildman_list);
this.context = context;
this.choose_material_list=buildman_list;

}    

private class ViewHolder
{
TextView material;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.choose_material_row, null,true);

holder = new ViewHolder();
holder.material = (TextView) convertView.findViewById(R.id.material_name_choose_material_row);

convertView.setTag(holder);
convertView.setTag(R.id.material_name_choose_material_row,     holder.material);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
ChooseMaterial_Model app = choose_material_list.get(position);


holder.material.setText( app.getMaterial_Name_CM());

convertView.setBackgroundResource(R.color.white);
//if ( position % 2 == 0)
//{
//	convertView.setBackgroundResource(R.color.gray);
//}else{
//	convertView.setBackgroundResource(R.color.white);
//}
return convertView;
}


}
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
import com.buildman.buildman.model.Received_Material_Model;

public class Received_Material_Adapter extends ArrayAdapter<Received_Material_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Received_Material_Model> material_list;
	    
public Received_Material_Adapter(Context context, int textViewResourceId,
		ArrayList<Received_Material_Model> buildman_list){


super(context, textViewResourceId, buildman_list);
this.context = context;
this.material_list=buildman_list;

}    

private class ViewHolder
{
TextView material,units,current_stock;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.received_material_row, null,true);

holder = new ViewHolder();
holder.material = (TextView) convertView.findViewById(R.id.material_received_material_row);
holder.units = (TextView) convertView.findViewById(R.id.units_received_material_row);
holder.current_stock = (TextView) convertView.findViewById(R.id.currentstock_received_material_row);

convertView.setTag(holder);
convertView.setTag(R.id.material_received_material_row,     holder.material);
convertView.setTag(R.id.units_received_material_row,     holder.units);
convertView.setTag(R.id.currentstock_received_material_row,     holder.current_stock);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Received_Material_Model app = material_list.get(position);

Log.e("bhuuuuuuuuuuu", ""+app.getUnits_MST());
holder.material.setText( app.getMaterial_Name_MST());

holder.units.setText(app.getUnits_MST());
holder.current_stock.setText(app.getCurrent_Stock_MST());
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
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

public class ViewStockHistoryDialog_Adapter extends ArrayAdapter<Received_Material_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Received_Material_Model> material_list;
	    
public ViewStockHistoryDialog_Adapter(Context context, int textViewResourceId,
		ArrayList<Received_Material_Model> buildman_list){


super(context, textViewResourceId, buildman_list);
this.context = context;
this.material_list=buildman_list;

}    

private class ViewHolder
{
TextView date,received,used,indent;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.view_stock_history_dialog_row, null,true);

holder = new ViewHolder();
holder.date = (TextView) convertView.findViewById(R.id.date_view_stock_history_dialog_row);
holder.received = (TextView) convertView.findViewById(R.id.received_view_stock_history_dialog_row);
holder.used = (TextView) convertView.findViewById(R.id.used_view_stock_history_dialog_row);
holder.indent = (TextView) convertView.findViewById(R.id.indent_view_stock_history_dialog_row);

convertView.setTag(holder);
convertView.setTag(R.id.date_view_stock_history_dialog_row,     holder.date);
convertView.setTag(R.id.received_view_stock_history_dialog_row,     holder.received);
convertView.setTag(R.id.used_view_stock_history_dialog_row,     holder.used);
convertView.setTag(R.id.indent_view_stock_history_dialog_row,     holder.indent);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Received_Material_Model app = material_list.get(position);


holder.date.setText( app.getTransaction_Date_MST());
holder.received.setText( app.getReceived_Stock_MST());
holder.used.setText(app.getUsed_MST());
holder.indent.setText(app.getIndent_MST());
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
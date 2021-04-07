package com.buildman.buildman.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.KnowledgeCenter_Model;

public class KnowledgeCenter_Adapter extends ArrayAdapter<KnowledgeCenter_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<KnowledgeCenter_Model> knowledgeCenter_list;
	  ArrayList<KnowledgeCenter_Model>  arraylist = null;
	    
public KnowledgeCenter_Adapter(Context context, int textViewResourceId,
		ArrayList<KnowledgeCenter_Model> knowledgeC_list){


super(context, textViewResourceId, knowledgeC_list);
this.context = context;
this.knowledgeCenter_list=knowledgeC_list;
this.arraylist = new ArrayList<KnowledgeCenter_Model>();
this.arraylist.addAll(knowledgeC_list);
}    

private class ViewHolder
{
TextView file_title_names;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.knowledgecenter_row, null,true);

holder = new ViewHolder();
holder.file_title_names = (TextView) convertView.findViewById(R.id.file_titlename_knowledgecenter_row);



convertView.setTag(holder);
convertView.setTag(R.id.file_titlename_knowledgecenter_row,     holder.file_title_names);


}
else
{
holder = (ViewHolder) convertView.getTag();
}
KnowledgeCenter_Model app = knowledgeCenter_list.get(position);

Log.e("bhuuuuuuuuuuu", ""+app.getFile_Title());
holder.file_title_names.setText( app.getFile_Title());


//if ( position % 2 == 0)
//{
//	convertView.setBackgroundResource(R.color.gray);
//}else{
//	convertView.setBackgroundResource(R.color.white);
//}
return convertView;
}
public void filter(String charText) {
	// TODO Auto-generated method stub
	charText = charText.toLowerCase(Locale.getDefault());
	knowledgeCenter_list.clear();
	if (charText.length() == 0) {
		knowledgeCenter_list.addAll(arraylist);
	} else {
		for (KnowledgeCenter_Model wp : arraylist) {
			if (wp.getFile_Title().toLowerCase(Locale.getDefault()).contains(charText)) {
				knowledgeCenter_list.add(wp);
			}else if (wp.getKeywords().toLowerCase(Locale.getDefault()).contains(charText)) {
				knowledgeCenter_list.add(wp);
			}
		}
	}
	notifyDataSetChanged();
}

}

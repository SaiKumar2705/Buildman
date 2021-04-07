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
import com.buildman.buildman.model.Status_Model;

public class Issues_Status_Description_Adapter extends ArrayAdapter<Status_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Status_Model> issues_list;
	    
public Issues_Status_Description_Adapter(Context context, int textViewResourceId,
		ArrayList<Status_Model> buildman_list){


super(context, textViewResourceId, buildman_list);
this.context = context;
this.issues_list=buildman_list;

}    

private class ViewHolder
{
TextView issue_creation_date,issues_title;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.issues_status_description_row, null,true);

holder = new ViewHolder();
holder.issue_creation_date = (TextView) convertView.findViewById(R.id.issue_creation_issue_status_description_row);
holder.issues_title = (TextView) convertView.findViewById(R.id.issue_title_issue_status_description_row);


convertView.setTag(holder);
convertView.setTag(R.id.issue_creation_issue_status_description_row,     holder.issue_creation_date);
convertView.setTag(R.id.issue_title_issue_status_description_row,     holder.issues_title);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Status_Model app = issues_list.get(position);

Log.e("bhuuuuuuuuuuu", ""+app.getIssues_CreationDateTime_ISSUES());
holder.issue_creation_date.setText( app.getIssues_CreationDateTime_ISSUES());

holder.issues_title.setText(app.getIssues_Title_ISSUES());

//if ( position % 2 == 0)
//{
//	convertView.setBackgroundResource(R.color.gray);
//}else{
//	convertView.setBackgroundResource(R.color.white);
//}
return convertView;
}


}
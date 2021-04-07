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
import com.buildman.buildman.model.UserSites_Model;

public class UserSites_Adapter extends ArrayAdapter<UserSites_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<UserSites_Model> usersites_list;
	  ArrayList<UserSites_Model>  arraylist = null;
public UserSites_Adapter(Context context, int textViewResourceId,
		ArrayList<UserSites_Model> sites_list){


super(context, textViewResourceId, sites_list);
this.context = context;
this.usersites_list = sites_list;
this.arraylist = new ArrayList<UserSites_Model>();
this.arraylist.addAll(sites_list);
}    

private class ViewHolder
{
TextView site_names;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.user_sites_row, null,true);

holder = new ViewHolder();
holder.site_names = (TextView) convertView.findViewById(R.id.sites_names_usersites_row);



convertView.setTag(holder);
convertView.setTag(R.id.sites_names_usersites_row,     holder.site_names);


}
else
{
holder = (ViewHolder) convertView.getTag();
}
UserSites_Model app = usersites_list.get(position);

Log.e("bhuuuuuuuuuuu", ""+app.getSite_Name());
holder.site_names.setText( app.getSite_Name());




/*if ( position % 2 == 0)
{
	convertView.setBackgroundResource(R.color.gray);
}else{
	convertView.setBackgroundResource(R.color.white);
}*/
return convertView;
}
public void filter(String charText) {
		// TODO Auto-generated method stub
		charText = charText.toLowerCase(Locale.getDefault());
		usersites_list.clear();
		if (charText.length() == 0) {
			usersites_list.addAll(arraylist);
		} else {
			for (UserSites_Model wp : arraylist) {
				if (wp.getSite_Name().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					usersites_list.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}

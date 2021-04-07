package com.buildman.buildman.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.LastSyncReport_Child_Model;
import com.buildman.buildman.model.LastSyncReport_Parent_Model;

public class LastSynchronizationReport_Expandable_Adapter extends BaseExpandableListAdapter {
 
 
    private LayoutInflater inflater;
    private ArrayList<LastSyncReport_Parent_Model> mParent;
   
    Context context;
    public LastSynchronizationReport_Expandable_Adapter(Context context, ArrayList<LastSyncReport_Parent_Model> parent){
    	this.context = context;
    	mParent = parent;
    	
        inflater = LayoutInflater.from(context);
    }
 
 
    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }
 
    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
    	 ArrayList<LastSyncReport_Child_Model> chList = mParent.get(i).getArrayChildren();
        return chList.size();
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getSite_Name_Parent_LastSynReport();
    }
 
    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
//    	 ArrayList<StatusReport_Child_Model> chList = mParent.get(groupPosition).getItems();
//         return chList.get(childPosition);
    	
        return mParent.get(i).getArrayChildren().get(i1);
    }
 
    @Override
    public long getGroupId(int i) {
        return i;
    }
 
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
 
    @Override
    public boolean hasStableIds() {
        return true;
    }
 
    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(final int groupPosition, boolean b, View view, ViewGroup viewGroup) {
 
        ViewHolder holder = new ViewHolder();
        holder.groupPosition = groupPosition;
 
        if (view == null) {
            view = inflater.inflate(R.layout.lastsynchronizationreport_parent_row, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.site_name_lastsynchreport_parent_row);
        textView.setText(getGroup(groupPosition).toString());
        
        TextView textView_symbol = (TextView) view.findViewById(R.id.lastsynreport_symbol_lastsynchreport_parent_row);
        int identifier_value= mParent.get(groupPosition).getIdentifier_Parent_LastSynReport();
        if(identifier_value==1){
        	textView_symbol.setText("!");
        }else if (identifier_value==0) {
        	textView_symbol.setText("");
		}
        System.out.println("identifier_value"+mParent.get(groupPosition).getIdentifier_Parent_LastSynReport());
        ImageView call_img = (ImageView) view.findViewById(R.id.call_img_lastsynchreport_child_row);
        System.out.println("mobilwwwwww"+mParent.get(groupPosition).getMobileNumber_Parent_LastSynReport());
        call_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				Intent callIntent = new Intent(Intent.ACTION_CALL);  
                callIntent.setData(Uri.parse("tel:"+(mParent.get(groupPosition).getMobileNumber_Parent_LastSynReport())));  
                context.startActivity(callIntent);  
			}
		});
 
        view.setTag(holder);
 
        //return the entire view
        return view;
    }
 
    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
 
        ViewHolder holder = new ViewHolder();
        holder.childPosition = childPosition;
        holder.groupPosition = groupPosition;
        LastSyncReport_Child_Model child = (LastSyncReport_Child_Model) getChild(groupPosition, childPosition);
        if (view == null) {
            view = inflater.inflate(R.layout.lastsynchronizationreport_child_row, viewGroup,false);
        }
 
        TextView moduleName_textView = (TextView) view.findViewById(R.id.module_name_lastsynchreport_child_row);
        TextView number_Of_days_txv = (TextView) view.findViewById(R.id.syndate_lastsynchreport_child_row);
       
//        textView.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition));
 
        moduleName_textView.setText(child.getModule_Name_Child_LastSynReport().toString());
        number_Of_days_txv.setText(String.valueOf(child.getNumberOfDays_Child_LastSynReport()+" days ago"));
       
        
        view.setTag(holder);
 
        //return the entire view
        return view;
    }
 
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
 
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }
 
// Intentionally put on comment, if you need on click deactivate it
/*  @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder)view.getTag();
        if (view.getId() == holder.button.getId()){
 
           // DO YOUR ACTION
        }
    }*/
 
 
    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected Button button;
    }
    
   
}
package com.buildman.buildman.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;

public class Issues_Status_Expandable_Adapter extends BaseExpandableListAdapter {
 
	Context context;
    private LayoutInflater inflater;
    private ArrayList<Status_Model> mParent;
    DatabaseHandler db; 
    public Issues_Status_Expandable_Adapter(Context context, ArrayList<Status_Model> parent){
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
    	 ArrayList<Status_Child_Model> chList = mParent.get(i).getArrayChildren();
        return chList.size();
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getMain_Task_Name();
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
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
 
        ViewHolder holder = new ViewHolder();
        holder.groupPosition = groupPosition;
 
        if (view == null) {
            view = inflater.inflate(R.layout.issues_status_row, viewGroup,false);
        } 
               
        TextView mMain_task_name_Txv = (TextView) view.findViewById(R.id.taskname_issues_status_row);        
         mMain_task_name_Txv.setText(getGroup(groupPosition).toString());
        
        TextView start_date_Txv = (TextView) view.findViewById(R.id.startdate_issues_status_row);
        start_date_Txv.setText(mParent.get(groupPosition).getStart_Date());
        
        TextView qty_completion_Txv = (TextView) view.findViewById(R.id.qty_issues_status_row);
        String qty_completion=String.format("%.2f", Float.parseFloat(mParent.get(groupPosition).getSum_Entered_Qty()));
        qty_completion_Txv.setText(qty_completion);
        
        TextView units_Txv = (TextView) view.findViewById(R.id.units_issues_status_row);
        units_Txv.setText(mParent.get(groupPosition).getPRJ_EST_Work_TOT_QTY_Units());    
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
        Status_Child_Model child = (Status_Child_Model) getChild(groupPosition, childPosition);
        if (view == null) {
            view = inflater.inflate(R.layout.issues_status_child_row, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.sub_task_name_issues_status_child_row);
        TextView issue_symbol_txv = (TextView) view.findViewById(R.id.issues_symbol_issues_status_child_row);
        TextView balc_qty_txv = (TextView) view.findViewById(R.id.balance_qty_issues_status_child_row);
        TextView completed_qty_txv = (TextView) view.findViewById(R.id.qtycompleted_issues_status_child_row);
        TextView total_qty_txv = (TextView) view.findViewById(R.id.totalqty_issues_status_child_row);
        
 
        textView.setText(child.getPRJ_EST_WorkLocation_Name().toString());       
        String completed_qty=String.format("%.2f", Float.parseFloat(child.getPRJ_EST_Work_COM_QTY_Child().toString()));
        completed_qty_txv.setText(completed_qty);
        
        String total_qty=String.format("%.2f", Float.parseFloat(child.getPRJ_EST_Work_TOT_QTY_Child().toString()));	       
        total_qty_txv.setText(total_qty);
//        calculating balc_qty
        float balance_qty_float=((Float.parseFloat(child.getPRJ_EST_Work_TOT_QTY_Child().toString()))-
        							(Float.parseFloat(child.getPRJ_EST_Work_COM_QTY_Child().toString())));
		String balance_qty_result=String.format("%.2f", balance_qty_float);
        balc_qty_txv.setText(balance_qty_result);   
//        setting issues symbol       
        db = new DatabaseHandler(context);      
        List<Status_Model> list = db.find_SiteID_WorkMasterId_ProjectIssuesList(child.getSite_ID_Child(),
        		child.getPRJ_EST_WorkMaster_ID_Child());  
        if(list.size()==0){
        	
        	issue_symbol_txv.setText("");
        }else {
        	issue_symbol_txv.setText("!");
        }
    
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
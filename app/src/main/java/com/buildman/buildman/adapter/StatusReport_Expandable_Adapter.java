package com.buildman.buildman.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.StatusReport_Child_Model;
import com.buildman.buildman.model.StatusReport_Parent_Model;

public class StatusReport_Expandable_Adapter extends BaseExpandableListAdapter {
 
 
    private LayoutInflater inflater;
    private ArrayList<StatusReport_Parent_Model> mParent;
    ArrayList<StatusReport_Parent_Model>  arraylist = null;
    Context context;
    public StatusReport_Expandable_Adapter(Context context, ArrayList<StatusReport_Parent_Model> parent){
    	this.context = context;
        mParent = parent;
        this.arraylist = new ArrayList<StatusReport_Parent_Model>();
        this.arraylist.addAll(parent);
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
    	 ArrayList<StatusReport_Child_Model> chList = mParent.get(i).getArrayChildren();
        return chList.size();
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getTitle_SiteName();
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
            view = inflater.inflate(R.layout.status_report_parent_row, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.task_name_status_report_parent_row);
        textView.setText(getGroup(groupPosition).toString());
        
        TextView textView_total_billable_amt = (TextView) view.findViewById(R.id.sum_total_billable_amt_status_report_parent_row);
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
	    String mTotal_Billable_Amt_Rupees = formatter.format(Float.parseFloat(mParent.get(groupPosition).getSum_Total_BillableAmt()));
        textView_total_billable_amt.setText(mTotal_Billable_Amt_Rupees);
        System.out.println("summmmmmbillable_amt"+mParent.get(groupPosition).getSum_Total_BillableAmt());
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
        StatusReport_Child_Model child = (StatusReport_Child_Model) getChild(groupPosition, childPosition);
        if (view == null) {
            view = inflater.inflate(R.layout.status_report_child_row, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.task_name_status_report_child_row);
        TextView balc_qty_txv = (TextView) view.findViewById(R.id.balance_qty_status_report_child_row);
        TextView completed_qty_txv = (TextView) view.findViewById(R.id.qtycompleted_status_report_child_row);
        TextView total_qty_txv = (TextView) view.findViewById(R.id.totalqty_status_report_child_row);
        TextView billable_amt = (TextView) view.findViewById(R.id.billable_amt_status_report_child_row);
//        TextView units_completed_qty_txv = (TextView) view.findViewById(R.id.units_qtycompleted_status_report_child_row);

 
        textView.setText(child.getTaskName_Child().toString());
        balc_qty_txv.setText(child.getBalance_Qty_Child().toString());
        completed_qty_txv.setText(child.getCompleted_Qty_Child().toString()+" ("+child.getPercentage_WorkCompleted_Qty_Child().toString()+"%)");
        total_qty_txv.setText(child.getTotal_Qty_Child().toString());
        
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
	    String mBillable_Amt_Child_Rupees = formatter.format(Float.parseFloat(child.getBillable_Amt_Child().toString()));
        billable_amt.setText("Billable Amount: "+mBillable_Amt_Child_Rupees);
//        units_completed_qty_txv.setText(child.getUnits_Child().toString());
        
        
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
    public void filter(String charText) {
  		// TODO Auto-generated method stub
  		charText = charText.toLowerCase(Locale.getDefault());
  		mParent.clear();
  		if (charText.length() == 0) {
  			mParent.addAll(arraylist);
  		} else {
  			for (StatusReport_Parent_Model wp : arraylist) {
  				if (wp.getTitle_SiteName().toLowerCase(Locale.getDefault())
  						.contains(charText)) {
  					mParent.add(wp);
  				}
  			}
  		}
  		notifyDataSetChanged();
  	}
}
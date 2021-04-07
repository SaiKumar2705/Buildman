package com.buildman.buildman.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.utils.Utils;

public class Status_Status_Expandable_Adapter extends BaseExpandableListAdapter {
 
 
    private LayoutInflater inflater;
    private ArrayList<Status_Model> mParent;
    Date  date;
    String today;
    DateFormat dateFormat;
    public Status_Status_Expandable_Adapter(Context context, ArrayList<Status_Model> parent){
        mParent = parent;
        inflater = LayoutInflater.from(context);
        date=new Date();
        today= Utils.sdf.format(new Date());
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
            view = inflater.inflate(R.layout.status_status_row, viewGroup,false);
        } 
               
        TextView mMain_task_name_Txv = (TextView) view.findViewById(R.id.taskname_status_status_row);
        System.out.println("main task nameeeee"+getGroup(groupPosition).toString()+","+mParent.get(groupPosition).getMain_Task_Name());
         mMain_task_name_Txv.setText(getGroup(groupPosition).toString());
        
        TextView start_date_Txv = (TextView) view.findViewById(R.id.startdate_status_status_row);
        holder.img = (ImageView) view.findViewById(R.id.imageViewclock);
        String enddate=mParent.get(groupPosition).getPRJ_Work_EST_End_Date();
        try {
            Date date1=Utils.sdf.parse(enddate);
            Date date2=Utils.sdf.parse(today);
            if(date2.compareTo(date1)>0)
            {
               if(Float.parseFloat(mParent.get(groupPosition).getSum_Entered_Qty())<Float.parseFloat(mParent.get(groupPosition).getPRJ_EST_Work_TOT_QTY()))
                   holder.img.setImageResource(R.drawable.delay);
               else
                   holder.img.setImageResource(R.drawable.cfinish);
            }
            else
            {
                if(Float.parseFloat(mParent.get(groupPosition).getSum_Entered_Qty())==Float.parseFloat(mParent.get(groupPosition).getPRJ_EST_Work_TOT_QTY()))
                    holder.img.setImageResource(R.drawable.cfinish);
                else
                    holder.img.setVisibility(View.GONE);
            }
        } catch (Exception ex) {

        }
        start_date_Txv.setText(mParent.get(groupPosition).getStart_Date());
        TextView qty_completion_Txv = (TextView) view.findViewById(R.id.qty_status_status_row);    
        String qty_completion=String.format("%.2f", Float.parseFloat(mParent.get(groupPosition).getSum_Entered_Qty()));
        qty_completion_Txv.setText(qty_completion);
        TextView units_Txv = (TextView) view.findViewById(R.id.units_status_status_row);
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
            view = inflater.inflate(R.layout.status_status_child_row, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.sub_task_name_status_status_child_row);
        TextView balc_qty_txv = (TextView) view.findViewById(R.id.balance_qty_status_status_child_row);
        TextView completed_qty_txv = (TextView) view.findViewById(R.id.qtycompleted_status_status_child_row);
        TextView total_qty_txv = (TextView) view.findViewById(R.id.totalqty_status_status_child_row);
        
 
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
        ImageView img;
    }
}
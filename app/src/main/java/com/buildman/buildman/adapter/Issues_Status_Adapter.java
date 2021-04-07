package com.buildman.buildman.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;

import java.util.ArrayList;
import java.util.List;

public class Issues_Status_Adapter extends BaseAdapter {
    Context mContext;
    List<Status_Child_Model> listIssuesIR =new ArrayList<Status_Child_Model>();
    Status_Child_Model rowItem;
    Status_Child_Model product;
    DatabaseHandler db;

    public Issues_Status_Adapter(Context context, List<Status_Child_Model> listbean) {
        this.mContext = context;
        this.listIssuesIR = listbean;
        Log.d("listitems", "" + listIssuesIR);

    }
    @Override
    public int getCount() {
        return listIssuesIR.size();
    }

    @Override
    public Object getItem(int position) {
        return listIssuesIR.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MyViewHolder mViewHolder;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            mViewHolder = new MyViewHolder();
            rowItem = (Status_Child_Model) getItem(position);
            convertView = mInflater.inflate(R.layout.issues_status_child_row, null);
            mViewHolder.sub_task             = (TextView) convertView.findViewById(R.id.sub_task_name_issues_status_child_row);
            mViewHolder.issues_symbol             = (TextView) convertView.findViewById(R.id.issues_symbol_issues_status_child_row);
            mViewHolder.balance_qty             = (TextView) convertView.findViewById(R.id.balance_qty_issues_status_child_row);
            mViewHolder.qtycompleted             = (TextView) convertView.findViewById(R.id.qtycompleted_issues_status_child_row);
            mViewHolder.totalqty            = (TextView) convertView.findViewById(R.id.totalqty_issues_status_child_row);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        product = listIssuesIR.get(position);

        mViewHolder.sub_task.setText(product.getPRJ_EST_WorkLocation_Name().toString());


        float balance_qty_float=((Float.parseFloat(product.getPRJ_EST_Work_TOT_QTY_Child().toString()))-
                (Float.parseFloat(product.getPRJ_EST_Work_COM_QTY_Child().toString())));
        String balance_qty_result=String.format("%.2f", balance_qty_float);
        mViewHolder.balance_qty.setText(balance_qty_result+" "+product.getPRJ_EST_Work_TOT_QTY_Units_Child());


        String completed_qty=String.format("%.2f", Float.parseFloat(product.getPRJ_EST_Work_COM_QTY_Child().toString()));
        mViewHolder.qtycompleted.setText(completed_qty+" "+product.getPRJ_EST_Work_TOT_QTY_Units_Child());

        String total_qty=String.format("%.2f", Float.parseFloat(product.getPRJ_EST_Work_TOT_QTY_Child().toString()));
        mViewHolder.totalqty.setText(total_qty+" "+product.getPRJ_EST_Work_TOT_QTY_Units_Child());

        db = new DatabaseHandler(mContext);
        List<Status_Model> list = db.find_SiteID_WorkMasterId_ProjectIssuesList(product.getSite_ID_Child(),
                product.getPRJ_EST_WorkMaster_ID_Child());
        if(list.size()==0){

            mViewHolder.issues_symbol.setText("");
        }else {
            mViewHolder.issues_symbol.setText("!");
        }




        return convertView;
    }
    private class MyViewHolder {
        TextView sub_task,issues_symbol,balance_qty,qtycompleted,totalqty;

    }
}



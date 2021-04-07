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
import com.buildman.buildman.model.Status_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 26-10-2017.
 */



public class IssuesImagesAdpter extends BaseAdapter {
        Context mContext;
        List<Status_Model> listPatients =new ArrayList<Status_Model>();
        MyViewHolder viewHolder;
        Status_Model rowItem;
        Status_Model product;
        public IssuesImagesAdpter(Context context, List<Status_Model> listbean) {
            this.mContext = context;
            this.listPatients = listbean;
            Log.d("listitems", "" + listPatients);

        }
        @Override
        public int getCount() {
            return listPatients.size();
        }

        @Override
        public Object getItem(int position) {
            return listPatients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder mViewHolder;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {

                viewHolder = new MyViewHolder();
                rowItem = (Status_Model) getItem(position);
                convertView = mInflater.inflate(R.layout.issuess_images_status_description_row, null);
                viewHolder.txtMrno = (TextView) convertView.findViewById(R.id.issuess_images_creation_date_images_status_description_row);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.issue_images_status_description_row);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            product = listPatients.get(position);
//            viewHolder.txtMrno.setText(product.g);

            return convertView;
        }
        private class MyViewHolder {

            TextView txtMrno;
            ImageView imageView;

        }
    }



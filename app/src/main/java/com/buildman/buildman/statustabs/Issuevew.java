package com.buildman.buildman.statustabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.ImageListAdapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Status_Model;

import java.util.ArrayList;
import java.util.List;

public class Issuevew extends Activity {
    TextView mIssues_Title_Dialog_Txv,mIssues_Details_Dialog_Txv;
//    Button mClose_btn;
    GridView listview_images_issues;
    List<String> imgList;
    List<Status_Model> list;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issuevew);
        db = new DatabaseHandler(this);
        mIssues_Title_Dialog_Txv= (TextView)findViewById(R.id.issues_title_listview_issues_status_descrp_dialog);
        mIssues_Details_Dialog_Txv = (TextView)findViewById(R.id.issues_details_listview_issues_status_descrp_dialog);
//        mClose_btn = (Button)findViewById(R.id.close_btn_listview_issues_status_descrp_dialog);
        listview_images_issues = (GridView)findViewById(R.id.listview_images_issues);
        String ISSUE_TITLE = getIntent().getStringExtra("ISSUE_TITLE");
        String ISSUE_DETAIL = getIntent().getStringExtra("ISSUE_DETAIL");
        String s = getIntent().getStringExtra("issueid");
        mIssues_Title_Dialog_Txv.setText("ISSUE TITLE : "+ISSUE_TITLE);
        mIssues_Details_Dialog_Txv.setText("ISSUE DETAIL : "+ISSUE_DETAIL);
        int issueid=Integer.parseInt(s);
        imgList = new ArrayList<String>();
        list = db.find_ProjectIssuesImgsList(issueid);
        System.out.println("list size with issueId"+list.size());
        if(list.size()==0)
            listview_images_issues.setVisibility(View.GONE);
        else
        {
            for (Status_Model cn : list) {

                String imgpath = cn.getPATH_PRJ_ISSUES_IMAGES();
//                Toast.makeText(this, "imgpath : " + imgpath, Toast.LENGTH_SHORT).show();
                imgList.add(imgpath);
            }
            if (imgList.size() > 0)
                listview_images_issues.setAdapter(new ImageListAdapter(this, imgList));
        }

        listview_images_issues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
              String val=imgList.get(position);
                Intent intent = new Intent(Issuevew.this, FullScreenImageActivity.class);
                intent.putExtra("img", val);
                startActivity(intent);

            }
        });
    }
}

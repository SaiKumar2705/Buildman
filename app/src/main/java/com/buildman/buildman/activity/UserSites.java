package com.buildman.buildman.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.adapter.UserSites_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.knowledgecenter.KnowledgeCenter;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserSites extends Activity {
    private ListView mListview;
    UserSites_Adapter adapter;
    private ImageView mKnowledge_Center_Img, mAnalytics_Img, mSearch_Icon_Img;
    private TextView mKnowledge_Center_Txv, mAnalytics_Txv, mVersion_txv;
    private EditText mSearch_Edt;
    DatabaseHandler db;
    private int party_id_fmLoginDB, org_id_fmLoginDB;
    private static String url_GetNewSites;
    private ProgressDialog pDialog_newSites;
    private ArrayList<UserSites_Model> sites_list = new ArrayList<UserSites_Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersites);
        android.app.ActionBar bar = getActionBar();
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
//		      creating methods to write code
        getIds();
        get_IdsFm_LoginTable();
        serach_Image_Click();
        search_Functionality();
        getUser_SitesList_Fm_DB();
        mKnowledgeCenter_Click();
        mAnalytics_Img_Click();
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Toast.makeText(getApplicationContext(), "You are going to logout", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(UserSites.this, Login.class);
        startActivity(i);
        finish();
        return;
    }

    private void mAnalytics_Img_Click() {
        // TODO Auto-generated method stub
        mAnalytics_Img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), "This functionality is restricted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mKnowledgeCenter_Click() {
        // TODO Auto-generated method stub
        mKnowledge_Center_Img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(UserSites.this, KnowledgeCenter.class);
                i.putExtra("send_SiteId_Knowledger", "siteId");
                startActivity(i);
                finish();
            }
        });
        mKnowledge_Center_Txv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(UserSites.this, KnowledgeCenter.class);
                i.putExtra("send_SiteId_Knowledger", "siteId");
                startActivity(i);
                finish();
            }
        });
    }

    private void get_IdsFm_LoginTable() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(UserSites.this);
        Login_Model login_model = db.get_FirstRow_Login(1);
        party_id_fmLoginDB = login_model.getParty_ID();
        org_id_fmLoginDB = login_model.getOrg_ID();
    }

    private void serach_Image_Click() {
        // TODO Auto-generated method stub
        mSearch_Icon_Img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.search_layout_header_usersites);
                rl1.setVisibility(View.VISIBLE);
            }
        });
    }

    private void search_Functionality() {
        // TODO Auto-generated method stub
        //adding list to edittext for searchfunctionality
        mSearch_Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (mSearch_Edt.getText().length() == 0) {

                } else if (mSearch_Edt.getText().toString() != null) {
                    String text = mSearch_Edt.getText().toString().toLowerCase(Locale.getDefault());
                    System.out.println("search edittext box value-" + text);
                    adapter.filter(text);
                }

            }
        });
    }

    private void getUser_SitesList_Fm_DB() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(this);
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<UserSites_Model> list = db.getAll_UserSitesList();

        for (UserSites_Model cn : list) {

            String site_names = cn.getSite_Name();
            System.out.println("site names" + site_names);
            int site_Ids = cn.getSite_ID();
            int org_id = cn.getOrg_ID();
            UserSites_Model appadd = new UserSites_Model(site_names, site_Ids);
            sites_list.add(appadd);

        }
        adapter = new
                UserSites_Adapter(UserSites.this, R.layout.user_sites_row, sites_list);

        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                UserSites_Model usersites_model = (UserSites_Model) parent.getItemAtPosition(position);

                Intent i = new Intent(UserSites.this, Home.class);
                int selected_siteId = usersites_model.getSite_ID();
                String selected_Site_Name = usersites_model.getSite_Name();
                //passing site_id to material tabs class, Received_Material class
                SharedPreferences selected_SiteId_UserSite_Pref = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor selected_SiteId_UserSite_edit = selected_SiteId_UserSite_Pref.edit();
                selected_SiteId_UserSite_edit.putInt("selected_SiteId_UserSite_KEY_PREF", selected_siteId);
                selected_SiteId_UserSite_edit.commit();
//                  passing site_name to material tabs
                SharedPreferences selected_Site_Name_Pref = getSharedPreferences("Selected_SiteName_UserSite_MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor selected_Site_Name_edit = selected_Site_Name_Pref.edit();
                selected_Site_Name_edit.putString("Selected_SiteName_UserSite_KEY_PREF", selected_Site_Name);
                selected_Site_Name_edit.commit();
                startActivity(i);
                finish();

            }
        });
    }

    private void getIds() {
        // TODO Auto-generated method stub
//			getting textview Ids fm Xml
        mSearch_Icon_Img = (ImageView) findViewById(R.id.search_icon_img_usersites);
        mSearch_Edt = (EditText) findViewById(R.id.search_edittext_site_names_usersites);
        mListview = (ListView) findViewById(R.id.listview_usersites);
        mKnowledge_Center_Img = (ImageView) findViewById(R.id.knowledge_center_img_usersites);
        mKnowledge_Center_Txv = (TextView) findViewById(R.id.knowledge_center_txv_usersites);
        mAnalytics_Img = (ImageView) findViewById(R.id.analytics_img_usersites);
        mVersion_txv = (TextView) findViewById(R.id.version_mainactivity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.usersites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.syn_usersitesmenu:
                isInternetOn();
                break;

        }

        return true;
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet


            new GetNewSites().execute();


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, "Please connect to internet for Synchornization", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    private class GetNewSites extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_newSites = new ProgressDialog(UserSites.this);
            pDialog_newSites.setMessage("Please Wait.");
            pDialog_newSites.setCancelable(false);
            pDialog_newSites.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(UserSites.this);


            String org_id_string = String.valueOf(org_id_fmLoginDB);
            String party_id_string = String.valueOf(party_id_fmLoginDB);
            String module_id_string = String.valueOf(0);
            String mMy_Version = mVersion_txv.getText().toString();

            StringBuffer newSites_sb = new StringBuffer();

            newSites_sb.append(Utils.URL + "GetNewProjectDetails" + "?" + "PartyId=" + party_id_string + "&OrgId=" + org_id_string + "&MobModuleId=" + module_id_string + "&versionid=" + mMy_Version);
            System.out.print("party id is" + party_id_string + "org id" + org_id_string);

            url_GetNewSites = newSites_sb.toString();
            System.out.print("send to server" + url_GetNewSites);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_GetNewSites);
            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0)
                    {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONArray jArray1 = jArray.getJSONArray(i);
                            String project_id = jArray1.getString(0);
                            String project_name = jArray1.getString(1);
                            String site_id = jArray1.getString(2);
                            String site_name = jArray1.getString(3);
                            String user_type = jArray1.getString(4);
                            String constant_String = jArray1.getString(5);
                            String Display_flag = jArray1.getString(6);
                            int mProject_Id_result = Integer.parseInt(project_id);
                            int mSite_Id_result = Integer.parseInt(site_id);
                            if (constant_String.equals("DI")) {
                                db = new DatabaseHandler(UserSites.this);

                                if ((db.countRows_MatchedOf_ProjectID_SiteID_UserSitesTable(mProject_Id_result, mSite_Id_result)) == 0) {
                                    db.add_UserSites_Record(new UserSites_Model(mProject_Id_result, project_name, mSite_Id_result, site_name, party_id_fmLoginDB, org_id_fmLoginDB, user_type));

                                } else {


                                    //DELETE from USER_SITES where  SITE_ID_USER_SITES=Display_flag;

                                    UserSites_Model model = db.find_ProjectID_SiteID_UserSitesTable(mProject_Id_result, mSite_Id_result);
                                    if (model != null && Display_flag.equals("N")) {
                                        db.getUserSitesDelete(mSite_Id_result);

                                    } else if (model != null) {
                                        model.setProject_ID(mProject_Id_result);
                                        model.setProject_Name(project_id);
                                        model.setSite_ID(mSite_Id_result);
                                        model.setSite_Name(site_name);
                                        model.setUserType(user_type);
                                        model.setParty_ID(party_id_fmLoginDB);
                                        model.setOrg_ID(org_id_fmLoginDB);

                                        db.Update_UserSitesTable(model);

                                    }
                                    //db.Update_UserSitesTable(model);
                                }

                            }

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "New Projects Added Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No New Projects Available", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // display toast here
                        Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog_newSites.isShowing()) ;
            pDialog_newSites.dismiss();
            finish();
            startActivity(getIntent());

        }


    }
}

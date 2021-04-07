package com.buildman.buildman.materialtabs;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Dashboard;
import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.Login;
import com.buildman.buildman.activity.Material_Dashboard;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.activity.UserSites;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Dashboard_Model;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Material_Dashboard_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MaterialTabs extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    TabHost tabHost;
    DatabaseHandler db;
    String sending;
    StringBuffer sb;
    ProgressDialog pDialog_UserType, pDialog, pDialog_checkPassword, pDialog_dashboard, pDialog_Matl_dashboard,
            pDialog_getNewMateials, pDialog_AppVersion, pDialog_SynMaster;
    int party_id_fmLoginDB, org_id_fmLoginDB;
    int selected_SiteID;
    String versionName, selected_SiteName, mDate_Time_Now;
    private TextView mVersion_txv;
    private static String url_InsertStockDetailsbySite;
    private static String url_GetNewSites;
    private static String url_send_Party_Site_Org_ID_Dashboard;
    private static String url_send_Party_Site_Org_ID_Matl_Dashboard;
    private static String url_send_Params_GetNewMaterials;
    private static String url_send_Party_OrgId_CheckPassword;
    private static String url_send_Params_SynMaster;
    private static String url_UpdateAppVersion;
    private static String url_GetUserType;
    public final static int RECEIVED = 0;
    public final static int USED_MATERIAL = 1;
    public final static int RAISE_INDENT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materialtabs);
        android.app.ActionBar bar = getActionBar();
        mVersion_txv = (TextView) findViewById(R.id.version_mainactivity);
        SharedPreferences versionName_pref1 = getSharedPreferences("VersonName_Login_MyPrefs", Context.MODE_PRIVATE);
        versionName = versionName_pref1.getString("VersionName_Login_KEY_PREF", "");

        SharedPreferences siteName_pref1 = getSharedPreferences("Selected_SiteName_UserSite_MyPrefs", Context.MODE_PRIVATE);
        selected_SiteName = siteName_pref1.getString("Selected_SiteName_UserSite_KEY_PREF", "");

        SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
        selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);

        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>" + selected_SiteName + "</font>"));
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Material </font>"));

        tabHost = getTabHost();

        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Received");
        // setting Title and Icon for the Tab
        photospec.setIndicator("Received");
        Intent photosIntent = new Intent(this, Received_Material.class);
        photospec.setContent(photosIntent);

        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Matl.Used");
        songspec.setIndicator("Matl.Used");
        Intent songsIntent = new Intent(this, Used_Material.class);
        songspec.setContent(songsIntent);

        // Tab for Raise Indent
        TabSpec indentspec = tabHost.newTabSpec("Indent");
        indentspec.setIndicator("Indent");
        Intent indentIntent = new Intent(this, Indent_Material.class);
        indentspec.setContent(indentIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(indentspec); // Adding songs tab
//  get party_id and org_id fm Login table which always have only 1 row
        db = new DatabaseHandler(MaterialTabs.this);
        Login_Model login_model = db.get_FirstRow_Login(1);
        party_id_fmLoginDB = login_model.getParty_ID();
        org_id_fmLoginDB = login_model.getOrg_ID();

// styling tabs strip before selection after selection

        TabWidget widget = tabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView) v.findViewById(android.R.id.title);
            tv.setTextSize(getResources().getDimension(R.dimen.tab_textview));
            if (tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_indicator);
        }

        System.out.println("" + tabHost.getCurrentTab());

        int type = 0;
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("from_material")) {
                type = getIntent().getExtras().getInt("from_material");
                switch (type) {
                    case RECEIVED:
                        tabHost.setCurrentTab(0);
                    case USED_MATERIAL:
                        tabHost.setCurrentTab(1);
                    case RAISE_INDENT:
                        tabHost.setCurrentTab(2);

                    default:
                        tabHost.setCurrentTab(1);
                }
                if (type == 0) {
                    tabHost.setCurrentTab(0);
                } else if (type == 1) {
                    tabHost.setCurrentTab(1);
                } else if (type == 2) {
                    tabHost.setCurrentTab(2);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.material_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.home_materialmenu:
                break;

            case R.id.house_materialmenu:
                Intent i_home = new Intent(MaterialTabs.this, Home.class);
                startActivity(i_home);
                finish();
                break;

            case R.id.site_chart_materialmenu:
                Intent i_chart = new Intent(MaterialTabs.this, Dashboard.class);
                startActivity(i_chart);
                finish();
                break;

            case R.id.material_chart_materialmenu:
                Intent i_material_chart = new Intent(MaterialTabs.this, Material_Dashboard.class);
                startActivity(i_material_chart);
                finish();
                break;

            case R.id.choose_material_materialmenu:
                Intent i_choose_material = new Intent(MaterialTabs.this, Choose_Material.class);
                startActivity(i_choose_material);
                finish();
                break;

            case R.id.switchsite_materialmenu:
                Intent i_switchsite = new Intent(MaterialTabs.this, UserSites.class);
                startActivity(i_switchsite);
                finish();
                break;


            case R.id.logout_materialmenu:
                Intent i = new Intent(MaterialTabs.this, Login.class);
                startActivity(i);
                finish();
                break;

            case R.id.syn_materialmenu:
                dateTime_Now();
                isInternetOn();
                break;

        }
        return true;

    }

    private void dateTime_Now() {
        // TODO Auto-generated method stub
        mDate_Time_Now = Utils.sdf.format(new Date());
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
            new Get_UserType().execute();
//               check usertype is primary or not             	
            check_UserType();
            new sendParty_Site_Org_ID_Dashboard().execute();
            new sendParty_Site_Org_IDs_MaterialDashboard().execute();
            new Send_Params_GetNewMaterials().execute();
            new sendPartyID_OrgID_CheckPassword().execute();
            new UpdateAppVersion().execute();
            new GetNewSites().execute();
            new Send_Params_SynMaster().execute();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, "Please connect to internet for Synchornization", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    private void check_UserType() {
        // TODO Auto-generated method stub
        System.out.println("usertype checking before sending maTERIALS");
        UserSites_Model model = db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
        if (model != null) {
            String userType_inDB = model.getUserType();
            if (userType_inDB.equals("PRIMRY")) {
//     			transaction data of materials to be synched for only primary_type user
                new sendMaterials().execute();
            } else {
                Toast.makeText(this, "User type is restricted to transfer data", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class Get_UserType extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_UserType = new ProgressDialog(MaterialTabs.this);

            pDialog_UserType.setMessage("Please Wait.");
            pDialog_UserType.setCancelable(false);
            pDialog_UserType.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            StringBuffer sb_UserType = new StringBuffer();
            sb_UserType.append(Utils.URL + "GetUserType" + "?" + "PartyId=" + party_id_fmLoginDB + "&SiteId=" + selected_SiteID + "&OrgId=" + org_id_fmLoginDB);
            url_GetUserType = sb_UserType.toString();
            System.out.println("usertype:" + url_GetUserType.toString());
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_GetUserType);
            System.out.println("json string of user_type>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {

                        String user_type_portal = jArray.getString(0);
                        System.out.println("User Type is:" + user_type_portal);
                        UserSites_Model model = db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
                        if (model != null) {
                            model.setUserType(user_type_portal);
                            db.Update_UserSitesTable(model);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // display toast here
                                    Toast.makeText(getApplicationContext(), "User type updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        db.Update_UserSitesTable(model);
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
//        	        	  Toast.makeText(getApplicationContext(), "invalid User type", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_UserType.isShowing()) ;
            pDialog_UserType.dismiss();
        }

    }

    private class sendMaterials extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MaterialTabs.this);

            pDialog.setMessage("Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            System.out.println("selected id is" + selected_SiteID);

            ArrayList<Received_Material_Model> send_material_list = db.find_SiteID_Synch_Status_Material_Stk_TransList(selected_SiteID, "N");

            for (Received_Material_Model cn : send_material_list) {

                final int material_id_portal = cn.getMaterial_ID_MST();
                String received_stock_portal = cn.getReceived_Stock_MST();
                String used_stock_portal = cn.getUsed_MST();
                String indent_portal = cn.getIndent_MST();
                String current_stock_portal = cn.getCurrent_Stock_MST();
                String transaction_date_portal1 = cn.getTransaction_Date_MST();
                String transaction_date_portal = transaction_date_portal1.replace(" ", "%20");

                int project_id_portal = cn.getProject_ID_MST();
                final int site_id_portal = cn.getSite_ID_MST();
                int party_id_portal = cn.getParty_ID_MST();
                int org_id_portal = cn.getOrg_ID_MST();
                String material_req_by_date_portal = cn.getMaterial_Req_By_Date_MST();
                String doc_Type = cn.getDoc_Type_MST();
                String dc_Invoice = cn.getDC_Invoice_MST();
                String adjustments_Type1 = cn.getAdjustments_MST();
                String adjustments_Type = adjustments_Type1.replace(" ", "%20");
                String adjust_reason1 = cn.getAdjustReason_MST();
                String adjust_reason = adjust_reason1.replace(" ", "%20");

                String remarks1 = cn.getRemarks_MST();
                String remarks = remarks1.replace(" ", "%20");
                int subTask_ID = cn.getTask_ID();

                sb = new StringBuffer();
                sb.append(Utils.URL + "InsertStockDetailsbySite" + "?" + "data=" + material_id_portal + "," + received_stock_portal + ","
                        + used_stock_portal + "," + indent_portal + "," + current_stock_portal + "," + transaction_date_portal + ","
                        + project_id_portal + "," + site_id_portal + "," + party_id_portal + "," + org_id_portal + ","
                        + material_req_by_date_portal + "," + doc_Type + "," + dc_Invoice + "," + adjustments_Type + "," + adjust_reason + "," + subTask_ID + ","
                        + remarks);

                url_InsertStockDetailsbySite = sb.toString();
                System.out.println("send to server--- " + url_InsertStockDetailsbySite);
                ServerConnection hh = new ServerConnection();
                String jsonStr = hh.getmethods(url_InsertStockDetailsbySite);


                System.out.println("json string>>>>>>>>" + jsonStr);


                if (jsonStr != null) {
                    try {
                        // Extract JSON array from the response
                        JSONArray jArray = new JSONArray(jsonStr);
                        System.out.println("length of json string is " + jArray.length());
                        if (jArray.length() != 0) {

                            String syn_status_value = jArray.getString(0);
                            int syn_status_int = Integer.parseInt(syn_status_value);
                            System.out.println("Syn ststus value" + syn_status_int);
                            if (syn_status_int == 1) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        ArrayList<Received_Material_Model> db_list = db.find_SiteID_MaterialID_SynStatus_Material_Stk_TransList(site_id_portal, material_id_portal, "N");
                                        for (Received_Material_Model model : db_list) {

                                            model.setSync_flag_MST("Y");

                                            db.Update_Material_Stk_TransRow(model);

                                        }
                                        // display toast here
                                        Toast.makeText(getApplicationContext(), "Material data transferred", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // display toast here
                                        Toast.makeText(getApplicationContext(), "Material data not transferred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        } else {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // display toast here
//            	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // display toast here
                            Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


//	  for loop ending
            }
            return null;

        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()) ;
            pDialog.dismiss();

        }


    }

    private class sendPartyID_OrgID_CheckPassword extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_checkPassword = new ProgressDialog(MaterialTabs.this);

            pDialog_checkPassword.setMessage("Please Wait.");
            pDialog_checkPassword.setCancelable(false);
            pDialog_checkPassword.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);
            StringBuffer sb_PartyID_OrgID = new StringBuffer();
            sb_PartyID_OrgID.append(Utils.URL + "CheckPassword" + "?" + "PartyId=" + party_id_fmLoginDB + "&" + "OrgId=" + org_id_fmLoginDB);
            url_send_Party_OrgId_CheckPassword = sb_PartyID_OrgID.toString();
            System.out.println("send to server of party id and org id" + url_send_Party_OrgId_CheckPassword);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_send_Party_OrgId_CheckPassword);


            System.out.println("json string>>>>>>>>" + jsonStr);


            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println("length of json string is " + jArray.length());
                    if (jArray.length() != 0) {
                        db = new DatabaseHandler(MaterialTabs.this);
                        String syn_Username = jArray.getString(0);
                        String syn_Password = jArray.getString(1);
                        System.out.println("changed email:" + syn_Username + "- and pasd :" + syn_Password);
                        Login_Model model = db.find_PartyId_OrgId_Login(party_id_fmLoginDB, org_id_fmLoginDB);
                        if (model != null) {
                            model.setEmail(syn_Username);
                            model.setPassword(syn_Password);

                            db.Update_Login_Row(model);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // display toast here
                                    Toast.makeText(getApplicationContext(), "Your Password is synchornized with portal ", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                        db.Update_Login_Row(model);


                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
//         	        	  Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_checkPassword.isShowing()) ;
            pDialog_checkPassword.dismiss();

        }


    }

    private class sendParty_Site_Org_ID_Dashboard extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_dashboard = new ProgressDialog(MaterialTabs.this);

            pDialog_dashboard.setMessage("Please Wait.");
            pDialog_dashboard.setCancelable(false);
            pDialog_dashboard.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);

            StringBuffer sb_dashboard = new StringBuffer();
            String org_Id_String = String.valueOf(org_id_fmLoginDB);
            String party_Id_String = String.valueOf(party_id_fmLoginDB);
            String site_Id_String = String.valueOf(selected_SiteID);

            sb_dashboard.append(Utils.URL + "GetSiteStockDashBoard" + "?" + "PartyId=" + party_Id_String + "&SiteId=" + site_Id_String + "&OrgId=" + org_Id_String);

            url_send_Party_Site_Org_ID_Dashboard = sb_dashboard.toString();
            System.out.println("send to server " + url_send_Party_Site_Org_ID_Dashboard);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_send_Party_Site_Org_ID_Dashboard);


            System.out.println("json string>>>>>>>>" + jsonStr);


            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObject1 = jArray.getJSONObject(i);

                            String site_id = jObject1.getString("SiteId");

                            String stock_value = jObject1.getString("StockValue");


                            String site_Name = jObject1.getString("SiteName");


                            int mSite_id_result = Integer.parseInt(site_id);
                            if ((db.countRows_MatchedOf_SiteID_DashboardTable(mSite_id_result)) == 0) {
//     				 						inserting in database
                                db.add_Dashboard_Record(new Dashboard_Model(mSite_id_result, stock_value, site_Name, mDate_Time_Now));
                            } else {
                                Dashboard_Model model = db.find_SiteId_Dashboard(mSite_id_result);
                                if (model != null) {
                                    model.setSiteStockValue(stock_value);
                                    model.setSiteName(site_Name);
                                    model.setDate(mDate_Time_Now);
                                    db.Update_Dashboard_Row(model);

                                }
                                db.Update_Dashboard_Row(model);

                            }


//      				 for loop ending
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "Site Stock Dashboard data collected", Toast.LENGTH_SHORT).show();

                            }
                        });

//          jArray.length() if loop ending      			
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No data available for Site Stock Dashboard", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_dashboard.isShowing()) ;
            pDialog_dashboard.dismiss();

        }


    }

    private class sendParty_Site_Org_IDs_MaterialDashboard extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_Matl_dashboard = new ProgressDialog(MaterialTabs.this);

            pDialog_Matl_dashboard.setMessage("Please Wait.");
            pDialog_Matl_dashboard.setCancelable(false);
            pDialog_Matl_dashboard.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            StringBuffer sb_dashboard = new StringBuffer();
            String org_Id_String = String.valueOf(org_id_fmLoginDB);
            String party_Id_String = String.valueOf(party_id_fmLoginDB);
            String site_Id_String = String.valueOf(selected_SiteID);
            sb_dashboard.append(Utils.URL + "GetMaterialStockDashBoard" + "?" + "PartyId=" + party_Id_String + "&SiteId=" + site_Id_String + "&OrgId=" + org_Id_String);

            url_send_Party_Site_Org_ID_Matl_Dashboard = sb_dashboard.toString();
            System.out.println("send to server " + url_send_Party_Site_Org_ID_Matl_Dashboard);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_send_Party_Site_Org_ID_Matl_Dashboard);


            System.out.println("json string>>>>>>>>" + jsonStr);


            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONArray jArray1 = jArray.getJSONArray(i);

                            String material_id = jArray1.getString(0);
                            String material_name = jArray1.getString(1);
                            String material_quantity = jArray1.getString(2);
                            String material_units = jArray1.getString(3);
                            System.out.println("dashboard material units is" + material_units);

                            int mMaterial_ID_result = Integer.parseInt(material_id);
                            if ((db.countRows_MatchedOf_MaterialID_SiteID_Material_DashboardTable(mMaterial_ID_result, selected_SiteID)) == 0) {
//     				 					inserting in database
                                db.add_MaterialDashboard_Record(new Material_Dashboard_Model(mMaterial_ID_result, material_name, material_units, material_quantity, selected_SiteID, selected_SiteName, mDate_Time_Now));
                            } else {
                                Material_Dashboard_Model model = db.find_MaterialID_SiteID_Material_DashboardTable(mMaterial_ID_result, selected_SiteID);
                                if (model != null) {
                                    model.setMaterial_Quantity(material_quantity);
                                    model.setDate(mDate_Time_Now);
                                    db.Update_MaterialDashboard_Row(model);

                                }
                                db.Update_MaterialDashboard_Row(model);

                            }
//                 	     for loop ending
                        }

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "Material Dashboard data collected Successful", Toast.LENGTH_SHORT).show();
                            }
                        });

//       jArray.length() if loop ending      			
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No Data Available for Material Dashboard", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_Matl_dashboard.isShowing()) ;
            pDialog_Matl_dashboard.dismiss();

        }

    }

    private class Send_Params_GetNewMaterials extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_getNewMateials = new ProgressDialog(MaterialTabs.this);

            pDialog_getNewMateials.setMessage("Please Wait.");
            pDialog_getNewMateials.setCancelable(false);
            pDialog_getNewMateials.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);

            String org_id_string = String.valueOf(org_id_fmLoginDB);
            String party_id_string = String.valueOf(party_id_fmLoginDB);
            String selected_SiteID_string = String.valueOf(selected_SiteID);


            StringBuffer new_material_sb = new StringBuffer();

            new_material_sb.append(Utils.URL + "GetNewMaterialDetails" + "?" + "OrgId=" + org_id_string + "&SiteId=" + selected_SiteID_string + "&PartyId=" + party_id_string + "&MobModuleId=" + String.valueOf(1));

            System.out.print("org id is" + org_id_string + "party" + party_id_string + "siteid" + selected_SiteID_string);

            url_send_Params_GetNewMaterials = new_material_sb.toString();

            System.out.println("new materials string>>>>>>>>" + url_send_Params_GetNewMaterials);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_send_Params_GetNewMaterials);
            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONArray jArray1 = jArray.getJSONArray(i);

                            String material_id = jArray1.getString(0);
                            String material_name = jArray1.getString(1);
                            String material_quantity = jArray1.getString(2);
                            String units = jArray1.getString(3);
                            String site_id = jArray1.getString(4);
                            String constant_String_portal = jArray1.getString(5);

                            int mMaterial_Id_result = Integer.parseInt(material_id);
                            int mSite_Id_result = Integer.parseInt(site_id);

                            String mTodayDate = Utils.sdf.format(new Date());

                            UserSites_Model usersites_model = db.find_Org_ID_SiteID_UserSitesTable(org_id_fmLoginDB, selected_SiteID);
                            int project_id_int = usersites_model.getProject_ID();
                            int party_id_int = usersites_model.getParty_ID();

                            System.out.println("consta value for new materials" + constant_String_portal);

                            if (constant_String_portal.equals("I")) {
//	    		     					inserting in database
                                db.add_Material_Record(new Received_Material_Model(mMaterial_Id_result, material_name, units, "0.00"));
                                db.add_Material_Stk_Trans_Record(new Received_Material_Model(mMaterial_Id_result, material_name, units, "0.00", material_quantity, "0.00", "0.00", material_quantity, mTodayDate, project_id_int, mSite_Id_result, party_id_int, org_id_fmLoginDB, "Y", "Y", "00-00-0000", "0", "0", "0", mTodayDate, "0", "0", "0", 0));
                            } else if (constant_String_portal.equals("DI")) {
                                if ((db.countRows_MatchedOf_MaterialID_SiteID_Material_Stk_Trans(mMaterial_Id_result, mSite_Id_result)) == 0) {
                                    db.add_Material_Stk_Trans_Record(new Received_Material_Model(mMaterial_Id_result, material_name, units, "0.00", material_quantity, "0.00", "0.00", material_quantity, mTodayDate, project_id_int, mSite_Id_result, party_id_int, org_id_fmLoginDB, "Y", "Y", "00-00-0000", "0", "0", "0", mTodayDate, "0", "0", "0", 0));
                                } else {
                                    Received_Material_Model model = db.find_MaterialID_SiteID_DisplayFlag_Material_Stk_Trans(mMaterial_Id_result, mSite_Id_result, "Y");
                                    if (model != null) {
                                        model.setMaterial_ID_MST(mMaterial_Id_result);
                                        model.setMaterial_Name_MST(material_name);
                                        model.setUnits_MST(units);
                                        model.setRate_MST("0.00");
                                        model.setReceived_Stock_MST(material_quantity);
                                        model.setUsed_MST("0.00");
                                        model.setIndent_MST("0.00");
                                        model.setCurrent_Stock_MST(material_quantity);
                                        model.setTransaction_Date_MST(mTodayDate);
                                        model.setProject_ID_MST(project_id_int);
                                        model.setSite_ID_MST(mSite_Id_result);
                                        model.setParty_ID_MST(party_id_int);
                                        model.setOrg_ID_MST(org_id_fmLoginDB);
                                        model.setSync_flag_MST("Y");
                                        model.setDisplay_Flag_MST("Y");
                                        model.setMaterial_Req_By_Date_MST("00-00-0000");
                                        model.setDC_Invoice_MST("0");
                                        model.setDoc_Type_MST("0");
                                        model.setCreated_Date_MST(mTodayDate);
                                        model.setRemarks_MST("0");
                                        model.setAdjustments_MST("0");
                                        model.setAdjustReason_MST("0");
                                        db.Update_Material_Stk_TransRow(model);

                                    }
                                    db.Update_Material_Stk_TransRow(model);
                                }

                            }
//	         					for loop endimg  		 
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "New Materials Added Successful", Toast.LENGTH_SHORT).show();

                            }
                        });
//	              if loop ending		         				
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No New Materials Available", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
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
            if (pDialog_getNewMateials.isShowing()) ;
            pDialog_getNewMateials.dismiss();

        }
    }


    private class UpdateAppVersion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_AppVersion = new ProgressDialog(MaterialTabs.this);

            pDialog_AppVersion.setMessage("Please Wait.");
            pDialog_AppVersion.setCancelable(false);
            pDialog_AppVersion.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            String org_id_string = String.valueOf(org_id_fmLoginDB);
            String party_id_string = String.valueOf(party_id_fmLoginDB);

            StringBuffer appVersion_sb = new StringBuffer();

            appVersion_sb.append(Utils.URL + "UpdateAppVersion" + "?" + "PartyId=" + party_id_string + "&OrgId=" + org_id_string + "&version=" + versionName);

            url_UpdateAppVersion = appVersion_sb.toString();
            System.out.print("send to server" + url_UpdateAppVersion);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_UpdateAppVersion);
            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {

                        String response_Value = jArray.getString(0);

                        int mResponse_result = Integer.parseInt(response_Value);
                        if (mResponse_result == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // display toast here
//	                               	        	  Toast.makeText(getApplicationContext(), "version inserted"+versionName, Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // display toast here
//					                 	        	  Toast.makeText(getApplicationContext(), "Error in version"+versionName, Toast.LENGTH_SHORT).show();
                                }
                            });


                        }


                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
//	            	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_AppVersion.isShowing()) ;
            pDialog_AppVersion.dismiss();

        }
    }

    private class GetNewSites extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_SynMaster = new ProgressDialog(MaterialTabs.this);

            pDialog_SynMaster.setMessage("Please Wait.");
            pDialog_SynMaster.setCancelable(false);
            pDialog_SynMaster.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            String org_id_string = String.valueOf(org_id_fmLoginDB);
            String party_id_string = String.valueOf(party_id_fmLoginDB);
            String mMy_Version = mVersion_txv.getText().toString();
            String module_id_string = String.valueOf(0);

            StringBuffer newSites_sb = new StringBuffer();

            newSites_sb.append(Utils.URL + "GetNewProjectDetails" + "?" + "PartyId=" + party_id_string + "&OrgId=" + org_id_string + "&MobModuleId=" + module_id_string + "&version=" + mMy_Version);
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
                    if (jArray.length() != 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONArray jArray1 = jArray.getJSONArray(i);
                            String project_id = jArray1.getString(0);
                            String project_name = jArray1.getString(1);
                            String site_id = jArray1.getString(2);
                            String site_name = jArray1.getString(3);
                            String user_type = jArray1.getString(4);
                            String constant_String = jArray1.getString(5);
                            int mProject_Id_result = Integer.parseInt(project_id);
                            int mSite_Id_result = Integer.parseInt(site_id);
                            if (constant_String.equals("DI")) {
                                if ((db.countRows_MatchedOf_ProjectID_SiteID_UserSitesTable(mProject_Id_result, mSite_Id_result)) == 0) {

                                    db.add_UserSites_Record(new UserSites_Model(mProject_Id_result, project_name, mSite_Id_result, site_name, party_id_fmLoginDB, org_id_fmLoginDB, user_type));

                                } else {
                                    UserSites_Model model = db.find_ProjectID_SiteID_UserSitesTable(mProject_Id_result, mSite_Id_result);
                                    if (model != null) {
                                        model.setProject_ID(mProject_Id_result);
                                        model.setProject_Name(project_id);
                                        model.setSite_ID(mSite_Id_result);
                                        model.setSite_Name(site_name);
                                        model.setUserType(user_type);
                                        model.setParty_ID(party_id_fmLoginDB);
                                        model.setOrg_ID(org_id_fmLoginDB);

                                        db.Update_UserSitesTable(model);

                                    }
                                    db.Update_UserSitesTable(model);
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
                    } else {
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

            } else {
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
            if (pDialog_SynMaster.isShowing()) ;
            pDialog_SynMaster.dismiss();
            finish();
            startActivity(getIntent());
        }
    }


    private class Send_Params_SynMaster extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_SynMaster = new ProgressDialog(MaterialTabs.this);

            pDialog_SynMaster.setMessage("Please Wait.");
            pDialog_SynMaster.setIcon(R.drawable.material);
            pDialog_SynMaster.setCancelable(false);
            pDialog_SynMaster.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(MaterialTabs.this);


            String org_id_string = String.valueOf(org_id_fmLoginDB);
            String party_id_string = String.valueOf(party_id_fmLoginDB);
            String selected_SiteID_string = String.valueOf(selected_SiteID);
            String module_Id_string = String.valueOf(1);

            StringBuffer synMaster_sb = new StringBuffer();

            synMaster_sb.append(Utils.URL + "InsertSyncMaster" + "?" + "PartyId=" + party_id_string + "&SiteId=" + selected_SiteID_string + "&OrgId=" + org_id_string + "&MobModuleId=" + module_Id_string);

            url_send_Params_SynMaster = synMaster_sb.toString();
            System.out.print("send to server" + url_send_Params_SynMaster);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_send_Params_SynMaster);
            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {

                        String response_Value = jArray.getString(0);

                        int mResponse_result = Integer.parseInt(response_Value);
                        if (mResponse_result == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // display toast here
                                    Toast.makeText(getApplicationContext(), "Synchornization Complete", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // display toast here
                                    Toast.makeText(getApplicationContext(), "Error in Synchornization", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }


                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
//	            	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
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
            if (pDialog_SynMaster.isShowing()) ;
            pDialog_SynMaster.dismiss();
            finish();
            startActivity(getIntent());
        }
    }

}



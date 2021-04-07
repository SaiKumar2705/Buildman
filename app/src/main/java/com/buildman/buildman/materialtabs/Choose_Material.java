package com.buildman.buildman.materialtabs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Choose_Material_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.ChooseMaterial_Model;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;


import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Choose_Material extends Activity {
    DatabaseHandler db;
    private ProgressDialog pDialog_Get_Choose_Material;
    private ImageView mBack_Img;
    private static String url_Get_Choose_Material;
    private ListView mListview;
    private int party_id_fmLoginDB, org_id_fmLoginDB, selected_SiteID;
    private Dialog dialog;

    ArrayList<ChooseMaterial_Model> choose_Material_List = new ArrayList<ChooseMaterial_Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_material);
        db = new DatabaseHandler(Choose_Material.this);
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Material List</font>"));
        getIds();
        get_Ids_FmDB_LoginTable();
        get_Data();
        isInternetOn();
        back_Img_Click();


    }

    @Override
    public void onBackPressed() {
        // do something on back.
//	    			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Choose_Material.this, MaterialTabs.class);
        startActivity(i);
        finish();
        return;
    }

    private void get_Data() {
        // TODO Auto-generated method stub
        SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
        selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
    }

    private void get_Ids_FmDB_LoginTable() {
        // TODO Auto-generated method stub
        //  get party_id and org_id fm Login table which always have only 1 row
        db = new DatabaseHandler(Choose_Material.this);
        Login_Model login_model = db.get_FirstRow_Login(1);
        party_id_fmLoginDB = login_model.getParty_ID();
        org_id_fmLoginDB = login_model.getOrg_ID();
    }

    private void getIds() {
        // TODO Auto-generated method stub
        mListview = (ListView) findViewById(R.id.listview_choose_material);
        mBack_Img = (ImageView) findViewById(R.id.back_img_choose_material);
    }

    public final boolean isInternetOn() {
        // TODO Auto-generated method stub
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet then setup the app

            new Get_Choose_Material().execute();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

//	            	 Toast.makeText(getApplicationContext(), "Please connect to internet for latest data", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    private void back_Img_Click() {
        // TODO Auto-generated method stub
        mBack_Img.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Choose_Material.this, MaterialTabs.class);
                startActivity(i);
                finish();
            }
        });
    }

    private class Get_Choose_Material extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_Get_Choose_Material = new ProgressDialog(Choose_Material.this);
            pDialog_Get_Choose_Material.setMessage("Please Wait.");
            pDialog_Get_Choose_Material.setCancelable(false);
            pDialog_Get_Choose_Material.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance

            String org_id_string = String.valueOf(org_id_fmLoginDB);


            StringBuffer choose_material_sb = new StringBuffer();

            choose_material_sb.append(Utils.URL + "ChooseMaterial" + "?" + "OrgId=" + org_id_string);

            System.out.print("org id is" + org_id_string);

            url_Get_Choose_Material = choose_material_sb.toString();

            System.out.println("choose materials string>>>>>>>>" + url_Get_Choose_Material);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_Get_Choose_Material);
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
                            int mMaterial_Id_result = Integer.parseInt(material_id);
                            ChooseMaterial_Model choose_material_model = new ChooseMaterial_Model(mMaterial_Id_result, material_name, material_quantity, units);
                            choose_Material_List.add(choose_material_model);


//	         					for loop endimg  		 
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "All Materials Collected", Toast.LENGTH_SHORT).show();

                            }
                        });
//	              if loop ending		         				
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No Materials Available", Toast.LENGTH_SHORT).show();
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
            if (pDialog_Get_Choose_Material.isShowing()) ;
            pDialog_Get_Choose_Material.dismiss();

            Choose_Material_Adapter adapter = new
                    Choose_Material_Adapter(Choose_Material.this, R.layout.choose_material_row, choose_Material_List);

            mListview.setAdapter(adapter);

            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    ChooseMaterial_Model choose_material_model = (ChooseMaterial_Model) parent.getItemAtPosition(position);
                    final int mMaterial_Id_result = choose_material_model.getMaterial_ID_CM();
                    final String material_name = choose_material_model.getMaterial_Name_CM();
                    final String material_quantity = choose_material_model.getMaterial_Quantity_CM();
                    final String units = choose_material_model.getUnits_CM();
                    if ((db.countRows_MatchedOf_MaterialID_SiteID_Material_Stk_Trans(mMaterial_Id_result, selected_SiteID)) == 0) {

                        // Create custom dialog object
                        dialog = new Dialog(Choose_Material.this);
                        // hide to default title for Dialog
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        dialog.setContentView(R.layout.choose_material_dialog);

                        TextView title_Dialog = (TextView) dialog.findViewById(R.id.title_choose_material_dialog);
                        Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn_choose_material_dialog);
                        Button confirm_btn = (Button) dialog.findViewById(R.id.confirm_btn_choose_material_dialog);

                        title_Dialog.setText("Do you want to add " + material_name);
                        cancel_btn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();

                            }
                        });


                        confirm_btn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String mTodayDate = Utils.sdf.format(new Date());

                                UserSites_Model usersites_model = db.find_Org_ID_SiteID_UserSitesTable(org_id_fmLoginDB, selected_SiteID);
                                int project_id_int = usersites_model.getProject_ID();
                                int party_id_int = usersites_model.getParty_ID();

                                db.add_Material_Stk_Trans_Record(new Received_Material_Model(mMaterial_Id_result, material_name, units, "0.00", material_quantity, "0.00", "0.00", material_quantity, mTodayDate, project_id_int, selected_SiteID, party_id_int, org_id_fmLoginDB, "Y", "Y", "00-00-0000", "0", "0", "0", mTodayDate, "0", "0", "0", 0));
                                dialog.dismiss();
                            }

                        });

                        // Display the dialog
                        dialog.show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Already Added", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


    }
}
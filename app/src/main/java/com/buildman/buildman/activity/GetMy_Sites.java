package com.buildman.buildman.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.materialtabs.GetMy_Materials;
import com.buildman.buildman.model.SetUp_MyApp_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import org.json.JSONArray;
import org.json.JSONException;

public class GetMy_Sites extends Activity {
    TextView mBuilder_txv, mBuilderTech_txv;
    Button mGetMy_Sites_Btn;
    DatabaseHandler db;
    ProgressDialog pDialog;
    StringBuffer sb;


    private static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getmy_sites);
        android.app.ActionBar bar = getActionBar();
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
//		      creating methods to write code
        getIds();
        homeTitles();
        getMy_Sites_Btn_Click();

    }

    @Override
    public void onBackPressed() {
        // do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();		

        return;
    }

    private void getIds() {
        // TODO Auto-generated method stub
//			getting textview Ids fm Xml
        mBuilder_txv = (TextView) findViewById((R.id.buildman_txv_setmy_sites));
        mBuilderTech_txv = (TextView) findViewById((R.id.buildmantechnologies_setmy_sites));

        mGetMy_Sites_Btn = (Button) findViewById((R.id.getmy_sites_btn_setmy_sites));
    }

    private void homeTitles() {
        // TODO Auto-generated method stub
        String udata = "B U I L D M A N";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        mBuilder_txv.setText(content);

    }

    private void getMy_Sites_Btn_Click() {
        // TODO Auto-generated method stub
        mGetMy_Sites_Btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new GetMySites().execute();
            }
        });
    }

    private class GetMySites extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GetMy_Sites.this);
            pDialog.setMessage("Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(GetMy_Sites.this);

            String mMy_email_id = getIntent().getStringExtra("setmy_sites_userName");
            String mMy_Password = getIntent().getStringExtra("setmy_sites_password");
            SetUp_MyApp_Model setup_myapp_model = db.find_Email_Password_SetUp_MyApp(mMy_email_id, mMy_Password);
            int my_party_id_int = setup_myapp_model.getParty_ID();
            int my_org_id_int = setup_myapp_model.getOrg_ID();


            sb = new StringBuffer();
            String my_party_id_string = String.valueOf(my_party_id_int);
            String my_org_id_string = String.valueOf(my_org_id_int);
            sb.append(Utils.URL + "GetProjectDetails" + "?" + "PartyId=" + my_party_id_string + "&" + "OrgId=" + my_org_id_string);
            System.out.print("party id is" + my_party_id_string + "," + mMy_email_id + mMy_Password);

            url = sb.toString();
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url);
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
                            System.out.println("My project id and name is:" + project_id + project_name);
                            System.out.println("My site_id is:" + site_id);
                            System.out.println("My site_name is:" + site_name);


                            int mProject_Id_result = Integer.parseInt(project_id);
                            int mSite_Id_result = Integer.parseInt(site_id);
                            db.add_UserSites_Record(new UserSites_Model(mProject_Id_result, project_name, mSite_Id_result, site_name, my_party_id_int, my_org_id_int, user_type));
                            System.out.println("My org is:" + my_org_id_int);

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "Projects Setup Successful", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent i_intent = new Intent(GetMy_Sites.this, GetMy_Materials.class);

                        i_intent.putExtra("org_id_getMySites_to_getMyMaterial", my_org_id_int);
                        startActivity(i_intent);
                        finish();


                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No Data Available ", Toast.LENGTH_SHORT).show();

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
            if (pDialog.isShowing()) ;
            pDialog.dismiss();

        }


    }

}

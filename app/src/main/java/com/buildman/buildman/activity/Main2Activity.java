package com.buildman.buildman.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.SetUp_MyApp_Model;
import com.buildman.buildman.utils.ConnectionDetector;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;


import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TextView mBuilder_txv, mBuilderTech_txv, mVersion_txv;
    EditText mUserName, mPassword;
    Button mSetUpMyApp_Btn;
    DatabaseHandler db;
    ProgressDialog pDialog;
    StringBuffer sb;
    private static String url;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Toolbar toolbar;
    TextView textViewheading;
    ConnectionDetector cd;
    Boolean isConnected;
    CheckBox chackBoxRememerme;
    String chkvalue="No";
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar =(Toolbar)findViewById(R.id.my_toolbarcommermenu);
        textViewheading=(TextView)toolbar.findViewById(R.id.textViewheading);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textViewheading.setText(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkcallPermission();
        mBuilder_txv = (TextView) findViewById((R.id.buildman_txv_mainactivity));
        mBuilderTech_txv = (TextView) findViewById((R.id.buildmantechnologies_mainactivity));
        mVersion_txv = (TextView) findViewById((R.id.version_mainactivity));
        mUserName = (EditText) findViewById((R.id.email_id_edt_mainactivity));
        mPassword = (EditText) findViewById((R.id.password_edt_mainactivity));
        mSetUpMyApp_Btn = (Button) findViewById((R.id.setupmyapp_btn_mainactivity));
        chackBoxRememerme = (CheckBox) findViewById((R.id.chackBoxRememerme));
        sharedpreferences = getSharedPreferences(Utils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        chackBoxRememerme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    chkvalue="Yes";
                else
                    chkvalue="No";

            }
        });
        homeTitles();
        db = new DatabaseHandler(Main2Activity.this);
        if (db.getSetUp_MyAppTable_Row_Count() == 0) {
            setUpMyApp_Btn_Click();
        }
        else {
            goto_LoginScreen();
            finish();
        }

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    private void homeTitles() {
        // TODO Auto-generated method stub
        String udata = "B U I L D M A N";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        mBuilder_txv.setText(content);

    }

    private void goto_LoginScreen() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(Main2Activity.this);
        SetUp_MyApp_Model setup_myapp_model = db.get_FirstRow_SetUp_MyApp(1);
        String username = setup_myapp_model.getEmail();
        String password = setup_myapp_model.getPassword();
        System.out.println("name and password" + username + "," + password);
        Intent i = new Intent(Main2Activity.this, Login.class);
        startActivity(i);
        finish();
    }


    private void setUpMyApp_Btn_Click() {
        // TODO Auto-generated method stub

        mSetUpMyApp_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cd = new ConnectionDetector(Main2Activity.this);
                isConnected = cd.isConnectingToInternet();

                if (mUserName.getText().length() == 0)
                    mUserName.setError("Please fill the field");
                else if (mPassword.getText().length() == 0)
                    mPassword.setError("Please fill the field");
                else{
                    if(isConnected)
                        new SetUpApp().execute();
                    else
                        Toast.makeText(Main2Activity.this, "Please connect to internet for Setup App", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class SetUpApp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main2Activity.this);
            pDialog.setMessage("Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(Main2Activity.this);

            String mMy_UserName = mUserName.getText().toString();
            String mMy_Password = mPassword.getText().toString();
            String mMy_Version = mVersion_txv.getText().toString();
            sb = new StringBuffer();
            sb.append(Utils.URL + "SetUpMyApp" + "?" + "username=" + mMy_UserName + "&" + "password=" + mMy_Password + "&version=" + mMy_Version);
            url = sb.toString();
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url);
//            ServiceHandler sh = new ServiceHandler();
//            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONArray jArray1 = jArray.getJSONArray(i);

                            String party_id = jArray1.getString(0);

                            String party_name = jArray1.getString(1);

                            System.out.println("My first_name is:" + party_name);
                            String org_id = jArray1.getString(2);
                            System.out.println("My org_id is:" + org_id);
                            String org_name = jArray1.getString(3);
                            System.out.println("My org_name is:" + org_name);


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // display toast here
                                    Toast.makeText(getApplicationContext(), "Buildman Setup Successful", Toast.LENGTH_SHORT).show();
                                }
                            });


                            System.out.println("My Org_ID is:" + org_id + ", My party_Id is:" + party_id + ", org_name" + org_name);

                            String mTodayDate = Utils.sdf.format(new Date());
                            int mParty_id_result = Integer.parseInt(party_id);
                            int mOrg_id_result = Integer.parseInt(org_id);
//     					inserting in database
                            db.add_SetUp_MyApp_Record(new SetUp_MyApp_Model(mMy_UserName, mMy_Password, mTodayDate, mParty_id_result, mOrg_id_result));
                            db.add_Login_Record(new Login_Model(mMy_UserName, mMy_Password, mParty_id_result, mOrg_id_result, party_name, org_name,chkvalue));
                            if(chkvalue.equalsIgnoreCase("YES")){
                                editor.putString("USERNAME", mMy_UserName);
                                editor.putString("PASSWORD", mMy_Password);
                                editor.commit();
                            }
                            Intent i_intent = new Intent(Main2Activity.this, GetMy_Sites.class);
                            i_intent.putExtra("setmy_sites_userName", mUserName.getText().toString());
                            i_intent.putExtra("setmy_sites_password", mPassword.getText().toString());
                            startActivity(i_intent);
                            finish();

                        }

                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // display toast here
                            Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
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
            if (pDialog.isShowing()) ;
            pDialog.dismiss();

        }


    }
    private boolean checkcallPermission(){
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        if (storage != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}

package com.buildman.buildman.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.utils.Utils;

public class Login extends AppCompatActivity {
    TextView mBuilder_txv, mBuilderTech_txv, mVersion_txv;
    EditText mEmail_Edt, mPassword_Edt;
    Button mLogin_Btn;
    DatabaseHandler db;
    Toolbar toolbar;
    TextView textViewheading;
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    String userId,pwd;
    CheckBox chackBoxRememerme;
    String chkvalue="No";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toolbar =(Toolbar)findViewById(R.id.my_toolbarcommermenu);
        textViewheading=(TextView)toolbar.findViewById(R.id.textViewheading);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textViewheading.setText(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
        sharedpreferences = getSharedPreferences(Utils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        chackBoxRememerme = (CheckBox) findViewById((R.id.chackBoxRememerme));
        db = new DatabaseHandler(this);
        if (sharedpreferences.contains("USERNAME")){
            userId = sharedpreferences.getString("USERNAME", "");
            pwd = sharedpreferences.getString("PASSWORD", "");
        }
        if(userId!=null){
            chackBoxRememerme.setChecked(true);
            chkvalue="Yes";
        }


        chackBoxRememerme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    chkvalue="Yes";
                else
                    chkvalue="No";

            }
        });
        getIds();
        homeTitles();
        login_Btn_Click();

    }

    private void getIds() {
        // TODO Auto-generated method stub
//			getting textview Ids fm Xml
        mBuilder_txv = (TextView) findViewById((R.id.buildman_txv_login));
        mBuilderTech_txv = (TextView) findViewById((R.id.buildmantechnologies_login));
        mVersion_txv = (TextView) findViewById((R.id.version_login));
        mEmail_Edt = (EditText) findViewById((R.id.email_id_edt_login));
        mPassword_Edt = (EditText) findViewById((R.id.password_edt_login));
        mLogin_Btn = (Button) findViewById((R.id.login_btn_login));
        if(userId!=null){
            mEmail_Edt.setText(userId);
            mEmail_Edt.setSelection(mEmail_Edt.getText().toString().length());
            mPassword_Edt.setText(pwd);
            mPassword_Edt.setSelection(mPassword_Edt.getText().toString().length());
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        clearEditTexts();
        mPassword_Edt.clearFocus();
        mEmail_Edt.requestFocus();
        return;
    }

    private void homeTitles() {
        // TODO Auto-generated method stub
        String udata = "B U I L D M A N";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        mBuilder_txv.setText(content);
    }

    private void clearEditTexts() {
        // TODO Auto-generated method stub
        mEmail_Edt.setText("");
        mPassword_Edt.setText("");
    }

    private void login_Btn_Click() {
        // TODO Auto-generated method stub
        mLogin_Btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mEmail_Edt.getText().length() == 0) {
                    mEmail_Edt.setError("Please fill the field");
                }
                else if (mPassword_Edt.getText().length() == 0) {
                    mPassword_Edt.setError("Please fill the field");
                }
                else {
                    db = new DatabaseHandler(Login.this);
                    String mMy_Email = mEmail_Edt.getText().toString();
                    String mMy_Password = mPassword_Edt.getText().toString();

                    Login_Model login_model = db.find_Email_Password_Login(mMy_Email, mMy_Password);
                    if (db.countRecordsMatched_Email_Password_Login(mMy_Email, mMy_Password) == 0) {
                        Toast.makeText(Login.this, "ENTER VALID USERNAME AND PASSWORD", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        String email_fmDB = login_model.getEmail();
                        String password_fmDB = login_model.getPassword();
                        System.out.println("mail in db" + email_fmDB);
                        System.out.println("convert DBmail to lowercase" + email_fmDB.toLowerCase());
                        System.out.println("convert DBpassd to lowercase" + password_fmDB.toLowerCase());
                        System.out.println("convert ENTERedmail to lowercase" + mMy_Email.toLowerCase());
                        if ((mMy_Email.toLowerCase()).equals(email_fmDB.toLowerCase()) && mMy_Password.equals(password_fmDB)) {
                            if(chkvalue.equalsIgnoreCase("YES")){
                                editor.putString("USERNAME", userId);
                                editor.putString("PASSWORD", mMy_Password);
                                editor.commit();
                            }
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, UserSites.class);
                            i.putExtra("login_userName", mMy_Email);
                            SharedPreferences version_Name_Pref = getSharedPreferences("VersonName_Login_MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor version_Name_edit = version_Name_Pref.edit();
                            version_Name_edit.putString("VersionName_Login_KEY_PREF", mVersion_txv.getText().toString());
                            version_Name_edit.commit();
                            startActivity(i);
                            finish();
//
                        }
                        else
                            Toast.makeText(Login.this, "ENTER VALID USERNAME AND PASSWORD", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


}


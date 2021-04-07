package com.buildman.buildman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.expensestabs.ExpenseTabs;
import com.buildman.buildman.knowledgecenter.Documents;
import com.buildman.buildman.manpowertabs.ManpowerTabs;
import com.buildman.buildman.materialtabs.MaterialTabs;
import com.buildman.buildman.statustabs.StatusTabs;


public class Home extends Activity implements OnClickListener {

    TextView mBuilder_txv, mBuilderTech_txv, mDocuments_txv, mMaterial_txv, mManpower_txv, mMachinery_txv, mExpenses_txv,
            mStatus_txv;
    ImageView mDocuments_img, mMaterial_img, mManpower_img, mMachinery_img, mExpenses_img, mStatus_img;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        android.app.ActionBar bar = getActionBar();

//	        get user_login_name from login sreen
        String login_userName = getIntent().getStringExtra("login_userName");
        System.out.println("login " + login_userName);
        if (login_userName != null) {
            getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>" + login_userName + "</font>"));
        } else {
            getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>" + "Buildman" + "</font>"));
        }
//		      creating methods to write code
        getIds();
        setListeners();
        homeTitles();

    }

    @Override
    public void onBackPressed() {
        // do something on back.
//	    			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Home.this, UserSites.class);
        startActivity(i);
        finish();
        return;
    }

    private void getIds() {
        // TODO Auto-generated method stub
//			getting textview Ids fm Xml

        mBuilder_txv = (TextView) findViewById((R.id.buildman_home));
        mDocuments_txv = (TextView) findViewById((R.id.project_doc_txv_home));
        mStatus_txv = (TextView) findViewById((R.id.status_txt_home));
        mMaterial_txv = (TextView) findViewById((R.id.material_txv_home));
        mManpower_txv = (TextView) findViewById((R.id.manpower_txt_home));
        mMachinery_txv = (TextView) findViewById((R.id.machinery_txt_home));
        mExpenses_txv = (TextView) findViewById((R.id.expenses_txv__home));
        mStatus_txv = (TextView) findViewById((R.id.status_txt_home));

        mDocuments_img = (ImageView) findViewById(R.id.project_doc_img_home);
        mMaterial_img = (ImageView) findViewById(R.id.material_img_home);
        mManpower_img = (ImageView) findViewById(R.id.manpower_img_home);
        mMachinery_img = (ImageView) findViewById(R.id.machinery_img_home);
        mExpenses_img = (ImageView) findViewById(R.id.expenses_img_home);
        mStatus_img = (ImageView) findViewById(R.id.status_img_home);

    }

    private void setListeners() {
        // TODO Auto-generated method stub
        mDocuments_img.setOnClickListener(this);
        mMaterial_img.setOnClickListener(this);
        mManpower_img.setOnClickListener(this);
        mMachinery_img.setOnClickListener(this);
        mExpenses_img.setOnClickListener(this);
        mStatus_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.project_doc_img_home:
                Toast.makeText(getBaseContext(), "This feature is available for premium users only", Toast.LENGTH_SHORT).show();

////				 Toast.makeText(getBaseContext(), "This functionality is restricted", Toast.LENGTH_SHORT).show();
//                mIntent = new Intent(Home.this, Documents.class);
//                startActivity(mIntent);
//                finish();
                break;

            case R.id.material_img_home:
                Toast.makeText(getBaseContext(), "This feature is available for premium users only", Toast.LENGTH_SHORT).show();

//
//                mIntent = new Intent(Home.this, MaterialTabs.class);
//                startActivity(mIntent);
//                finish();
                break;

            case R.id.manpower_img_home:
                Toast.makeText(getBaseContext(), "This feature is available for premium users only", Toast.LENGTH_SHORT).show();

//				  Toast.makeText(getBaseContext(), "This functionality is restricted", Toast.LENGTH_SHORT).show();
//                mIntent = new Intent(Home.this, ManpowerTabs.class);
//                startActivity(mIntent);
//                finish();
                break;

            case R.id.machinery_img_home:
                Toast.makeText(getBaseContext(), "This feature is available for premium users only", Toast.LENGTH_SHORT).show();

                break;
            case R.id.expenses_img_home:
                Toast.makeText(getBaseContext(), "This feature is available for premium users only", Toast.LENGTH_SHORT).show();

//				Toast.makeText(getBaseContext(), "This functionality is restricted", Toast.LENGTH_SHORT).show();
//                mIntent = new Intent(Home.this, ExpenseTabs.class);
//                startActivity(mIntent);
//                finish();
                break;

            case R.id.status_img_home:

                mIntent = new Intent(Home.this, StatusTabs.class);
                startActivity(mIntent);
                finish();
                break;


            default:
                break;
        }
    }

    private void homeTitles() {
        // TODO Auto-generated method stub
        String udata = "B U I L D M A N";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        mBuilder_txv.setText(content);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.home_loginmenu:
                break;

            case R.id.settings_loginmenu:
                break;

            case R.id.switchsite_loginmenu:
                Intent i_switchsite = new Intent(Home.this, UserSites.class);
                startActivity(i_switchsite);
                finish();
                break;

            case R.id.logout_loginmenu:
                Intent i = new Intent(Home.this, Login.class);
                startActivity(i);
                finish();
                break;

        }
        return true;

    }


}

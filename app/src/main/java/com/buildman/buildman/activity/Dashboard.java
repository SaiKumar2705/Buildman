package com.buildman.buildman.activity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.materialtabs.MaterialTabs;
import com.buildman.buildman.model.Dashboard_Model;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Dashboard extends Activity {

    DatabaseHandler db;
    ProgressDialog pDialog;
    Spinner mSpinnerDropDown_SiteNames;
    Button mGet_btn;
    BarChart mChart;
    ImageView mBack_Img;
    String mDate_Time_Now, mDate_OfChart;

    private static float[] yData;
    private static String[] xData;

    private static String url_send_Party_Site_Org_ID_Dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        db = new DatabaseHandler(Dashboard.this);
        android.app.ActionBar bar = getActionBar();
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Site Chart </font>"));
//		      creating methods to write code
        getIds();
        dateTime_Now();
        spinner_DropDown_SiteNames();
        isInternetOn();
        get_Btn_Click();
        back_Img_Click();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Dashboard.this, MaterialTabs.class);
        startActivity(i);
        finish();
        return;
    }

    private void dateTime_Now() {
        // TODO Auto-generated method stub
        mDate_Time_Now = Utils.sdf.format(new Date());
    }

    private void get_Btn_Click() {
        // TODO Auto-generated method stub
        mGet_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isInternetOn();

            }
        });
    }

    private void spinner_DropDown_SiteNames() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(this);
        final List<String> spinner_List = new ArrayList<String>();
        spinner_List.add("All");
        List<UserSites_Model> list = db.getAll_UserSitesList();

        for (int i = 0; i < list.size(); i++) {
            UserSites_Model model = list.get(i);
            String site_names = model.getSite_Name();


            String site_Name = model.getSite_Name();
            spinner_List.add(site_Name);
            System.out.println("spinner_sites" + spinner_List.get(i));

            System.out.println("length for spinner_sites" + spinner_List.size());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinner_List);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDropDown_SiteNames.setAdapter(dataAdapter);


        SharedPreferences siteName_pref1 = getSharedPreferences("Selected_SiteName_UserSite_MyPrefs", Context.MODE_PRIVATE);
        String selected_SiteName = siteName_pref1.getString("Selected_SiteName_UserSite_KEY_PREF", "");

        for (int i = 0; i < dataAdapter.getCount(); i++) {
            if (selected_SiteName.trim().equals(dataAdapter.getItem(i).toString())) {
                mSpinnerDropDown_SiteNames.setSelection(i);
                break;
            }
        }

        // Set the ClickListener for Spinner
        mSpinnerDropDown_SiteNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                // TODO Auto-generated method stub

            }

            // If no option selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
    }


    private void back_Img_Click() {
        // TODO Auto-generated method stub
        mBack_Img.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Dashboard.this, MaterialTabs.class);
                startActivity(i);
                finish();

            }
        });
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

            new sendParty_Site_Org_ID_Dashboard().execute();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            create_Chart();

            return false;
        }
        return false;
    }


    private class sendParty_Site_Org_ID_Dashboard extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Dashboard.this);

            pDialog.setMessage("Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(Dashboard.this);

            Login_Model login_model = db.get_FirstRow_Login(1);
            int org_id = login_model.getOrg_ID();
            int party_id = login_model.getParty_ID();
            int site_Id_DropDown;
            String selectedSpinner_SiteName = String.valueOf(mSpinnerDropDown_SiteNames.getSelectedItem());


            if (selectedSpinner_SiteName.equals("All")) {
                site_Id_DropDown = 0;
            } else {
                UserSites_Model usersites_model = db.find_SiteName_UserSitesTable(selectedSpinner_SiteName);
                site_Id_DropDown = usersites_model.getSite_ID();
            }

            StringBuffer sb_dashboard = new StringBuffer();
            String org_Id_String = String.valueOf(org_id);
            String party_Id_String = String.valueOf(party_id);
            String site_Id_DropDown_String = String.valueOf(site_Id_DropDown);

            sb_dashboard.append(Utils.URL + "GetSiteStockDashBoard" + "?" + "PartyId=" + party_Id_String + "&SiteId=" + site_Id_DropDown_String + "&OrgId=" + org_Id_String);

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
                            System.out.println("site id" + site_id + "," + stock_value);


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
                                Toast.makeText(getApplicationContext(), "Dashboard data collected", Toast.LENGTH_SHORT).show();
                                create_Chart();
                            }
                        });

//             if lloop ending			
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
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

    private void create_Chart() {
        // TODO Auto-generated method stub
        List<Dashboard_Model> list;
        String selectedSpinner_SiteName = String.valueOf(mSpinnerDropDown_SiteNames.getSelectedItem());
        if (selectedSpinner_SiteName.equals("All")) {
            list = db.getAll_Dashboard_List();
        } else {
            list = db.find_Site_Id_BySiteName_List_DashboardTable(selectedSpinner_SiteName);
        }

        int n = list.size();
        xData = new String[n];
        yData = new float[n];


        for (int i = 0; i < list.size(); i++) {
            Dashboard_Model model = list.get(i);
            int site_ID = model.getSite_ID();
            String site_name = model.getSiteName();
            String stock_Value = model.getSiteStockValue();

            mDate_OfChart = model.getDate();


//   		  to ceate pie chart add to array

            xData[i] = site_name;

            // Casting of integer to float

            float values_float = (Float.parseFloat(stock_Value));

            yData[i] = values_float;
            System.out.println(" added name ist" + yData[i]);
        }
        System.out.println(" length of name list" + xData.length);

        addBarData();
        zoom_PopupWindow();

    }

    private void zoom_PopupWindow() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(Dashboard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.zoom_dashboard);
        // set the custom dialog components - text, image
        // and button
        TextView title_Txv = (TextView) dialog.findViewById(R.id.titletxv_zoom_dashboard);

        Button close_Btn = (Button) dialog.findViewById(R.id.closebtn_zoom_dashboard);


        close_Btn
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

        dialog.show();
    }


    private void addBarData() {
        // TODO Auto-generated method stub
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new BarEntry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        BarDataSet barDataSet1 = new BarDataSet(yVals1, "Site Stock On " + mDate_OfChart);
//	 barDataSet1.setColor(Color.LTGRAY);
//	  barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        barDataSet1.setColors(colors);
        // instantiate pie data object now
        BarData data = new BarData(xVals, barDataSet1);

        mChart.setData(data);
//chart.setDescription("My Chart");
        mChart.setDescription("");
        mChart.animateXY(2000, 2000);
        mChart.fitScreen();

        mChart.invalidate();
        mChart.fitScreen();
    }


    private void getIds() {
        // TODO Auto-generated method stub
        mSpinnerDropDown_SiteNames = (Spinner) findViewById(R.id.spinner_SiteNames_Dashboard);
        mGet_btn = (Button) findViewById(R.id.Get_SiteNames_Btn_Dashboard);
        mChart = (BarChart) findViewById(R.id.chart);
        mBack_Img = (ImageView) findViewById(R.id.back_img_dashboard);
    }
}
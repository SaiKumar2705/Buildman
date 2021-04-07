package com.buildman.buildman.knowledgecenter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.KnowledgeCenter_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.KnowledgeCenter_Model;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Documents extends Activity {
    /*in this class we show particular documents from knownledgecenter for selected_SiteId*/
    private ListView mListview;
    private EditText mSerach_Edt;
    DatabaseHandler db;
    private int selected_SiteID, party_id_fmLoginDB, org_id_fmLoginDB, mFile_UploadMaster_ID_SelectedList;
    private String mDate_Time_Now, mFileName;
    private ProgressDialog pDialog_GetKnowledgeCenter, pDialog_File;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    // File url to download
    private static String url_GetKnowledgeCenter;
    private static String url_File;
    private KnowledgeCenter_Adapter adapter;
    private ImageView mBack_Img;
    Boolean isInternetPresent = false;

    private ArrayList<KnowledgeCenter_Model> knowledgeCenter_list = new ArrayList<KnowledgeCenter_Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents);
        android.app.ActionBar bar = getActionBar();
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
//		      creating methods to write code
        getIds();
        getData();
        get_Ids_FmDB_LoginTable();
        dateTime_Now();
        search_Functionality();
        check_Internet();
        back_Btn_Click();

    }

    @Override
    public void onBackPressed() {
        // do something on back.
//	    			Toast.makeText(getApplicationContext(), "You are going ", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Documents.this, Home.class);
        startActivity(i);
        finish();
        return;
    }

    private void check_Internet() {
        // TODO Auto-generated method stub
        isInternetPresent = isInternetOn();

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            Toast.makeText(getApplicationContext(), "connected to internet", Toast.LENGTH_SHORT).show();
//             	 here getting konwledgecenter for selected_site
            new Get_KnowledgeCenter_Data().execute();
        } else {
            // Internet connection is not present
            Toast.makeText(getApplicationContext(), "Please connect to internet for latest data", Toast.LENGTH_SHORT).show();
            getList_fromDB();
        }
    }

    private void back_Btn_Click() {
        // TODO Auto-generated method stub
        mBack_Img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Documents.this, Home.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void focusEdtText() {
        // TODO Auto-generated method stub
        mSerach_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                if (hasFocus) {

                    mSerach_Edt.setText("");
                } else {

                }
            }
        });
    }

    private void getData() {
        // TODO Auto-generated method stub

        SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
        selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
        System.out.println("selected id is--" + selected_SiteID);

    }

    private void get_Ids_FmDB_LoginTable() {
        // TODO Auto-generated method stub
        //  get party_id and org_id fm Login table which always have only 1 row
        db = new DatabaseHandler(Documents.this);
        Login_Model login_model = db.get_FirstRow_Login(1);
        party_id_fmLoginDB = login_model.getParty_ID();
        org_id_fmLoginDB = login_model.getOrg_ID();
    }

    private void dateTime_Now() {
        // TODO Auto-generated method stub
        mDate_Time_Now = Utils.sdf.format(new Date());
    }

    private void search_Functionality() {
        // TODO Auto-generated method stub
        //adding list to edittext for searchfunctionality
        mSerach_Edt.addTextChangedListener(new TextWatcher()

        {

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
                if (mSerach_Edt.getText().length() == 0) {

                } else if (mSerach_Edt.getText().toString() != null) {
                    String text = mSerach_Edt.getText().toString().toLowerCase(Locale.getDefault());
                    System.out.println("search edittext box value-" + text);
                    adapter.filter(text);
                }

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


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

//		            	 Toast.makeText(getApplicationContext(), "Please connect to internet for latest data", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    private class Get_KnowledgeCenter_Data extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_GetKnowledgeCenter = new ProgressDialog(Documents.this);

            pDialog_GetKnowledgeCenter.setMessage("Please Wait.");
            pDialog_GetKnowledgeCenter.setCancelable(false);
            pDialog_GetKnowledgeCenter.show();
        }

        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // Creating service handler class instance
            db = new DatabaseHandler(Documents.this);

            StringBuffer sb_KnowledgeCenter = new StringBuffer();
            String org_Id_String = String.valueOf(org_id_fmLoginDB);
            String site_id_String = String.valueOf(selected_SiteID);
            String date_time;
            if (db.getKnowledgeCenterTable_Row_Count() == 0) {
                date_time = mDate_Time_Now.replace(" ", "%20");
            } else {
                KnowledgeCenter_Model model = db.find_LatestUpdatedDateRow_KnowledgeCenter_Table();
                String updated_Date = model.getUpdated_Date();
                date_time = updated_Date.replace(" ", "%20");
                System.out.println("maximum updated_date" + updated_Date);
            }

            sb_KnowledgeCenter.append(Utils.URL + "GetKnowledgeCenter" + "?" + "SiteId=" + site_id_String + "&OrgId=" + org_Id_String + "&UpdatedDate=" + date_time);

            url_GetKnowledgeCenter = sb_KnowledgeCenter.toString();
            System.out.println("send to server " + url_GetKnowledgeCenter);
            ServerConnection hh = new ServerConnection();
            String jsonStr = hh.getmethods(url_GetKnowledgeCenter);
//            ServiceHandler sh = new ServiceHandler();
//            String jsonStr = sh.makeServiceCall(url_GetKnowledgeCenter, ServiceHandler.GET);

            System.out.println("json string>>>>>>>>" + jsonStr);
            if (jsonStr != null) {
                try {
                    // Extract JSON array from the response
                    JSONArray jArray = new JSONArray(jsonStr);
                    System.out.println(jArray.length());
                    if (jArray.length() != 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jArray1 = jArray.getJSONObject(i);

                            String mFileUploadMasterId = jArray1.getString("FileUploadMasterId");
                            String mFileTittle = jArray1.getString("FileTittle");
                            String mFileTypeId = jArray1.getString("FileTypeId");
                            String mKeywords = jArray1.getString("Keywords");
                            String mPurposeId = jArray1.getString("PurposeId");
                            String mSiteId = jArray1.getString("SiteId");
                            String mDescription = jArray1.getString("Description");
                            String mPortal_FilePath = jArray1.getString("FilePath");
                            String mFlag = jArray1.getString("Flag");

                            int mFileUploadMasterId_result = Integer.parseInt(mFileUploadMasterId);
                            int mFileTypeId_result = Integer.parseInt(mFileTypeId);
                            int mPurposeId_result = Integer.parseInt(mPurposeId);
                            int mSite_id_result = Integer.parseInt(mSiteId);
                            if (mFlag.equals("I")) {
//					 						inserting in database
                                db.add_KnowledgeCenter_Record(new KnowledgeCenter_Model(mFileUploadMasterId_result, mFileTittle, mFileTypeId_result, mKeywords, mPurposeId_result, "0", mSite_id_result, mDescription, mPortal_FilePath, mDate_Time_Now, mDate_Time_Now, "N"));
                                System.out.println("inserting in table");
                            } else if (mFlag.equals("DI")) {
                                if ((db.countRows_MatchedOf_FileUploadMasterId_KnowledgeCenterTable(mFileUploadMasterId_result)) == 0) {
//			        				 						inserting in database
                                    db.add_KnowledgeCenter_Record(new KnowledgeCenter_Model(mFileUploadMasterId_result, mFileTittle, mFileTypeId_result, mKeywords, mPurposeId_result, "0", mSite_id_result, mDescription, mPortal_FilePath, mDate_Time_Now, mDate_Time_Now, "N"));

                                } else {
                                    KnowledgeCenter_Model model = db.find_FileUploadMasterId_KnowledgeCenterTable(mFileUploadMasterId_result);
                                    if (model != null) {

                                        model.setFile_Title(mFileTittle);
                                        model.setFileType_Id(mFileTypeId_result);
                                        model.setKeywords(mKeywords);
                                        model.setPurpose_Id(mPurposeId_result);
                                        model.setSite_Id(mSite_id_result);
                                        model.setDescription(mDescription);
                                        model.setPortalPath(mPortal_FilePath);
                                        model.setCreated_Date(mDate_Time_Now);
                                        model.setUpdated_Date(mDate_Time_Now);
                                        model.setDownload_Flag("N");

                                        db.Update_KnowledgeCenter_Row(model);

                                    }
                                    db.Update_KnowledgeCenter_Row(model);

                                }

                            }

//			         				 for loop ending
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // display toast here
                                Toast.makeText(getApplicationContext(), "Knowledge Center data collected", Toast.LENGTH_SHORT).show();

                            }
                        });

//			                if lloop ending			
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
            if (pDialog_GetKnowledgeCenter.isShowing()) ;
            pDialog_GetKnowledgeCenter.dismiss();
            getList_fromDB();
        }
    }

    private void getList_fromDB() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(this);
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
//		        get selected site_id from usersite class    


        List<KnowledgeCenter_Model> list = db.find_SiteID_KnowledgeCenterList(selected_SiteID);

        LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_documents);
        if (list.size() == 0) {

            relative.setBackgroundResource(0);
            Toast.makeText(getApplicationContext(), "No Documents Available", Toast.LENGTH_SHORT).show();
        } else {
            relative.setBackgroundResource(R.drawable.box_bottom_border);
        }
        for (KnowledgeCenter_Model cn : list) {


            knowledgeCenter_list.add(cn);


        }
        adapter = new
                KnowledgeCenter_Adapter(Documents.this, R.layout.knowledgecenter_row, knowledgeCenter_list);

        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int item_id = (int) parent.getItemIdAtPosition(position);
                KnowledgeCenter_Model model = (KnowledgeCenter_Model) parent.getItemAtPosition(position);
                mFile_UploadMaster_ID_SelectedList = model.getFile_UploadMaster_Id();
                System.out.println("Mobile Path---" + model.getMobilePath());
                url_File = model.getPortalPath();
                mFileName = url_File.substring(url_File.lastIndexOf('/') + 1);
                isInternetPresent = isInternetOn();

                // check for Internet status
                if (isInternetPresent) {
                    // Internet Connection is Present
//				             	 Toast.makeText(getApplicationContext(),  "connected to internet", Toast.LENGTH_SHORT).show();
                    // Trigger Async Task (onPreExecute method)
                    new DownloadFileFromURL().execute(url_File);
                } else {
                    // Internet connection is not present
                    //Toast.makeText(getApplicationContext(), "Please connect to internet for latest data", Toast.LENGTH_SHORT).show();
                    File file = new File(Environment.getExternalStorageDirectory() + "/Buildman Files/" + mFileName);
                    // Check if the  file already exists in sd card
                    if (file.exists()) {
                        Toast.makeText(getApplicationContext(), "File already exist under SD card, open file", Toast.LENGTH_LONG).show();
                        openfile();
                        // If the File doesn't exist in SD card (Not yet downloaded)
                    } else {
                        Toast.makeText(getApplicationContext(), "File doesn't exist under SD Card, Please connect to internet for download", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }

    /**
     * Showing Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog_File = new ProgressDialog(this);
                pDialog_File.setMessage("Downloading file. Please wait...");
                pDialog_File.setIndeterminate(false);
                pDialog_File.setMax(100);
                pDialog_File.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog_File.setCancelable(true);
                pDialog_File.show();
                return pDialog_File;
            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;

            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "Buildman Files");
                folder.mkdir();

                File file = new File(folder, mFileName);
                // Output stream
                OutputStream output = new FileOutputStream(file.toString());

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog_File.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
//		            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // setting downloaded into image view
//		            my_image.setImageDrawable(Drawable.createFromPath(imagePath));
            if (pDialog_File.isShowing()) {
                pDialog_File.dismiss();
            }
            update_DB();
            Toast.makeText(getApplicationContext(), " downloaded successfully, please wait", Toast.LENGTH_SHORT).show();
            openfile();
        }

    }

    private void update_DB() {
        // TODO Auto-generated method stub
        File file = new File(Environment.getExternalStorageDirectory() + "/Buildman Files/" + mFileName);
        KnowledgeCenter_Model model = db.find_FileUploadMasterId_KnowledgeCenterTable(mFile_UploadMaster_ID_SelectedList);
        if (model != null) {
            model.setMobilePath(file.toString());
            model.setUpdated_Date(mDate_Time_Now);
            model.setDownload_Flag("Y");

            db.Update_KnowledgeCenter_Row(model);

        }
        db.Update_KnowledgeCenter_Row(model);
    }

    private void openfile() {
        // TODO Auto-generated method stub
        File file = new File(Environment.getExternalStorageDirectory()
                + "/Buildman Files/" + mFileName);
        String application_Type = getMimeType(url_File);
        PackageManager packageManager = getPackageManager();
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setType(application_Type);
        List list = packageManager.queryIntentActivities(pdfIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, application_Type);
        startActivity(intent);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private void getIds() {
        // TODO Auto-generated method stub
        mSerach_Edt = (EditText) findViewById(R.id.search_edt_documents);
        mListview = (ListView) findViewById(R.id.listview_documents);
        mBack_Img = (ImageView) findViewById(R.id.back_img_documents);

    }
}

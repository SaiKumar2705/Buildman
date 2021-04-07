package com.buildman.buildman.statustabs;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
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

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.Login;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.activity.UserSites;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Project_Images;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class StatusTabs extends TabActivity {
	/**
	 * Called when the activity is first created.
	 */
	TabHost tabHost;
	private String mDate_Time_Now, versionName, selected_SiteName;
	private int selected_SiteID, org_id, party_id, project_id, module_Id = 5;
	private int work_master_id;
	DatabaseHandler db;
	public final static int STATUS = 0;
	public final static int IMAGES = 1;
	public final static int ISSUES = 2;
	private TextView mVersion_txv;
	int party_id_fmLoginDB, org_id_fmLoginDB;
	private ProgressDialog pDialog_UserType, pDialog_Send_ProjectTasks, pDialog_GetProjectTasks, pDialog_SendIssues,
			pDialog_SendImages, pDialog_AppVersion, pDialog_SynMaster, pDialog_DeviationMail;
	private static String url_GetUserType;
	private static String url_GetNewSites;
	private static String url_send_Params_InsertProjectWorkStatusLog;
	private static String url_send_Params_GetProjectTasks;
	private static String url_sendIssues_InsertProjectWorkIssuesDetails;
	private static String url_UpdateAppVersion;
	private static String url_send_Params_SynMaster;
	private static String url_send_Params_DeviationMail;
	private static String url_get_issues_and_images;
	String mMy_Version;
	int syn_status_int = 0;
	int issues_Id_Portal = 0;
	int issuesandimades = 0;
	long vAnInt;
	String GridViewDemo_ImagePath;
    List<Project_Images> project_images;
	Project_Images projectimages;
	String imageData;
	int primary_id_portals;
	String portalid,imageValue1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statustabs);
		android.app.ActionBar bar = getActionBar();
		mVersion_txv = (TextView) findViewById(R.id.version_mainactivity);
		SharedPreferences versionName_pref1 = getSharedPreferences("VersonName_Login_MyPrefs", Context.MODE_PRIVATE);
		versionName = versionName_pref1.getString("VersionName_Login_KEY_PREF", "");
		SharedPreferences siteName_pref1 = getSharedPreferences("Selected_SiteName_UserSite_MyPrefs", Context.MODE_PRIVATE);
		selected_SiteName = siteName_pref1.getString("Selected_SiteName_UserSite_KEY_PREF", "");
		SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
		selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
		work_master_id = getIntent().getIntExtra("Send_mSubTaskID", 0);
		getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>" + selected_SiteName + "</font>"));
		getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Status </font>"));
		tabHost = getTabHost();
		// Tab for Photos
		TabSpec statusspec = tabHost.newTabSpec("Status");
		// setting Title and Icon for the Tab
		statusspec.setIndicator("Status");
		Intent statussIntent = new Intent(this, Status_Status.class);
		statusspec.setContent(statussIntent);

		// Tab for Songs
		TabSpec imagespec = tabHost.newTabSpec("Images");
		imagespec.setIndicator("Images");
		Intent imagesIntent = new Intent(this, Images_Status.class);
		imagespec.setContent(imagesIntent);

		// Tab for Raise Indent
		TabSpec issuesspec = tabHost.newTabSpec("Issues");
		issuesspec.setIndicator("Issues");
		Intent issuesIntent = new Intent(this, Issues_Status.class);
		issuesspec.setContent(issuesIntent);


		// Adding all TabSpec to TabHost
		tabHost.addTab(statusspec); // Adding photos tab
		tabHost.addTab(imagespec); // Adding songs tab
		tabHost.addTab(issuesspec); // Adding songs tab
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
			if (getIntent().getExtras().containsKey("from_status")) {
				type = getIntent().getExtras().getInt("from_status");
				switch (type) {
					case STATUS:
						tabHost.setCurrentTab(0);
					case IMAGES:
						tabHost.setCurrentTab(1);
					case ISSUES:
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
		inflater.inflate(R.menu.status_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case R.id.home_statusmenu:
				break;
			case R.id.house_statusmenu:
				Intent i_home = new Intent(StatusTabs.this, Home.class);
				startActivity(i_home);
				finish();
				break;
			case R.id.statusreport_statusmenu:
				Intent i_report = new Intent(StatusTabs.this, Status_Report.class);
				startActivity(i_report);
				finish();
				break;
			case R.id.last_syn_report_statusmenu:
				db = new DatabaseHandler(StatusTabs.this);
				if (db.getLastSynReportTable_Row_Count() == 0) {
					Intent i_last_synreport = new Intent(StatusTabs.this, Last_Synchronization_Report_Selection.class);
					startActivity(i_last_synreport);
					finish();
				} else {
					Intent i_last_synreport = new Intent(StatusTabs.this, Last_Synchronization_Report.class);
					startActivity(i_last_synreport);
					finish();
				}
				break;
			case R.id.switchsite_statusmenu:
				Intent i_switchsite = new Intent(StatusTabs.this, UserSites.class);
				startActivity(i_switchsite);
				finish();
				break;
			case R.id.logout_statusmenu:
				Intent i = new Intent(StatusTabs.this, Login.class);
				startActivity(i);
				finish();
				break;
			case R.id.syn_statusmenu:
				dateTime_Now();
				getParameters_IDfmDB();
				isInternetOn();
				break;
		}
		return true;
	}

	private void dateTime_Now() {
		// TODO Auto-generated method stub
		mDate_Time_Now = Utils.sdf.format(new Date());
	}

	private void getParameters_IDfmDB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(StatusTabs.this);

		Login_Model login_model = db.get_FirstRow_Login(1);
		org_id = login_model.getOrg_ID();
		party_id = login_model.getParty_ID();

		UserSites_Model usersites_model = db.find_Site_ID_UserSitesTable(selected_SiteID);
		project_id = usersites_model.getProject_ID();

	}

	public final boolean isInternetOn() {
		String party_id_string = String.valueOf(party_id);
		String selected_SiteID_string = String.valueOf(selected_SiteID);
		String org_id_string = String.valueOf(org_id);
		ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
		if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
				connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
			new Get_UserType().execute();
			check_UserType();
			new GetProjectTasksOverloaded().execute();
			new GetNewSites().execute();
			new UpdateAppVersion().execute();
			String url_UserRegisterGetDetails = Utils.URL + "GetIssuesAndImages?OrgId=" + org_id_string + "&PartyId=" + party_id_string + "&SiteId=" + selected_SiteID_string + "&ModuleId=" + module_Id;
			Log.d("GetIssuesAndImages","GetIssuesAndImages" +url_UserRegisterGetDetails);
			new GetIssuesAndImages().execute(url_UserRegisterGetDetails);
			new SendStatusDeviationMail().execute();
			new Send_Params_SynMaster().execute();
			check_Project_Images();
			return true;

		}
		else if (
				connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
						connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
			Toast.makeText(this, "Please connect to internet for Synchornization", Toast.LENGTH_SHORT).show();
			return false;
		}
		return false;
	}

	private void check_UserType() {
		// TODO Auto-generated method stub
		UserSites_Model model = db.find_PartyID_SiteID_UserSitesTable(party_id, selected_SiteID);
		if (model != null) {
			String userType_inDB = model.getUserType();
			if (userType_inDB.equalsIgnoreCase("PRIMRY")) {
					new sendProjectTasks().execute();
			}
		}

	}
	private void check_Project_Images() {
		// TODO Auto-generated method stub
		String party_id_string = String.valueOf(party_id);
		String selected_SiteID_string = String.valueOf(selected_SiteID);
		String org_id_string = String.valueOf(org_id);
		project_images = db.getAllImages(selected_SiteID_string);
		if (project_images != null&&project_images.size()>0) {
			for(Project_Images project_images : project_images){
				String PrjId=project_images.getPROJECT_ID_PRJ_IMAGES();
				String SiteId=project_images.getSITE_ID_PRJ_IMAGES();
				String TaskId=project_images.getPRJ_EST_WORKMASTER_ID_PRJ_IMAGES();
				String ImageString=getFileToByte(project_images.getPATH_PRJ_IMAGES());
				String PartyId=party_id_string;
				String OrgId=org_id_string;
				projectimages=new Project_Images();
				projectimages.setPROJECT_IMAGES_ID(project_images.getPROJECT_IMAGES_ID());
				imageData=PrjId+","+SiteId+","+TaskId+","+ImageString+","+PartyId+","+OrgId;
				new sendProjectImages().execute();
			}
		}
	}

	private class Get_UserType extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_UserType = new ProgressDialog(StatusTabs.this);
			pDialog_UserType.setMessage("Please Wait.");
			pDialog_UserType.setCancelable(false);
			pDialog_UserType.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);
			StringBuffer sb_UserType = new StringBuffer();
			sb_UserType.append(Utils.URL + "GetUserType" + "?" + "PartyId=" + party_id + "&SiteId=" + selected_SiteID + "&OrgId=" + org_id);
			url_GetUserType = sb_UserType.toString();
			System.out.println("usertype:   " + url_GetUserType.toString());
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
						UserSites_Model model = db.find_PartyID_SiteID_UserSitesTable(party_id, selected_SiteID);
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
					} else {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// display toast here
//        	        	 		 Toast.makeText(getApplicationContext(), "invalid User type", Toast.LENGTH_SHORT).show();
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

	private class sendProjectTasks extends AsyncTask<Void, Void, Void> {
		int count;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_Send_ProjectTasks = new ProgressDialog(StatusTabs.this);
			pDialog_Send_ProjectTasks.setMessage("Please Wait.");
			pDialog_Send_ProjectTasks.setCancelable(false);
			pDialog_Send_ProjectTasks.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance

			db = new DatabaseHandler(StatusTabs.this);

			ArrayList<Status_Model> send_project_task_status_list = db.find_SiteID_SynStatusFlag_ProjectStatusList(selected_SiteID, "N");

			for (int i = 0; i < send_project_task_status_list.size(); i++) {
				Status_Model cn = send_project_task_status_list.get(i);
				int project_id_portal = cn.getProject_ID();
				int site_id_portal = cn.getSite_ID();
				final int work_master_id_portal = cn.getPRJ_EST_WorkMaster_ID();

				String percentage_completion = cn.getPercentage_Completion();
				String work_completed_qty = cn.getPRJ_EST_Work_COM_QTY();
				String actual_StartDate = cn.getStart_Date();
				String actual_EndDate = cn.getEnd_Date();
				String transaction_date_portal1 = cn.getTransaction_Date();
				String transaction_date_portal = transaction_date_portal1.replace(" ", "%20");
				String created_date_portal1 = cn.getCreated_Date();
				String created_date_portal = created_date_portal1.replace(" ", "%20");

				StringBuffer sb_Send_ProjectTaskStatus = new StringBuffer();
				sb_Send_ProjectTaskStatus.append(Utils.URL + "InsertProjectWorkStatusLog" + "?" + "data=" + project_id_portal + "," + site_id_portal + "," + work_master_id_portal + "," + percentage_completion + "," + work_completed_qty + "," + actual_StartDate + "," + actual_EndDate + "," + transaction_date_portal + "," + created_date_portal + "," + party_id + "," + org_id);

				url_send_Params_InsertProjectWorkStatusLog = sb_Send_ProjectTaskStatus.toString();
				System.out.println("sendProjectTasks   " + url_send_Params_InsertProjectWorkStatusLog);
				System.out.println(" data=" + project_id_portal + "," + site_id_portal + "," + work_master_id_portal + "," + percentage_completion + "," + work_completed_qty + "," + actual_StartDate + "," + actual_EndDate + "," + transaction_date_portal + "," + created_date_portal + "," + party_id + "," + org_id);
				ServerConnection hh = new ServerConnection();
				String jsonStr = hh.getmethods(url_send_Params_InsertProjectWorkStatusLog);


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

										ArrayList<Status_Model> db_list = db.find_WorkMaster_ID_SynStatus_ProjectStatus_Table(work_master_id_portal, "N");
										for (Status_Model model : db_list) {

											model.setSyn_Status("Y");

											db.Update_Project_Status_Row(model);

										}
										// display toast here
//            	        	  Toast.makeText(getApplicationContext(), "Project Status data transferred", Toast.LENGTH_SHORT).show();

									}
								});
							} else {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// display toast here
//          	        	  Toast.makeText(getApplicationContext(), "Project Status data not transferred", Toast.LENGTH_SHORT).show();
										count++;
									}
								});
							}


						}
						else {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {

									// display toast here
//    	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
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


//for loop ending
			}

			if (count > 0) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// display toast here
						Toast.makeText(getApplicationContext(), "Project Status data transfer failed, Please try again or contact administrator", Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// display toast here
						Toast.makeText(getApplicationContext(), "Project Status data successfully transfered ", Toast.LENGTH_SHORT).show();
					}
				});
			}
			return null;

		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog_Send_ProjectTasks.isShowing()) ;
			pDialog_Send_ProjectTasks.dismiss();

		}
	}
	private class sendProjectImages extends AsyncTask<Void, Void, Void> {
		String responseServer;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_Send_ProjectTasks = new ProgressDialog(StatusTabs.this);
			pDialog_Send_ProjectTasks.setMessage("Please Wait.");
			pDialog_Send_ProjectTasks.setCancelable(false);
			pDialog_Send_ProjectTasks.show();
		}
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.URL + "SendImages" + "?");
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("Data", imageData));
				Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				InputStream inputStream = response.getEntity().getContent();
				InputStreamToStringExample str = new InputStreamToStringExample();
				responseServer = str.getStringFromInputStream(inputStream);
				Log.e("response", "response -----" + responseServer);
				if (responseServer != null) {
					try {
						JSONArray jArray = new JSONArray(responseServer);
						System.out.println("length of json string is " + jArray.length());
						if (jArray.length() != 0) {

							String syn_status_value = jArray.getString(0);
							int syn_status_int = Integer.parseInt(syn_status_value);
							System.out.println("Syn ststus value" + syn_status_int);
							if (syn_status_int == 1) {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										db.UpdateImages_Row(new Project_Images(projectimages.getPROJECT_IMAGES_ID()));
									}
								});
							} else {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {

									}
								});
							}


						}
						else {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {

									// display toast here
//    	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
				}




			}
			catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println("json string>>>>>>>>" + jsonStr);
//			if (jsonStr != null) {
//				try {
//					// Extract JSON array from the response
//					JSONArray jArray = new JSONArray(jsonStr);
//					System.out.println("length of json string is " + jArray.length());
//					if (jArray.length() != 0) {
//
//						String syn_status_value = jArray.getString(0);
//						int syn_status_int = Integer.parseInt(syn_status_value);
//						System.out.println("Syn ststus value" + syn_status_int);
//						if (syn_status_int == 1) {
//							runOnUiThread(new Runnable() {
//
//								@Override
//								public void run() {
//
//
//								}
//							});
//						}
//						else {
//							runOnUiThread(new Runnable() {
//
//								@Override
//								public void run() {
//
//									// display toast here
////          	        	  Toast.makeText(getApplicationContext(), "Project Status data not transferred", Toast.LENGTH_SHORT).show();
//									count++;
//								}
//							});
//						}
//
//
//					}
//					else {
//						runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//
//								// display toast here
////    	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
//							}
//						});
//
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//			else {
//				Log.e("ServiceHandler", "Couldn't get any data from the url");
//				runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//
//						// display toast here
//						Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
			return null;
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog_Send_ProjectTasks.isShowing()) ;
			pDialog_Send_ProjectTasks.dismiss();

		}
	}

	private class GetProjectTasksOverloaded extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_GetProjectTasks = new ProgressDialog(StatusTabs.this);

			pDialog_GetProjectTasks.setMessage("Please Wait.");
			pDialog_GetProjectTasks.setCancelable(false);
//     	 pDialog_GetProjectTasks.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);

			String party_Id_String = String.valueOf(party_id);
			String site_Id_String = String.valueOf(selected_SiteID);
			String project_Id_String = String.valueOf(project_id);
			String org_Id_String = String.valueOf(org_id);
			String module_Id_String = String.valueOf(5);

			StringBuffer sb_ProjectTasks = new StringBuffer();
//			sb_ProjectTasks.append(Utils.URL + "GetProjectTasksOverloaded" + "?" + "PartyId=" + party_Id_String + "&SiteId=" + site_Id_String + "&PrjId=" + project_Id_String + "&OrgId=" + org_Id_String + "&MobModuleId=" + module_Id_String);
			sb_ProjectTasks.append(Utils.URL + "GetMyProjectTasksOverloaded" + "?" + "PartyId=" + party_Id_String + "&SiteId=" + site_Id_String + "&PrjId=" + project_Id_String + "&OrgId=" + org_Id_String);
			url_send_Params_GetProjectTasks = sb_ProjectTasks.toString();
			System.out.println("send to server  " + url_send_Params_GetProjectTasks);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(url_send_Params_GetProjectTasks);
			System.out.println("json string>>>>>>>>" + jsonStr);
			if (jsonStr != null) {
				try {
					// Extract JSON array from the response
					JSONArray jArray = new JSONArray(jsonStr);
					System.out.println(jArray.length());
					if (jArray.length() != 0) {
						for (int i = 0; i < jArray.length(); i++) {
							JSONObject jObject = jArray.getJSONObject(i);

							String project_id_portal = jObject.getString("ProjectId");
							String site_id_portal = jObject.getString("PrjSiteId");
							String prj_EST_WorkMaster_Id_portal = jObject.getString("PrjEstWorkMasterId");
							String prj_EST_Work_Location_Name_portal = jObject.getString("PrjEstWorkLocationName");
							String remarks_portal = jObject.getString("Remarks");
							String prj_Work_EST_Start_Date_portal = jObject.getString("PrjWorkEstStartDate");
							String prj_Work_EST_End_Date_portal = jObject.getString("PrjWorkEstEndDate");

//							SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//							SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
							Date date_Start_Date = null;
							Date date_End_Date = null;
							try {
								date_Start_Date = Utils.sdf.parse(prj_Work_EST_Start_Date_portal);
								date_End_Date = Utils.sdf.parse(prj_Work_EST_End_Date_portal);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String prj_Work_EST_Start_Date_portal_formated = Utils.sdf.format(date_Start_Date);
							String prj_Work_EST_End_Date_portal_formated = Utils.sdf.format(date_End_Date);

							String percentage_Completion_ = jObject.getString("PercentageCompletion");
							String percentage_Completion_portal;
							if (percentage_Completion_ != null) {
								percentage_Completion_portal = percentage_Completion_;
							} else {
								percentage_Completion_portal = "0.00";
							}
							String prj_EST_Work_COM_Qty_portal = jObject.getString("PrjEstWorkCompQty");
							String prj_EST_Work_TOT_Qty_portal = jObject.getString("PrjEstWorkTotQty");
							String prj_EST_Work_TOT_Qty_Units_portal = jObject.getString("MatrlUnitName");
							String constant_String_portal = jObject.getString("Flag");
							String workTaskType_Name = jObject.getString("WorkTaskTypeName");
							String workTaskType_ID = jObject.getString("WorkTaskTypeId");
							String mainTask_ID = jObject.getString("WorkMainTaskId");
							String mainTask_Name = jObject.getString("WorkMainTaskName");
							String link_toBill = jObject.getString("LinkToBill");

//    			calculations for sum_entered_Qty
							String mSum_Entered_Qty;
							if (link_toBill.equals("YES")) {
								mSum_Entered_Qty = prj_EST_Work_COM_Qty_portal;
							} else {
								mSum_Entered_Qty = "0.00";
							}

							int project_id_int = Integer.parseInt(project_id_portal);
							int site_id_int = Integer.parseInt(site_id_portal);
							int prj_EST_WorkMaster_id_int = Integer.parseInt(prj_EST_WorkMaster_Id_portal);
							int workTaskType_ID_int = Integer.parseInt(workTaskType_ID);
							int mainTask_ID_int = Integer.parseInt(mainTask_ID);

							System.out.println("consta value" + constant_String_portal);

							if (constant_String_portal.equals("I")) {
//				 						inserting in database
								db.add_Project_Status_Record(new Status_Model(project_id_int, site_id_int, prj_EST_WorkMaster_id_int, prj_EST_Work_Location_Name_portal, percentage_Completion_portal, prj_EST_Work_TOT_Qty_portal, prj_EST_Work_TOT_Qty_Units_portal, prj_EST_Work_COM_Qty_portal, prj_Work_EST_Start_Date_portal_formated, prj_Work_EST_End_Date_portal_formated, "00-00-0000", "00-00-0000", "Y", mDate_Time_Now, mDate_Time_Now, "Y", remarks_portal, workTaskType_ID_int, mainTask_ID_int, mainTask_Name, link_toBill, mSum_Entered_Qty));
								System.out.println("inserting in table");
							}
							else if (constant_String_portal.equals("DI")) {
								if ((db.CountRecords_Matched_SiteId_WorkMasterId_ProjectStatus_Table(site_id_int, prj_EST_WorkMaster_id_int)) == 0) {
									db.add_Project_Status_Record(new Status_Model(project_id_int, site_id_int, prj_EST_WorkMaster_id_int, prj_EST_Work_Location_Name_portal, percentage_Completion_portal, prj_EST_Work_TOT_Qty_portal, prj_EST_Work_TOT_Qty_Units_portal, prj_EST_Work_COM_Qty_portal, prj_Work_EST_Start_Date_portal_formated, prj_Work_EST_End_Date_portal_formated, "00-00-0000", "00-00-0000", "Y", mDate_Time_Now, mDate_Time_Now, "Y", remarks_portal, workTaskType_ID_int, mainTask_ID_int, mainTask_Name, link_toBill, mSum_Entered_Qty));
								}
								else {

									Status_Model model = db.find_WorkMasterId_SiteID_DisplayFlag_ProjectStatus(prj_EST_WorkMaster_id_int, site_id_int, "Y");
									if (model != null) {
										model.setProject_ID(project_id_int);
										model.setSite_ID(site_id_int);
										model.setPRJ_EST_WorkMaster_ID(prj_EST_WorkMaster_id_int);
										model.setPRJ_EST_WorkLocation_Name(prj_EST_Work_Location_Name_portal);
										model.setPercentage_Completion(percentage_Completion_portal);
										model.setPRJ_EST_Work_TOT_QTY(prj_EST_Work_TOT_Qty_portal);
										model.setPRJ_EST_Work_TOT_QTY_Units(prj_EST_Work_TOT_Qty_Units_portal);
										model.setPRJ_EST_Work_COM_QTY(prj_EST_Work_COM_Qty_portal);
										model.setPRJ_Work_EST_Start_Date(prj_Work_EST_Start_Date_portal_formated);
										model.setPRJ_Work_EST_End_Date(prj_Work_EST_End_Date_portal_formated);
										model.setStart_Date("00-00-0000");
										model.setEnd_Date("00-00-0000");
										model.setSyn_Status("Y");
										model.setCreated_Date(mDate_Time_Now);
										model.setTransaction_Date(mDate_Time_Now);
										model.setDisplay_Flag("Y");
										model.setRemarks(remarks_portal);
										model.setWork_TaskType_ID(workTaskType_ID_int);
										model.setMain_Task_ID(mainTask_ID_int);
										model.setMain_Task_Name(mainTask_Name);
										model.setLink_ToBill(link_toBill);
										model.setSum_Entered_Qty(mSum_Entered_Qty);
										db.Update_Project_Status_Row(model);
									}
									db.Update_Project_Status_Row(model);
								}

							}
						}
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// display toast here
								Toast.makeText(getApplicationContext(), "Project Tasks collected", Toast.LENGTH_SHORT).show();

							}
						});
					}
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
					}
				});
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog_GetProjectTasks.isShowing()) ;
			pDialog_GetProjectTasks.dismiss();
//
		}
	}

	private class SendIssues extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_SendIssues = new ProgressDialog(StatusTabs.this);
			pDialog_SendIssues.setMessage("Please Wait.");
			pDialog_SendIssues.setCancelable(false);
//		pDialog_SendIssues.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);
			String party_Id_String = String.valueOf(party_id);
			String org_Id_String = String.valueOf(org_id);
			ArrayList<Status_Model> send_issues_NotSyncd = db.find_SiteID_SynFlag_ProjectIssuesList(selected_SiteID, "N");
			for (Status_Model cn : send_issues_NotSyncd) {
				final int issues_Id = cn.getProject_Issues_ID();
				int project_Id_Portal = cn.getProject_ID_Issues();
				int site_Id_portal = cn.getSite_ID_ISSUES();
				int task_Id_portal = cn.getPRJ_EST_WorkMaster_ID_ISSUES();
				String task_name_portal1 = cn.getPRJ_EST_WorkLocation_Name_ISSUES();
				String task_name_portal = task_name_portal1.replace(" ", "%20");
				String issues_title_portal1 = cn.getIssues_Title_ISSUES();
				String issues_title_portal = issues_title_portal1.replace(" ", "%20");
				String issues_details_portal1 = cn.getIssues_Details_ISSUES();
				String issues_details_portal = issues_details_portal1.replace(" ", "%20");
				String issues_creation_date_portal1 = cn.getIssues_CreationDateTime_ISSUES();
				String issues_creation_date_portal11=null;
//				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				try{
					Date date1=Utils.sdf.parse(issues_creation_date_portal1);
					issues_creation_date_portal11 = Utils.sdf.format(date1);
				}
				catch (Exception e){
					Date d =new Date();
					issues_creation_date_portal11 = Utils.sdf.format(d);
				}
				String issues_creation_date_portal = issues_creation_date_portal11.replace(" ", "%20");
				String project_Id_String = String.valueOf(project_Id_Portal);
				String site_Id_String = String.valueOf(site_Id_portal);
				String task_Id_String = String.valueOf(task_Id_portal);
				StringBuffer sb_SendIssues = new StringBuffer();
				sb_SendIssues.append(Utils.URL + "InsertProjectWorkIssuesDetails?data=" + project_Id_String + "," + site_Id_String + "," + task_Id_String + "," + task_name_portal + "," + issues_title_portal.trim() + "," + issues_details_portal + "," + issues_creation_date_portal + "," + party_Id_String + "," + org_Id_String);
				url_sendIssues_InsertProjectWorkIssuesDetails = sb_SendIssues.toString();
				System.out.println("send to server    " + url_sendIssues_InsertProjectWorkIssuesDetails);
				ServerConnection hh = new ServerConnection();
				String jsonStr = hh.getmethods(url_sendIssues_InsertProjectWorkIssuesDetails);
				System.out.println("json string>>>>>>>>" + jsonStr);
				if (jsonStr != null) {
					String str = jsonStr.replaceAll("[\\[\\]]", "");
					if(str!=null){
						syn_status_int = Integer.parseInt(str.trim());
						System.out.println("Syn ststus value" + syn_status_int);
						if (syn_status_int > 0) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ArrayList<Status_Model> db_list = db.find_IssuesID_SynFlag_ProjectIssuesList(issues_Id, "N");
									for (Status_Model model : db_list) {
										model.setSyn_Flag_ISSUES("Y");
										model.setProtal_ISSUES_id(syn_status_int);
										db.Update_ProjectIssuesRow(model);
									}
									Toast.makeText(getApplicationContext(), "Issues data transferred", Toast.LENGTH_SHORT).show();
								}
							});
						}
						else {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									// display toast here
									Toast.makeText(getApplicationContext(), "Issues data not transferred", Toast.LENGTH_SHORT).show();
								}
							});
						}
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
			}
			return null;
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog_SendIssues.isShowing()) ;
			pDialog_SendIssues.dismiss();
//			new SendIssuessImages().execute();
			sendingimgs();
		}
	}
	private class SendIssuessImages extends AsyncTask<Void, Void, Void> {
		String responseServer;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			pDialog_SendImages=new ProgressDialog(StatusTabs.this);
//			pDialog_SendImages.setMessage("Please Wait.");
//			pDialog_SendImages.setCancelable(false);
//			pDialog_SendImages.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
//			ServerConnection hh = new ServerConnection();
//			String stream = hh.editfee(newuserssetpis, methode);



			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Utils.URL + "UploadIssueImage" + "?");
			try {

				db = new DatabaseHandler(StatusTabs.this);
				JSONArray jArray1 = new JSONArray();
				ArrayList<Status_Model> send_images_NotSyncd = db.find_IssueID_SynFlag_ProjectImages(selected_SiteID, "N");
				for (Status_Model cn : send_images_NotSyncd) {
					int issueid = Integer.parseInt(cn.getIssues_id());
					if (issueid > 0) {
						String portalid = db.find_PortalId(issueid);
						if (portalid != null) {
							final int primary_id_portal = cn.getPROJECT_ISSUES_IMAGES_ID();
							primary_id_portals=cn.getPROJECT_ISSUES_IMAGES_ID();
							String image_path = cn.getPATH_PRJ_ISSUES_IMAGES();
							File imgFile = new File(image_path);
							Bitmap myBitmap = null;
							if (imgFile.exists()) {
								myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
							}
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P)
							  myBitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
							else
								myBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
							byte[] b = baos.toByteArray();
							String encodedImage_portal = Base64.encodeToString(b, Base64.DEFAULT);
							jArray1.put(encodedImage_portal);
							StringBuffer sbuffer = new StringBuffer();
							sbuffer.append(portalid + "," + party_id + "," + encodedImage_portal);
							System.out.println(" json String is" + jArray1.toString());
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Data", sbuffer.toString()));
							Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
							HttpResponse response = httpclient.execute(httppost);
							InputStream inputStream = response.getEntity().getContent();
							InputStreamToStringExample str = new InputStreamToStringExample();
							responseServer = str.getStringFromInputStream(inputStream);
							Log.e("response", "response -----" + responseServer);
							if (responseServer != null) {
								try {
									JSONArray jArray = new JSONArray(responseServer);
									System.out.println("length of json string is " + jArray.length());
									if (jArray.length() != 0) {
										for (int i = 0; i < jArray.length(); i++) {
											String syn_status_reponse = jArray.getString(0);
											int syn_status_reponse_result = Integer.parseInt(syn_status_reponse);
											if (syn_status_reponse_result == 1) {
												ArrayList<Status_Model> db_list = db.find_PrimaryKeyID_SynFlag_ProjectIssueImages(primary_id_portal, "N");
												for (Status_Model model : db_list) {
													model.setSYN_FLAG_PRJ_ISSUES_IMAGES("Y");
													db.Update_ProjectIsseusImagesRow(model);
												}


											} else {
												// display toast here
												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// display toast here
														Toast.makeText(getApplicationContext(), " Images not transferred", Toast.LENGTH_SHORT).show();
													}
												});


											}

										} //for loop end

									} else {
										// display toast here
//                          	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							else {
								Log.e("ServiceHandler", "Couldn't get any data from the url");
								Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
							}


						}
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
//			if(pDialog_SendImages.isShowing());
//			pDialog_SendImages.dismiss();
//			System.out.println(" result  is"+responseServer);

		}  // postexecute_method end


	}
	public class UploadImages extends AsyncTask<String, Void, String> {
		InputStream in = null;
		HttpURLConnection conn = null;

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			String stream = null;
			String Datarquest=params[0];
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Data", Datarquest);
				URL url = new URL(Utils.URL + "UploadIssueImage?");
				Log.d("inserturlfeeedti", "inserturlfeeedti" + url);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDefaultUseCaches(false);
				conn.setAllowUserInteraction(true);
				HttpResponseCache.setDefault(null);
				conn.setRequestProperty("Content-Type", "application/json");
				Log.d("insertdata", "insertdata" + jsonObject.toString());
				OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
				os.write(jsonObject.toString());
				os.flush();
				if (conn.getResponseCode() == 200) {
					in = new BufferedInputStream(conn.getInputStream());
					BufferedReader r = new BufferedReader(new InputStreamReader(in));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null)
						sb.append(line);
					stream = sb.toString();
					System.out.println("Output11" + stream);
					conn.disconnect();
				}
				else {
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (in != null) {
						in.close();
					}
					conn.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					stream = "ERROR";
				}
			}
			return stream;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONArray jArray = new JSONArray(result);
					System.out.println("length of json string is " + jArray.length());
					if (jArray.length() != 0) {
						for (int i = 0; i < jArray.length(); i++) {
							String syn_status_reponse = jArray.getString(0);
							int syn_status_reponse_result = Integer.parseInt(syn_status_reponse);
							if (syn_status_reponse_result == 1) {
								ArrayList<Status_Model> db_list = db.find_PrimaryKeyID_SynFlag_ProjectIssueImages(primary_id_portals, "N");
								for (Status_Model model : db_list) {
									model.setSYN_FLAG_PRJ_ISSUES_IMAGES("Y");
									db.Update_ProjectIsseusImagesRow(model);
								}


							} else {
								// display toast here
								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// display toast here
										Toast.makeText(getApplicationContext(), " Images not transferred", Toast.LENGTH_SHORT).show();
									}
								});


							}

						} //for loop end

					} else {
						// display toast here
//                          	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
					}
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
				Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
			}


		}
	}

	public static class InputStreamToStringExample {

		public static void main(String[] args) throws IOException {

			// intilize an InputStream
			InputStream is =
					new ByteArrayInputStream("file content..blah blah".getBytes());

			String result = getStringFromInputStream(is);

			System.out.println(result);
			System.out.println("Done");

		}

		// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return sb.toString();
		}

	}

	private class UpdateAppVersion extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_AppVersion = new ProgressDialog(StatusTabs.this);

			pDialog_AppVersion.setMessage("Please Wait.");
			pDialog_AppVersion.setCancelable(false);
//	 pDialog_AppVersion.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);


			String org_id_string = String.valueOf(org_id);
			String party_id_string = String.valueOf(party_id);

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
//                           	        	  Toast.makeText(getApplicationContext(), "version inserted"+versionName, Toast.LENGTH_SHORT).show();
								}
							});

						} else {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									// display toast here
//			                 	        	  Toast.makeText(getApplicationContext(), "Error in version"+versionName, Toast.LENGTH_SHORT).show();
								}
							});


						}


					} else {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// display toast here
//        	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();

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

	private class Send_Params_SynMaster extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_SynMaster = new ProgressDialog(StatusTabs.this);

			pDialog_SynMaster.setMessage("Please Wait.");
			pDialog_SynMaster.setCancelable(false);
//	 pDialog_SynMaster.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);


			String org_id_string = String.valueOf(org_id);
			String party_id_string = String.valueOf(party_id);
			String selected_SiteID_string = String.valueOf(selected_SiteID);
			String module_Id_string = String.valueOf(module_Id);

			StringBuffer synMaster_sb = new StringBuffer();

			synMaster_sb.append(Utils.URL + "InsertSyncMaster" + "?" + "PartyId=" + party_id_string + "&SiteId=" + selected_SiteID_string + "&OrgId=" + org_id_string + "&MobModuleId=" + module_Id_string);

			url_send_Params_SynMaster = synMaster_sb.toString();
			System.out.print("send to server  " + url_send_Params_SynMaster);
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
//        	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();

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

	private class SendStatusDeviationMail extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			db = new DatabaseHandler(StatusTabs.this);
			String party_id_string = String.valueOf(party_id);
			String project_Id_string = String.valueOf(project_id);
			String selected_SiteID_string = String.valueOf(selected_SiteID);
			String org_id_string = String.valueOf(org_id);
			StringBuffer deviationMail_sb = new StringBuffer();
			deviationMail_sb.append(Utils.URL + "SendStatusDeviationMail" + "?" + "PartyId=" + party_id_string + "&ProjectId=" + project_Id_string + "&SiteId=" + selected_SiteID_string + "&OrgId=" + org_id_string);
			url_send_Params_DeviationMail = deviationMail_sb.toString();
			System.out.print("send to server" + url_send_Params_DeviationMail);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(url_send_Params_DeviationMail);
			System.out.println("json string>>>>>>>>" + jsonStr);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}


	}

	private class GetIssuesAndImages extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... strings) {
			String stream = null;
			String urlString = strings[0];
			ServerConnection hh = new ServerConnection();
			stream = hh.getmethods(urlString);
			return stream;
		}

		protected void onPostExecute(String stream) {
			super.onPostExecute(stream);
			System.out.println("json string>>>>>>>>" + stream);
			if (stream != null&&!stream.equalsIgnoreCase("ERROR"))
			{
				try {
					JSONArray jsonArray = new JSONArray(stream);
					if (jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							issuesandimades = Integer.parseInt(jsonObject.getString("IssueId"));
							String issuesandimadesval = db.find_Portal_Issues_Id(issuesandimades);
							if (issuesandimadesval != null) {
								Boolean b = false;
								b = file_download(jsonObject.getString("Image1"), jsonObject.getString("ImageName"));
								if (b) {
									GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jsonObject.getString("ImageName");
//								String imgpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/"+jsonObject.getString("ImageName");
									db.add_Project_Issues_Images_Record(new Status_Model(issuesandimadesval, String.valueOf(project_id), String.valueOf(selected_SiteID), String.valueOf(work_master_id), GridViewDemo_ImagePath, "Y", mDate_Time_Now, "Y"));
								}
							}
							else {
								vAnInt = db.add_Project_issues_Record(new Status_Model(project_id, selected_SiteID, Integer.parseInt(jsonObject.getString("TaskId")), jsonObject.getString("TaskName"), jsonObject.getString("IssueTitle"), jsonObject.getString("IssueDescription"), "Y", mDate_Time_Now, issuesandimades, "Y"));
								Boolean b = false;
								b = file_download(jsonObject.getString("Image1"), jsonObject.getString("ImageName"));
								if (b) {
									GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jsonObject.getString("ImageName");
//								String imgpath = RetriveCapturedImagePath();
									db.add_Project_Issues_Images_Record(new Status_Model(String.valueOf(vAnInt), String.valueOf(project_id), String.valueOf(selected_SiteID), String.valueOf(work_master_id), GridViewDemo_ImagePath, "Y", mDate_Time_Now, "Y"));
								}

							}
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			new SendIssues().execute();

		}


	}

	private String RetriveCapturedImagePath() {
		File f = new File(GridViewDemo_ImagePath);
		String imgpath = null;
		if (f.exists()) {
			File[] files = f.listFiles();
			Arrays.sort(files);
			File file = files[files.length - 1];
			imgpath = file.getPath();

		}
		return imgpath;
	}

	public boolean file_download(String uRl, String name) {
		boolean b = false;
		File direct = new File(Environment.getExternalStorageDirectory() + "/Buildman/receive");
		if (!direct.exists()) {
			direct.mkdirs();
		}

		DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
		Uri downloadUri = Uri.parse(uRl);
		DownloadManager.Request request = new DownloadManager.Request(downloadUri);
		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false).setTitle("Demo")
				.setDescription("Something useful. No, really.")
				.setDestinationInExternalPublicDir("/Buildman/receive", name);
		mgr.enqueue(request);
		return true;
	}

	private class GetNewSites extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog_DeviationMail = new ProgressDialog(StatusTabs.this);
			pDialog_DeviationMail.setMessage("Please Wait.");
			pDialog_DeviationMail.setCancelable(false);
//	 pDialog_DeviationMail.show();
		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Creating service handler class instance
			String party_id_string = String.valueOf(party_id);
			String selected_SiteID_string = String.valueOf(selected_SiteID);
			String org_id_string = String.valueOf(org_id);
			db = new DatabaseHandler(StatusTabs.this);
//			String mMy_Version=mVersion_txv.getText().toString();
			String mMy_Version = versionName;
			String module_id_string = String.valueOf(0);
			StringBuffer newSites_sb = new StringBuffer();
			newSites_sb.append(Utils.URL + "GetNewProjectDetails" + "?" + "PartyId=" + party_id_string + "&OrgId=" + org_id_string + "&MobModuleId=" + module_id_string);
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
			if (pDialog_DeviationMail.isShowing()) ;
			pDialog_DeviationMail.dismiss();

		}
	}
	public static String getFileToByte(String filePath){
		Bitmap bmp = null;
		ByteArrayOutputStream bos = null;
		byte[] bt = null;
		String encodeString = null;
		try{
			bmp = BitmapFactory.decodeFile(filePath);
			bos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bt = bos.toByteArray();
			encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
		}catch (Exception e){
			e.printStackTrace();
		}
		return encodeString;
	}


	public  String convert(Bitmap bitmap)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
		return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
	}
	public void sendingimgs(){
		db = new DatabaseHandler(StatusTabs.this);
		ArrayList<Status_Model> send_images_NotSyncd = db.find_IssueID_SynFlag_ProjectImages(selected_SiteID, "N");
		for (Status_Model cn : send_images_NotSyncd) {
			int issueid = Integer.parseInt(cn.getIssues_id());
			if (issueid > 0) {
				portalid = db.find_PortalId(issueid);
				if (portalid != null) {
					final int primary_id_portal = cn.getPROJECT_ISSUES_IMAGES_ID();
					primary_id_portals=cn.getPROJECT_ISSUES_IMAGES_ID();
					String image_path = cn.getPATH_PRJ_ISSUES_IMAGES();
					File imgFile = new File(image_path);
					Bitmap myBitmap = null;
					if (imgFile.exists()) {
						myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						imageValue1 = getStringImage(myBitmap);
						new UpdateClinicPics().execute();
					}

				}
			}
		}
	}
	public String getStringImage(Bitmap bmp){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 5, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
		return encodedImage;
	}
	public class UpdateClinicPics extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}
		@Override
		protected String doInBackground(String... params) {
			String stream = null;
			String methode="PostUploadIssueImage";
			JSONObject jsonObject =new JSONObject();
			try{
				jsonObject.put("IssueId1",portalid);
				jsonObject.put("PartyId1",party_id);
				jsonObject.put("Img",imageValue1);
				ServerConnection serverConnection=new ServerConnection();
				stream=serverConnection.editfee(jsonObject,methode);
			}
			catch (Exception e){}
			return stream;
		}

		@Override
		protected void onPostExecute(String responseServer) {

			super.onPostExecute(responseServer);

			Log.d("resultcheck", "resultcheck" + responseServer);
			if (responseServer != null) {
				try {
					JSONArray jArray = new JSONArray(responseServer);
					System.out.println("length of json string is " + jArray.length());
					if (jArray.length() != 0) {
						for (int i = 0; i < jArray.length(); i++) {
							String syn_status_reponse = jArray.getString(0);
							int syn_status_reponse_result = Integer.parseInt(syn_status_reponse);
							if (syn_status_reponse_result == 1) {
								ArrayList<Status_Model> db_list = db.find_PrimaryKeyID_SynFlag_ProjectIssueImages(primary_id_portals, "N");
								for (Status_Model model : db_list) {
									model.setSYN_FLAG_PRJ_ISSUES_IMAGES("Y");
									db.Update_ProjectIsseusImagesRow(model);
								}


							}
						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}


}
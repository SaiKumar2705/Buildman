package com.buildman.buildman.materialtabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Received_Material_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.model.UserSites_Model;

import java.util.ArrayList;
import java.util.List;

public class
Received_Material extends Activity {
    private TextView mMatl_header_txv, mUnits_headertxv, mCur_Stk_header_txv, mRecd_Qty_header_txv;
    private ListView mListview;
    private Button mAdd_btn;
    private int selected_SiteID, party_id_fmLoginDB, org_id_fmLoginDB;
    DatabaseHandler db;
    Received_Material_Model received_model;
    ArrayList<Received_Material_Model> material_list = new ArrayList<Received_Material_Model>();
    ArrayList<Integer> material_id_list = new ArrayList<Integer>();
    ArrayList<String> matl_name_list = new ArrayList<String>();
    ArrayList<String> currentStock_list = new ArrayList<String>();
    ArrayList<String> units_list = new ArrayList<String>();
    ArrayList<String> receivedStock_list = new ArrayList<String>();
    ArrayList<String> dc_invoice_list = new ArrayList<String>();
    ArrayList<String> rate_list = new ArrayList<String>();
    ArrayList<String> display_flag_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received_material);
        received_model = new Received_Material_Model();
        db = new DatabaseHandler(this);
//		      creating methods to write code
        getIds();
        getIds_FmLoginTable();


        if ((db.get_Material_Stk_TransTable_Row_Count() == 0)) {
            create_DB();
        } else {

            System.out.println("DB has data ");
        }
        getList_fromDB();
    }

    @Override
    public void onBackPressed() {
        // do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Received_Material.this, Home.class);
        startActivity(i);
        finish();
        return;
    }

    private void getList_fromDB() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(this);
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
//        get selected site_id from usersite class    

        SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
        selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
        System.out.println("selected id is" + selected_SiteID);
        List<Received_Material_Model> list = db.find_SiteID_Material_Stk_TransList(selected_SiteID, "Y");
        System.out.println("list size with siteid" + list.size());
        LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_recived_matl);
        if (list.size() == 0) {

            relative.setBackgroundResource(0);
        } else {
            relative.setBackgroundResource(R.drawable.box_bottom_border);
        }
        for (Received_Material_Model cn : list) {

            int material_id = cn.getMaterial_ID_MST();
            String material = cn.getMaterial_Name_MST();
            System.out.println("materials" + material);
            String units = cn.getUnits_MST();
            String currentStock = cn.getCurrent_Stock_MST();
            String dc_invoice = cn.getDC_Invoice_MST();
            String receivedStock = cn.getReceived_Stock_MST();
            String rate = cn.getRate_MST();
            String display_flag = cn.getDisplay_Flag_MST();
            Received_Material_Model appadd = new Received_Material_Model(material, units, currentStock, receivedStock);
            material_list.add(appadd);

            material_id_list.add(material_id);
            matl_name_list.add(material);
            currentStock_list.add(currentStock);
            dc_invoice_list.add(dc_invoice);
            units_list.add(units);
            receivedStock_list.add(receivedStock);
            rate_list.add(rate);
            display_flag_list.add(display_flag);
        }
        Received_Material_Adapter adapter = new
                Received_Material_Adapter(Received_Material.this, R.layout.received_material_row, material_list);

        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                UserSites_Model model = db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
                if (model != null) {
                    String userType_inDB = model.getUserType();
//	             		Toast.makeText(getApplicationContext(), 
//             					"User-"+userType_inDB, Toast.LENGTH_SHORT).show();
                    if (userType_inDB.equals("PRIMRY")) {
                        Received_Material_Model received_model = (Received_Material_Model) parent.getItemAtPosition(position);

                        Intent i = new Intent(Received_Material.this, Received_Material_Description.class);

                        i.putIntegerArrayListExtra("matl_id_list_send", material_id_list);
                        i.putStringArrayListExtra("matl_name_list_send", matl_name_list);
                        i.putStringArrayListExtra("currentStock_list_send", currentStock_list);
                        i.putStringArrayListExtra("dc_invoice_list_send", dc_invoice_list);
                        i.putStringArrayListExtra("units_list_send", units_list);
                        i.putStringArrayListExtra("receivedStock_list_send", receivedStock_list);
                        i.putStringArrayListExtra("rate_list_send", rate_list);
                        i.putStringArrayListExtra("display_flag_list_send", display_flag_list);

                        int item_id = (int) parent.getItemIdAtPosition(position);
                        i.putExtra("matl_name_id", item_id);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "User type is restricted", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void getIds_FmLoginTable() {
        // TODO Auto-generated method stub
        //  get party_id and org_id fm Login table which always have only 1 row
        db = new DatabaseHandler(Received_Material.this);
        Login_Model login_model = db.get_FirstRow_Login(1);
        party_id_fmLoginDB = login_model.getParty_ID();
        org_id_fmLoginDB = login_model.getOrg_ID();
    }

    private void create_DB() {
        // TODO Auto-generated method stub
        Log.d("Insert: ", "Insertingsssssssssss ..");
//		 db = new DatabaseHandler(this); 
//		  db.add_Material_Record( new Received_Material_Model( "Membrane","Nos.", "0.00","0", "0.00","0.00","200.00"));  

    }


    private void getIds() {
        // TODO Auto-generated method stub

        mListview = (ListView) findViewById(R.id.listview_received_material);

    }


}

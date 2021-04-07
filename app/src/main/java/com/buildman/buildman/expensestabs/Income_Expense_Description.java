package com.buildman.buildman.expensestabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Expense_Income_Model;
import com.buildman.buildman.model.Status_Model;

public class Income_Expense_Description extends Activity{
	private TextView mIncomeTitle_Txv,mDate_title_Txv,mDate_Result_Txv,mTime_Txv,mAmountTitle_Txv,mPayerTitle_txv,
	mCategoryTitle_Txv,mCategory_result_Txv,mContractorTitle_Txv,mContractor_result_Txv,mPay_methodTitle_txv,
	mPay_method_result_txv,mCheck_Status_Result_Txv;
	private Button  mDate_Btn,mAmount_Cal_Btn,mPayer_Btn,mCategory_Btn,mContractor_Btn,mPay_method_Btn,
	mCheck_status_Btn,mTax_Cal_Btn,mConfirm_Btn;
	private EditText mAmount_Edt,mPayer_Edt,mCheck_Edt,mDescription_Edt,mTax_Edt;
	private ImageView mBackImg,mCamera_Img;
	private String mTime;
	private int numberOfImages;
	private String iname,selected_ImagePath;
	DatabaseHandler db;


		 @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.income_expense_description);
		        getId(); 
		      
//		        date calculating
		        currentDate();
		        datePicker();		         
//		        time calculating		         
		        currentTime();
		        timePicker(); 
//		        amount calculator
		        amount_calculator();
		        get_Calculated_Amt(); 
		        payer_btnClick();
		        get_Payer_SelectedItem();
		        category_BtnClick();
			    get_Category_Selected_Item();
			    contractor_BtnClick();
			    get_Contractor_Selected_Item();		          
		        payMethod_btnClick();
		        get_PayMethod_SelectedItem();
		        check_Status_btnClick();
		        get_CheckStatus_SelectedItem();
		        camera_btnClick();
		        tax_btnClick();
		        get_Tax_Calculated_Amt();
		        back_BtnClick();
		        confirm_BtnClick(); 
	}
		
		@Override
	public void onBackPressed() {
		 				// do something on back.
//		 			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
			 Intent i=new Intent(Income_Expense_Description.this,ExpenseTabs.class);	
				startActivity(i);				
				finish();
		 		return;
		}		
		
	private void currentDate() {
				// TODO Auto-generated method stub
				 Calendar c = Calendar.getInstance();
		           int mYear = c.get(Calendar.YEAR);
		           int mMonth = c.get(Calendar.MONTH);
		           int mDay = c.get(Calendar.DAY_OF_MONTH);
		           mDate_Result_Txv.setText(String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear));	
	}
	private void datePicker() {
		// TODO Auto-generated method stub
		mDate_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
	           int mYear = c.get(Calendar.YEAR);
	           int mMonth = c.get(Calendar.MONTH);
	           int mDay = c.get(Calendar.DAY_OF_MONTH);
	 
	            // Launch Date Picker Dialog
	            DatePickerDialog dpd = new DatePickerDialog(Income_Expense_Description.this,
	                    new DatePickerDialog.OnDateSetListener() {
	 
	                        @Override
	                        public void onDateSet(DatePicker view, int year,
	                                int monthOfYear, int dayOfMonth) {
	                            // Display Selected date in textbox
	                            mDate_Result_Txv.setText(dayOfMonth + "-"
	                                    + (monthOfYear + 1) + "-" + year);
	 
	                        }
	                    }, mYear, mMonth, mDay);
	            dpd.show();
			}
		});
	}

	 private void currentTime() {
					// TODO Auto-generated method stub
		 Calendar c = Calendar.getInstance();
		  int mHour = c.get(Calendar.HOUR_OF_DAY);
          int  mMinute = c.get(Calendar.MINUTE);
          mTime_Txv.setText(String.valueOf(mHour)+":"+String.valueOf(mMinute));				
	}
	 private void timePicker() {
			// TODO Auto-generated method stub
			mTime_Txv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Process to get Current Time
		            final Calendar c = Calendar.getInstance();
		           int mHour = c.get(Calendar.HOUR_OF_DAY);
		          int  mMinute = c.get(Calendar.MINUTE);
		 
		            // Launch Time Picker Dialog
		            TimePickerDialog tpd = new TimePickerDialog(Income_Expense_Description.this,
		                    new TimePickerDialog.OnTimeSetListener() {
		 
		                        @Override
		                        public void onTimeSet(TimePicker view, int hourOfDay,
		                                int minute) {
		                            // Display Selected time in textbox
		                        	mTime=(hourOfDay + ":" + minute);
		                        	mTime_Txv.setText(mTime);
		                        }
		                    }, mHour, mMinute, false);
		            tpd.show();
//					Toast.makeText(getApplicationContext(), "u clicked", Toast.LENGTH_LONG).show();
				}
			});
		}
	 private void amount_calculator() {
			// TODO Auto-generated method stub
			mAmount_Cal_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Amount_Calculator.class);
					startActivity(i);
					
				}
			
			});
		}
		private void get_Calculated_Amt() {
			// TODO Auto-generated method stub
//			String calculated_amt=getIntent().getStringExtra("calculated_value_amt");
			SharedPreferences calculated_amt_pref = getSharedPreferences("Calculated_amt_MyPrefs", Context.MODE_PRIVATE);		 
			String calculated_amt = calculated_amt_pref.getString("Calculated_amt_KEY_PREF", "");
			
			mAmount_Edt.setText(calculated_amt);
		}		
		private void payer_btnClick() {
			// TODO Auto-generated method stub
			mPayer_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Payer_List.class);
					startActivity(i);
				}
			});
		}
		private void get_Payer_SelectedItem() {
			// TODO Auto-generated method stub
//			String payer_selected_item=getIntent().getStringExtra("payer_selected_item");
//			get data for age from consultation class
			SharedPreferences payer_item_pref = getSharedPreferences("Payer_item_MyPrefs", Context.MODE_PRIVATE);		 
			String payer_selected_item = payer_item_pref.getString("Payer_item_KEY_PREF", "");
			mPayer_Edt.setText(payer_selected_item);
		}
		private void category_BtnClick() {
			// TODO Auto-generated method stub
			
			mCategory_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Category_List.class);
					startActivity(i);
					finish();
				}
			});
		}
		private void get_Category_Selected_Item() {
			// TODO Auto-generated method stub
//			String category_selected_item=getIntent().getStringExtra("Category_item_selected");
//			mPresent_Category_Btn.setText(category_selected_item);
			SharedPreferences category_selected_item_pref = getSharedPreferences("category_item_MyPrefs", Context.MODE_PRIVATE);		 
			String category_selected_item = category_selected_item_pref.getString("category_item_KEY_PREF", "");
			if(category_selected_item==""){
				mCategory_result_Txv.setText("Material");
			}else{
				mCategory_result_Txv.setText(category_selected_item);
			}		
			
		}

		private void contractor_BtnClick() {
			// TODO Auto-generated method stub
			mContractor_Btn.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Contractor_List.class);
					startActivity(i);
				}
			});		
			
			
		}

		private void get_Contractor_Selected_Item() {
			// TODO Auto-generated method stub
//			String category_selected_item=getIntent().getStringExtra("Category_item_selected");
//			mPresent_Category_Btn.setText(category_selected_item);
			SharedPreferences category_selected_item_pref = getSharedPreferences("contractor_item_MyPrefs", Context.MODE_PRIVATE);		 
			String category_selected_item = category_selected_item_pref.getString("contractor_item_KEY_PREF", "");
			if(category_selected_item.equals("")){
				mContractor_result_Txv.setText("Kranthi");
			}else{
				mContractor_result_Txv.setText(category_selected_item);
			}		
			
		}
		private void payMethod_btnClick() {
			// TODO Auto-generated method stub
			mPay_method_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,PayMethod_List.class);
					startActivity(i);
				}
			});
		}
		private void get_PayMethod_SelectedItem() {
			// TODO Auto-generated method stub
			SharedPreferences payMethod_item_pref = getSharedPreferences("payMethod_item_MyPrefs", Context.MODE_PRIVATE);		 
			String payMethod_selected_item = payMethod_item_pref.getString("payMethod_item_KEY_PREF", "");
			mPay_method_result_txv.setText(payMethod_selected_item);
		}
		private void check_Status_btnClick() {
			// TODO Auto-generated method stub
			mCheck_status_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Cheque_Status_List.class);
					startActivity(i);
				}
			});
		}
		private void get_CheckStatus_SelectedItem() {
			// TODO Auto-generated method stub
			SharedPreferences check_Status_item_pref = getSharedPreferences("check_Status_item_MyPrefs", Context.MODE_PRIVATE);		 
			String checkStatus_selected_item = check_Status_item_pref.getString("check_Status_item_KEY_PREF", "");
			mCheck_Status_Result_Txv.setText(checkStatus_selected_item);
		}
		private void camera_btnClick() {
			// TODO Auto-generated method stub
			mCamera_Img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 selectImage();
				}
			});
		}
		private void selectImage() {
			 
	        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(Income_Expense_Description.this);
	        builder.setTitle("Add Photo!");
	        builder.setItems(options, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	                if (options[item].equals("Take Photo"))
	                {
	                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
	                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	                    startActivityForResult(intent, 1);*/
	                	callCamera();
	                }
	                else if (options[item].equals("Choose from Gallery"))
	                {
	                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                    startActivityForResult(intent, 2);
	 
	                }
	                else if (options[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
	            }
	        });
	        builder.show();
	    }
		 public void callCamera() {

			 Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);				
			    // start the image capture Intent
			    startActivityForResult(cameraIntent, 1);		 

		 }
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == RESULT_OK) {
	            if (requestCode == 1) {
	            	Bundle extras = data.getExtras();

	     	   	   if (extras != null) {
	     	   	    Bitmap yourImage = extras.getParcelable("data");
	          	    saveImage(yourImage);

	     	   	    // Inserting Contacts
	     	   	    Log.d("camera Pathissssss: ", selected_ImagePath);
//	     	   	   db.add_Project_Images_Record(new Status_Model(project_id, selected_SiteID,work_master_id,task_name,selected_ImagePath,"N",mCurrent_DateTime_Now));
	     	       	 finish();
	     			 startActivity(getIntent());   	  

	     	   	   }
	            } else if (requestCode == 2) {
	 
	                Uri selectedImage = data.getData();
	                String[] filePath = { MediaStore.Images.Media.DATA };
	                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
	                c.moveToFirst();
	                int columnIndex = c.getColumnIndex(filePath[0]);
	                String picturePath = c.getString(columnIndex);
	                c.close();
	                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
	                Log.w("path of image from gallery......******************.........", picturePath+"");
	                mCamera_Img.setImageBitmap(thumbnail);
	            }
	        }
	    }   
	    private void saveImage(Bitmap finalBitmap) {
			 System.out.println("imageissss");
			    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
		
			    System.out.println("image rootttt"+root);
			    File myDir = new File(root + "/Buildman");

			    if (!myDir.exists()) {
			    	myDir.mkdirs();
		        }

			    Random generator = new Random();
			    int n = 10000;
			    n = generator.nextInt(n);
			    iname = "Image-" + n + ".jpg";
			    File file = new File(myDir, iname);
			    if (file.exists())
			        file.delete();
			    try {
			        FileOutputStream out = new FileOutputStream(file);
			        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			        System.out.println("bitmap mapp");
			        out.flush();
			        out.close();
			    }
			    catch (Exception e) {
			        e.printStackTrace();
			    }
	           
			    // Tell the media scanner about the new file so that it is
			    // immediately available to the user.
			    MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
			            new MediaScannerConnection.OnScanCompletedListener() {
			                public void onScanCompleted(String path, Uri uri) {
			                
			                    Log.i("ExternalStorage", "Scanned " + path + ":");
			                    Log.i("ExternalStorage", "-> uri=" + uri);
			                }
			    });

			    selected_ImagePath=file.toString();
	System.out.println("image path ---"+selected_ImagePath);

			    File[] files = myDir.listFiles();
		    	numberOfImages=files.length;
		    	
		    	System.out.println("Total images in Folder "+numberOfImages);
			}
	    private void tax_btnClick() {
			// TODO Auto-generated method stub
			mTax_Cal_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Income_Expense_Description.this,Tax_Calculator.class);
					startActivity(i);
				}
			});
		}
	    private void get_Tax_Calculated_Amt() {
			// TODO Auto-generated method stub
			SharedPreferences tax_calculated_amt_pref = getSharedPreferences("Tax_Calculated_amt_MyPrefs", Context.MODE_PRIVATE);		 
			String tax_calculated_amt = tax_calculated_amt_pref.getString("Tax_Calculated_amt_KEY_PREF", "");
			
			mTax_Edt.setText(tax_calculated_amt);
		}
	    private void back_BtnClick() {
			// TODO Auto-generated method stub
			mBackImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent i=new Intent(Income_Expense_Description.this,ExpenseTabs.class);	
				startActivity(i);
				finish();
				}
			});
		}

		private void confirm_BtnClick() {
			// TODO Auto-generated method stub
		mConfirm_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				create_Insert_DB();
				
			}

			
		});	
		}
		private void create_Insert_DB() {
			// TODO Auto-generated method stub
			if(mAmount_Edt.getText().length()==0){
				mAmount_Edt.setError("Enter the field");
			}else if (mPayer_Edt.getText().length()==0) {
				mPayer_Edt.setError("Enter the field");
			}else if (mCheck_Edt.getText().length()==0) {
				mCheck_Edt.setError("Enter the field");
			}else if (mDescription_Edt.getText().length()==0) {
				mDescription_Edt.setError("Enter the field");
			}else if (mTax_Edt.getText().length()==0) {
				mTax_Edt.setError("Enter the field");
			}else {
				
			
			db = new DatabaseHandler(this);
			String category=mCategory_result_Txv.getText().toString();
			String contractor=mContractor_result_Txv.getText().toString();
			String date=mDate_Result_Txv.getText().toString();
			String time=mTime_Txv.getText().toString();
			String amount=mAmount_Edt.getText().toString();
			String payer=mPayer_Edt.getText().toString();
			String pay_method=mPay_method_result_txv.getText().toString();
			String check_no=mCheck_Edt.getText().toString();
			String check_status=mCheck_Status_Result_Txv.getText().toString();
			String description=mDescription_Edt.getText().toString();
			String image="0";
			String tax=mTax_Edt.getText().toString();
			db.add_Exp_Income_Record(new Expense_Income_Model(payer,"NA",pay_method,category,contractor,date,time,amount,payer,pay_method,check_no,check_status,description,image,tax)) ;
			Intent i=new Intent(Income_Expense_Description.this,ExpenseTabs.class);
			startActivity(i);
		}
		}	
		
		
		private void getId() {
			// TODO Auto-generated method stub
     		mIncomeTitle_Txv=(TextView)findViewById(R.id.income_title_exp_income_descp);
//			mPresent_Category_Btn=(Button)findViewById(R.id.present_category_exp_income_descp);
//			mPrev_Sub_Category_Img=(ImageView)findViewById(R.id.prev_exp_income_descp);
//			mNext_Sub_Category_Img=(ImageView)findViewById(R.id.next_exp_income_descp);
//			mPresent_Sub_Category_Txv=(TextView)findViewById(R.id.sub_category_txv);
			mDate_title_Txv=(TextView)findViewById(R.id.date_title_txv_exp_income_descp);
			mDate_Result_Txv=(TextView)findViewById(R.id.date_result_txv_exp_income_descp);
			mTime_Txv=(TextView)findViewById(R.id.time_txv_exp_income_descp);
			mDate_Btn=(Button)findViewById(R.id.datebtn_exp_income_descp);
			mAmountTitle_Txv=(TextView)findViewById(R.id.amount_title_txv_exp_income_descp);
			mAmount_Edt=(EditText)findViewById(R.id.amount_edt_exp_income_descp);
			mAmount_Cal_Btn=(Button)findViewById(R.id.atm_calculatorbtn_exp_income_descp);
			mPayerTitle_txv=(TextView)findViewById(R.id.payer_txv_exp_income_descp);
			mPayer_Edt=(EditText)findViewById(R.id.payer_edt_exp_income_descp);
			mPayer_Btn=(Button)findViewById(R.id.payerbtn_exp_income_descp);
			mCategoryTitle_Txv=(TextView)findViewById(R.id.category_title_txv_exp_income_descp);
			mCategory_result_Txv=(TextView)findViewById(R.id.category_result_txv_exp_income_descp);
			mCategory_Btn=(Button)findViewById(R.id.categorybtn_exp_income_descp);
			mContractorTitle_Txv=(TextView)findViewById(R.id.contractor_title_txv_exp_income_descp);
			mContractor_result_Txv=(TextView)findViewById(R.id.contractor_result_txv_exp_income_descp);
			mContractor_Btn=(Button)findViewById(R.id.contractorbtn_exp_income_descp);
			mPay_methodTitle_txv=(TextView)findViewById(R.id.paymethod_title_txv_exp_income_descp);
			mPay_method_result_txv=(TextView)findViewById(R.id.paymethod_result_txv_exp_income_descp);
			mPay_method_Btn=(Button)findViewById(R.id.paymethodbtn_exp_income_descp);
			mCheck_Edt=(EditText)findViewById(R.id.check_edt_exp_income_descp);
			mCheck_Status_Result_Txv=(TextView)findViewById(R.id.status_txv_exp_income_descp);
			mCheck_status_Btn=(Button)findViewById(R.id.check_statusbtn_exp_income_descp);
			mDescription_Edt=(EditText)findViewById(R.id.description_edt_exp_income_descp);
			mCamera_Img=(ImageView)findViewById(R.id.descriptionImg_exp_income_descp);
			mTax_Edt=(EditText)findViewById(R.id.tax_edt_exp_income_descp);
			mTax_Cal_Btn=(Button)findViewById(R.id.taxbtn_exp_income_descp);
			mBackImg=(ImageView)findViewById(R.id.back_exp_income_descp);
			mConfirm_Btn=(Button)findViewById(R.id.confirm_exp_income_descp);
		}
	}



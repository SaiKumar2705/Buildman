package com.buildman.buildman.expensestabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.buildman.buildman.model.Expense_Expense_Model;

public class Expense_Expense_Description extends Activity{
	TextView mExpenseTitle_Txv,mPresent_Sub_Category_Txv,mDate_title_Txv,mDate_Result_Txv,mTime_Txv,mAmountTitle_Txv,mPayeeTitle_txv,
	mPay_methodTitle_txv,mPay_method_result_txv,mCheck_Status_Result_Txv;
	Button mPresent_Category_Btn, mDate_Btn,mAmount_Cal_Btn,mPayee_Btn,mPay_method_Btn,mCheck_status_Btn,mTax_Cal_Btn,mConfirm_Btn;
	EditText mAmount_Edt,mPayee_Edt,mCheck_Edt,mDescription_Edt,mTax_Edt;
	ImageView  mPrev_Sub_Category_Img,mNext_Sub_Category_Img, mBackImg,mCamera_Img;
	String mTime;
	DatabaseHandler db;
	int index_position=0;
	ArrayList<String> sub_Category_list=new ArrayList<String>();
		 @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.expense_expense_description);
		        getId();
		      
		        category_TxvClick();
		        get_Category_Selected_Item();
		        sub_category_TxvClick();
//		        date calculating
		         Calendar c = Calendar.getInstance();
		           int mYear = c.get(Calendar.YEAR);
		           int mMonth = c.get(Calendar.MONTH);
		           int mDay = c.get(Calendar.DAY_OF_MONTH);
		           mDate_Result_Txv.setText(String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear));
		          datePicker();
//		        time calculating
		         
		           int mHour = c.get(Calendar.HOUR_OF_DAY);
		          int  mMinute = c.get(Calendar.MINUTE);
		          mTime_Txv.setText(String.valueOf(mHour)+":"+String.valueOf(mMinute));
		          timePicker();
//		          amount calculator
		          amount_calculator();
		          get_Calculated_Amt(); 
		          payer_btnClick();
		          get_Payer_SelectedItem();
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
			 Intent i=new Intent(Expense_Expense_Description.this,ExpenseTabs.class);	
				i.putExtra("from_expense", 1);
				startActivity(i);
				finish();
		 		return;
		 			}		

		private void sub_category_TxvClick() {
			// TODO Auto-generated method stub
//			adding sub_category_items
			sub_Category_list.add("Kranthi");
			sub_Category_list.add("Sridhar");
			sub_Category_list.add("Ganesh");
			sub_Category_list.add("Harish");
			sub_Category_list.add("Mahesh");
			
//			next and previous arrow clicks
			 mPrev_Sub_Category_Img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//		 if(mCurrent_Qty_rs_Edt.getText().length()==0){
//		 mCurrent_Qty_rs_Edt.setError("Enter the value");
//								
//		  }else {
						
						index_position=index_position-1;
			            if(index_position>=0){
//			                 setting material_name to textfield
			                 String material=sub_Category_list.get(index_position);		                 
			                 mPresent_Sub_Category_Txv.setText(material);

//			        		setting visible 
			                 mNext_Sub_Category_Img.setVisibility(View.VISIBLE);
			            }
			            else{
			                index_position=index_position+1;
//		                Toast.makeText(getBaseContext(), "first", Toast.LENGTH_LONG).show();

			                mPrev_Sub_Category_Img.setVisibility(View.INVISIBLE);
			            }
			            
			         
					}
//			  }		 
				});
			 mNext_Sub_Category_Img.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
//		 if(mCurrent_Qty_rs_Edt.getText().length()==0){
//		 mCurrent_Qty_rs_Edt.setError("Enter the value");
//									
//		 }else {					
							  index_position=index_position+1;
					            if(index_position<sub_Category_list.size()){
//					                 setting material_name to textfield
					                 String material=sub_Category_list.get(index_position);		                 
					                 mPresent_Sub_Category_Txv.setText(material);

//					        		setting visible 
					                 mPrev_Sub_Category_Img.setVisibility(View.VISIBLE);
					            }
					            else{
					                index_position=index_position-1;
//					                Toast.makeText(getBaseContext(), "last", Toast.LENGTH_LONG).show();
					                mNext_Sub_Category_Img.setVisibility(View.INVISIBLE);
					            }
					            
				            
						}
//				}
					});
			
		}



		private void category_TxvClick() {
			// TODO Auto-generated method stub
			
			mPresent_Category_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,Category_List.class);
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
				mPresent_Category_Btn.setText("Manpower");
			}else{
			mPresent_Category_Btn.setText(category_selected_item);
			}
			
			
			
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
			}else if (mPayee_Edt.getText().length()==0) {
				mPayee_Edt.setError("Enter the field");
			}else if (mCheck_Edt.getText().length()==0) {
				mCheck_Edt.setError("Enter the field");
			}else if (mDescription_Edt.getText().length()==0) {
				mDescription_Edt.setError("Enter the field");
			}else if (mTax_Edt.getText().length()==0) {
				mTax_Edt.setError("Enter the field");
			}else {
				
			
			db = new DatabaseHandler(this);
			String category=mPresent_Category_Btn.getText().toString();
			String sub_category=mPresent_Sub_Category_Txv.getText().toString();
			String date=mDate_Result_Txv.getText().toString();
			String time=mTime_Txv.getText().toString();
			String amount=mAmount_Edt.getText().toString();
			String payee=mPayee_Edt.getText().toString();
			String pay_method=mPay_method_result_txv.getText().toString();
			String check_no=mCheck_Edt.getText().toString();
			String check_status=mCheck_Status_Result_Txv.getText().toString();
			String description=mDescription_Edt.getText().toString();
			String image="0";
			String tax=mTax_Edt.getText().toString();
			db.add_Exp_Expense_Record(new Expense_Expense_Model(category,sub_category,date,time,amount,payee,pay_method,check_no,check_status,description,image,tax)) ;
			Intent i=new Intent(Expense_Expense_Description.this,ExpenseTabs.class);
			i.putExtra("from_expense", 1);
			startActivity(i);
			finish();
		}
		}
		private void back_BtnClick() {
			// TODO Auto-generated method stub
			mBackImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent i=new Intent(Expense_Expense_Description.this,ExpenseTabs.class);	
				i.putExtra("from_expense", 1);
				startActivity(i);
				finish();
				}
			});
		}

		private void get_Tax_Calculated_Amt() {
			// TODO Auto-generated method stub
			SharedPreferences tax_calculated_amt_pref = getSharedPreferences("Tax_Calculated_amt_MyPrefs", Context.MODE_PRIVATE);		 
			String tax_calculated_amt = tax_calculated_amt_pref.getString("Tax_Calculated_amt_KEY_PREF", "");
			
			mTax_Edt.setText(tax_calculated_amt);
		}

		private void tax_btnClick() {
			// TODO Auto-generated method stub
			mTax_Cal_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,Tax_Calculator.class);
					startActivity(i);
					finish();
				}
			});
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
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(Expense_Expense_Description.this);
	        builder.setTitle("Add Photo!");
	        builder.setItems(options, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	                if (options[item].equals("Take Photo"))
	                {
	                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
	                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	                    startActivityForResult(intent, 1);
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
	 
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == RESULT_OK) {
	            if (requestCode == 1) {
	                File f = new File(Environment.getExternalStorageDirectory().toString());
	                for (File temp : f.listFiles()) {
	                    if (temp.getName().equals("temp.jpg")) {
	                        f = temp;
	                        break;
	                    }
	                }
	                try {
	                    Bitmap bitmap;
	                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	 
	                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
	                            bitmapOptions); 
	                   
	                    mCamera_Img.setImageBitmap(bitmap);
	 
	                    String path = android.os.Environment
	                            .getExternalStorageDirectory()
	                            + File.separator
	                            + "Phoenix" + File.separator + "default";
	                    f.delete();
	                    OutputStream outFile = null;
	                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
	                    try {
	                        outFile = new FileOutputStream(file);
	                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
	                        outFile.flush();
	                        outFile.close();
	                    } catch (FileNotFoundException e) {
	                        e.printStackTrace();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
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
		private void get_CheckStatus_SelectedItem() {
			// TODO Auto-generated method stub
			SharedPreferences check_Status_item_pref = getSharedPreferences("check_Status_item_MyPrefs", Context.MODE_PRIVATE);		 
			String checkStatus_selected_item = check_Status_item_pref.getString("check_Status_item_KEY_PREF", "");
			mCheck_Status_Result_Txv.setText(checkStatus_selected_item);
		}

		private void check_Status_btnClick() {
			// TODO Auto-generated method stub
			mCheck_status_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,Cheque_Status_List.class);
					startActivity(i);
					finish();
				}
			});
		}

		private void get_PayMethod_SelectedItem() {
			// TODO Auto-generated method stub
			SharedPreferences payMethod_item_pref = getSharedPreferences("payMethod_item_MyPrefs", Context.MODE_PRIVATE);		 
			String payMethod_selected_item = payMethod_item_pref.getString("payMethod_item_KEY_PREF", "");
			mPay_method_result_txv.setText(payMethod_selected_item);
		}

		private void payMethod_btnClick() {
			// TODO Auto-generated method stub
			mPay_method_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,PayMethod_List.class);
					startActivity(i);
					finish();
				}
			});
		}

		private void get_Payer_SelectedItem() {
			// TODO Auto-generated method stub
//			String payer_selected_item=getIntent().getStringExtra("payer_selected_item");
//			get data for age from consultation class
			SharedPreferences payer_item_pref = getSharedPreferences("Payer_item_MyPrefs", Context.MODE_PRIVATE);		 
			String payer_selected_item = payer_item_pref.getString("Payer_item_KEY_PREF", "");
			mPayee_Edt.setText(payer_selected_item);
		}

		private void payer_btnClick() {
			// TODO Auto-generated method stub
			mPayee_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,Payer_List.class);
					startActivity(i);
					finish();
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

		private void amount_calculator() {
			// TODO Auto-generated method stub
			mAmount_Cal_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Expense_Expense_Description.this,Amount_Calculator.class);
					startActivity(i);
					finish();
				}
			});
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
		            TimePickerDialog tpd = new TimePickerDialog(Expense_Expense_Description.this,
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
		            DatePickerDialog dpd = new DatePickerDialog(Expense_Expense_Description.this,
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

		private void getId() {
			// TODO Auto-generated method stub
			mExpenseTitle_Txv=(TextView)findViewById(R.id.expense_title_exp_expense_descp);
			mPresent_Category_Btn=(Button)findViewById(R.id.present_category_exp_expense_descp);
			mPrev_Sub_Category_Img=(ImageView)findViewById(R.id.prev_exp_expense_descp);
			mNext_Sub_Category_Img=(ImageView)findViewById(R.id.next_exp_expense_descp);
			mPresent_Sub_Category_Txv=(TextView)findViewById(R.id.sub_category_txv_exp_expense_descp);
			mDate_title_Txv=(TextView)findViewById(R.id.date_title_txv_exp_expense_descp);
			mDate_Result_Txv=(TextView)findViewById(R.id.date_result_txv_exp_expense_descp);
			mTime_Txv=(TextView)findViewById(R.id.time_txv_exp_expense_descp);
			mDate_Btn=(Button)findViewById(R.id.datebtn_exp_expense_descp);
			mAmountTitle_Txv=(TextView)findViewById(R.id.amount_title_txv_exp_expense_descp);
			mAmount_Edt=(EditText)findViewById(R.id.amount_edt_exp_expense_descp);
			mAmount_Cal_Btn=(Button)findViewById(R.id.atm_calculatorbtn_exp_expense_descp);
			mPayeeTitle_txv=(TextView)findViewById(R.id.payee_txv_exp_expense_descp);
			mPayee_Edt=(EditText)findViewById(R.id.payee_edt_exp_expense_descp);
			mPayee_Btn=(Button)findViewById(R.id.payeebtn_exp_expense_descp);
			mPay_methodTitle_txv=(TextView)findViewById(R.id.paymethod_title_txv_exp_expense_descp);
			mPay_method_result_txv=(TextView)findViewById(R.id.paymethod_result_txv_exp_expense_descp);
			mPay_method_Btn=(Button)findViewById(R.id.paymethodbtn_exp_expense_descp);
			mCheck_Edt=(EditText)findViewById(R.id.check_edt_exp_expense_descp);
			mCheck_Status_Result_Txv=(TextView)findViewById(R.id.status_txv_exp_expense_descp);
			mCheck_status_Btn=(Button)findViewById(R.id.check_statusbtn_exp_expense_descp);
			mDescription_Edt=(EditText)findViewById(R.id.description_edt_exp_expense_descp);
			mCamera_Img=(ImageView)findViewById(R.id.descriptionImg_exp_expense_descp);
			mTax_Edt=(EditText)findViewById(R.id.tax_edt_exp_expense_descp);
			mTax_Cal_Btn=(Button)findViewById(R.id.taxbtn_exp_expense_descp);
			mBackImg=(ImageView)findViewById(R.id.back_exp_expense_descp);
			mConfirm_Btn=(Button)findViewById(R.id.confirm_exp_expense_descp);
		}
	}


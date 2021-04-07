package com.buildman.buildman.expensestabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.buildman.buildman.activity.R;

public class Tax_Calculator extends Activity {
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bdot,badd,bsub,bmul,bdiv,beq,clear,ok;
	EditText et;
	Float val1,val2;
	boolean add,sub,div,mul;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.calculator);
	  
	  android.app.ActionBar bar=getActionBar();
      getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman </font>"));
      
	  clear=(Button)findViewById(R.id.clear);
	  ok=(Button)findViewById(R.id.ok);
	  b1=(Button) findViewById(R.id.button1);
	  b2=(Button) findViewById(R.id.Button01);
	  b3=(Button) findViewById(R.id.Button02);
	  b4=(Button) findViewById(R.id.Button03);
	  b5=(Button) findViewById(R.id.Button04);
	  b6=(Button) findViewById(R.id.Button05);
	  b7=(Button) findViewById(R.id.Button06);
	  b8=(Button) findViewById(R.id.Button07);
	  b9=(Button) findViewById(R.id.Button08);
	  b0=(Button) findViewById(R.id.Button09);
	  bdot=(Button) findViewById(R.id.Button10);
	  badd=(Button) findViewById(R.id.Button11);
	  bsub=(Button) findViewById(R.id.Button12);
	  bmul=(Button) findViewById(R.id.Button13);
	  bdiv=(Button) findViewById(R.id.Button14);
	  beq=(Button) findViewById(R.id.Button15);
	  et=(EditText) findViewById(R.id.editText1);
	  
	  b1.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"1");
	   }
	  });
	  b2.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"2");
	   }
	  });
	  b3.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"3");
	   }
	  });
	  b4.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"4");
	   }
	  });
	  b5.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"5");
	   }
	  });
	  b6.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"6");
	   }
	  });
	  b7.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"7");
	   }
	  });
	  b8.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"8");
	   }
	  });
	  b9.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"9");
	   }
	  });
	  b0.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+"0");
	   }
	  });
	  bdot.setOnClickListener(new View.OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    et.setText(et.getText()+".");
	   }
	  });
	  
	 badd.setOnClickListener(new View.OnClickListener() {
	  
	  @Override
	  public void onClick(View v) {
	   val1= Float.parseFloat(et.getText()+"");
	   add=true;
	   et.setText(null);
	  }
	 });
	bsub.setOnClickListener(new View.OnClickListener() {
	  
	  @Override
	  public void onClick(View v) {
	   val1= Float.parseFloat(et.getText()+"");
	   sub=true;
	   et.setText(null);
	  }
	 });
	bdiv.setOnClickListener(new View.OnClickListener() {
	 
	 @Override
	 public void onClick(View v) {
	  val1= Float.parseFloat(et.getText()+"");
	  div=true;
	  et.setText(null);
	 }
	});
	bmul.setOnClickListener(new View.OnClickListener() {
	 
	 @Override
	 public void onClick(View v) {
	  val1= Float.parseFloat(et.getText()+"");
	  mul=true;
	  et.setText(null);
	 }
	});

	beq.setOnClickListener(new View.OnClickListener() {
	 
	 @Override
	 public void onClick(View v) {
	  val2= Float.parseFloat(et.getText()+"");
	  if (add==true) {
	   et.setText(val1+val2+"");
	   add=false; 
	  }
	  if (sub==true) {
		  
	   et.setText(val1-val2+"");
	   sub=false; 
	  }
	  if (mul==true) {
	   et.setText(val1*val2+"");
	   mul=false; 
	  }
	  if (div==true) {
	   et.setText(val1/val2+"");
	   div=false; 
	  }
	 }
	 
	});
	ok.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(et.getText().length()==0){
				et.setText("0.00");
			}
			
			 float final_value_float=Float.parseFloat(et.getText().toString());
			
      		String  final_value_result = String.format("%.2f", final_value_float);
			
			Intent i=new Intent(Tax_Calculator.this,Income_Expense_Description.class);
			
//			i.putExtra("calculated_value_amt", final_value_result);
			SharedPreferences tax_calculated_amt_pref = Tax_Calculator.this.getSharedPreferences("Tax_Calculated_amt_MyPrefs", Context.MODE_PRIVATE);		
		  	SharedPreferences.Editor tax_calculated_amt_edit = tax_calculated_amt_pref.edit();	
		  	tax_calculated_amt_edit.putString("Tax_Calculated_amt_KEY_PREF",final_value_result);	
		  	tax_calculated_amt_edit.commit();
			startActivity(i);
		}
	});
	clear.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			et.setText("");
		}
	});

	 }
	 

	
	}

package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity {

	EditText name, email, password;
	Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		name = (EditText)findViewById(R.id.nameRegister);
		email = (EditText)findViewById(R.id.emailRegister);
		password = (EditText)findViewById(R.id.passwordRegister);
		register = (Button)findViewById(R.id.registerBtn);
		
		register.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v) {
				String n = name.getText().toString();
				String e = email.getText().toString();
				String p = password.getText().toString();
				boolean didItWork = true;
				long test;
				String output="";
				
				try{
				
					Database entry = new Database(Register.this);
				
					entry.open();
					test = entry.createEntry(n,e,p);
					output = String.valueOf(test);
					
				
					entry.close();
				}
				catch(Exception ex){
					didItWork = false;
					Dialog d = new Dialog(Register.this);
					String error = ex.toString();
					d.setTitle("failed to enter data");
					TextView tv = new TextView(Register.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				}
				finally{
					if(didItWork){
						/*Dialog d = new Dialog(Register.this);
						TextView tv = new TextView(Register.this);
						tv.setText(output);
						d.setContentView(tv);
						d.show();*/
						
						Intent i = new Intent(getApplicationContext(), Start.class);
		            	startActivity(i);
					}
				}
			}	
				
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
					/*Dialog d = new Dialog(Register.this);
					String error = ex.toString();
					d.setTitle("failed to enter data");
					TextView tv = new TextView(Register.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
					*/
				}
				finally{						
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Register.this);
					Intent i;
					if(didItWork){
						alertDialogBuilder.setTitle("Successfull Registration");
						alertDialogBuilder.setMessage("Click Ok to Login");
					}
					else{
						alertDialogBuilder.setTitle("Registration Failed");
						alertDialogBuilder.setMessage("Click Ok to Try Again");
					}
					final boolean whereToGo = didItWork;
					alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent i;
							if(whereToGo){
								i = new Intent(getApplicationContext(), TeacherLogin.class);
							}
							else{
								i = new Intent(getApplicationContext(), Register.class);
							}
				            startActivity(i);
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
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
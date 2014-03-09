package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TeacherLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_login);
		Button login = (Button)findViewById(R.id.loginBtn);
	
		
		login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	EditText email = (EditText)findViewById(R.id.emailLogin);
        		EditText password = (EditText)findViewById(R.id.passwordLogin);
        		String e,p;
        		Database db = new Database(TeacherLogin.this);
        		boolean didItWork=false;
            	try{
            		e = email.getText().toString();
            		p = password.getText().toString();
            		db.open();
            		String[] data = db.getTeacherLoginInfo();
            		db.close();
            		for(int i=0; i<data.length; i=i+2){
            			if((data[i].equals(e)) && (data[i+1].equals(p))){
            				didItWork = true;
            				i = data.length + 1;
            			}
            		}
            		/*if(correct){
            			Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
            			//pass on teacher's email to next activity
            			i.putExtra("email", e);
            			startActivity(i);
            		}
            		*/
            		
            	}
            	catch(Exception ex){
            		didItWork=false;
            		/*
            		Dialog d = new Dialog(TeacherLogin.this);
        			String error = ex.toString();
        			d.setTitle("failed to get data");
        			TextView tv = new TextView(TeacherLogin.this);
        			tv.setText(error);
        			d.setContentView(tv);
        			d.show();
        			*/
            	}
            	finally{
            		if(didItWork){
            			Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
            			//pass on teacher's email to next activity
            			String em = email.getText().toString();
            			i.putExtra("email", em);
            			startActivity(i);
            		}
            		else{
            			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								TeacherLogin.this);
						alertDialogBuilder.setTitle("Unable to Login");
						alertDialogBuilder.setMessage("Click Ok to Try Again");
						alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								Intent i = new Intent(getApplicationContext(), TeacherLogin.class);
				            	startActivity(i);
							}
						  });
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
            		}
            	}
            }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teacher_login, menu);
		return true;
	}

}
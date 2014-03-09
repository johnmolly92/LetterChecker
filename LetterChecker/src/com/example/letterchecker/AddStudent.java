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

public class AddStudent extends Activity {

	EditText name;
	Button add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_student);
		
		name = (EditText)(findViewById(R.id.studentName));
		add = (Button)(findViewById(R.id.addToClassBtn));
		final Bundle extras = getIntent().getExtras();
		
		add.setOnClickListener(new View.OnClickListener(){
		
			public void onClick(View v){
				String n = name.getText().toString();
				boolean didItWork=true;
				try{
					Database entry = new Database(AddStudent.this);
					entry.open();
					String email ="";
	            	if (extras != null) {
	        		    email = extras.getString("email");
	        		}
					entry.createStudentEntry(n, email, 0);
					entry.close();
					
					/*
					Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
					i.putExtra("email", email);
	            	startActivity(i);
	            	*/
				}
				catch(Exception ex){
					didItWork=false;
					Dialog d = new Dialog(AddStudent.this);
					String error = ex.toString();
					d.setTitle("failed to enter data");
					TextView tv = new TextView(AddStudent.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				}
				finally{
					if(didItWork){
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								AddStudent.this);
						alertDialogBuilder.setTitle("Student Added to Class");
						alertDialogBuilder.setMessage("Click Ok to return to Homepage");
						alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								String email ="";
				            	if (extras != null) {
				        		    email = extras.getString("email");
				        		}
								Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								i.putExtra("email", email);
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
		getMenuInflater().inflate(R.menu.add_student, menu);
		return true;
	}

}

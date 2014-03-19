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
		
		// get from xml
		name = (EditText)(findViewById(R.id.studentName));
		add = (Button)(findViewById(R.id.addToClassBtn));
		final Bundle EXTRAS = getIntent().getExtras();
		
		//listen for user to press add button
		add.setOnClickListener(new View.OnClickListener(){
		
			public void onClick(View v){
				String n = name.getText().toString();
				boolean didItWork=true;
				//did the user enter valid data?
				boolean validData=true;
				if(n.equals("")){
					validData=false;
					didItWork=false;
				}
				try{
					if(validData){
						//if they entered a valid name
						Database entry = new Database(AddStudent.this);
						entry.open();
						String email ="";
						//get the teacher email from the previous activity
						if (EXTRAS != null) {
							email = EXTRAS.getString("email");
						}
						//create an entry in the student table
						entry.createStudentEntry(n, email, 0);
						entry.close();
					}

				}
				//catches SQL exceptions
				catch(Exception ex){
					didItWork=false;
					//output the error for debugging
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
						//let the user know it worked
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								AddStudent.this);
						alertDialogBuilder.setTitle("Student Added to Class");
						alertDialogBuilder.setMessage("Click Ok to return to Homepage");
						//if they press ok
						alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								String email ="";
				            	if (EXTRAS != null) {
				            		//get teacher email from previous activity
				        		    email = EXTRAS.getString("email");
				        		}
								Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								//send the email to the next activity
								i.putExtra("email", email);
								//back to teacher logged in page
				            	startActivity(i);
							}
						  });
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					}
					else{
						//let the user know the process didnt work
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								AddStudent.this);
						alertDialogBuilder.setTitle("Unable to Add Student to Class");
						alertDialogBuilder.setMessage("Please try again later, Click Ok to return to Homepage");
						//if they press ok
						alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								String email ="";
				            	if (EXTRAS != null) {
				            		//get teacher email from previous activity
				        		    email = EXTRAS.getString("email");
				        		}
								Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								//send the email to the next activity
								i.putExtra("email", email);
								//back to teacher logged in page
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

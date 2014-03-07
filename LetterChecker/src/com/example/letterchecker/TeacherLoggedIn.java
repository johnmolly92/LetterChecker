package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TeacherLoggedIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_logged_in);
		Button ManageStudents = (Button)findViewById(R.id.manageStudentsBtn);
		Button ChooseLessons = (Button)findViewById(R.id.chooseLessonsBtn);
		Button ViewResults = (Button)findViewById(R.id.viewResultsBtn);
		Button Logout = (Button)findViewById(R.id.loginBtn);
		final Bundle extras = getIntent().getExtras();
		
		
		ManageStudents.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	Intent i = new Intent(getApplicationContext(), ManageStudents.class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	
            }
        });
		
		ChooseLessons.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	/*
            	Intent i = new Intent(getApplicationContext(), .class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	*/
            	
            }
        });
		
		ViewResults.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	/*
            	Intent i = new Intent(getApplicationContext(), RemoveStudent.class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	*/
            	
            }
        });
		
		Logout.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), Start.class);
            	startActivity(i);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teacher_logged_in, menu);
		return true;
	}

}

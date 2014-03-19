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
		//get button from xml
		Button ManageStudents = (Button)findViewById(R.id.manageStudentsBtn);
		Button ChooseLessons = (Button)findViewById(R.id.chooseLessonsBtn);
		Button ViewResults = (Button)findViewById(R.id.viewResultsBtn);
		Button Logout = (Button)findViewById(R.id.LogoutBtn);
		final Bundle EXTRAS = getIntent().getExtras();
		
		///wait for user to press button and open the corresponding page and
		// send the next activity the teacher's email
		
		ManageStudents.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	Intent i = new Intent(getApplicationContext(), ManageStudents.class);
            	String email ="";
            	if (EXTRAS != null) {
        		    email = EXTRAS.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	
            }
        });
		
		ChooseLessons.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), ChooseLessons.class);
            	String email ="";
            	if (EXTRAS != null) {
        		    email = EXTRAS.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);	
            }
        });
		
		ViewResults.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), ViewResults.class);
            	String email ="";
            	if (EXTRAS != null) {
        		    email = EXTRAS.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
       
            	
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

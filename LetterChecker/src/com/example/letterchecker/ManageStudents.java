package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ManageStudents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_students);
		Button ViewStudents = (Button)findViewById(R.id.viewStudentsBtn);
		Button AddStudent = (Button)findViewById(R.id.addStudentsBtn);
		Button RemoveStudent = (Button)findViewById(R.id.removeStudentsBtn);
		final Bundle extras = getIntent().getExtras();
		
		
		ViewStudents.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	Intent i = new Intent(getApplicationContext(), ViewStudents.class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	
            }
        });
		
		AddStudent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	Intent i = new Intent(getApplicationContext(), AddStudent.class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	
            }
        });
		
		RemoveStudent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	Intent i = new Intent(getApplicationContext(), RemoveStudent.class);
            	String email ="";
            	if (extras != null) {
        		    email = extras.getString("email");
        		}
            	i.putExtra("email", email);
            	startActivity(i);
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_students, menu);
		return true;
	}

}


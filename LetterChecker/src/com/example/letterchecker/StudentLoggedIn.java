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
import android.widget.TextView;

public class StudentLoggedIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_logged_in);
		Button Logout = (Button)findViewById(R.id.studentLogoutBtn);
		Button StartLesson = (Button)findViewById(R.id.startLessonBtn);
		
		
		Logout.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), Start.class);
            	startActivity(i);
            }
        });
		
		StartLesson.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {	
            	Bundle extras = getIntent().getExtras();
    			String studentSelected ="";
    			String teacherEmail = "";
        		if (extras != null) {
    		    	studentSelected = extras.getString("studentName");
    		    	teacherEmail = extras.getString("email");
    			}
        		Database db = new Database(StudentLoggedIn.this);
        		db.open();
        		String lesson = db.getStudentNextLesson(studentSelected, teacherEmail);
        		db.close(); 
            	Dialog d = new Dialog(StudentLoggedIn.this);
    			d.setTitle("Next Lesson is");
    			TextView tv = new TextView(StudentLoggedIn.this);
    			tv.setText(lesson);
    			d.setContentView(tv);
    			d.show();
    			/*
            	Intent i = new Intent(getApplicationContext(), Test.class);
            	startActivity(i);
            	*/
            
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_logged_in, menu);
		return true;
	}

}

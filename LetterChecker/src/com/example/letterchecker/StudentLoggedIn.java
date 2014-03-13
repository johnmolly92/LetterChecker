package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StudentLoggedIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_logged_in);
		Button Logout = (Button)findViewById(R.id.studentLogoutBtn);
		Button StartLesson = (Button)findViewById(R.id.startLessonBtn);
		
		final Bundle extras = getIntent().getExtras();
		String studentSelected ="";
		String teacherEmail = "";
    	if (extras != null) {
		    studentSelected = extras.getString("studentName");
		    teacherEmail = extras.getString("email");
		}
    	
    	final String tmpStudent = studentSelected; 
    	final String tmpEmail = teacherEmail;
		
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
            	
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						StudentLoggedIn.this);
				alertDialogBuilder.setTitle("Current Lesson is");
				Database db = new Database(StudentLoggedIn.this);
				db.open();
				String Lesson = db.getStudentNextLesson(tmpStudent, tmpEmail);
				alertDialogBuilder.setMessage(Lesson);
				alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent i = new Intent(getApplicationContext(), Start.class);
		            	startActivity(i);
					}
				  });
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
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

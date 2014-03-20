package com.example.letterchecker;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

//student login in is a two part process
//1. displaying a list of teachers
//2. displaying a list of students
//this is part two
public class ListOfStudents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_students);
		ListView listview = (ListView)findViewById(R.id.studentsListviewLogin);
		final Bundle EXTRAS = getIntent().getExtras();
		//get the teachers name from previous activity
		String teacherName ="";
    	if (EXTRAS != null) {
		    teacherName = EXTRAS.getString("name");
		}
    	final String TEACHEREMAIL = teacherName;
    	try{
			Database info = new Database(this);
			info.open();
			//get student info from database
			String[] tmpData = info.getStudentInfo();
			//get students that are relevant to a teacher
			String[] data = info.getTeachersStudentsByName(tmpData,teacherName);
			//String[] data = getTeachersStudents(tmpData,teacherName);
			info.close();
			//display it to the screen
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			//wait for a click on a name
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String studentName = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
					//send the student's name and the teacher's email to the next activity
					EXTRAS.putString("studentName", studentName);
					Database info = new Database(ListOfStudents.this);
					info.open();
					EXTRAS.putString("email", info.getTeacherEmail(TEACHEREMAIL));
					i.putExtras(EXTRAS);
					info.close();
					startActivity(i);
				}
	        });
		}
    	//catches SQlExceptions
		catch(Exception ex){
			//outputs error for debugging
			Dialog d = new Dialog(ListOfStudents.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ListOfStudents.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_students, menu);
		return true;
	}

}

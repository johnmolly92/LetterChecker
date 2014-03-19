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
//Choosing lessons is broken into two parts:
// 1: selecting the student
// 2: selecting the lesson
//This is part one
public class ChooseLessons extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_lessons);
		//get listview from xml
		ListView listview = (ListView)findViewById(R.id.ChooseLessonsStudentLV);
		final Bundle EXTRAS = getIntent().getExtras();
		String teacherEmail ="";
		//get teacher's email from previous activity
    	if (EXTRAS != null) {
		    teacherEmail = EXTRAS.getString("email");
		    EXTRAS.putString("email", teacherEmail);
		}
    	
    	try{
			Database info = new Database(this);
			info.open();
			//get all student info
			String[] tmpData = info.getStudentInfo();
			info.close();
			//get teacher's students based on teacher's email
			//put all their names in data array
			String[] data = getTeachersStudents(tmpData,teacherEmail);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			//display array of names to screen
			listview.setAdapter(adapter);
			//wait for user to click on student name
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String studentName = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), ChooseLessonsPartTwo.class);
					//send the student name to the next activity
					EXTRAS.putString("studentName", studentName);
					i.putExtras(EXTRAS);
					startActivity(i);
				}
	        });
			
		}
    	//catches SQlExceptions
		catch(Exception ex){
			//outputs error for debugging
			Dialog d = new Dialog(ChooseLessons.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ChooseLessons.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}
	
	
	
	//method to take array of all students and their info and returns an array of the names of students who have a teacher
	//whos email is passed as a parameter
	public String[] getTeachersStudents(String[] array, String email){
		String id, name, teacherEmail, nextLesson;
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i=0; i<array.length; i=i+4){
			id = array[i];
			name = array[i+1];
			teacherEmail = array[i+2];
			nextLesson = array[i+3];
			if(teacherEmail.equals(email)){
				tmp.add(name);
			}
		}
		String[] result = tmp.toArray(new String[tmp.size()]);
		return result;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_lessons, menu);
		return true;
	}

}

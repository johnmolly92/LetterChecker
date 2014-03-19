package com.example.letterchecker;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewStudents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_students);
		final ListView listview;
		final Bundle EXTRAS = getIntent().getExtras();
		listview = (ListView)findViewById(R.id.studentListView);
		String email ="";
    	if (EXTRAS != null) {
		    email = EXTRAS.getString("email");
		}
		try{
			Database info = new Database(this);
			info.open();
			//get student info from database
			String[] tmpData = info.getStudentInfo();
			info.close();
			//get data relevant to student
			String[] data = getRelevantData(tmpData,email);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			// display to screen
			listview.setAdapter(adapter);
			
		}
		//catches SQL exceptions
		catch(Exception ex){
			//output the error for debugging
			Dialog d = new Dialog(ViewStudents.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ViewStudents.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}
	//takes an array of all students and all there info and returns the students names who are relevant to the logged in teacher
	public String[] getRelevantData(String[] array, String currentEmail){
		String id, name, teacherEmail, nextLesson;
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i=0; i<array.length; i=i+4){
			id = array[i];
			name = array[i+1];
			teacherEmail = array[i+2];
			nextLesson = array[i+3];
			if(teacherEmail.equals(currentEmail)){
				tmp.add(name);
			}
		}
		String[] result = tmp.toArray(new String[tmp.size()]);
		return result;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_students, menu);
		return true;
	}

}

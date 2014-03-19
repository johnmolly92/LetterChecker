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

// the viewing results process is a two step process
// 1. step one is selecting the student
// 2. step two is displaying the reports
// this is step one
public class ViewResults extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_results);
		ListView listview = (ListView)findViewById(R.id.viewResultsLV);
		final Bundle EXTRAS = getIntent().getExtras();
		String teacherEmail ="";
		// get teacher's email from previous activity
    	if (EXTRAS != null) {
		    teacherEmail = EXTRAS.getString("email");
		    EXTRAS.putString("email", teacherEmail);
		}
    	final String TEACHEREMAIL = teacherEmail;
    	try{
			Database info = new Database(this);
			info.open();
			//get student info from database
			String[] tmpData = info.getStudentInfo();
			info.close();
			//get teacher's student
			String[] data = getTeachersStudents(tmpData,teacherEmail);
			//display student's to screen
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			//wait for user to select student
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String studentName = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), ViewResultsPartTwo.class);
					// send student's name and teacher's email to next activity
					EXTRAS.putString("studentName", studentName);
					EXTRAS.putString("email", TEACHEREMAIL);
					i.putExtras(EXTRAS);
					startActivity(i);
				}
	        });
			
		}
    	//catches SQL exceptions
		catch(Exception ex){
			//output the error for debugging
			Dialog d = new Dialog(ViewResults.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ViewResults.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}
	
	//get students who are relevant to the teacher
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
		getMenuInflater().inflate(R.menu.view_results, menu);
		return true;
	}

}

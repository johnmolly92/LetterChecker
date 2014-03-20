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
			//get data relevant to student
			//String[] data = getRelevantData(tmpData,email);
			String[] data = info.getTeachersStudentsByEmail(tmpData, email);
			info.close();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_students, menu);
		return true;
	}

}

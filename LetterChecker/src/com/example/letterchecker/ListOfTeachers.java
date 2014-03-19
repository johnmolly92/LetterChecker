package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;

//student login in is a two part process
// 1. displaying a list of teachers
// 2. displaying a list of students
// this is part one
public class ListOfTeachers extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_teachers);
		//get listview from xml
		final ListView listview;
		listview = (ListView)findViewById(R.id.listView);
		try{
			Database info = new Database(this);
			info.open();
			//get teacher names from database
			String[] data = info.getTeacherNames();
			info.close();
			//display names to screen
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			//wait for click on teacher from user
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					//send the teacher name to the next activity
					String name = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), ListOfStudents.class);
					i.putExtra("name", name);
					startActivity(i);
					
				}
	        });
		}
		//catches SQlExceptions
		catch(Exception ex){
			//outputs error for debugging
			Dialog d = new Dialog(ListOfTeachers.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ListOfTeachers.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_teachers, menu);
		return true;
	}

}

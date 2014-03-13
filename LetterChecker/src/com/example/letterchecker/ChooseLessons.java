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

public class ChooseLessons extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_lessons);
		ListView listview = (ListView)findViewById(R.id.ChooseLessonsStudentLV);
		final Bundle extras = getIntent().getExtras();
		String teacherEmail ="";
    	if (extras != null) {
		    teacherEmail = extras.getString("email");
		    extras.putString("email", teacherEmail);
		}
    	
    	try{
			Database info = new Database(this);
			info.open();
			String[] tmpData = info.getStudentInfo();
			info.close();
			String[] data = getTeachersStudents(tmpData,teacherEmail);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String studentName = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), ChooseLessonsPartTwo.class);
					extras.putString("studentName", studentName);
					i.putExtras(extras);
					startActivity(i);
					
				}
	        });
			
		}
		catch(Exception ex){
			Dialog d = new Dialog(ChooseLessons.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ChooseLessons.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}
	
	
	
	
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

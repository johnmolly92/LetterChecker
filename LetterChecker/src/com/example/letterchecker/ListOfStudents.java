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

public class ListOfStudents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_students);
		ListView listview = (ListView)findViewById(R.id.studentsListviewLogin);
		final Bundle extras = getIntent().getExtras();
		String teacherName ="";
    	if (extras != null) {
		    teacherName = extras.getString("name");
		}
    	final String tmpTeacherEmail = teacherName;
    	try{
			Database info = new Database(this);
			info.open();
			String[] tmpData = info.getStudentInfo();
			info.close();
			String[] data = getTeachersStudents(tmpData,teacherName);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String studentName = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
					extras.putString("studentName", studentName);
					extras.putString("email", getTeacherEmail(tmpTeacherEmail));
					i.putExtras(extras);
					startActivity(i);
					
				}
	        });
			
		}
		catch(Exception ex){
			Dialog d = new Dialog(ListOfStudents.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ListOfStudents.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}
	
	
	public String getTeacherEmail(String teacher_name){
		Database info = new Database(this);
		info.open();
		String[] tmpData = info.getTeacherInfo();
		info.close();
		String result="";
		String id, name, email, password;
		for(int i=0; i<tmpData.length; i=i+4){
			id = tmpData[i];
			name = tmpData[i+1];
			email = tmpData[i+2];
			password=tmpData[1+3];
			if(name.equals(teacher_name)){
				result=email;
			}
		}
		return result;
	}
	
	public String[] getTeachersStudents(String[] array, String teacherName){
		String email = getTeacherEmail(teacherName);
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
		getMenuInflater().inflate(R.menu.list_of_students, menu);
		return true;
	}

}

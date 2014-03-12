package com.example.letterchecker;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RemoveStudent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_student);
		super.onCreate(savedInstanceState);
		final ListView listview;
		final Bundle extras = getIntent().getExtras();
		listview = (ListView)findViewById(R.id.removeStudentListview);
		String email ="";
    	if (extras != null) {
		    email = extras.getString("email");
		}
    	final String teacherEmail = email;
		try{
			Database info = new Database(this);
			info.open();
			String[] tmpData = info.getStudentInfo();
			info.close();
			String[] data = getRelevantData(tmpData,email);
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					final String studentName = ((TextView)view).getText().toString();
					AlertDialog.Builder warningBuilder = new AlertDialog.Builder(RemoveStudent.this);
					warningBuilder.setTitle("Warning");
					warningBuilder.setMessage("Are you sure you want to delete this student?");
					warningBuilder.setNeutralButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Database db = new Database(RemoveStudent.this);
							try{
								db.open();
								db.deleteStudent(studentName, teacherEmail);
								db.close();
							
								Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								i.putExtra("email", teacherEmail);
								startActivity(i);
							}
							catch(Exception ex){
								Dialog d = new Dialog(RemoveStudent.this);
								String error = ex.toString();
								d.setTitle("failed to get data");
								TextView tv = new TextView(RemoveStudent.this);
								tv.setText(error);
								d.setContentView(tv);
								d.show();
							}
						}
					});
					AlertDialog alertDialog = warningBuilder.create();
					alertDialog.show();
				}
	        });
			
		}
		catch(Exception ex){
			Dialog d = new Dialog(RemoveStudent.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(RemoveStudent.this);
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
		getMenuInflater().inflate(R.menu.remove_student, menu);
		return true;
	}

}

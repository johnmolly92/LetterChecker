package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChooseLessonsPartTwo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_lessons_part_two);
		final Bundle extras = getIntent().getExtras();
		String studentSelected ="";
		String teacherEmail = "";
    	if (extras != null) {
		    studentSelected = extras.getString("studentName");
		    teacherEmail = extras.getString("email");
		}
    	final String tmpStudent = studentSelected; 
    	final String tmpEmail = teacherEmail;
    	Resources res = getResources();
    	String[] tmpArray = res.getStringArray(R.array.LessonsArray);
    	ListView listview = (ListView)findViewById(R.id.ChooseLessonsPartTwoLV);
    	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmpArray);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				try{
					String lesson = ((TextView)view).getText().toString();
					Database db = new Database(ChooseLessonsPartTwo.this);
					db.open();
					int IntLesson = lessonStringToInt(lesson);
					db.changeNextLesson(tmpStudent, tmpEmail, IntLesson);
					db.close();
					Intent i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
					i.putExtra("email", tmpEmail);
					startActivity(i);
				}
				catch(Exception ex){
					Dialog d = new Dialog(ChooseLessonsPartTwo.this);
					String error = ex.toString();
					d.setTitle("failed to get data");
					TextView tv = new TextView(ChooseLessonsPartTwo.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				}
				
			}
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_lessons_part_two, menu);
		return true;
	}

	public int lessonStringToInt(String lesson){
		Resources res = getResources();
    	String[] lessonsArray = res.getStringArray(R.array.LessonsArray);
    	int result=-1;
    	String test="";
    	for(int i=0; i<lessonsArray.length; i++){
    		test = lessonsArray[i];
    		if(test.equals(lesson)){
    			result = i;
    			i = lessonsArray.length + 1;
    		}
    	}
    	return result;
	}
}

package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

//Choosing lessons is broken into two parts:
//1: selecting the student
//2: selecting the lesson
//This is part one
public class ChooseLessonsPartTwo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_lessons_part_two);
		final Bundle EXTRAS = getIntent().getExtras();
		String studentSelected ="";
		String teacherEmail = "";
		//get teacher email and student selected from previous activity
    	if (EXTRAS != null) {
		    studentSelected = EXTRAS.getString("studentName");
		    teacherEmail = EXTRAS.getString("email");
		}
    	//must make final to be used in an onClick method
    	final String STUDENT = studentSelected; 
    	final String EMAIL = teacherEmail;
    	//get lessons list from resources
    	Resources res = getResources();
    	String[] tmpArray = res.getStringArray(R.array.LessonsArray);
    	//display the list of lessons to the user
    	ListView listview = (ListView)findViewById(R.id.ChooseLessonsPartTwoLV);
    	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmpArray);
		listview.setAdapter(adapter);
		//wait for user to select which lesson they want to assign to the student
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				boolean didItWork = true;
				try{
					String lesson = ((TextView)view).getText().toString();
					Database db = new Database(ChooseLessonsPartTwo.this);
					db.open();
					int IntLesson = lessonStringToInt(lesson);
					//assign they lesson to the student
					db.changeNextLesson(STUDENT, EMAIL, IntLesson);
					db.close();
				}
				//catches SQlExceptions
				catch(Exception ex){
					//outputs error for debugging
					didItWork=false;
					Dialog d = new Dialog(ChooseLessonsPartTwo.this);
					String error = ex.toString();
					d.setTitle("failed to get data");
					TextView tv = new TextView(ChooseLessonsPartTwo.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				}
				finally{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseLessonsPartTwo.this);
					Intent i;
					//let the user know it worked
					if(didItWork){
						alertDialogBuilder.setTitle("Lesson has been assigned to Student");
						alertDialogBuilder.setMessage("Click Ok to return to Homepage");
					}
					else{
						alertDialogBuilder.setTitle("Lesson could not be assigned to Student");
						alertDialogBuilder.setMessage("Please try again later");
					}
					final boolean whereToGo = didItWork;
					//bring the user back to the Teacher Logged In page
					alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent i;
							if(whereToGo){
								i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								i.putExtra("email", EMAIL);
							}
							else{
								i = new Intent(getApplicationContext(), TeacherLoggedIn.class);
								i.putExtra("email", EMAIL);
							}
				            startActivity(i);
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
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

	//takes a lesson as a string and returns it as an int
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

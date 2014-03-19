package com.example.letterchecker;

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

//the viewing results process is a two step process
//1. step one is selecting the student
//2. step two is displaying the reports
// this is step 2

public class ViewResultsPartTwo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_results_part_two);
		final Bundle EXTRAS = getIntent().getExtras();
		String studentSelected ="";
		String teacherEmail = "";
		//get student and teacher from previous activity
    	if (EXTRAS != null) {
		    studentSelected = EXTRAS.getString("studentName");
		    teacherEmail = EXTRAS.getString("email");
		}
    	final String STUDENT = studentSelected; 
    	final String TEACHEREMAIL = teacherEmail;
    	// listview from xml
    	ListView listview = (ListView)findViewById(R.id.ViewResultsPartTwoLV);
    
    	try{
    		Database db = new Database(ViewResultsPartTwo.this);
    		db.open();
    		//get reports from database
    		String[] tmpArray = db.getReportsForTeacher(STUDENT, TEACHEREMAIL);
    		db.close();
    		//display reports to teacher
    		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmpArray);
    		listview.setAdapter(adapter);
    	}
    	//catches SQL exceptions
    	catch(Exception ex){
    		//output the error for debugging
    		Dialog d = new Dialog(ViewResultsPartTwo.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(ViewResultsPartTwo.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
    	}	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_results_part_two, menu);
		return true;
	}

}

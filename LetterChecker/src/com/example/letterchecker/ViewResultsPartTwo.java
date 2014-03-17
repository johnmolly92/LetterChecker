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

public class ViewResultsPartTwo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_results_part_two);
		final Bundle extras = getIntent().getExtras();
		String studentSelected ="";
		String teacherEmail = "";
    	if (extras != null) {
		    studentSelected = extras.getString("studentName");
		    teacherEmail = extras.getString("email");
		}
    	final String tmpStudent = studentSelected; 
    	final String tmpEmail = teacherEmail;
    	
    	ListView listview = (ListView)findViewById(R.id.ViewResultsPartTwoLV);
    
    	try{
    		Database db = new Database(ViewResultsPartTwo.this);
    		db.open();
    		String[] tmpArray = db.getReports(tmpStudent, tmpEmail);
    		db.close();
    		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmpArray);
    		listview.setAdapter(adapter);
    	}
    	catch(Exception ex){
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

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

public class ListOfTeachers extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_teachers);
		final ListView listview;
		listview = (ListView)findViewById(R.id.listView);
		try{
			Database info = new Database(this);
			info.open();
			String[] data = info.getTeacherNames();
			info.close();
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String name = ((TextView)view).getText().toString();
					Intent i = new Intent(getApplicationContext(), ListOfStudents.class);
					i.putExtra("name", name);
					startActivity(i);
					
				}
	        });
		}
		catch(Exception ex){
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

package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		final ListView listview;
		listview = (ListView)findViewById(R.id.testLV);
		try{
			Database info = new Database(this);
			info.open();
			String[] data = info.getStudentInfo();
			data[data.length - 1] = "conor mcarthy lesson = " + info.getStudentNextLesson("Conor McCarthy", "robmcevoy1@gmail.com");
			info.close();
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
			listview.setAdapter(adapter);
			
		}
		catch(Exception ex){
			Dialog d = new Dialog(Test.this);
			String error = ex.toString();
			d.setTitle("failed to get data");
			TextView tv = new TextView(Test.this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}

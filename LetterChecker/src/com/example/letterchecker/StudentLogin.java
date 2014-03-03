package com.example.letterchecker;

// git test..
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class StudentLogin extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_login);
		
		Button enter = (Button)findViewById(R.id.enterBtn);
		
		
		final ListView listview;
		String[] names = this.getResources().getStringArray(R.array.namesArray);
		
		listview = (ListView)findViewById(R.id.list);
		
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
		
		
		enter.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	EditText className = (EditText)findViewById(R.id.className);
            	String c = className.getText().toString();
            	if(c.equals("example")){
            		listview.setAdapter(adapter);
            	}
            	
            }
        });
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_login, menu);
		return true;
	}

}

//John test 3/3/14 .
//Rob Test
package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Start extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Button StudentLogin = (Button)findViewById(R.id.StudentLoginBtn);
		Button TeacherLogin = (Button)findViewById(R.id.TeacherLoginBtn);
		Button Register = (Button)findViewById(R.id.RegisterBtn);
		
		StudentLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), StudentLogin.class);
            	startActivity(i);
            }
        });
		
		TeacherLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), TeacherLogin.class);
            	startActivity(i);
            }
        });
		
		Register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Intent i = new Intent(getApplicationContext(), Register.class);
            	startActivity(i);
            }
        });
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}

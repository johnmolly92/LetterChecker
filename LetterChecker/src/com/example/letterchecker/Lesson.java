package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Lesson extends Activity {

	OurView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		view = new OurView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesson, menu);
		return true;
	}
	
	public class OurView extends View {

		
	
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Paint blue = new Paint();
		private Paint black = new Paint();
		private Path path = new Path();
		
		float x0 =0;
		float y0 =0;
		
		boolean reportCreated=false;
		
		boolean startBool = false;
		boolean middle1 = false;
		boolean endBool = false;
		
		Rect ourRect = new Rect();
		RectF start = new RectF();
		RectF end = new RectF();
		RectF mid1 = new RectF();
		
		public OurView(Context context) {
		//Constructor
			super(context);
			
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(10f);
			red.setColor(Color.RED);
			green.setColor(Color.GREEN);
			green.setStyle(Paint.Style.FILL);
			blue.setColor(Color.YELLOW);
			blue.setStyle(Paint.Style.FILL); 
			black.setColor(Color.BLACK);
			black.setStyle(Paint.Style.FILL);
			
			
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			
			start.set(canvas.getWidth()/8-20, canvas.getHeight()/2-20, canvas.getWidth()/8+20, canvas.getHeight()/2+20);
			mid1.set(canvas.getWidth()/2-20, canvas.getHeight()/2-20, canvas.getWidth()/2+20, canvas.getHeight()/2+20);
			end.set((canvas.getWidth()/8*7)-20,canvas.getHeight()/2-20,(canvas.getWidth()/8*7)+20,canvas.getHeight()/2+20);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
			
			if(startBool == true && middle1 == true && endBool == true && !reportCreated){
				canvas.drawRect(ourRect, blue);
				Bundle extras = getIntent().getExtras();
				String studentSelected ="";
				String teacherEmail = "";
				if (extras != null) {
			    	studentSelected = extras.getString("studentName");
			    	teacherEmail = extras.getString("email");
				}
				try{
					int mark = 100;
					Database db = new Database(Lesson.this);
					db.open();
					db.createReportEntry(studentSelected, teacherEmail, mark, 0, 1);
					db.close();
					Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
					reportCreated = true;
        			extras.putString("studentName", studentSelected);
					extras.putString("email", teacherEmail);
					i.putExtras(extras);
                	startActivity(i);
				}
				catch(Exception ex){
					Dialog d = new Dialog(Lesson.this);
					String error = ex.toString();
					//String error = studentSelected + " " + teacherEmail;
					d.setTitle("failed to get data");
					TextView tv = new TextView(Lesson.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
					
				}
			}
			
 			canvas.drawRect(start, green);
			canvas.drawRect(end,red);
			canvas.drawRect(mid1, black);
			
			if((x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) && (y0 > canvas.getHeight()/2-20) 
					&& (y0 < canvas.getHeight()/2+20)){
				startBool = true;
			}
			if(startBool == true && (x0 > canvas.getWidth()/2-20) && (x0 < canvas.getWidth()/2+20) 
					&& (y0 > canvas.getHeight()/2-20) && (y0 < canvas.getHeight()/2+20)){
				middle1 = true;
			}
			if(startBool == true && middle1 == true && x0 > ((canvas.getWidth()/8*7)-20) && x0 < ((canvas.getWidth()/8*7)+20) 
					&& y0 > (canvas.getHeight()/2-20) && y0 < (canvas.getHeight()/2+20)){
				endBool = true;
			}
			
			canvas.drawPath(path, paint);	
		}
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			float x = event.getX();
			float y = event.getY();
			x0 = x;
			y0 = y;
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				path.moveTo(x, y);
				return true;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(x, y);
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				return false;
			}
			invalidate();
			return true;
		}
	}
}


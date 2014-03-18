package com.example.letterchecker;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Triangle extends Activity {

	OurView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_triangle);
		view = new OurView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.triangle, menu);
		return true;
	}
	
	public class OurView extends View {
		
	
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Paint blue = new Paint();
		private Paint black = new Paint();
		private Path path = new Path();
		
		boolean reportCreated=false;
		
		float x0 =0;
		float y0 =0;
		
		boolean startBool = false;
		boolean middle1 = false;
		boolean middle2 = false;
		boolean middle3 = false;
		boolean middle4 = false;
		boolean middle5 = false;
		boolean endBool = false;
		
		Rect ourRect = new Rect();
		RectF start = new RectF();
		RectF mid1 = new RectF();
		RectF mid2 = new RectF();
		RectF mid3 = new RectF();
		RectF mid4 = new RectF();
		RectF mid5 = new RectF();
		RectF end = new RectF();
		
		
		
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
			
			start.set(canvas.getWidth()/8-20, canvas.getHeight()/3*2-20,
					canvas.getWidth()/8+20, canvas.getHeight()/3*2+20);
			
			mid1.set(canvas.getWidth()/2-20, canvas.getHeight()/3*2-20,
					canvas.getWidth()/2+20, canvas.getHeight()/3*2+20);
			
			mid2.set(canvas.getWidth()/8*7-20, canvas.getHeight()/3*2-20,
					canvas.getWidth()/8*7+20, canvas.getHeight()/3*2+20);
			
			mid3.set(canvas.getWidth()/16*11-20, canvas.getHeight()/2-20,
					canvas.getWidth()/16*11+20, canvas.getHeight()/2+20);
			
			mid4.set(canvas.getWidth()/2-20, canvas.getHeight()/3-20,
					canvas.getWidth()/2+20, canvas.getHeight()/3+20);
			
			mid5.set(canvas.getWidth()/16*5-20, canvas.getHeight()/2-20,
					canvas.getWidth()/16*5+20, canvas.getHeight()/2+20);
			
			end.set(canvas.getWidth()/8-20, canvas.getHeight()/3*2-20,
					canvas.getWidth()/8+20, canvas.getHeight()/3*2+20);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
			
			if(startBool == true && middle1 == true && middle2 == true && middle3 == true
					&& middle4 == true && middle5 == true && endBool == true && !reportCreated ){
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
					Database db = new Database(Triangle.this);
					db.open();
					db.createReportEntry(studentSelected, teacherEmail, mark, 2, 1);
					db.close();
					Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
					reportCreated = true;
        			extras.putString("studentName", studentSelected);
					extras.putString("email", teacherEmail);
					i.putExtras(extras);
                	startActivity(i);
				}
				catch(Exception ex){
					Dialog d = new Dialog(Triangle.this);
					String error = ex.toString();
					//String error = studentSelected + " " + teacherEmail;
					d.setTitle("failed to get data");
					TextView tv = new TextView(Triangle.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
					
				}
			}
			if(middle1 == false)
				canvas.drawRect(start, green);
			if(middle1 == true)
				canvas.drawRect(end,red);
			canvas.drawRect(mid1, black);
			canvas.drawRect(mid2, black);
			canvas.drawRect(mid3, black);
			canvas.drawRect(mid4, black);
			canvas.drawRect(mid5, black);
			//canvas.drawCircle(canvas.getWidth()/3, canvas.getHeight()/2, 20, green);
			//canvas.drawCircle((canvas.getWidth()/3)*2, canvas.getHeight()/2, 20, red);
			//canvas.drawRect(100, 150, 120, 170, green);
			
			//start
			if((x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) && 
					(y0 > canvas.getHeight()/3*2-20) && (y0 < canvas.getHeight()/3*2+20)){
				startBool = true;
			}
			
			//mid1
			if((x0 > canvas.getWidth()/2-20) && (x0 < canvas.getWidth()/2+20) 
					&& (y0 > canvas.getHeight()/3*2-20) && (y0 < canvas.getHeight()/3*2+20)){
				middle1 = true;
			}
			
			//middle2
			if((x0 > canvas.getWidth()/8*7-20) && (x0 < canvas.getWidth()/8*7+20) 
					&& (y0 > canvas.getHeight()/3*2-20) && (y0 < canvas.getHeight()/3*2+20)){
				middle2 = true;
			}
			
			//middle3
			if((x0 > canvas.getWidth()/16*11-20) && (x0 < canvas.getWidth()/16*11+20) 
					&& (y0 > canvas.getHeight()/2-20) && (y0 < canvas.getHeight()/2+20)){
				middle3 = true;
			}
			
			//middle4
			if((x0 > canvas.getWidth()/2-20) && (x0 < canvas.getWidth()/2+20) 
					&& (y0 > canvas.getHeight()/3-20) && (y0 < canvas.getHeight()/3+20)){
				middle4 = true;
			}
			
			//middle5
			if((x0 > canvas.getWidth()/16*5-20) && (x0 < canvas.getWidth()/16*5+20) 
					&& (y0 > canvas.getHeight()/2-20) && (y0 < canvas.getHeight()/2+20)){
				middle5 = true;
			}
			//end
			if(startBool == true && middle1 == true && middle2 == true && 
					middle3 == true && middle4 == true && middle5 == true &&
					(x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) && 
					(y0 > canvas.getHeight()/3*2-20) && (y0 < canvas.getHeight()/3*2+20)){
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

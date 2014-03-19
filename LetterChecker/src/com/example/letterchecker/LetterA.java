package com.example.letterchecker;

import com.example.letterchecker.Lesson.OurView;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class LetterA extends Activity {

	OurView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letter);
		view = new OurView(this);
		setContentView(view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letter, menu);
		return true;
	}
	
	public class OurView extends View {
		
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Paint blue = new Paint();
		private Paint black = new Paint();
		private Paint white = new Paint();
		private Path path = new Path();
		
		float x0 =0;
		float y0 =0;
		
		boolean reportCreated=false;
		
		boolean startBool = false;
		boolean middle1 = false;
		boolean middle2 = false;
		boolean middle3 = false;
		boolean middle4 = false;
		boolean middle5 = false;
		boolean middle6 = false;
		boolean middle7 = false;
		boolean endBool = false;
		
		int mark = 0;
		int totalMark = 0;
		int attempts = 0;
		
		Rect ourRect = new Rect();
		RectF topLine = new RectF();
		RectF bottomLine = new RectF();
		RectF start = new RectF();
		RectF end = new RectF();
		RectF mid1 = new RectF();
		RectF mid2 = new RectF();
		RectF mid3 = new RectF();
		RectF mid4 = new RectF();
		RectF mid5 = new RectF();
		RectF mid6 = new RectF();
		RectF mid7 = new RectF();
		RectF mid8 = new RectF();
		
		Bitmap arrow_left;
		Bitmap arrow_right;
		Bitmap arrow_up;
		Bitmap arrow_down;
		
		Bitmap a;
		
		Bitmap tick;
		
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
			white.setColor(Color.WHITE);
			white.setStyle(Paint.Style.FILL);
			
			arrow_left = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
			arrow_right = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
			arrow_up = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);
			arrow_down = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_down);
			a = BitmapFactory.decodeResource(getResources(), R.drawable.a);
			tick = BitmapFactory.decodeResource(getResources(), R.drawable.green_tick);
			
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			
			float height = canvas.getHeight();
			float width = canvas.getWidth();
			
			topLine.set(0,(height/100*25)-5,width,(height/100*25)+5);
			bottomLine.set(0,(height/100*85)-5,width,(height/100*85)+5);
			start.set((width/100*75)-20, (height/100*40)-20, (width/100*75)+20,(height/100*40)+20);
			mid1.set((width/100*50)-10, (height/100*35)-10, (width/100*50)+10, (height/100*35)+10);
			mid2.set((width/100*33)-10, (height/100*48)-10, (width/100*33)+10, (height/100*48)+10);
			mid3.set((width/100*29)-10, (height/100*65)-10, (width/100*29)+10, (height/100*65)+10);
			mid4.set((width/100*45)-10, (height/100*80)-10, (width/100*45)+10, (height/100*80)+10);
			mid5.set((width/100*65)-10, (height/100*73)-10, (width/100*65)+10, (height/100*73)+10);
			mid6.set((width/100*70)-10, (height/100*57)-10, (width/100*70)+10, (height/100*57)+10);
			mid7.set((width/100*73)-10, (height/100*79)-10, (width/100*73)+10, (height/100*79)+10);
			end.set((width/100*87)-20, (height/100*75)-20, (width/100*87)+20, (height/100*75)+20);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
			canvas.drawRect(ourRect, white);
			
			/*if(endBool == true && attempts <4){
				//calculate mark
				if(startBool == true)
					mark += 1;
				if( middle1 == true)
					mark +=1;
				if(middle2 == true)
					mark += 1;
				if(middle3 == true)
					mark += 1;
				if(middle4 == true)
					mark += 1;
				if(middle5 == true)
					mark += 1;
				if(middle6 == true)
					mark += 1;
				if(middle7 == true)
					mark += 1;
				if(endBool == true)
					mark += 1;
				
				mark = (mark/8)*100;
				totalMark += mark;
				//draw bitmap
				//canvas.drawBitmap(tick, 0, height/100*20, black);
				canvas.drawRect(ourRect, blue);
				
				//erase line
				path.reset();
				//reset booleans
				startBool = middle1 = middle2 = middle3 = middle4 = middle5 = middle6 = middle7 = endBool = false;
				attempts++;
			}*/
			
			//check drawn correct
			/*if(startBool == true && middle1 == true && middle2 == true && middle3 == true && middle4 == true && 
					middle5 == true && middle6 == true && middle7 == true && endBool == true && !reportCreated){*/
			if(endBool == true && !reportCreated){
				
				/*if(startBool == true)
					mark += 1;
				if( middle1 == true)
					mark +=1;
				if(middle2 == true)
					mark += 1;
				if(middle3 == true)
					mark += 1;
				if(middle4 == true)
					mark += 1;
				if(middle5 == true)
					mark += 1;
				if(middle6 == true)
					mark += 1;
				if(middle7 == true)
					mark += 1;
				if(endBool == true)
					mark += 1;
				
				mark = (mark/8)*100;*/
					
				canvas.drawRect(ourRect, blue);
				/*Bundle extras = getIntent().getExtras();
				String studentSelected ="";
				String teacherEmail = "";
				if (extras != null) {
			    	studentSelected = extras.getString("studentName");
			    	teacherEmail = extras.getString("email");
				}
				try{
					//int mark = 100;
					Database db = new Database(LetterA.this);
					db.open();
					db.createReportEntry(studentSelected, teacherEmail, mark, 3, 1);
					db.close();
					Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
					
					reportCreated = true;
					
        			extras.putString("studentName", studentSelected);
					extras.putString("email", teacherEmail);
					i.putExtras(extras);
                	startActivity(i);
				}
				catch(Exception ex){
					Dialog d = new Dialog(LetterA.this);
					String error = ex.toString();
					//String error = studentSelected + " " + teacherEmail;
					d.setTitle("failed to get data");
					TextView tv = new TextView(LetterA.this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				}*/
			}
			
			
			canvas.drawBitmap(a, width/100*40, 10, black);
			
			canvas.drawBitmap(arrow_left, width/100*50,height/100*26, black);
			if(!middle2 == false){
				canvas.drawBitmap(arrow_down, width/100*10,height/100*55, black);
			}
			if(!middle3 == false){
				canvas.drawBitmap(arrow_right, width/100*48,height/100*78, black);
			}
			if(!middle5 == false && middle6 == false){
				canvas.drawBitmap(arrow_up, width/100*75,height/100*55, black);
			}
			if(!middle6 == false){
				canvas.drawBitmap(arrow_down, width/100*75,height/100*55, black);
			}
			
 			canvas.drawRect(start, green);
			canvas.drawRect(end,red);
			canvas.drawRect(mid1, black);
			canvas.drawRect(mid2, black);
			canvas.drawRect(mid3, black);
			canvas.drawRect(mid4, black);
			canvas.drawRect(mid5, black);
			canvas.drawRect(mid6, black);
			canvas.drawRect(mid7, black);			
			canvas.drawRect(topLine, black);
			canvas.drawRect(bottomLine, black);
			
			if((x0 > (width/100*75)-20) && (x0 < (width/100*75)+20) && (y0 > (height/100*40)-20) 
					&& (y0 < (height/100*40)+20)){
				startBool = true;
			}
		
			if((x0 > (width/100*50)-20) && (x0 < (width/100*50)+20) && (y0 > (height/100*35)-20) 
					&& (y0 < (height/100*35)+20)){
				middle1 = true;
			}
			
			if((x0 > (width/100*33)-20) && (x0 < (width/100*33)+20) && (y0 > (height/100*48)-20) 
					&& (y0 < (height/100*48)+20)){
				middle2 = true;
			}
			
			if((x0 > (width/100*29)-20) && (x0 < (width/100*29)+20) && (y0 > (height/100*65)-20) 
					&& (y0 < (height/100*65)+20)){
				middle3 = true;
			}
		
			if((x0 > (width/100*45)-20) && (x0 < (width/100*45)+20) && (y0 > (height/100*80)-20) 
					&& (y0 < (height/100*80)+20)){
				middle4 = true;
			}
			
			if((x0 > (width/100*65)-20) && (x0 < (width/100*65)+20) && (y0 > (height/100*73)-20) 
					&& (y0 < (height/100*73)+20)){
				middle5 = true;
			}
			
			if((x0 > (width/100*70)-20) && (x0 < (width/100*70)+20) && (y0 > (height/100*57)-20) 
					&& (y0 < (height/100*57)+20)){
				middle6 = true;
			}
			
			if((x0 > (width/100*73)-20) && (x0 < (width/100*73)+20) && (y0 > (height/100*79)-20) 
					&& (y0 < (height/100*79)+20)){
				middle7 = true;
			}
			
			if((x0 > (width/100*87)-20) && (x0 < (width/100*87)+20) && (y0 > (height/100*75)-20) 
					&& (y0 < (height/100*75)+20)){
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

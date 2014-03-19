package com.example.letterchecker;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Square extends Activity {

	OurView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_square);
		view = new OurView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.square, menu);
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
		boolean endBool = false;
		
		Rect ourRect = new Rect();
		RectF start = new RectF();
		RectF mid1 = new RectF();
		RectF mid2 = new RectF();
		RectF mid3 = new RectF();
		RectF end = new RectF();
		
		Bitmap arrow_left;
		Bitmap arrow_right;
		Bitmap arrow_up;
		Bitmap arrow_down;
		
		int mark = 0;
		
		
		
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
				
				arrow_left = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
				arrow_right = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
				arrow_up = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);
				arrow_down = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_down);
				
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			
			float height = canvas.getHeight();
			float width = canvas.getWidth();
			
			start.set(canvas.getWidth()/8-20, canvas.getHeight()/4*3-20,
					canvas.getWidth()/8+20, canvas.getHeight()/4*3+20);
			
			mid1.set(canvas.getWidth()/8*7-20, canvas.getHeight()/4*3-20,
					canvas.getWidth()/8*7+20, canvas.getHeight()/4*3+20);
			
			mid2.set(canvas.getWidth()/8*7-20, canvas.getHeight()/4-20,
					canvas.getWidth()/8*7+20, canvas.getHeight()/4+20);
			
			mid3.set(canvas.getWidth()/8-20, canvas.getHeight()/4-20,
					canvas.getWidth()/8+20, canvas.getHeight()/4+20);
			
			end.set(canvas.getWidth()/8-20, canvas.getHeight()/4*3-20,
					canvas.getWidth()/8+20, canvas.getHeight()/4*3+20);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
			
			if(endBool == true && !reportCreated){
				if(startBool == true)
					mark += 100;
				if( middle1 == true)
					mark +=100;
				if(middle2 == true)
					mark += 100;
				if(middle3 == true)
					mark+=100;
				if(endBool == true)
					mark += 100;
				
				mark = mark/5;
				
				reportCreated = true;
				
				//canvas.drawRect(ourRect, green);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Square.this);
				alertDialogBuilder.setTitle("Lesson Complete");
				alertDialogBuilder.setMessage("Click Ok to continue");
				alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Bundle extras = getIntent().getExtras();
						String studentSelected ="";
						String teacherEmail = "";
						if (extras != null) {
					    	studentSelected = extras.getString("studentName");
					    	teacherEmail = extras.getString("email");
						}
						try{
							//int mark = 100;
							Database db = new Database(Square.this);
							db.open();
							db.createReportEntry(studentSelected, teacherEmail, mark, 1, 1);
							db.close();
							Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
							
							//reportCreated = true;
							
		        			extras.putString("studentName", studentSelected);
							extras.putString("email", teacherEmail);
							i.putExtras(extras);
		                	startActivity(i);
						}
						catch(Exception ex){
							Dialog d = new Dialog(Square.this);
							String error = ex.toString();
							//String error = studentSelected + " " + teacherEmail;
							d.setTitle("failed to get data");
							TextView tv = new TextView(Square.this);
							tv.setText(error);
							d.setContentView(tv);
							d.show();
						}
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
			if(middle1 == false)
				canvas.drawRect(start, green);
			if(middle1 == true)
				canvas.drawRect(end,red);
			canvas.drawRect(mid1, black);
			canvas.drawRect(mid2, black);
			canvas.drawRect(mid3, black);
			
			canvas.drawBitmap(arrow_right, width/100*30,height/100*65, black);
			canvas.drawBitmap(arrow_right, width/100*55,height/100*65, black);
			canvas.drawBitmap(arrow_up, width/100*70,height/100*35, black);
			canvas.drawBitmap(arrow_up, width/100*70,height/100*55, black);
			canvas.drawBitmap(arrow_left, width/100*30,height/100*30, black);
			canvas.drawBitmap(arrow_left, width/100*55,height/100*30, black);
			canvas.drawBitmap(arrow_down, width/100*15,height/100*55, black);
			canvas.drawBitmap(arrow_down, width/100*15,height/100*35, black);
			
			//canvas.drawCircle(canvas.getWidth()/3, canvas.getHeight()/2, 20, green);
			//canvas.drawCircle((canvas.getWidth()/3)*2, canvas.getHeight()/2, 20, red);
			//canvas.drawRect(100, 150, 120, 170, green);
			
			//start
			if((x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) && 
					(y0 > canvas.getHeight()/4*3-20) && (y0 < canvas.getHeight()/4*3+20)){
				startBool = true;
			}
			
			//mid1
			if((x0 > canvas.getWidth()/8*7-20) && (x0 < canvas.getWidth()/8*7+20) 
					&& (y0 > canvas.getHeight()/4*3-20) && (y0 < canvas.getHeight()/4*3+20)){
				middle1 = true;
			}
			
			//middle2
			if((x0 > canvas.getWidth()/8*7-20) && (x0 < canvas.getWidth()/8*7+20) 
					&& (y0 > canvas.getHeight()/4-20) && (y0 < canvas.getHeight()/4+20)){
				middle2 = true;
			}
			
			//middle3
			if((x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) 
					&& (y0 > canvas.getHeight()/4-20) && (y0 < canvas.getHeight()/4+20)){
				middle3 = true;
			}
			
			
			//end
			if(middle1 == true  && (x0 > canvas.getWidth()/8-20) && (x0 < canvas.getWidth()/8+20) && 
					(y0 > canvas.getHeight()/4*3-20) && (y0 < canvas.getHeight()/4*3+20)){
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

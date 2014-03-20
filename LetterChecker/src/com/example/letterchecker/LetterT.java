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
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class LetterT extends Activity {

	OurView view; //creates a new instance of the OurView class to handle all the drawing.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//when the activity is created we set our view to contain this activity
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letter_t);
		view = new OurView(this);
		setContentView(view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letter_t, menu);
		return true;
	}
	
	public class OurView extends View {
		//create different colour paints
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Paint blue = new Paint();
		private Paint black = new Paint();
		private Paint white = new Paint();
		private Path path = new Path();
		
		//these values store the co-ordinates of the users finger
		float x0 =0;
		float y0 =0;
		
		//true if a report has been submitted for the lesson
		boolean reportCreated=false;
		
		//values for each point the user must hit
		boolean startBool = false;
		boolean middle1 = false;
		boolean middle2 = false;
		boolean middle3 = false;
		boolean middle4 = false;
		boolean middle5 = false;
		boolean middle6 = false;
		boolean middle7 = false;
		boolean endBool = false;
		
		//students mark, calculated at the end of the lesson
		int mark = 0;
		int totalMark = 0;
		int attempts = 0;
		
		//rectangles to represent points the user must hit
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
		
		//bitmaps to store the guide arrows
		Bitmap arrow_left;
		Bitmap arrow_right;
		Bitmap arrow_up;
		Bitmap arrow_down;
		
		//bitmap to hold the guide letter
		Bitmap t;
		
		
		public OurView(Context context) {
		//Constructor
			super(context);
			//set the different styles of each paint
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
			
			//decode the .png arrow file to a bitmap 
			arrow_left = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
			arrow_right = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
			arrow_up = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);
			arrow_down = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_down);
			
			//decode the .png letter file to a bitmap 
			t = BitmapFactory.decodeResource(getResources(), R.drawable.t);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			//This method is continuously called and handles drawing to the canvas
			super.onDraw(canvas);
			
			//get the height and width of the drawable surface
			float height = canvas.getHeight();
			float width = canvas.getWidth();
			
			//set the position of the guide rectangles
			topLine.set(0,(height/100*25)-5,width,(height/100*25)+5);
			bottomLine.set(0,(height/100*85)-5,width,(height/100*85)+5);
			
			if(startBool == false)
			//set the start rectangle to be bigger than the others
				start.set((width/100*47)-20, (height/100*40)-20, (width/100*47)+20,(height/100*40)+20);
			else
			//when the user touches the start rectangle reduce its size to match the others
				start.set((width/100*47)-10, (height/100*40)-10, (width/100*47)+10,(height/100*40)+10);
			
			mid1.set((width/100*35)-20, (height/100*48)-20, (width/100*35)+20, (height/100*48)+20);
			mid2.set((width/100*47)-10, (height/100*48)-10, (width/100*47)+10, (height/100*48)+10);
			mid3.set((width/100*47)-10, (height/100*56)-10, (width/100*47)+10, (height/100*56)+10);
			mid4.set((width/100*47)-10, (height/100*64)-10, (width/100*47)+10, (height/100*64)+10);
			mid5.set((width/100*47)-10, (height/100*72)-10, (width/100*47)+10, (height/100*72)+10);
			mid6.set((width/100*52)-10, (height/100*80)-10, (width/100*52)+10, (height/100*80)+10);
			
			if(middle7 == false)
				mid7.set((width/100*64)-20, (height/100*82)-20, (width/100*64)+20, (height/100*82)+20);
			else
				mid7.set((width/100*64)-10, (height/100*82)-10, (width/100*64)+10, (height/100*82)+10);
			end.set((width/100*59)-20, (height/100*48)-20, (width/100*59)+20, (height/100*48)+20);
			
			//if the user has reached the last rectangle we calculate the mark and submit the report
			if(endBool == true && !reportCreated){
				//calculate mark
				if(startBool == true)
					mark += 100;
				if( middle1 == true)
					mark +=100;
				if(middle2 == true)
					mark += 100;
				if(middle3 == true)
					mark += 100;
				if(middle4 == true)
					mark += 100;
				if(middle5 == true)
					mark += 100;
				if(middle6 == true)
					mark += 100;
				if(middle7 == true)
					mark += 100;
				if(endBool == true)
					mark += 100;
				
				mark = (mark/9);
				reportCreated = true;
					
				//alert box for when the lesson is finished
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LetterT.this);
				alertDialogBuilder.setTitle("Lesson Complete");
				alertDialogBuilder.setMessage("Click Ok to continue");
				alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						//pass the student name and teacher email to the next activity
						Bundle extras = getIntent().getExtras();
						String studentSelected ="";
						String teacherEmail = "";
						if (extras != null) {
					    	studentSelected = extras.getString("studentName");
					    	teacherEmail = extras.getString("email");
						}
						try{
							//create an instance of the database in this activity
							Database db = new Database(LetterT.this);
							db.open();
							//put the report into the database
							db.createReportEntry(studentSelected, teacherEmail, mark, 22, 1);
							db.close();
							
							//next activity will be the student logged in screen
							Intent i = new Intent(getApplicationContext(), StudentLoggedIn.class);
							
							//pass the information to the next activity
		        			extras.putString("studentName", studentSelected);
							extras.putString("email", teacherEmail);
							i.putExtras(extras);
		                	startActivity(i);
						}
						catch(Exception ex){
							//will display an error message if something goes wrong
							Dialog d = new Dialog(LetterT.this);
							String error = ex.toString();
							d.setTitle("failed to get data");
							TextView tv = new TextView(LetterT.this);
							tv.setText(error);
							d.setContentView(tv);
							d.show();
						}
					}
				});
				//display the alert box
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
			
			
			canvas.drawBitmap(t, width/100*25, 0, black);
			
			//draw the guide arrows to the canvas
			if(middle7 == false){
				canvas.drawBitmap(arrow_down, width/100*50,height/100*40, black);
				canvas.drawBitmap(arrow_down, width/100*50,height/100*52, black);
				canvas.drawBitmap(arrow_down, width/100*50,height/100*64, black);
				canvas.drawBitmap(arrow_right, width/100*55,height/100*72, black);
			}
			else{
				canvas.drawBitmap(arrow_right, width/100*27,height/100*37, black);
				canvas.drawBitmap(arrow_right, width/100*54,height/100*37, black);
			}
			
			//draw the guide rectangles to the canvas
			if(startBool == false)
				canvas.drawRect(start, green);
			else
				canvas.drawRect(start, black);
			if(middle7 == true)
			{
				canvas.drawRect(end,red);
				canvas.drawRect(mid1, green);
			}
			canvas.drawRect(mid2, black);
			canvas.drawRect(mid3, black);
			canvas.drawRect(mid4, black);
			canvas.drawRect(mid5, black);
			canvas.drawRect(mid6, black);
			if(middle7 == false)
				canvas.drawRect(mid7, red);
			else
				canvas.drawRect(mid7, black);
			canvas.drawRect(topLine, black);
			canvas.drawRect(bottomLine, black);
			
			//if the users finger passes over the guide rectangle change the boolean value corresponding to the rectangle
			if((x0 > (width/100*47)-20) && (x0 < (width/100*47)+20) && (y0 > (height/100*40)-20) 
					&& (y0 < (height/100*40)+20)){
				startBool = true;
			}
		
			if((x0 > (width/100*35)-20) && (x0 < (width/100*35)+20) && (y0 > (height/100*48)-20) 
					&& (y0 < (height/100*48)+20)){
				middle1 = true;
			}
			
			if((x0 > (width/100*47)-20) && (x0 < (width/100*47)+20) && (y0 > (height/100*48)-20) 
					&& (y0 < (height/100*48)+20)){
				middle2 = true;
			}
			
			if((x0 > (width/100*47)-20) && (x0 < (width/100*47)+20) && (y0 > (height/100*56)-20) 
					&& (y0 < (height/100*56)+20)){
				middle3 = true;
			}
		
			if((x0 > (width/100*47)-20) && (x0 < (width/100*47)+20) && (y0 > (height/100*64)-20) 
					&& (y0 < (height/100*64)+20)){
				middle4 = true;
			}
			
			if((x0 > (width/100*47)-20) && (x0 < (width/100*47)+20) && (y0 > (height/100*72)-20) 
					&& (y0 < (height/100*72)+20)){
				middle5 = true;
			}
			
			if((x0 > (width/100*52)-20) && (x0 < (width/100*52)+20) && (y0 > (height/100*80)-20) 
					&& (y0 < (height/100*80)+20)){
				middle6 = true;
			}
			
			if((x0 > (width/100*64)-20) && (x0 < (width/100*64)+20) && (y0 > (height/100*82)-20) 
					&& (y0 < (height/100*82)+20)){
				middle7 = true;
			}
			
			if((x0 > (width/100*59)-20) && (x0 < (width/100*59)+20) && (y0 > (height/100*48)-20) 
					&& (y0 < (height/100*48)+20)){
				endBool = true;
			}
			
			//draw a line where the users finger travels 
			canvas.drawPath(path, paint);	
		}
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
		//users touching the screen is handled by this method
			//get the users fingers x and y position
			float x = event.getX();
			float y = event.getY();
			x0 = x;
			y0 = y;
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//when the users fingers touches down on the screen the path is moved to this point
				path.moveTo(x, y);
				return true;
			case MotionEvent.ACTION_MOVE:
			//when the users finger moves the path is drawn along the x and y co-ordinates
				path.lineTo(x, y);
				break;
			case MotionEvent.ACTION_UP:
			//nothing happens when the user removes the finger
				break;
			default:
				return false;
			}
			//call invalidate(); to continually call this method and update the x and y co-ordinates
			invalidate();
			return true;
		}
	}

}

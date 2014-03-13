package com.example.letterchecker;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.LinearLayout;

public class Lesson extends Activity {

	OurView view;
	//public Paint paint = new Paint();
	//public Path path = new Path();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		view = new OurView(this);
		//view.setOnTouchListener(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesson, menu);
		return true;
	}
	
	public class OurView extends View {

		Thread t = null;
		SurfaceHolder holder;
		boolean okToRun = false;
	
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Paint blue = new Paint();
		private Paint black = new Paint();
		private Path path = new Path();
		
		float x0 =0;
		float y0 =0;
		
		boolean startBool = false;
		boolean middle1 = false;
		boolean endBool = false;
		
		Rect ourRect = new Rect();
		RectF start = new RectF();
		RectF end = new RectF();
		RectF mid1 = new RectF();
		
		public LayoutParams params;
		LinearLayout parentLinearLayout;
		
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
			blue.setColor(Color.CYAN);
			blue.setStyle(Paint.Style.FILL); 
			black.setColor(Color.BLACK);
			black.setStyle(Paint.Style.FILL);
			
			parentLinearLayout = new LinearLayout(context);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			
			start.set(canvas.getWidth()/8-20, canvas.getHeight()/2-20, canvas.getWidth()/8+20, canvas.getHeight()/2+20);
			mid1.set(canvas.getWidth()/2-20, canvas.getHeight()/2-20, canvas.getWidth()/2+20, canvas.getHeight()/2+20);
			end.set((canvas.getWidth()/8*7)-20,canvas.getHeight()/2-20,(canvas.getWidth()/8*7)+20,canvas.getHeight()/2+20);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
			
			if(startBool == true && middle1 == true && endBool == true){
				canvas.drawRect(ourRect, blue);
			}
			
 			canvas.drawRect(start, green);
			canvas.drawRect(end,red);
			canvas.drawRect(mid1, black);
			//canvas.drawCircle(canvas.getWidth()/3, canvas.getHeight()/2, 20, green);
			//canvas.drawCircle((canvas.getWidth()/3)*2, canvas.getHeight()/2, 20, red);
			//canvas.drawRect(100, 150, 120, 170, green);
			
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


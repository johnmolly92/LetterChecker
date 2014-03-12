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
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class Lesson extends Activity {

	OurView view;
	public Paint paint = new Paint();
	public Path path = new Path();
	//float x,y;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		view = new OurView(this);
		//view.setOnTouchListener(this);
		setContentView(view);
	}

	/*@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		view.pause();
	}*/

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.resume();
	}*/

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
		//
		private Paint paint = new Paint();
		private Paint red = new Paint();
		private Paint green = new Paint();
		private Path path = new Path();
		
		float x =0;
		float y =0;
		
		
		
		
		
		public LayoutParams params;
		LinearLayout parentLinearLayout;
		//
		public OurView(Context context) {
		//Constructor
			super(context);
			//holder = getHolder();
			
			//params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(10f);
			red.setColor(Color.RED);
			green.setColor(Color.GREEN);
			green.setStyle(Paint.Style.FILL);
			
			parentLinearLayout = new LinearLayout(context);
		}
		
		
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			Rect ourRect = new Rect();
			RectF start = new RectF();
			start.set(100, 150, 150, 200);
			ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);
			Paint blue = new Paint();
			blue.setColor(Color.CYAN);
			blue.setStyle(Paint.Style.FILL); 
			//canvas.drawRect(ourRect, blue);
			canvas.drawRect(start, green);
			canvas.drawCircle(canvas.getWidth()/3, canvas.getHeight()/2, 20, green);
			canvas.drawCircle((canvas.getWidth()/3)*2, canvas.getHeight()/2, 20, red);
			//canvas.drawRect(100, 150, 120, 170, green);
			
			
			
			canvas.drawPath(path, paint);
			
			if(start.contains(x, y));{
				canvas.drawRect(ourRect, blue);
			}
			/*if(path.isRect(start)){
				path.reset();
			}*/
			
			
			
			
		}



		/*public void run() {
		//First method run when thread starts
			//Draw guide circles
			Canvas c = holder.lockCanvas();
			c.drawColor(Color.BLACK);
			c.drawCircle(10,10,50,paint);
			holder.unlockCanvasAndPost(c);
			
			while(okToRun == true){
				// draw to canvas
				if(!holder.getSurface().isValid()){
					continue;
				}
				
				holder.lockCanvas();
				c.drawARGB(255, 0, 50, 255);
				c.drawPath(path, paint);
				holder.unlockCanvasAndPost(c);
				
			}
		}*/
		
		
		
		public void pause() {
			okToRun = false;
			while(true){
				try{
					t.join();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}
		
		/*public void resume() {
			okToRun = true;
			//t = new Thread(this);
			t.start();
		}*/
	
		
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();
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

	/*@Override
	public boolean onTouch(View view, MotionEvent me) {
	//When user touches screen onTouch is called.
		
		
		
			float x = me.getX();
			float y = me.getY();
			switch(me.getAction()){
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
			return true;
	}*/
	
}


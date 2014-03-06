package com.example.letterchecker;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class Lesson extends Activity implements OnTouchListener{

	OurView view;
	public Paint paint = new Paint();
	public Path path = new Path();
	//float x,y;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		view = new OurView(this);
		view.setOnTouchListener(this);
		setContentView(view);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		view.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.resume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesson, menu);
		return true;
	}
	
	public class OurView extends SurfaceView implements Runnable{
	 
		

		Thread t = null;
		SurfaceHolder holder;
		boolean okToRun = false;
		//
		
		public LayoutParams params;
		LinearLayout parentLinearLayout;
		//
		public OurView(Context context) {
		//Constructor
			super(context);
			holder = getHolder();
			
			//
			
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(10f);
			
			parentLinearLayout = new LinearLayout(context);
		}
		
		public void run() {
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
		}
		
		
		
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
		
		public void resume() {
			okToRun = true;
			t = new Thread(this);
			t.start();
		}
	
		
	
	
	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
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
		return true;
	}
	}*/
	}

	@Override
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
	}
	
}


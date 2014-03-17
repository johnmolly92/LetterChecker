package com.example.letterchecker;

import com.example.letterchecker.Lesson.OurView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

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
		private Path path = new Path();
		
		float x0 =0;
		float y0 =0;
		
		boolean startBool = false;
		boolean middle1 = false;
		boolean endBool = false;
		
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

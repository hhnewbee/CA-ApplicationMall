package com.example.downloadui.itemview;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

public class ClassifyrefreshOnclicklistener implements OnTouchListener{
	private String type;
	private int index=0;
	private int line;
	private boolean refresh=false;
	public ClassifyrefreshOnclicklistener(String type){
		this.type=type;
	}
			@Override
			public boolean onTouch(View v, MotionEvent  event) {
				// TODO Auto-generated method stub
				 switch (event.getAction() & MotionEvent.ACTION_MASK){  
			        case MotionEvent.ACTION_MOVE:
			        	System.out.println(v.getScrollY()+"ÕýÔÚÒÆ¶¯");
			        	if(((ViewGroup) v).getChildAt(0) != null && (((ViewGroup) v).getChildAt(0).getMeasuredHeight() <= v.getScrollY() + v.getHeight()&&!refresh)){
			        		line=((ViewGroup) v).getChildAt(0).getMeasuredHeight();
			        		if(!refresh)
			        		index++;
				        	Classifylistener.listener(type, index);
				    		refresh=true;
				    		System.out.println(index+"---"+line+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				        	}
			        	if(((ViewGroup) v).getChildAt(0).getMeasuredHeight()>line){
			        		refresh=false;
			        		System.out.println(""+index+"++++++++++++++++++++");
			        	}
			        	break;
				 }
				return false;
			}
}

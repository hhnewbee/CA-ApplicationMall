package com.example.downloadui.itemview;

import com.example.downlaodui.infodata.Temporarydata;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

public class ClassifyrefreshOnclicklistener implements OnTouchListener{
	private String type;
	private int index=1;
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
			        		System.out.println(v.getScrollY()+"------------");
				        	Classifylistener.listener(type, index);
				    		line=v.getScrollY() + v.getHeight();
				    		refresh=true;
				        	}
			        	if(v.getScrollY()-line==1&&refresh){
			        		index++;
			        		refresh=false;
			        		System.out.println(""+index+"++++++++++++++++++++");
			        	}else if(!Temporarydata.refresh){
			        		refresh=false;
			        		Temporarydata.refresh=true;
			        	}
			        	break;
				 }
				return false;
			}
}

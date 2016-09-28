package com.example.downloadui.itemview;

import com.example.downlaodui.infodata.AppContext;
import com.example.downloadui.jsonservic.JsonService;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class RefreshOnclicklistener implements OnTouchListener{
	private String type;
	private int index=0;
	private int line=0;
	private boolean refresh=false;
	public RefreshOnclicklistener(String type){
		this.type=type;
	}
			@Override
			public boolean onTouch(View v, MotionEvent  event) {
				// TODO Auto-generated method stub
				 switch (event.getAction() & MotionEvent.ACTION_MASK){
			        case MotionEvent.ACTION_MOVE:
			        	System.out.println(v.getScrollY()+"ÕýÔÚÒÆ¶¯");
			        	System.out.println(index+"index");
			        	System.out.println(line+"###################################");
			        	if(((ViewGroup) v).getChildAt(0) != null && (((ViewGroup) v).getChildAt(0).getMeasuredHeight() <= v.getScrollY() + v.getHeight()&&!refresh)) {
			        		line=((ViewGroup) v).getChildAt(0).getMeasuredHeight();
			        		if(!refresh){
			        			index++;
			        		}
				        	Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
				    		intent.putExtra("type",type);
				    		intent.putExtra("index", ""+index);
				    		AppContext.getInstence().startService(intent);
				    		refresh=true;
				    		System.out.println(line+"------------");
				    		System.out.println(index+"************************");
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

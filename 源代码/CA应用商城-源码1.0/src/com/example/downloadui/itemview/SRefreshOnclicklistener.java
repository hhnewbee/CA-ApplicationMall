package com.example.downloadui.itemview;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.JsonService;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class SRefreshOnclicklistener implements OnTouchListener{
	private String type;
	private int index=0;
	private int line=0;
	private boolean refresh=false;
	public SRefreshOnclicklistener(String type){
		this.type=type;
	}
			@Override
			public boolean onTouch(View v, MotionEvent  event) {
				// TODO Auto-generated method stub
				 switch (event.getAction() & MotionEvent.ACTION_MASK){
			        case MotionEvent.ACTION_MOVE:
			        	if(((ViewGroup) v).getChildAt(0) != null && (((ViewGroup) v).getChildAt(0).getMeasuredHeight() <= v.getScrollY() + v.getHeight()&&(!refresh||!Temporarydata.spdr))) {
			        		line=((ViewGroup) v).getChildAt(0).getMeasuredHeight();
			        		if(!refresh){
			        			index++;
			        		}
				        	Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
				    		intent.putExtra("type",type);
				    		intent.putExtra("index", ""+index);
				    		AppContext.getInstence().startService(intent);
				    		refresh=true;
				        	}
			        	if(((ViewGroup) v).getChildAt(0).getMeasuredHeight()>line){
			        		refresh=false;
			        	}
			        	
			        	break;
				 }
				return false;
			}
}

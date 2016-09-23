package com.example.downloadui.itemview;

import java.util.List;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.managerView.Informbroadcast;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class RandomView {
//	public static RandomView randomview;
//	
//	public static RandomView getinstence(){
//		if(randomview==null)
//		randomview=new RandomView();
//		return randomview;
//	}
//	private RandomView(){}
	public void getrandomitem(List<View> list){
		
		LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());  
		LinearLayout viewitem1=(LinearLayout) inflater.inflate(R.layout.viewpageritem,null);
		
		ScrollView scroll=(ScrollView)viewitem1.findViewById(R.id.scrollview);
		scroll.setOnTouchListener(new RefreshOnclicklistener("suiji"));
		View waiting_dwonloaditem=inflater.inflate(R.layout.waiting_downloaditem, null);
		LinearLayout randomadditemlinearlayout=(LinearLayout)viewitem1.findViewById(R.id.managelayout);
		randomadditemlinearlayout.addView(waiting_dwonloaditem);
		
		UIrefreshbroadcast.randomadditemlinearlayout=randomadditemlinearlayout;
		Informbroadcast.randomadditemlinearlayout=randomadditemlinearlayout;
		Temporarydata.random_waiting_downloaditem=waiting_dwonloaditem;
		list.add(viewitem1);
	}
}

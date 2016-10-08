package com.example.downloadui.itemview;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.ADset;
import com.example.downloadui.R;
import com.example.downloadui.jsonservic.JsonService;
import com.example.downloadui.managerView.Informbroadcast;

public class RecommendView {
//	public static RecommendView recommendView;
	
//	public static RecommendView getinstence(){
//		if(recommendView==null)
//		recommendView=new RecommendView();
//		return recommendView;
//	}
//	private RecommendView(){}
	
	public void getrecommenditem(List<View> list){
		LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());  
		View viewitem1 = inflater.inflate(R.layout.adviewpager, null);
		Temporarydata.recommend_view=viewitem1;
		
		LinearLayout downloaditemlinearlayout=(LinearLayout)viewitem1.findViewById(R.id.downloadlayout);
		View waiting_dwonloaditem=inflater.inflate(R.layout.waiting_downloaditem, null);
		
		downloaditemlinearlayout.addView(waiting_dwonloaditem);
		
		UIrefreshbroadcast.downloaditemlinearlayout=downloaditemlinearlayout;
		Informbroadcast.downloaditemlinearlayout=downloaditemlinearlayout;
		Temporarydata.recommend_waiting_downloaditem=waiting_dwonloaditem;
		
		Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
		intent.putExtra("type","tuijian");
		intent.putExtra("index", "0");
		AppContext.getInstence().startService(intent); 
		
		new ADset(Stringtool.getadpictureurl(),viewitem1);
		
		ScrollView scrollview=(ScrollView)viewitem1.findViewById(R.id.downloaditemscrollview);
		scrollview.setOnTouchListener(new RefreshOnclicklistener("tuijian"));
		
		
		list.add(viewitem1);
	}
	
}

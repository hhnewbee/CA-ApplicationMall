package com.example.downloadui;

import java.util.ArrayList;
import java.util.List;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.itemview.ClassifyView;
import com.example.downloadui.itemview.RandomView;
import com.example.downloadui.itemview.RecommendView;
import com.example.downloadui.jsonservic.JsonService;
import com.example.downloadui.managerView.ManageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Viewpagerset{
	private View view;
	private ViewPager viewpage;
	private TextView[] texts;
	private List<View> list;
	private int index=0;
	
	public Viewpagerset(Context context,View view){
		this.view=view;
	}
	public void viewpage(){
		textview();
		viewpage=(ViewPager)view.findViewById(R.id.mPager);
		list=new ArrayList<View>();
		new RecommendView().getrecommenditem(list);
		new RandomView().getrandomitem(list);
		new ClassifyView().getclassifyitem(list);
		new ManageView().getmanageitem(list);
		Myviewpageradapter adapter=new Myviewpageradapter(list);
		viewpage.setOffscreenPageLimit(4);
		viewpage.setAdapter(adapter);
		viewpage.setCurrentItem(0);
		viewpage.setOnPageChangeListener(new Myonpagerchangelistener());
	}
	private void textview(){
		TextView[] texts={(TextView)view.findViewById(R.id.text1),(TextView)view.findViewById(R.id.text2)
				,(TextView)view.findViewById(R.id.text3)
				,(TextView)view.findViewById(R.id.text4)};
		for(int i=0;i<texts.length;i++){
			texts[i].setOnClickListener(new Myonclicklistener(i));
		}
		texts[0].setTextColor(Color.RED);
		this.texts=texts;
	}
	
	
	public class Myonclicklistener implements OnClickListener{
		private int index=0;
		public Myonclicklistener(int index){
			this.index=index;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			viewpage.setCurrentItem(index);	
		}
	}
	public class Myonpagerchangelistener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int index) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int index, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0){
			// TODO Auto-generated method stub
			
			if(arg0==1&&Temporarydata.suijiinternectpd==false){	
			Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
			intent.putExtra("type","suiji");
			intent.putExtra("index","0");
			AppContext.getInstence().startService(intent);
			}
			
			if(arg0==0&&Temporarydata.tuijianinternectpd==false){
				Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
				intent.putExtra("type","tuijian");
				intent.putExtra("index","0");
				AppContext.getInstence().startService(intent);
				
			}
			if(!Temporarydata.adinternectpd){
			new ADset(Stringtool.getadpictureurl(),Temporarydata.recommend_view);
			}
			
			if(Temporarydata.gamepopupwindow!=null){
				if(Temporarydata.gamepopupwindow.isShowing()){
					Temporarydata.gamepopupwindow.dismiss();
				}
			}
			
			texts[arg0].setTextColor(Color.RED);
			texts[index].setTextColor(Color.BLACK);
			index=arg0;
		}
	}
}

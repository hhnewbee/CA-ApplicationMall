package com.example.downloadui;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class Myviewpageradapter extends PagerAdapter{

	private List<View> list;
	public Myviewpageradapter(List<View> list){
		this.list=list;
		
	}
	public void destroyItem(ViewGroup container,int postion,Object object){
		System.out.println(postion+"Ïú»Ù");
		container.removeView((View) list.get(postion));
	}
	public Object instantiateItem(ViewGroup container,int postion){
		System.out.println(postion+"´´½¨");
		container.addView((View) list.get(postion),0);
		return list.get(postion);
		
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override 
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
	
		return arg0==arg1;
	}
}

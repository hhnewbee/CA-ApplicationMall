package com.example.downlaodui.infodata;

import com.example.downloadui.MainActivity;
import android.app.Application;

public class AppContext extends Application{
	public static AppContext instance;
	public static MainActivity main;
	public static AppContext getInstence(){
		return instance;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance=this;
	}
	
}

package com.example.downloadui.itemview;

import java.util.ArrayList;

import android.content.Intent;
import android.widget.LinearLayout;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.JsonService;

public class Classifylistener {
	public static LinearLayout layout;
	public static void listener(String type,int index){
		if(!(Temporarydata.holdler.downloaditem.containsKey(type))||Temporarydata.holdler.refreshcount.get(type)<index){
			UIrefreshbroadcast.layoutlist=new ArrayList<LinearLayout>(); 
			Temporarydata.holdler.refreshcount.put(type,index);
			Intent intent = new Intent(AppContext.getInstence(), JsonService.class);
			intent.putExtra("type",type);
			intent.putExtra("index",index+"");
			AppContext.getInstence().startService(intent); 
		}else{
			for(int i=10*Temporarydata.holdler.refreshcount.get(type);i<Temporarydata.holdler.downloaditem.get(type).size();i++)
			layout.addView(Temporarydata.holdler.downloaditem.get(type).get(i));
		}
	}
}

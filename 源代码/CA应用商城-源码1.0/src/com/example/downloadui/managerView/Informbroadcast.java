package com.example.downloadui.managerView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Informbroadcast extends BroadcastReceiver{
	public static LinearLayout downloaditemlinearlayout ;
	public static LinearLayout randomadditemlinearlayout ;
	public static LinearLayout classifyadditemlinearlayout;
	public static LinearLayout refreshitem;
	private LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());
	private LinearLayout refreshpditem=(LinearLayout)inflater.inflate(R.layout.pdlayout, null);
	@Override
	public void onReceive(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
		
		
		switch(intent.getAction()){
		
		case "tuijianrefreshitem":
			setlayout(downloaditemlinearlayout);
			Temporarydata.tuijianpdlayout=refreshpditem;
			break;
			
		case "suijirefreshitem":
			setlayout(randomadditemlinearlayout);
			Temporarydata.suijipdlayout=refreshitem;
			break;
			
		case "classifyrefreshitem":
			setlayout(classifyadditemlinearlayout);
			Temporarydata.classifypdlayout=refreshitem;
			break;
//			
//		case "internectalert":
//			View customDialog = inflater.inflate(R.layout.dailog_layout, null);  
//			Dialog dialog = new Dialog(AppContext.main);
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setContentView(customDialog);  
//			dialog.show();  
//		    break;
		}
	}
	
	public void setlayout(LinearLayout layout){
		refreshitem=layout;
		refreshitem.addView(refreshpditem);
	}

}

package com.example.downloadui.itemview;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.downloadservic.Downloadbuttonlistener;
import com.example.downloadui.jsonservic.MemoryDb;
import com.example.downloadui.managerView.Softwareitemonclicklistener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UIrefreshbroadcast extends BroadcastReceiver{
	public static LinearLayout downloaditemlinearlayout ;
	public static LinearLayout randomadditemlinearlayout ;
	public static LinearLayout classifyadditemlinearlayout;
//	public static ArrayList<LinearLayout> layoutlist;
	private MemoryDb db=MemoryDb.getDefault();
	private String id;
	
	private LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());
	@Override
	public void onReceive(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
				this.id=intent.getStringExtra("id");
				LinearLayout downloaditem=(LinearLayout)inflater.inflate(R.layout.downloaditem, null);
				((TextView) downloaditem.findViewById(R.id.downloaditemname)).setText(db.Query("true_name",id));
				((TextView) downloaditem.findViewById(R.id.softwareinformation)).setText(db.Query("content",id).replaceAll ("\\\\n","\n"));
				((TextView) downloaditem.findViewById(R.id.downloaditemsize)).setText(setsize());
				ImageView image=(ImageView)downloaditem.findViewById(R.id.softwarepicture);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				new Getpicture().getpicture(db.Query("directory_img",id),image);
		switch(intent.getAction()){
		
		case "tuijian":
			if(Temporarydata.refreshitempd){
				downloaditemlinearlayout.removeViewAt(downloaditemlinearlayout.getChildCount()-1);
				Temporarydata.refreshitempd=false;
			}
			downloaditemlinearlayout.addView(downloaditem);
			break;
		case "suiji":
			if(Temporarydata.refreshitempd){
				randomadditemlinearlayout.removeViewAt(randomadditemlinearlayout.getChildCount()-1);
				Temporarydata.refreshitempd=false;
			}
			randomadditemlinearlayout.addView(downloaditem);
			break;
			
		case "xinkuyingyong":
			setclassifyitem(downloaditem,intent.getAction());
			break;
		case "tongxunshejiao":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "yingyinshiting":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "xinwenyuedu":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "youxiyule":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "bianjieshenghuo":
			setclassifyitem(downloaditem,intent.getAction());			
			break;	
		case "bangongshangwu":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "zhutibizhi":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "yuermuying":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "jiaotongdaohang":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "xitonganquan":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
			
			
		case "dongzuojingji":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "xiuxianyizhi":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "qipaitiyu":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "celuemaoxian":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "juesebanyan":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
		case "fuzhugongju":
			setclassifyitem(downloaditem,intent.getAction());
			break;	
			
			
		case "search":
			Temporarydata.searchlayout.addView(downloaditem);
			
	}
	TextView downloadbutton=(TextView)downloaditem.findViewById(R.id.downloadingbutton);
	downloadbutton.setOnClickListener(new Downloadbuttonlistener(intent.getStringExtra("id"),intent.getAction()));
	
	LinearLayout downloadinfo=(LinearLayout)downloaditem.findViewById(R.id.downloadinfo);
	downloadinfo.setOnClickListener(new Softwareitemonclicklistener(intent.getStringExtra("id"),intent.getAction(),"d"));
	}
	
	public void setclassifyitem(LinearLayout itemlayout,String type){
		if(Temporarydata.refreshitempd){
			classifyadditemlinearlayout.removeViewAt(classifyadditemlinearlayout.getChildCount()-1);
			Temporarydata.refreshitempd=false;
	}
		classifyadditemlinearlayout.addView(itemlayout);
//		layoutlist.add(itemlayout);
//		Temporarydata.holdler.downloaditem.put(type,layoutlist);
	}
	
	private String setsize(){
		return String.format("%.1f",Float.parseFloat(db.Query("size",id).substring(0,db.Query("size",id).indexOf("K")))/1024)+"MB";
	}
}
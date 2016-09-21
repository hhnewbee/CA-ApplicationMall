package com.example.downloadui.managerView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downloadui.info.Infoactivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class Softwareitemonclicklistener implements OnClickListener{
	private String id;
	private String type;
	public  Softwareitemonclicklistener(String id,String type){
		this.id=id;
		this.type=type;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new  Intent(AppContext.main,Infoactivity.class);
		intent.putExtra("id",id);
		intent.putExtra("type",type);
		AppContext.main.startActivity(intent);
	}
	
}
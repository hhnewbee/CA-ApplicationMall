package com.example.downloadui.managerView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downloadui.info.Infoactivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class Softwareitemonclicklistener implements OnClickListener{
	private String id;
	private String type;
	private String pd;
	public  Softwareitemonclicklistener(String id,String type,String pd){
		this.id=id;
		this.type=type;
		this.pd=pd;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new  Intent(AppContext.main,Infoactivity.class);
		intent.putExtra("id",id);
		intent.putExtra("type",type);
		intent.putExtra("pd",pd);
		AppContext.main.startActivity(intent);
	}
	
}
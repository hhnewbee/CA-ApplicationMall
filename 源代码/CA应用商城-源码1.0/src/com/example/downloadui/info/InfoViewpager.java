package com.example.downloadui.info;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downloadui.R;
import com.example.downloadui.jsonservic.MemoryDb;
import com.example.downloadui.jsonservic.MySQL_user;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class InfoViewpager{
    private View view;
    private Context context;
    private String type;
    private String id;
    private String pd;
    private MemoryDb db=MemoryDb.getDefault();
    
    public InfoViewpager(Context context,View view,String id,String type,String pd){
    	this.context=context;
    	this.view=view;
    	this.id=id;
    	this.type=type;
    	this.pd=pd;
    	initData();	
    }
	private void initData(){
		
		LinearLayout imagelayout=(LinearLayout)view.findViewById(R.id.softwareinfoimagelayout);
		LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(300,LinearLayout.LayoutParams.MATCH_PARENT);
		ps.setMargins(10,1,10,1);
		String picture[]=null;
		
		if(pd.equals("r")){
			MySQL_user dbr=MySQL_user.getInstance(AppContext.getInstence());
			String pictureurl[]={dbr.getdownloadinfourl("record","directory_img_content_1","id",id),dbr.getdownloadinfourl("record","directory_img_content_2","id",id),dbr.getdownloadinfourl("record","directory_img_content_3","id",id)};
			picture=pictureurl;
		}else{
			String pictureurl[]={db.Query("directory_img_content_1 text",type,id),db.Query("directory_img_content_2 text",type,id),db.Query("directory_img_content_3 text",type,id)};
			picture=pictureurl;
		}
        for(int i=0; i<picture.length; i++){
          ImageView iv = new ImageView(context);
          iv.setLayoutParams(ps);
          new Getpicture().getpicture(picture[i], iv);
          imagelayout.addView(iv);
        } 
	}
}

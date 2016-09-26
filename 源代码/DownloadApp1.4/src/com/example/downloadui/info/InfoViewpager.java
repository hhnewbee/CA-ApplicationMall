package com.example.downloadui.info;

import com.example.downlaodui.infodata.Getpicture;
import com.example.downloadui.R;
import com.example.downloadui.jsonservic.MemoryDb;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class InfoViewpager{
    private View view;
    private Context context;
    private String type;
    private String id;
    private MemoryDb db=MemoryDb.getDefault();
    
    public InfoViewpager(Context context,View view,String id,String type){
    	this.context=context;
    	this.view=view;
    	this.id=id;
    	this.type=type;
    	initData();	
    }
	private void initData(){
		
		LinearLayout imagelayout=(LinearLayout)view.findViewById(R.id.softwareinfoimagelayout);
		LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(300,LinearLayout.LayoutParams.MATCH_PARENT);
		ps.setMargins(20,1,20,1);
		String pictureurl[]={db.Query("directory_img_content_1 text",type,id),db.Query("directory_img_content_2 text",type,id),db.Query("directory_img_content_3 text",type,id)};
        for(int i=0; i<pictureurl.length; i++){
          ImageView iv = new ImageView(context);
          iv.setLayoutParams(ps);
          new Getpicture().getpicture(pictureurl[i], iv);
          imagelayout.addView(iv);
        } 
	}
}

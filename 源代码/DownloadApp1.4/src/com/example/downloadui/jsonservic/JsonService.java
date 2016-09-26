package com.example.downloadui.jsonservic;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.itemview.UIrefreshbroadcast;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by user on 2016/7/31.
 */

public class JsonService extends Service{
	private String type;
	private int index;
	private String url;
	private ContentValues values;
	private boolean pdtuijian;
	private boolean pdsuiji;
	private View tuijiannotnetlayot;
	private View suijinotnetlayout;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// TODO Auto-generated method stub
    	
    	
    	index=Integer.parseInt(intent.getStringExtra("index"));
    	type = intent.getStringExtra("type");
    	
    	if("tuijian".equals(type)||"suiji".equals(type)){
    		url=Stringtool.gettuijianandsuijiurl(intent.getStringExtra("index"), type);
    	}
    	else if("search".equals(type)){
    		url=Stringtool.getupdateurl(intent.getStringExtra("content"));
    	}
    	else{ 
    		url=Stringtool.getclassifyurl(intent.getStringExtra("index"),type);
    	}
    	
    	
    	 OkHttpUtils.get()
         .url(url)
         .build()
         .execute(new StringCallback() {
             @Override
             public void onError(Call call, Exception e, int id) {
             	
//             	Intent intent = new Intent("internectalert");
//	            AppContext.getInstence().sendBroadcast(intent);
        		LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());  
	            
	            if("tuijian".equals(type)&&!pdtuijian){
	            	tuijiannotnetlayot=inflater.inflate(R.layout.notnetlayout, null);
	            	Temporarydata.recommend_waiting_downloaditem.setVisibility(View.GONE);
	            	UIrefreshbroadcast.downloaditemlinearlayout.addView(tuijiannotnetlayot);
	            	pdtuijian=true;
	        	 }
	            
	            else if("suiji".equals(type)&&!pdsuiji){
	            	suijinotnetlayout=inflater.inflate(R.layout.notnetlayout, null);
	            	Temporarydata.random_waiting_downloaditem.setVisibility(View.GONE);
	            	UIrefreshbroadcast.randomadditemlinearlayout.addView(suijinotnetlayout);
	            	pdsuiji=true;
	            	
	             }
	            
	            System.out.println(e.toString());
             }

             @Override
             public void onResponse(String response, int id) {
            	 if(response==null){
            		 return;
            	}
            	 myGson(response,type);
            	 if("tuijian".equals(type))
            	 	{
	            		 Temporarydata.tuijianinternectpd=true;
	            		 if(Temporarydata.recommend_waiting_downloaditem.isShown())
	            	 	 Temporarydata.recommend_waiting_downloaditem.setVisibility(View.GONE);
	            	 	 if(tuijiannotnetlayot!=null){
	            	 		 UIrefreshbroadcast.downloaditemlinearlayout.removeView(tuijiannotnetlayot);
	            	 	 }
            	 	}
                 else if("suiji".equals(type)){
                	 Temporarydata.suijiinternectpd=true;
                	 if(Temporarydata.random_waiting_downloaditem.isShown())
                	 Temporarydata.random_waiting_downloaditem.setVisibility(View.GONE);
                	 if(suijinotnetlayout!=null){
                		 UIrefreshbroadcast.randomadditemlinearlayout.removeView(suijinotnetlayout);
	            	 	 }
                 }
             }
         });
    	return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    	
    	values = new ContentValues();
    	MemoryDb.getDefault();
    }

    private void myGson(String JsonData,String type){
    	if(JsonData.equals("[]")){
    		 if(type.equals("search")){
    			 LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());
    			 View searchnotlayot=inflater.inflate(R.layout.searchlayout, null);
    			 Temporarydata.searchlayout.addView(searchnotlayot);
    			 }
    		return;
    		}
    	
        Gson gson = new Gson();
        List<Myjson>  myjsonList=null;
        
        try{
        myjsonList=gson.fromJson(JsonData,new TypeToken<List<Myjson>>(){}.getType());
        }catch(JsonParseException e){
        	return;
        }
        
        if(myjsonList==null){return;}
        for(Myjson myjson:myjsonList){
            
	         values.put("id",myjson.getId());
	         values.put("category",myjson.getCategory());
	         values.put("true_name",myjson.getTrue_name());
	         values.put("name",myjson.getName());
	         values.put("content",myjson.getContent());
	         values.put("size",myjson.getSize());
	         values.put("version",myjson.getVersion());
	         values.put("download_times",myjson.getDownload_times());
	         values.put("time_upload",myjson.getTime_upload());
	         values.put("directoty_soft",myjson.getDirectory_soft());
	         values.put("directory_img",myjson.getDirectory_img());
	         values.put("directory_img_content_1",myjson.getDirectory_img_content_1());
	         values.put("directory_img_content_2",myjson.getDirectory_img_content_2());
	         values.put("directory_img_content_3",myjson.getDirectory_img_content_3());
	         
	         
	         MemoryDb.getDefault().Insert(values,type);
	         System.out.println(type+"----------------------");
	         
             Intent intent = new Intent(type);
             intent.putExtra("id",myjson.getId());
             sendBroadcast(intent);
             
             values.clear();
        }
        
        if("tuijian".equals(type)||"suiji".equals(type)){
        Intent intent = new Intent(type+"refreshitem");
		sendBroadcast(intent);
        }
        else{
        	Intent intent = new Intent("classifyrefreshitem");
     		sendBroadcast(intent);
        }
		
		 if(index>0){
			 Temporarydata.refreshitempd=true;
     	 }
		 
//		 System.out.println(JsonData+"-----------------------------");
    }

}

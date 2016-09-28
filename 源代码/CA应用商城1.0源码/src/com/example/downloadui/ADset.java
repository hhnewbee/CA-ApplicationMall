package com.example.downloadui;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.Myjson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class ADset {
	private View viewitem; 
	public List<ImageView> adimageviewlist=new ArrayList<ImageView>();
	public ADset(String url,View viewitem1){
		this.viewitem=viewitem1;
			OkHttpUtils.get()
	        .url(url)
	        .build()
	        .execute(new StringCallback() {
	            @Override
	            public void onError(Call call, Exception e, int id) {
	            	return;
	            }

	            @Override
	            public void onResponse(String response,int id) {
	            	  if("[]".equals(response)||response==null){
	            		  return;
	            	  }
	            	  Temporarydata.adinternectpd=true;
	            	  Gson gson = new Gson();
	                  List<Myjson> myjsonList= gson.fromJson(response,new TypeToken<List<Myjson>>(){}.getType());
	                  for(int i=0;i<myjsonList.size();i++){
	                	  ImageView adimageView=new ImageView(AppContext.getInstence());
	                	  new Getpicture().getpicture(myjsonList.get(i).getDirectory_img(),adimageView);
	                	  String adurl=myjsonList.get(i).getURL();
	                	  adimageView.setOnClickListener(new webviewonclick(adurl));
	                	  adimageviewlist.add(adimageView);
	                	  }
	                  new AdViewpager(AppContext.getInstence(),viewitem,adimageviewlist);
	            }
	        });
	}
	public class webviewonclick implements OnClickListener{
		private String url;
		public webviewonclick(String url){
			this.url=url;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(AppContext.main,Webactivity.class);
			intent.putExtra("url",url);
			AppContext.main.startActivity(intent);
		}
		
	}
}
package com.example.downloadui.update;

import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.downloadservic.Updatelistener;
import com.example.downloadui.jsonservic.MySQL_user;
import com.example.downloadui.jsonservic.Myjson;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by user on 2016/8/10.
 */

public class QueryAppInfo {
	
	private static QueryAppInfo queryAppInfo;
	
	private QueryAppInfo(){}
	
	public static QueryAppInfo  getinstenc(){
	    	if(queryAppInfo==null){
	    		queryAppInfo=new  QueryAppInfo();
	    	}
			return queryAppInfo;
	    }
    public  void  uploadApp(final AppInfo temp,final LinearLayout layout) {
   	 OkHttpUtils.get()
        .url(Stringtool.getupdateurl(temp.getAppName()))
        .build()
        .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("TGA", e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                if("[]".equals(response)){
                	return;
                }
                List<Myjson>  myjsonList=null;
                try{
                	myjsonList=gson.fromJson(response,new TypeToken<List<Myjson>>(){}.getType());
                }catch(JsonParseException e){
                	return;
                }
                if (myjsonList.get(0).getVersion().compareTo(temp.getVersion()) > 0) {
                	insectupdate(temp.getAppName());
                    /**
                     * 该软件需要更新
                     * 把对应的json解析出来加载到布局中
                     * 在子线程中，需要使用handler
                     */
                	LinearLayout updateitem=(LinearLayout)LayoutInflater.from(AppContext.getInstence()).inflate(R.layout.downloadlineitem, null);
                	updateitem.setTag(myjsonList.get(0).getTrue_name());
            		ImageView updateimage=(ImageView)updateitem.findViewById(R.id.downloadingpictue);
            		updateimage.setImageDrawable(temp.getAppIcon());
            		ProgressBar progressbar=(ProgressBar)updateitem.findViewById(R.id.downloadprogressbar);
            		
            		TextView progresstext=(TextView)updateitem.findViewById(R.id.progressbartext);
            		TextView ratetext=(TextView)updateitem.findViewById(R.id.downloadratetext);
            		
            		TextView nametext=(TextView)updateitem.findViewById(R.id.downloadnametext);
                	nametext.setText(myjsonList.get(0).getTrue_name());
                	
                	TextView downloadtext=(TextView)updateitem.findViewById(R.id.downloading);
                	downloadtext.setTag(myjsonList.get(0).getTrue_name());
                	Updatelistener updatelistener=null;
                	
                	if("1".equals(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","size","true_name",myjsonList.get(0).getTrue_name()))){
                		downloadtext.setText("安装");
                		updatelistener=new Updatelistener(myjsonList.get(0).getDirectory_soft(),myjsonList.get(0).getName(),progressbar,progresstext,ratetext,myjsonList.get(0).getTrue_name(),2);
                    	downloadtext.setOnClickListener(updatelistener);
                	}else{ 
                	downloadtext.setText("更新");
                	updatelistener=new Updatelistener(myjsonList.get(0).getDirectory_soft(),myjsonList.get(0).getName(),progressbar,progresstext,ratetext,myjsonList.get(0).getTrue_name(),0);
                	downloadtext.setOnClickListener(updatelistener);
                	}
                	
                	TextView canceltext=(TextView)updateitem.findViewById(R.id.canceldownload);
                	canceltext.setOnClickListener(updatelistener.new Cancelupdateonclick(downloadtext));
                	
                	layout.addView(updateitem);
                	
                	Temporarydata.upgradelayoutlist.add(updateitem);
                	Temporarydata.alldownload.add(downloadtext);
                } else {
                }
             }
        });
}
	public void insectupdate(String appname){
		if(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourlist("updateline","true_name").size()==0){
			MySQL_user.getInstance(AppContext.getInstence()).Insertupdate("updateline","true_name",appname,"size","0");
		}else{
		for(String name:MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourlist("updateline","true_name")){
			if(name.equals(appname)){
				return;
			}
		}
		MySQL_user.getInstance(AppContext.getInstence()).Insertupdate("updateline","true_name",appname,"size","0");
	}
}
}

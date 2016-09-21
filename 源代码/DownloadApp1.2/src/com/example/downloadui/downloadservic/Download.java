package com.example.downloadui.downloadservic;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.MySQL_user;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import java.io.File;
import okhttp3.Call;


/**
 * Created by user on 2016/8/5.
 */

@SuppressLint("SdCardPath")
class Download{
    ProgressBar MprogressBar;
    String Url;
    String Name;
    String type;
    String id;
    String downloadcounturl;
    TextView progresstext;
    TextView downloadtext;
    TextView ratetext;
    String  true_name;
    Integer[] pddownloading;
    HttpHandler<?> handler = null;
    long speeded;
    
    public Download(ProgressBar progressBar,TextView progresstext,TextView ratextext,TextView downloadtext,String url, String name,String downloadcounturl,String type,String id,Integer pddownloading[]) {
        this.MprogressBar = progressBar;
        this.progresstext=progresstext;
        this.downloadtext=downloadtext;
        this.ratetext=ratextext;
        this.Name = name;
        this.Url = url;
        this.id=id;
        this.type=type;
        this.downloadcounturl=downloadcounturl;
        this.pddownloading=pddownloading;
    }
    
    public Download(ProgressBar mprogressBar,TextView mprogresstext,TextView ratextext,TextView downloadtext,String Url,String FileName,Integer pdupdate[],String true_name){
    	 this.MprogressBar = mprogressBar;
    	 this.progresstext=mprogresstext;
    	 this.downloadtext=downloadtext;
    	 this.ratetext=ratextext;
    	 this.pddownloading=pdupdate;
         this.Name = FileName;
         this.Url = Url;
         this.true_name=true_name;
         
    }

    public void DownloadStart(){
        FinalHttp fh = new FinalHttp();
        String apkpath = "/sdcard/downloadapk/"+Name;
        System.out.println(apkpath);
        System.out.println(Url);
        handler = fh.download(Url, apkpath, true, new AjaxCallBack<File>() {
        	
			@Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                if(current!=count&&current!=0) {
                    MprogressBar.setProgress((int)(current / (float) count * 100));
                    progresstext.setText((int)(current / (float) count * 100)+"%");
                    ratetext.setText(getrate(current));
                }else {
                    MprogressBar.setProgress(100);
                    progresstext.setText("下载完成");
                    ratetext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.d("TGA","开始下载");
            }

            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                
                downloadtext.setText("安装");
                MprogressBar.setVisibility(View.GONE);
                pddownloading[0]=2;
                MySQL_user.getInstance(AppContext.getInstence()).Insert("record",type,id);
                MySQL_user.getInstance(AppContext.getInstence()).upDownloading("downloadline","0","id",id);
                Temporarydata.ids.add(id);
            	setdownloadcount();
                Log.d("TGA","下载完成");
            }
        });
    }
    
    public void DownloadStart(boolean pdupdate){
        FinalHttp fh = new FinalHttp();
        String apkpath = "/sdcard/downloadapk/"+Name;
        handler = fh.download(Url, apkpath, true, new AjaxCallBack<File>() {
        	
			@Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                if(current!=count&&current!=0) {
                	progresstext.setVisibility(View.VISIBLE);
                    MprogressBar.setProgress((int)(current / (float) count * 100));
                    progresstext.setText((int)(current / (float) count * 100)+"%");
                    ratetext.setText(getrate(current));
                }else {
                    MprogressBar.setProgress(100);
                    progresstext.setText("下载完成");
                    ratetext.setVisibility(View.GONE);
                }
            }
            
            @Override
            public void onStart() {
                super.onStart();
                Log.d("TGA","开始下载");
            }
            
            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                downloadtext.setText("安装");
                MprogressBar.setVisibility(View.GONE);
                pddownloading[0]=2;
                MySQL_user.getInstance(AppContext.getInstence()).upDownloading("updateline","1","true_name",true_name);
                Log.d("TGA","下载完成");
            }
        });
    }

    //暂停下载
    public void StopDownload(){
        handler.stop();
    }
    //取消下载
    public void Cancel(){
    	if(handler!=null){
        handler.cancel(true);
    	}
    }
    
    public void setdownloadcount(){
    	 OkHttpUtils.get()
         .url(downloadcounturl)
         .build()
         .execute(new StringCallback() {
             @Override
             public void onError(Call call, Exception e, int id) {
             }
             @Override
             public void onResponse(String response, int id) {
            	 System.out.println(downloadcounturl+"成功");
             }
         });
    }
    private String getrate(long current){
		  int speed=(int)((current-speeded)/1024);
		  speeded=current;
		  if(speed>=1024){
			  return speed/1024+"MB/s";
		  }
		  return speed+"KB/s";
  	
  }
}

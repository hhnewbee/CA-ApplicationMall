package com.example.downloadui.sidebar;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.Webactivity;
import com.example.downloadui.downloadservic.Updatelistener;
import com.example.downloadui.installApk.Installapk;
import com.example.downloadui.jsonservic.MySQL_user;
import com.example.downloadui.jsonservic.Myjson;
import com.example.downloadui.update.QueryAppInfo;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

@SuppressLint("SdCardPath")
public class Sidebarfunction {
    private View updateDialog;
    private ProgressBar updateprogress;
    private TextView updateinformation;
    private List<Myjson>  myjsonList;
    private String appname;
    private String appversion;
    private Drawable appicon;
    private boolean pdupdate=false;
    private static Sidebarfunction sidebarfunction;
    
    private Sidebarfunction(){}
    public static Sidebarfunction getinstenc(){
    	if(sidebarfunction==null){
    		sidebarfunction=new  Sidebarfunction();
    	}
		return sidebarfunction;
    }
    
    
    public void shareApp(){
    	Intent intent=new Intent(Intent.ACTION_SEND);  
		intent.setType("text/plain");  
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");  
		intent.putExtra(Intent.EXTRA_TEXT, "CA应用商城是一款可以使用内网高速免流量下载app的软件,欢迎大家下载：http://ca.sise.com.cn:83/uploads/CA%E5%BA%94%E7%94%A8%E5%95%86%E5%9F%8E.apk");  

		AppContext.main.startActivity(Intent.createChooser(intent, "分享到"));  
    }
    
    public void suggsetApp() {  
	    	LayoutInflater layoutInflater = LayoutInflater.from(AppContext.getInstence()); 
	    	View customDialog = layoutInflater.inflate(R.layout.updateself_suggestdialog,null);
	    	final EditText edittext=(EditText)customDialog.findViewById(R.id.textset);
	    	new AlertDialog.Builder(AppContext.main).setView(customDialog).setTitle("信息反馈")  
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        // TODO Auto-generated method stub
                    }  
                })  
                .setNeutralButton("发送", new DialogInterface.OnClickListener() {  
					@Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        // TODO Auto-generated method stub 
						String text=edittext.getText().toString();
						postsuggestContent(text);
                    }  
                }).create().show();  
        }  
    
    	public void vistofficailApp(){
    		Intent intent=new Intent(AppContext.main,Webactivity.class);
			intent.putExtra("url",Stringtool.getofficailurl);
			AppContext.main.startActivity(intent);
    	}
    	
    	public void postsuggestContent(final String text){
        	 OkHttpUtils.get()
             .url(Stringtool.getSuggestcontenturl+text)
             .build()
             .execute(new StringCallback() {
                 @Override
                 public void onError(Call call, Exception e, int id) {
                	Toast.makeText(AppContext.main,"网络无法链接", Toast.LENGTH_LONG).show();
                 }
                 @Override
                 public void onResponse(String response, int id) {
                	 Toast.makeText(AppContext.main,"已发送", Toast.LENGTH_LONG).show();
                 }
             });
        }
    	 
    public void updateApp(){
//    		if(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","name","true_name",appname)!=null){
//        	String pathname=MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","name","true_name",appname);
//    		File file=new File("/sdcard/downloadapk/"+ pathname);
//    		if(file.exists()){
//    			Toast.makeText(AppContext.main,"已经在更新队列中，请前往更新",Toast.LENGTH_LONG).show();
//    			return;
//    			}
//    		}
    		 getVersion();
    		 LayoutInflater layoutInflater =LayoutInflater.from(AppContext.getInstence());  
             updateDialog = layoutInflater.inflate(R.layout.updateself_updateselfdialog,null);
             updateinformation=(TextView)updateDialog.findViewById(R.id.updateinformation);
             updateprogress=(ProgressBar)updateDialog.findViewById(R.id.updateprogress);
             getupdateApp();
             new AlertDialog.Builder(AppContext.main).setView(updateDialog).setTitle("检查更新")  
                     .setPositiveButton("取消", new DialogInterface.OnClickListener() {  
                         @Override  
                         public void onClick(DialogInterface dialog, int which) {  
                             // TODO Auto-generated method stub
                         }  
                     })  
                     .setNeutralButton("确定", new DialogInterface.OnClickListener() {  
 						@Override  
                         public void onClick(DialogInterface dialog, int which) {  
                             // TODO Auto-generated method stub 
 							if(!pdupdate){
// 							setupdatelayout();
// 							Toast.makeText(AppContext.main,"请在更新队列中查看进度",Toast.LENGTH_LONG).show();
 							updateself();
 							}
                         }  
                     }).create().show();  
    	 }
    	 
    	 private void getupdateApp() {
    	   	 OkHttpUtils.get()
    	        .url(Stringtool.getselfUpdateurl)
    	        .build()
    	        .execute(new StringCallback() {
    	            @Override
    	            public void onError(Call call, Exception e, int id) {
    	            	updateprogress.setVisibility(View.GONE);
	  					updateinformation.setVisibility(View.VISIBLE);
	  					updateinformation.setText("暂无更新");
	  					pdupdate=true;
    	            }

    	            @Override
    	            public void onResponse(String response, int id) {
    	                Gson gson = new Gson();
    	                if("[]".equals(response)){
    	                	updateprogress.setVisibility(View.GONE);
    	  					updateinformation.setVisibility(View.VISIBLE);
    	  					updateinformation.setText("暂无更新");
    	  					pdupdate=true;
    	                	return;
    	                }
    	                try{
    	                	myjsonList=gson.fromJson(response,new TypeToken<List<Myjson>>(){}.getType());
    	                }catch(JsonParseException e){
    	                	return;
    	                }
    	                if (myjsonList.get(0).getVersion().compareTo(appversion) > 0) {
    	  					updateprogress.setVisibility(View.GONE);
    	  					updateinformation.setVisibility(View.VISIBLE);
    	  					updateinformation.setText("点击确定更新："+appname+" "+myjsonList.get(0).getVersion());
    	  					updateinformation.setTextColor(Color.BLACK);
    	 
    	                } else {
    	                	updateprogress.setVisibility(View.GONE);
    	  					updateinformation.setVisibility(View.VISIBLE);
    	  					updateinformation.setText("暂无更新");
    	  					pdupdate=true;
    	                }
    	             }
    	        });
    	}
    	 private void getVersion(){  
     		//获取packagemanager的实例   
     		PackageManager packageManager = AppContext.getInstence().getPackageManager();  
     		//getPackageName()是你当前类的包名，0代表是获取版本信息  
     		PackageInfo packInfo=null;
			try {
				packInfo = packageManager.getPackageInfo(AppContext.getInstence().getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
     		appname=packageManager.getApplicationLabel(packInfo.applicationInfo).toString();
     		appicon = packageManager.getApplicationIcon(packInfo.applicationInfo);
     		appversion=packInfo.versionName;
     } 
//    	private void setupdatelayout(){
//    		for(LinearLayout layout:Temporarydata.upgradelayoutlist){
//    			if(layout.getTag().equals(appname)){
//    				for(TextView downloadtext:Temporarydata.alldownload)
//    					if(downloadtext.getTag().equals(appname)){
//    						downloadtext.performClick();
//    						return;
//    					}
//    			}
//    		}
//    		
//    		LinearLayout updateitem=(LinearLayout)LayoutInflater.from(AppContext.getInstence()).inflate(R.layout.downloadlineitem, null);
//         	updateitem.setTag(myjsonList.get(0).getTrue_name());
//     		ImageView updateimage=(ImageView)updateitem.findViewById(R.id.downloadingpictue);
//     		updateimage.setImageDrawable(appicon);
//     		ProgressBar progressbar=(ProgressBar)updateitem.findViewById(R.id.downloadprogressbar);
//     		
//     		TextView progresstext=(TextView)updateitem.findViewById(R.id.progressbartext);
//     		TextView ratetext=(TextView)updateitem.findViewById(R.id.downloadratetext);
//     		
//     		TextView nametext=(TextView)updateitem.findViewById(R.id.downloadnametext);
//         	nametext.setText(myjsonList.get(0).getTrue_name());
//         	
//         	TextView downloadtext=(TextView)updateitem.findViewById(R.id.downloading);
//         	downloadtext.setTag(appname);
//         	
//         	Updatelistener updatelistener=null;
//         	
//         	if("1".equals(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","size","true_name",myjsonList.get(0).getTrue_name()))){
//         		downloadtext.setText("安装");
//         		updatelistener=new Updatelistener(myjsonList.get(0).getDirectory_soft(),myjsonList.get(0).getName(),progressbar,progresstext,ratetext,myjsonList.get(0).getTrue_name(),2);
//         		downloadtext.setOnClickListener(updatelistener);
//         	}else{ 
//         		downloadtext.setText("更新");
//         		updatelistener=new Updatelistener(myjsonList.get(0).getDirectory_soft(),myjsonList.get(0).getName(),progressbar,progresstext,ratetext,myjsonList.get(0).getTrue_name(),0);
//         		downloadtext.setOnClickListener(updatelistener);
//         	}
//         	downloadtext.performClick();
//         	
//         	TextView canceltext=(TextView)updateitem.findViewById(R.id.canceldownload);
//         	canceltext.setOnClickListener(updatelistener.new Cancelupdateonclick(downloadtext));
//         	
//         	Temporarydata.upgradelayoutlist.add(updateitem);
//         	Temporarydata.alldownload.add(downloadtext);
//         	QueryAppInfo.getinstenc().insectupdate(appname);
//    	 }
    	
		public void reminderupdateApp(){
			getVersion();
//			if(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","name","true_name",appname)!=null){
//	        String pathname=MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","name","true_name",appname);
//	    		File file=new File("/sdcard/downloadapk/"+ pathname);
//	    		if(file.exists()){
//	    			return;
//	    			}
//	    	}
    	   	 OkHttpUtils.get()
    	        .url(Stringtool.getselfUpdateurl)
    	        .build()
    	        .execute(new StringCallback() {
    	            @Override
    	            public void onError(Call call, Exception e, int id) {
    	            }

    	            @Override
    	            public void onResponse(String response, int id) {
    	                Gson gson = new Gson();
    	                if("[]".equals(response)){
    	                	return;
    	                }
    	                try{
    	                	myjsonList=gson.fromJson(response,new TypeToken<List<Myjson>>(){}.getType());
    	                }catch(JsonParseException e){
    	                	return;
    	                }
    	                if (myjsonList.get(0).getVersion().compareTo(appversion) > 0) {
    	                	LayoutInflater layoutInflater =LayoutInflater.from(AppContext.getInstence());  
    	                    LinearLayout remainderupdateDialog = (LinearLayout) layoutInflater.inflate(R.layout.updateself_remainderupdatedialog,null);
    	                    TextView remainderupdate=(TextView)remainderupdateDialog.findViewById(R.id.remainderupdatetext);
    	                    remainderupdate.setText("    "+myjsonList.get(0).getVersion()+"版本");
    	                    remainderupdate.setTextColor(Color.BLACK);
    	                    
    	                	new AlertDialog.Builder(AppContext.main).setView(remainderupdateDialog ).setTitle("请更新为")  
    	                     .setPositiveButton("取消", new DialogInterface.OnClickListener() {  
    	                         @Override  
    	                         public void onClick(DialogInterface dialog, int which) {  
    	                             // TODO Auto-generated method stub
    	                         }  
    	                     })  
    	                     .setNeutralButton("确定", new DialogInterface.OnClickListener() {  
    	 						@Override  
    	                         public void onClick(DialogInterface dialog, int which) {  
    	                             // TODO Auto-generated method stub 
//    	 							setupdatelayout();
    	 							updateself();
    	                         }  
    	                     }).create().show();  
    	                }
    	             }
    	        });
    	}
		public void updateself(){
			String name=myjsonList.get(0).getName();
			File file=new File("/sdcard/downloadapk/"+name);
    		if(file.exists()){
    			Installapk.installApk("/sdcard/downloadapk/"+name);
    			return;
    		}
			LayoutInflater layoutInflater =LayoutInflater.from(AppContext.getInstence());  
            LinearLayout updateprogress = (LinearLayout) layoutInflater.inflate(R.layout.updateself_updateprogress,null);
            AlertDialog.Builder dialog=new AlertDialog.Builder(AppContext.main);
            dialog.setView(updateprogress).setTitle("正在更新");
            AlertDialog aDialog=dialog.create();
            aDialog.show(); ;
            new Selfupdate().DownloadStart(myjsonList.get(0).getDirectory_soft(),name,aDialog);
		}
}

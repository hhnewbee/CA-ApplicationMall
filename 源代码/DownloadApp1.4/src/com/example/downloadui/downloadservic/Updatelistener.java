package com.example.downloadui.downloadservic;

import java.io.File;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.installApk.Installapk;
import com.example.downloadui.jsonservic.MySQL_user;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Updatelistener implements OnClickListener{
	private String downloadurl;
	private String  filename;
	private ProgressBar progressbar;
	private TextView progresstext;
	private TextView ratetext;
	private Integer pdupdate[]={0};
	private boolean first=false;
	private Download download;
	private String true_name;
	private boolean pdcancel=false;
	public LayoutInflater inflater=LayoutInflater.from(AppContext.getInstence());
	
	public Updatelistener(String downlaodurl,String Filename,ProgressBar progressbar,TextView progresstext,TextView ratetext,String true_name,int pd){
		this.downloadurl=downlaodurl;
		this.filename=Filename;
		this.progressbar=progressbar;
		this.progresstext=progresstext;
		this.ratetext=ratetext;
		this.true_name=true_name;
		this.pdupdate[0]=pd;
		progresstext.setVisibility(View.GONE);
	}
	@SuppressLint("SdCardPath")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//下载开始！！！！！！！！！！！！！！！
		if(pdupdate[0]==0){
		if(first==false){
		download=new Download(progressbar,progresstext,ratetext,(TextView)v,downloadurl,filename,pdupdate,true_name);
		}
		((TextView)v).setText("暂停");
		((TextView)v).setTextColor(Color.BLACK);
		download.DownloadStart(true);
		pdcancel=true;
		first=true;
		pdupdate[0]=1;
		}else if(pdupdate[0]==1){
			((TextView)v).setText("更新");
			((TextView)v).setTextColor(Color.RED);
			download.StopDownload();
			pdupdate[0]=0;
		}else{
			Installapk.installApk("/sdcard/downloadapk/"+filename);
		}
	}
	
	@SuppressLint("SdCardPath")
	public class Cancelupdateonclick implements OnClickListener{
		private TextView downloadtext;
		public Cancelupdateonclick(TextView downloadtext){
			this.downloadtext=downloadtext;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if("1".equals(MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("updateline","size","true_name",true_name))){
				for(int i=0;i<Temporarydata.updatelayout.getChildCount();i++){
	        		if(true_name.equals(Temporarydata.updatelayout.getChildAt(i).getTag())){
	        			Temporarydata.updatelayout.removeViewAt(i);
	        			Temporarydata.upgradelayoutlist.remove(i);
	        		}
	        	}
				MySQL_user.getInstance(AppContext.getInstence()).upDownloading("updateline","0","true_name",true_name);
//				MySQL_user.getInstance(AppContext.getInstence()).deleteDownloading("updateline","true_name",true_name);
			}else{
				if(pdcancel){
					download.Cancel();	
					downloadtext.setText("更新");
					progressbar.setProgress(0);
					progresstext.setText("");
					pdupdate[0]=0;
					pdcancel=false;
				}
					else{
						downloadtext.setText("更新");
						pdupdate[0]=0;
					}
				
				}
			File file=new File("/sdcard/downloadapk/"+filename);
			final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
			file.renameTo(to);
			to.delete();
		}
		
	}
}

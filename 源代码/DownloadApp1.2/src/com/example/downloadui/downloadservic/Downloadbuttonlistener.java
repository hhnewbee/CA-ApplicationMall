package com.example.downloadui.downloadservic;

import java.io.File;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downlaodui.infodata.Stringtool;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.installApk.Installapk;
import com.example.downloadui.installApk.Pddownload;
import com.example.downloadui.jsonservic.MemoryDb;
import com.example.downloadui.jsonservic.MySQL_user;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Downloadbuttonlistener implements OnClickListener{
	private String idurl;
	private String id;
	private String type;
	private MemoryDb db=MemoryDb.getDefault();
	private Download download;
	private boolean pddownload=false;
	private Integer[] pddownloading={0};
	public Downloadbuttonlistener(String id,String type){
		this.idurl=Stringtool.getdownloadcountsurl(id);
		this.id=id;
		this.type=type;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//下载开始！！！！！！！！！！！！！！！
		if(Pddownload.QueryApp(db.Query("true_name", id),db.Query("name", id))){
			Toast.makeText(AppContext.getInstence(),"已下载", Toast.LENGTH_LONG).show();
			return;
		}
		if(!pddownload){
		LinearLayout downloaditem=(LinearLayout)LayoutInflater.from(AppContext.getInstence()).inflate(R.layout.downloadlineitem,null);
		downloaditem.setTag(db.Query("true_name",type,id));
		ProgressBar progressbar=(ProgressBar)downloaditem.findViewById(R.id.downloadprogressbar);
		ImageView imageview=(ImageView)downloaditem.findViewById(R.id.downloadingpictue);
		new Getpicture().getpicture(db.Query("directory_img",type,id), imageview);
		TextView progresstext=(TextView)downloaditem.findViewById(R.id.progressbartext);
		TextView ratetext=(TextView)downloaditem.findViewById(R.id.downloadratetext);
		TextView downloading=(TextView)downloaditem.findViewById(R.id.downloading);
		TextView canceldownload=(TextView)downloaditem.findViewById(R.id.canceldownload);
		downloading.setText("暂停");
		download=new Download(progressbar, progresstext,ratetext,downloading,db.Query("directoty_soft",type,id),db.Query("name",type,id),idurl,type,id,pddownloading);
		download.DownloadStart();
		downloading.setOnClickListener(new Donwloadonclick());
		canceldownload.setOnClickListener(new CancelDonwloadonclick(downloaditem,(TextView)v));
		
		MySQL_user.getInstance(AppContext.getInstence()).Insert("downloadline", type, id);
		Temporarydata.downloadprogresslayout.add(downloaditem);
		
		((TextView)v).setText("下载中");
		((TextView)v).setTextColor(Color.BLACK);
		pddownload=true;
		}
	}
	@SuppressLint("SdCardPath")
	public class Donwloadonclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(pddownloading[0]==0){
				((TextView)v).setText("继续");
				((TextView)v).setTextColor(Color.RED);
				download.StopDownload();
				pddownloading[0]=1;
			}else if(pddownloading[0]==1){
				((TextView)v).setText("暂停");
				((TextView)v).setTextColor(Color.BLACK);
				download.DownloadStart();
				pddownloading[0]=0;
			}else{
				Installapk.installApk("/sdcard/downloadapk/"+db.Query("name",type,id));
			}
		} 
	}
	
	@SuppressLint("SdCardPath")
	public class CancelDonwloadonclick implements OnClickListener{
		public LinearLayout downloaditem;
		public TextView downloading;
		public CancelDonwloadonclick(LinearLayout downloaditem,TextView downloading){
			this.downloaditem=downloaditem;
			this.downloading=downloading;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			download.Cancel();
			downloaditem.setVisibility(View.GONE);
			MySQL_user.getInstance(AppContext.getInstence()).deleteDownloading("downloadline","id",id);
			Temporarydata.downloadprogresslayout.remove(downloaditem);
			
			File file = new File("/sdcard/downloadapk/"+db.Query("name",id));
			File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
			file.renameTo(to);
			to.delete();
			
			downloading.setText("下载");
			pddownload=false;
		}
	}
}

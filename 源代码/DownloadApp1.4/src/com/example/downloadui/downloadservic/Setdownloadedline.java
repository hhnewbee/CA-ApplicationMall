package com.example.downloadui.downloadservic;

import java.io.File;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.installApk.Installapk;
import com.example.downloadui.jsonservic.MySQL_user;

public class Setdownloadedline {
	private static Setdownloadedline setdownloadedline;
	private Setdownloadedline(){};
	public static Setdownloadedline getinstenc(){
		if(setdownloadedline==null){
			setdownloadedline=new Setdownloadedline();
		}
		return setdownloadedline;
	}
	
	private static MySQL_user db=MySQL_user.getInstance(AppContext.getInstence());
	
	public void set(LinearLayout layout){
		LinearLayout layoutt=null;
		for(String id:db.getdownloadinfourlist("downloadline","id")){
			boolean pd=false;
				for(LinearLayout progresslayout:Temporarydata.downloadprogresslayout){
					if(db.getdownloadinfourl("downloadline","true_name","id",id).equals((String)progresslayout.getTag())){
						pd=true;
						layoutt=progresslayout;
						break;
					}
				}
				if(pd){
					layout.addView(layoutt);
				}else{
					setitem(id,layout);
				}
			}
	}
	
	public void initset(LinearLayout layout){
		for(String id:db.getdownloadinfourlist("downloadline","id")){
			setitem(id,layout);
		}
	}
	
	private void setitem(String id,LinearLayout layout){
		Integer[] pddownloading={0};
		LinearLayout downloaditem=(LinearLayout)LayoutInflater.from(AppContext.getInstence()).inflate(R.layout.downloadlineitem,null);
		downloaditem.setTag(db.getdownloadinfourl("downloadline","true_name","id",id));
		ProgressBar progressbar=(ProgressBar)downloaditem.findViewById(R.id.downloadprogressbar);
		ImageView imageview=(ImageView)downloaditem.findViewById(R.id.downloadingpictue);
		new Getpicture().getpicture(db.getdownloadinfourl("downloadline","directory_img","id",id), imageview);
		TextView nametext=(TextView)downloaditem.findViewById(R.id.downloadnametext);
		nametext.setText(db.getdownloadinfourl("downloadline","true_name","id",id));
		
		TextView ratetext=(TextView)downloaditem.findViewById(R.id.downloadratetext);
		TextView progresstext=(TextView)downloaditem.findViewById(R.id.progressbartext);
		TextView downloading=(TextView)downloaditem.findViewById(R.id.downloading);
		TextView canceldownload=(TextView)downloaditem.findViewById(R.id.canceldownload);
		if(db.getdownloadinfourl("downloadline","size","id",id).equals("0")){
			downloading.setText("安装");
			progresstext.setText("下载完成");
			pddownloading[0]=2;
		}else{
		downloading.setText("下载");
		}
		Download download=new Download(progressbar, progresstext,ratetext,downloading,db.getdownloadinfourl("downloadline","directoty_soft","id",id),db.getdownloadinfourl("downloadline","name","id",id),null,db.getdownloadinfourl("downloadline","category","id",id),id,pddownloading);
		downloading.setOnClickListener(new Downloadedbutton(download,pddownloading,id));
		canceldownload.setOnClickListener(new CancelDonwloadonclicked(downloaditem,download,id));
		layout.addView(downloaditem);
}
	@SuppressLint("SdCardPath")
	public class Downloadedbutton implements OnClickListener{
		private Integer[] pddownloading;
		private Download download;
		private String id;
		public  Downloadedbutton(Download download, Integer[] pddownloading,String id){
			this.download=download;
			this.pddownloading=pddownloading;
			this.id=id;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(pddownloading[0]==0){
				((TextView)v).setText("暂停");
				((TextView)v).setTextColor(Color.BLACK);
				download.DownloadStart();
				pddownloading[0]=1;
				}else if(pddownloading[0]==1){
					((TextView)v).setText("继续");
					((TextView)v).setTextColor(Color.RED);
					download.StopDownload();
					pddownloading[0]=0;
			}
					else{
						Installapk.installApk("/sdcard/downloadapk/"+db.getdownloadinfourl("downloadline","name","id",id));
					}
		}
	}
	@SuppressLint("SdCardPath")
	public class CancelDonwloadonclicked implements OnClickListener{
				public LinearLayout downloaditem;
				private Download download;
				private String id;
					
				public CancelDonwloadonclicked(LinearLayout downloaditem,Download download,String id){
					this.downloaditem=downloaditem;
					this.download=download;
					this.id=id;
				}
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					download.Cancel();
					downloaditem.setVisibility(View.GONE);
					
					File file = new File("/sdcard/downloadapk/"+MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourl("downloadline","name","id",id));
					File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
					file.renameTo(to);
					to.delete();
					
					Temporarydata.downloadprogresslayout.remove(downloaditem);
					MySQL_user.getInstance(AppContext.getInstence()).deleteDownloading("downloadline","id",id);
				}
			}

}

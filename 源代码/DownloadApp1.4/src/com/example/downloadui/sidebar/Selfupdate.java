package com.example.downloadui.sidebar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import com.example.downloadui.installApk.Installapk;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import java.io.File;

class Selfupdate{
    @SuppressLint("SdCardPath")
	public void DownloadStart(String Url,String Name, final AlertDialog dialog){
        FinalHttp fh = new FinalHttp();
        final String apkpath = "/sdcard/downloadapk/"+Name;
        fh.download(Url, apkpath, true, new AjaxCallBack<File>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}
			@Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }
            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                Installapk.installApk(apkpath);
                dialog.dismiss();
            }
        });
    }
//    public void setdownloadcount(String downloadcounturl){
//    	 OkHttpUtils.get()
//         .url(downloadcounturl)
//         .build()
//         .execute(new StringCallback() {
//             @Override
//             public void onError(Call call, Exception e, int id) {
//             }
//             @Override
//             public void onResponse(String response, int id) {
//             }
//         });
//    }
}

package com.example.downloadui.installApk;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.MySQL_user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.widget.LinearLayout;

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	String packageName = intent.getData().getSchemeSpecificPart();
    	PackageManager manager = context.getPackageManager();
    	String AppName=null;
    	try {
    		PackageInfo packInfo = manager.getPackageInfo(packageName, 0);
    		AppName=manager.getApplicationLabel(packInfo.applicationInfo).toString();
    	} catch (NameNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
        	for(LinearLayout layout:Temporarydata.downloadprogresslayout){
        		if(AppName.equals(layout.getTag())){
        			layout.setVisibility(View.GONE);
        			Temporarydata.downloadprogresslayout.remove(layout);
        			MySQL_user.getInstance(AppContext.getInstence()).deleteDownloading("downloadline","true_name",AppName);
        		}
        	}
        }
        
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
        }
        
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
        	if(Temporarydata.updatelayout==null){
        		return;
        	}
        	for(int i=0;i<Temporarydata.updatelayout.getChildCount();i++){
        		if(AppName.equals(Temporarydata.updatelayout.getChildAt(i).getTag())){
        			Temporarydata.updatelayout.removeViewAt(i);
        			Temporarydata.upgradelayoutlist.remove(i);
        		}
        	}
        	MySQL_user.getInstance(AppContext.getInstence()).deleteDownloading("updateline","true_name",AppName);
        }
        

    }

}
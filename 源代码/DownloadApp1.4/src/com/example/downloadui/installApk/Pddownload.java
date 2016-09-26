package com.example.downloadui.installApk;

import java.io.File;
import java.util.List;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.example.downlaodui.infodata.AppContext;

public class Pddownload {
	public static boolean QueryApp(String name,String path) {
        PackageManager pm = AppContext.getInstence().getPackageManager();//获取PackageManager对象
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        File file=new File("/sdcard/downloadapk/"+path);
        for (PackageInfo pi : packageInfos) {
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                String AppName = pm.getApplicationLabel(pi.applicationInfo).toString();
                if(AppName.equals(name)||file.exists()){
                	return true;
                }
            }
        }
        return false;
     }
}

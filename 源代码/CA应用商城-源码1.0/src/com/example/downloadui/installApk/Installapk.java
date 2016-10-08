package com.example.downloadui.installApk;

import java.io.File;

import com.example.downlaodui.infodata.AppContext;

import android.content.Intent;
import android.net.Uri;

public class Installapk {
	public static void installApk(String apkurl) {  
		Uri uri = Uri.fromFile(new File(apkurl));
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
	    AppContext.getInstence().startActivity(intent);  
	}  
}

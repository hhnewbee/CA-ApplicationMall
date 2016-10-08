package com.example.downloadui.info;

import java.util.ArrayList;
import java.util.List;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.update.AppInfo;
import com.example.downloadui.update.QueryAppInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Upgradeactivity extends Activity{
	private List<AppInfo> listAppInfo=new ArrayList<AppInfo>();
	private LinearLayout upgradelayoutt;
	private boolean pd=false;
	private TextView upgradecount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upgradelayout);
		upgradecount=(TextView)findViewById(R.id.upgradecount);
		QueryApp();
		getitem();
	}
	public void getitem(){
		if(Temporarydata.updatelayout!=null){
			Temporarydata.updatelayout.removeAllViews();
		}
		LinearLayout layout=(LinearLayout)findViewById(R.id.upgradelllayout);
		for(AppInfo temp : listAppInfo){
			if(Temporarydata.upgradelayoutlist.size()==0){
				QueryAppInfo.getinstenc().uploadApp(temp,layout,upgradecount);
			}else{
			for(LinearLayout upgradelayout : Temporarydata.upgradelayoutlist){
				if(upgradelayout.getTag().equals(temp.getAppName())){
					pd=true;
					upgradelayoutt=upgradelayout;
					break;
				}
			}
			if(pd){
				layout.addView(upgradelayoutt);
				pd=false;
			}else{
				QueryAppInfo.getinstenc().uploadApp(temp,layout,upgradecount);
			}
		}
	}
		listAppInfo.clear();
		Temporarydata.updatelayout=layout;
		
		upgradecount.setText("共有"+Temporarydata.upgradelayoutlist.size()+"个可更新");
		
		TextView allupgrade=(TextView)findViewById(R.id.allupgrade);
		allupgrade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for(TextView down:Temporarydata.alldownload){
					if(down.getText().equals("更新"))
					down.performClick();
				}
			}
		});
		
		findViewById(R.id.backupgradeactivity).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Temporarydata.updatelayout.removeAllViews();
			}
		});
	}
	
	 /**
     * 循环读取手机中所有第三方应用
     */
	
	public void QueryApp() {
	        PackageManager pm = AppContext.getInstence().getPackageManager();//获取PackageManager对象
	        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);

	        for (PackageInfo pi : packageInfos) {
	            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
	                String AppName = pm.getApplicationLabel(pi.applicationInfo).toString();
	                String PackageName = pi.packageName;
	                String Version = pi.versionName;
	                Drawable Icon = pm.getApplicationIcon(pi.applicationInfo);
	                Intent intent = pm.getLaunchIntentForPackage(PackageName);

	                AppInfo appInfo = new AppInfo();
	                appInfo.setAppName(AppName);
	                appInfo.setPkgName(PackageName);
	                appInfo.setVersion(Version);
	                appInfo.setAppIcon(Icon);
	                appInfo.setIntent(intent);
	                
	                listAppInfo.add(appInfo);
	            }
	        }
	    }
//	@SuppressLint("HandlerLeak")
//	Handler mHandler = new Handler() {
//    	@Override
//    	public void handleMessage(Message msg) {
//    		super.handleMessage(msg);
//    		upgradecount.setText("共有"+Temporarydata.upgradelayoutlist.size()+"个可更新");
//    	}
//    };
}

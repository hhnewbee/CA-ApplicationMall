package com.example.downloadui;

import java.io.File;
import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.jsonservic.JsonService;
import com.example.downloadui.jsonservic.MemoryDb;
import com.example.downloadui.sidebar.Intro;
import com.example.downloadui.sidebar.Sidebarfunction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {
	public ScrollView scrolllayout;
	public EditText searchtext;
	public Viewpagerset getviewpage;
	private View view;
	private Handler mHandler = new Handler();
	private ImageView firstimage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainactivity);
		firstimage=(ImageView) findViewById(R.id.firstpicture);
		firstimage.setBackgroundResource(R.drawable.launch);
		AppContext.main=MainActivity.this;
		mHandler.postDelayed(mImageTimerTask,3000);
		
		getviewpager();
		setmainonclicklistener();
		File destDir = new File("/sdcard/downloadapk");
		  if (!destDir.exists()) {
			  destDir.mkdirs();
		  }
		updateremainderdiaglo();
		setsidebar();
	}
	private void getviewpager(){
		view=getWindow().getDecorView().findViewById(android.R.id.content);
		getviewpage=new Viewpagerset(this,view);
		getviewpage.viewpage();
	}
	private void setmainonclicklistener(){
		searchtext=(EditText)findViewById(R.id.searchingtext);
		findViewById(R.id.searchingbingen).setOnClickListener(new Mainonclicklistener());
	}
	private class Mainonclicklistener implements OnClickListener{
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			scrolllayout=(ScrollView)findViewById(R.id.searchscolloview);
			scrolllayout.setVisibility(View.VISIBLE);
			LinearLayout searchlayout=(LinearLayout)findViewById(R.id.searchlayout);
			Temporarydata.searchlayout=searchlayout;
			
			if(searchlayout.getChildCount()>1){
				searchlayout.removeViews(1,searchlayout.getChildCount()-1);
			}
			String content=searchtext.getText().toString();
			
			Intent intent = new Intent(MainActivity.this,JsonService.class);
			intent.putExtra("type","search");
			intent.putExtra("content",content);
			intent.putExtra("index","0");
			startService(intent); 
			  
			TextView searchback=(TextView)findViewById(R.id.searchcancel);
			searchback.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					scrolllayout.setVisibility(View.GONE);
				}
			});
	}
}
	private void updateremainderdiaglo(){
		Sidebarfunction.getinstenc().reminderupdateApp();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//广告图片是否转动的判断标志
		Temporarydata.stop=true;
		
		MemoryDb.getDefault().releaseMemory();
		
    	Intent intent = new Intent(this, JsonService.class);
		stopService(intent); 
		
		System.exit(0); 
		
		super.onDestroy();
	}
	
	private Runnable mImageTimerTask = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			firstimage.setVisibility(View.GONE);
		}
		
	};
	
	private void setsidebar(){
		LinearLayout setsidebar=(LinearLayout) findViewById(R.id.setsidebar);
		final LinearLayout sidebarpart=(LinearLayout)findViewById(R.id.sidebarpart);
		final DrawerLayout sidebarlayout=(DrawerLayout)findViewById(R.id.sidebarlayout);
		setsidebar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sidebarlayout.openDrawer(sidebarpart);
			}
		} );
		findViewById(R.id.shareapptext).setOnClickListener(new Textcheck());
		findViewById(R.id.aboutapptext).setOnClickListener(new Textcheck());
		findViewById(R.id.suggestapptext).setOnClickListener(new Textcheck());
		findViewById(R.id.updateapptext).setOnClickListener(new Textcheck());
		findViewById(R.id.visitofficial).setOnClickListener(new Textcheck());
		
	}
	private class Textcheck implements OnClickListener{
	    	@Override
	    	public void onClick(View v) {
	    		// TODO Auto-generated method stub
	    		switch (v.getId()) {
	    		case R.id.shareapptext:
	    			Sidebarfunction.getinstenc().shareApp();
	    			break;
	    		case R.id.updateapptext:
					Sidebarfunction.getinstenc().updateApp();
	    			break;
	    		case R.id.suggestapptext:
	    			Sidebarfunction.getinstenc().suggsetApp();
	    			break;
	    		case R.id.aboutapptext:
	    			Intent intent=new Intent(MainActivity.this,Intro.class);
	    			AppContext.main.startActivity(intent);
	    			break;
	    		case R.id.visitofficial:
	    			Sidebarfunction.getinstenc().vistofficailApp();
	    			break;
	    		}
	    	}
	    	
	    }
}
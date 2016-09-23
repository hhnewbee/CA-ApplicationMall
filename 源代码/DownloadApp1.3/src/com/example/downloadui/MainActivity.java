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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
	private ImageView searchimage;
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
		findViewById(R.id.mainlayout).setOnClickListener(new Mainlayoutonclicklistener());
	}
	private void getviewpager(){
		view=getWindow().getDecorView().findViewById(android.R.id.content);
		getviewpage=new Viewpagerset(this,view);
		getviewpage.viewpage();
	}
	private void setmainonclicklistener(){
		searchtext=(EditText)findViewById(R.id.searchingtext);
		searchimage=(ImageView) findViewById(R.id.searchingbingen);
		searchimage.setOnClickListener(new Mainonclicklistener());
		searchtext.setOnFocusChangeListener(new Editextonclicklistener());
		searchtext.setOnClickListener(new Mainlayoutonclicklistener());
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
	private class Editextonclicklistener implements OnFocusChangeListener{
		@Override
		public void onFocusChange(View v, boolean p) {
			// TODO Auto-generated method stub
			if(!p){
			searchimage.setVisibility(View.GONE);
			((EditText)v).setHint("CA应用商城");
			}else{
				searchimage.setVisibility(View.VISIBLE);
				((EditText)v).setHint("");
			}
		}
		
	}
	
	private class Mainlayoutonclicklistener implements OnClickListener{

		@Override
		public void onClick(View v) {
//			 TODO Auto-generated method stub
//			if(R.id.searchingtext==v.getId()){
//				searchtext.setFocusable(true);
//				searchtext.setFocusableInTouchMode(true);
//				searchtext.requestFocus();
//			}
//			else{
//			searchtext.setFocusable(false);//失去焦点
//			System.out.println("失去焦点");
//			}
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
		ImageView setsidebar=(ImageView) findViewById(R.id.setsidebar);
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
	
	@Override  
	public boolean dispatchTouchEvent(MotionEvent ev) {  
	    if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
	        View v = getCurrentFocus();  
	        if (isShouldHideInput(v, ev)) {  
	  
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	            if (imm != null) {  
	                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	            }  
	        }  
	        return super.dispatchTouchEvent(ev);  
	    }  
	    // 必不可少，否则所有的组件都不会有TouchEvent了  
	    if (getWindow().superDispatchTouchEvent(ev)) {  
	        return true;  
	    }  
	    return onTouchEvent(ev);  
	}  
	
	public  boolean isShouldHideInput(View v, MotionEvent event) {  
	    if (v != null && (v instanceof EditText)) {  
	        int[] leftTop = { 0, 0 };  
	        //获取输入框当前的location位置  
	        v.getLocationInWindow(leftTop);  
	        int left = leftTop[0];  
	        int top = leftTop[1];  
	        int bottom = top + v.getHeight();  
	        int right = left + v.getWidth();  
	        if (event.getX() > left && event.getX() < right  
	                && event.getY() > top && event.getY() < bottom) {  
	            // 点击的是输入框区域，保留点击EditText的事件  
	            return false;  
	        } else {  
	            return true;  
	        }  
	    }  
	    return false;  
	}  
}
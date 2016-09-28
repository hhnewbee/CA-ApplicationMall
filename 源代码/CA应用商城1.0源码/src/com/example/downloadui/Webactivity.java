package com.example.downloadui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Webactivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weblayout);
		setwebview(getIntent().getStringExtra("url"));
	}
	
	public void setwebview(String url){
		ImageView imageback=(ImageView)findViewById(R.id.webactivityback);
		imageback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		final WebView myWebView = (WebView)findViewById(R.id.webview);
		myWebView.setVisibility(View.GONE);
		
		final LinearLayout waitinglayout=(LinearLayout) findViewById(R.id.webview_waiting);
		myWebView.setWebViewClient(new WebViewClient()
	     {   
            @Override
            public void onPageFinished(WebView view, String url) 
            {
            	waitinglayout.setVisibility(View.GONE);
            	myWebView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
	     });
		myWebView.loadUrl(url);
	}
	
}

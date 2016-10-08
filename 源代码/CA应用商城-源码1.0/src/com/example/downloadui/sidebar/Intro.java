package com.example.downloadui.sidebar;

import com.example.downloadui.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Intro extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateself_intro);
		setfunction();
	}
	private void setfunction(){
		findViewById(R.id.backintroactivity).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}

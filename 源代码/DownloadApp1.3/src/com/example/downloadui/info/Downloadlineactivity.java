package com.example.downloadui.info;

import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.downloadservic.Setdownloadedline;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class  Downloadlineactivity extends Activity{
	private LinearLayout addprogressbarlayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.downloadline);
		setfunction();
	}
	
	public void setfunction(){
		ImageView back=(ImageView)findViewById(R.id.lineback);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		TextView torecordactivitytext=(TextView)findViewById(R.id.recordlayout);
		torecordactivitytext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Downloadlineactivity.this,Installrecordactiviy.class);
				startActivity(intent);
			}
		});
		
		
		addprogressbarlayout= (LinearLayout)findViewById(R.id.downloadlineadd);
		if(Temporarydata.downloadprogresslayout.size()==0){
			new Setdownloadedline().set(null,addprogressbarlayout);
		}else{
		for(LinearLayout progresslayout:Temporarydata.downloadprogresslayout){
//			new Setdownloadedline().set((String)progresslayout.getTag(),addprogressbarlayout);
			addprogressbarlayout.addView(progresslayout);
			}
		}
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		addprogressbarlayout.removeAllViews();
	}

}

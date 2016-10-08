package com.example.downloadui.itemview;

import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Classifyitemonclicklistener  implements OnClickListener{
	private  TextView text;
	private  LinearLayout classifyView;
	private  ScrollView classifyitemscrollview;
	public Classifyitemonclicklistener(TextView text,LinearLayout classifyView,ScrollView classifyitemscrollview){
		this.text=text;
		this.classifyView=classifyView;
		this.classifyitemscrollview=classifyitemscrollview;
	}
				@Override
				public void onClick(View v) {
//					// TODO Auto-generated method stub
					if(((TextView) v).getText().equals("游戏娱乐")){
						text.setText("动作竞技");
						setView("dongzuojingji");	
							setyouxilayout();
					}else{
						text.setText(((TextView) v).getText());
						setView((String) v.getTag());	
					}
	}
				
				private void setView(String type){
					MarginLayoutParams params=(MarginLayoutParams)classifyView.getLayoutParams();
					params.width=0;
					classifyView.requestLayout();
					goneyouxilayout();
					Classifylistener.listener(type,0);
					classifyitemscrollview.setOnTouchListener(new ClassifyrefreshOnclicklistener(type));
					Temporarydata.classifyinternectpd=false;
				}
				
				private void setyouxilayout(){
					ImageView gameclassify=(ImageView) Temporarydata.classify_view.findViewById(R.id.gameclassify);
					gameclassify.setVisibility(View.VISIBLE);
					Temporarydata.gameclassify=gameclassify;
					
					gameclassify.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
						Classify_game.getinstenc(Temporarydata.classify_view,text,classifyitemscrollview).setgamerlayout();
						}
					});
				}
				
				private void goneyouxilayout(){
					if(Temporarydata.gameclassify!=null){
						if(Temporarydata.gameclassify.isShown())
						    Temporarydata.gameclassify.setVisibility(View.GONE);
					}
				}
				
}

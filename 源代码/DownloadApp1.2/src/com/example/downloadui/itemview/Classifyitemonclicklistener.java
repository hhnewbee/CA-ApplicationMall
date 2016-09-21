package com.example.downloadui.itemview;

import com.example.downloadui.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
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
					switch(v.getId()){
					case R.id.jixieproduct1:
						text.setText("计协出品");
						setView("jixiechupin");	
						break;
						
					case R.id.jixieproduct2:
						text.setText("聊天通讯");
						setView("liaotiantongxun");						
						break;
					case R.id.jixieproduct3:
						text.setText("影音播放");
						setView("yingyinbofang");						
						break;
					case R.id.jixieproduct4:
						text.setText("图形图像");
						setView("tuxingtuxiang");						
						break;
					case R.id.jixieproduct5:
						text.setText("游戏娱乐");
						setView("youxiyule");
						break;
						
					case R.id.jixieproduct6:
						text.setText("编程开发");
						setView("bianchengkaifa");						
						break;
					case R.id.jixieproduct0:
						text.setText("网络软件");
						setView("wangluoruanjian");						
						break;
					case R.id.jixieproduct8:
						text.setText("实用软件");
						setView("shiyongruanjian");
						break;
						
					case R.id.jixieproduct9:
						text.setText("操作系统");
						setView("caozuoxitong");
						break;
		}
	}
				public void setView(String type){
					
					MarginLayoutParams params=(MarginLayoutParams)classifyView.getLayoutParams();
					params.width=0;
					classifyView.requestLayout();
					Classifylistener.listener(type,0);
					classifyitemscrollview.setOnTouchListener(new ClassifyrefreshOnclicklistener(type));
				}
}

package com.example.downloadui.itemview;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;
import com.example.downloadui.managerView.Informbroadcast;

public class ClassifyView {
	private LinearLayout additemView;
	private LinearLayout classifyView;
	
//	public static ClassifyView classifyview;
//	
//	public static ClassifyView getinstence(){
//		if(classifyview==null)
//		classifyview=new ClassifyView();
//		return classifyview;
//	}
//	private ClassifyView(){}
	
		public void getclassifyitem(List<View> list){
			
		LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());  
		View viewitem1=inflater.inflate(R.layout.classify,null);
		Temporarydata.classify_view=viewitem1;
		
		classifyView=(LinearLayout)viewitem1.findViewById(R.id.jixieitemlayout);
		additemView=(LinearLayout)viewitem1.findViewById(R.id.jixieproductslayout);
		
		View waiting_dwonloaditem=inflater.inflate(R.layout.waiting_downloaditem, null);
		additemView.addView(waiting_dwonloaditem);
		
		Classifylistener.layout=additemView;
		
		UIrefreshbroadcast.classifyadditemlinearlayout=additemView;
		Informbroadcast.classifyadditemlinearlayout=additemView;
		Temporarydata.classify_waiting_downloaditem=waiting_dwonloaditem;
		
		ScrollView classifyitemscrollview=(ScrollView)viewitem1.findViewById(R.id.classifyitemscrollview);
		
		TextView titletext=(TextView)viewitem1.findViewById(R.id.jixietext);
		ImageView back=(ImageView)viewitem1.findViewById(R.id.backjx);
		
		
		TextView  items[]={(TextView)viewitem1.findViewById(R.id.jixieproduct1),(TextView)viewitem1.findViewById(R.id.jixieproduct2),
							(TextView)viewitem1.findViewById(R.id.jixieproduct3),(TextView)viewitem1.findViewById(R.id.jixieproduct4),
							(TextView)viewitem1.findViewById(R.id.jixieproduct5),(TextView)viewitem1.findViewById(R.id.jixieproduct6),
							(TextView)viewitem1.findViewById(R.id.jixieproduct0),(TextView)viewitem1.findViewById(R.id.jixieproduct8),
							(TextView)viewitem1.findViewById(R.id.jixieproduct9),(TextView)viewitem1.findViewById(R.id.jixieproduct10),
							(TextView)viewitem1.findViewById(R.id.jixieproduct11)};
//		
		for(int i=0;i<items.length;i++){
			items[i].setOnClickListener(new Classifyitemonclicklistener(titletext,classifyView,classifyitemscrollview));
		}
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				MarginLayoutParams params=(MarginLayoutParams)classifyView.getLayoutParams();
				params.width=LinearLayout.LayoutParams.MATCH_PARENT;
				classifyView.requestLayout();
				
				if(Temporarydata.gamepopupwindow!=null){
					if(Temporarydata.gamepopupwindow.isShowing()){
						Temporarydata.gamepopupwindow.dismiss();
					}
				}
				
			  additemView.removeAllViews();
			}
		});
		
		list.add(viewitem1);
	}
}

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
		LinearLayout viewitem1=(LinearLayout) inflater.inflate(R.layout.classify,null);
//		viewitem1.refreshDrawableState();
		
		classifyView=(LinearLayout)viewitem1.findViewById(R.id.jixieitemlayout);
		additemView=(LinearLayout)viewitem1.findViewById(R.id.jixieproductslayout);
		Classifylistener.layout=additemView;
		
		UIrefreshbroadcast.classifyadditemlinearlayout=additemView;
		Informbroadcast.classifyadditemlinearlayout=additemView;
		
		ScrollView classifyitemscrollview=(ScrollView)viewitem1.findViewById(R.id.classifyitemscrollview);
		
		
		TextView titletext=(TextView)viewitem1.findViewById(R.id.jixietext);
		ImageView back=(ImageView)viewitem1.findViewById(R.id.backjx);
		
		
		TextView  items[]={(TextView)viewitem1.findViewById(R.id.jixieproduct1),(TextView)viewitem1.findViewById(R.id.jixieproduct2),
							(TextView)viewitem1.findViewById(R.id.jixieproduct3),(TextView)viewitem1.findViewById(R.id.jixieproduct4),
							(TextView)viewitem1.findViewById(R.id.jixieproduct5),(TextView)viewitem1.findViewById(R.id.jixieproduct6),
							(TextView)viewitem1.findViewById(R.id.jixieproduct0),(TextView)viewitem1.findViewById(R.id.jixieproduct8),
							(TextView)viewitem1.findViewById(R.id.jixieproduct9)};
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
				
				additemView.removeAllViews();
			}
		});
		list.add(viewitem1);
	}
}

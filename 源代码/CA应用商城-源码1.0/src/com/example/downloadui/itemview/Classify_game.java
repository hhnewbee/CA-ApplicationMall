package com.example.downloadui.itemview;

import android.app.ActionBar.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Temporarydata;
import com.example.downloadui.R;

public class Classify_game{
	private View popupview;
	private View fview;
	private PopupWindow gamepopupwindow;
	private static Classify_game game;
	private TextView text;
	private ScrollView classifyitemscrollview;
	
	private Classify_game(View view,TextView text, ScrollView classifyitemscrollview2){	
		this.fview=view;
		this.text=text;
		this.classifyitemscrollview=classifyitemscrollview2;
		
		LayoutInflater inflater=LayoutInflater.from(AppContext.getInstence());
		popupview=inflater.inflate(R.layout.game_popuwindow,null);
		gamepopupwindow=new PopupWindow(popupview,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		gamepopupwindow.setFocusable(false);
		Temporarydata.gamepopupwindow=gamepopupwindow;
		setfunction();
};

	public static Classify_game getinstenc(View view,TextView text, ScrollView classifyitemscrollview){
		if(game==null){
			game=new Classify_game(view,text,classifyitemscrollview);
		}
		return game;
	}
	
	public void setgamerlayout(){
		
		if(gamepopupwindow.isShowing()){
			gamepopupwindow.dismiss();
		}
		else{
			gamepopupwindow.showAtLocation(fview,Gravity.BOTTOM,0,0);
		}
		
	}
	
	private void setfunction(){
		TextView  items[]={(TextView)popupview.findViewById(R.id.game1),(TextView)popupview.findViewById(R.id.game2),
				(TextView)popupview.findViewById(R.id.game3),(TextView)popupview.findViewById(R.id.game4),
				(TextView)popupview.findViewById(R.id.game5),(TextView)popupview.findViewById(R.id.game6)};
		
	for(TextView textview:items){
		textview.setOnClickListener(new Gameonclicklistener((String)textview.getTag(),(String) textview.getText()));
	}
}
	
	private void setgameitemfunction(String type,String textname){
		Temporarydata.gamepopupwindow.dismiss();
		
		text.setText(textname);
		Classifylistener.layout.removeAllViews();
		Temporarydata.classifyinternectpd=false;
		Classifylistener.listener(type,0);
	}
	
	private class Gameonclicklistener implements OnClickListener{
		private String type;
		private String text;
		public Gameonclicklistener(String type,String text){
			this.type=type;
			this.text=text;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			setgameitemfunction(type,text);
			classifyitemscrollview.setOnTouchListener(new ClassifyrefreshOnclicklistener(type));
		}
		
	}
}

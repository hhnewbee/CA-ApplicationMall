package com.example.downloadui.info;

import java.util.ArrayList;
import java.util.List;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downloadui.R;
import com.example.downloadui.jsonservic.MySQL_user;
import com.example.downloadui.managerView.Softwareitemonclicklistener;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Installrecordactiviy extends Activity{
	private PopupWindow delectpopupwindow;
	private LinearLayout item;
	private LinearLayout recorditemlayout;
	private List<String> idlist=new ArrayList<String>();
	private List<CheckBox> checklist=new ArrayList<CheckBox>();
	private MySQL_user db=MySQL_user.getInstance(AppContext.getInstence());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.installlayout);
		getitem();
		setfunction();
	}
	
	public void getitem(){
		
	recorditemlayout=(LinearLayout)findViewById(R.id.addinstallrecorditemlayout);
	
	if(MySQL_user.getInstance(AppContext.getInstence()).getcount()!=0){
		
	idlist=MySQL_user.getInstance(AppContext.getInstence()).getdownloadinfourlist("record","id");
	for(String id:idlist){
		
	LinearLayout recordinfoitem=(LinearLayout)getLayoutInflater().inflate(R.layout.installrecorditem, null);
	TextView texttime=(TextView)recordinfoitem.findViewById(R.id.time);
	texttime.setText(db.getdownloadinfourl("record","time","id",id));
	TextView recodtext=(TextView)recordinfoitem.findViewById(R.id.recordtext);
	recodtext.setText(db.getdownloadinfourl("record","true_name","id",id));
	TextView informationtext=(TextView)recordinfoitem.findViewById(R.id.recordinformation);
	informationtext.setText(db.getdownloadinfourl("record","content","id",id));
	recordinfoitem.setOnClickListener(new Softwareitemonclicklistener(id,db.getdownloadinfourl("record","category","id",id)));
	new Getpicture().getpicture(db.getdownloadinfourl("record","directory_img","id",id),(ImageView) recordinfoitem.findViewById(R.id.recorditemimage));
	recorditemlayout.addView(recordinfoitem);
			}
		}
	}
	
	public void setfunction(){
		findViewById(R.id.backinstallactivity).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		findViewById(R.id.delectrecord).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(delectpopupwindow==null){
					
				View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
				popupView.findViewById(R.id.alldelecttext).setOnClickListener(new Checkboxfunction());
				popupView.findViewById(R.id.makesuredelecttext).setOnClickListener(new Checkboxfunction());
				popupView.findViewById(R.id.canceldelecttext).setOnClickListener(new Checkboxfunction());
				
				delectpopupwindow=new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				delectpopupwindow.setFocusable(false);
				}
				
				if(delectpopupwindow.isShowing()){
					delectpopupwindow.dismiss();
					for(int i=0;i<recorditemlayout.getChildCount();i++){
						item=(LinearLayout)recorditemlayout.getChildAt(i);
						checklist.get(i).setVisibility(View.GONE);
					}
					
				}
				
				else{
					delectpopupwindow.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content),Gravity.BOTTOM,0,0);
					for(int i=0;i<recorditemlayout.getChildCount();i++){
						item=(LinearLayout)recorditemlayout.getChildAt(i);
							checklist.add(((CheckBox)item.findViewById(R.id.delectcheckbox)));
							checklist.get(i).setVisibility(View.VISIBLE);
					}
				}
				
			}
		});
	}
	public class Checkboxfunction implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.alldelecttext:
				for(int i=0;i<recorditemlayout.getChildCount();i++){
					item=(LinearLayout)recorditemlayout.getChildAt(i);
					checklist.get(i).setChecked(true);
					System.out.println("ȫѡ"+checklist.get(i).isChecked());
				}
				break;
				
			case R.id.makesuredelecttext:
				for(int i=0;i<recorditemlayout.getChildCount();i++){
					item=(LinearLayout)recorditemlayout.getChildAt(i);
					if(checklist.get(i).isChecked()){
						recorditemlayout.removeView(item);
						db.deleteDownloading("record","id",idlist.get(i));
						idlist.remove(i);
						if(i>0){
							i--;
						}
					}
				}
				break;
				
			case R.id.canceldelecttext:
				delectpopupwindow.dismiss();
				for(int i=0;i<recorditemlayout.getChildCount();i++){
					item=(LinearLayout)recorditemlayout.getChildAt(i);
					checklist.get(i).setVisibility(View.GONE);
				}
				break;
			}
		}
		
	}
}

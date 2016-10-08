package com.example.downloadui.info;

import com.example.downlaodui.infodata.AppContext;
import com.example.downlaodui.infodata.Getpicture;
import com.example.downloadui.R;
import com.example.downloadui.downloadservic.Downloadbuttonlistener;
import com.example.downloadui.jsonservic.MemoryDb;
import com.example.downloadui.jsonservic.MySQL_user;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Infoactivity extends Activity implements OnClickListener{
	public boolean status1=false;
	public boolean status2=false;
	private TextView text1;
	private TextView text2;
	private MemoryDb db=MemoryDb.getDefault();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.softwareinfo);
		
		settextandimageandbutton();
		setbackturn();
		
	}

	public void settextandimageandbutton(){
		
		String type=getIntent().getStringExtra("type");
		String id=getIntent().getStringExtra("id");
		String pd=getIntent().getStringExtra("pd");
		
		View view=getWindow().getDecorView().findViewById(android.R.id.content);
		new InfoViewpager(this,view,id,type,pd);
		
		
	if(pd.equals("u")){
		setdownloadinfoitem(id,type);
		TextView downloadbutton=(TextView)findViewById(R.id.info_downloadbutton);
		downloadbutton.setVisibility(View.GONE);
	}else if(pd.equals("d")){
		setdownloadinfoitem(id,type);
		TextView downloadbutton=(TextView)findViewById(R.id.info_downloadbutton);
		downloadbutton.setOnClickListener(new Downloadbuttonlistener(id,type));
	}else{
		setrecordinfoitem(id,type);
		TextView downloadbutton=(TextView)findViewById(R.id.info_downloadbutton);
		downloadbutton.setOnClickListener(new Downloadbuttonlistener(id,type));
	}
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		
	}
	public void setbackturn(){
		ImageView image=(ImageView)findViewById(R.id.back1);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.softwareinformationtext1:
			if(status1 == false){
				((TextView) arg0).setSingleLine(false);
				((TextView) arg0).setEllipsize(null);
				status1=true;
			}else if(status1 == true){
				((TextView) arg0).setLines(2);
				((TextView) arg0).setEllipsize(TruncateAt.END);
				status1=false;
			}
			break;
		case R.id.info2:
			if(status2 == false){
				((TextView) arg0).setSingleLine(false);
				((TextView) arg0).setEllipsize(null);
				status2=true;
			}else if(status2 == true){
				((TextView) arg0).setLines(2);
				((TextView) arg0).setEllipsize(TruncateAt.END);
				status2=false;
			}
			break;
		default:break;
		}
	}
	private void setdownloadinfoitem(String id,String type){
		String name=db.Query("true_name",type,id);
		String size=String.format("%.1f",(Float.parseFloat(db.Query("size",type,id).substring(0,db.Query("size",type,id).indexOf("K")))/1024))+"MB";
		ImageView itemimage=(ImageView)findViewById(R.id.informationitempicture);
		new Getpicture().getpicture(db.Query("directory_img",type,id),itemimage);
		text1=(TextView)findViewById(R.id.softwareinformationtext1);
		text1.setText(name+" : "+size);
		text2=(TextView)findViewById(R.id.info2);
		text2.setText(db.Query("content",type,id).replaceAll ("\\\\n","\n"));
		TextView text3=(TextView)findViewById(R.id.info_softwarename);
		text3.setText(name);
		TextView text4=(TextView)findViewById(R.id.info_softwaresize);
		text4.setText(size);
	}
	
	private void setrecordinfoitem(String id,String type){
		MySQL_user dbr=MySQL_user.getInstance(AppContext.getInstence());
		
		String name=dbr.getdownloadinfourl("record","true_name","id",id);
		String size=String.format("%.1f",(Float.parseFloat(dbr.getdownloadinfourl("record","size","id",id).substring(0,dbr.getdownloadinfourl("record","size","id",id).indexOf("K")))/1024))+"MB";
		
		ImageView itemimage=(ImageView)findViewById(R.id.informationitempicture);
		new Getpicture().getpicture(dbr.getdownloadinfourl("record","directory_img","id",id),itemimage);
		
		text1=(TextView)findViewById(R.id.softwareinformationtext1);
		text1.setText(name+" : "+size);
		text2=(TextView)findViewById(R.id.info2);
		text2.setText(dbr.getdownloadinfourl("record","content","id",id).replaceAll ("\\\\n","\n"));
		TextView text3=(TextView)findViewById(R.id.info_softwarename);
		text3.setText(name);
		TextView text4=(TextView)findViewById(R.id.info_softwaresize);
		text4.setText(size);
	}
}

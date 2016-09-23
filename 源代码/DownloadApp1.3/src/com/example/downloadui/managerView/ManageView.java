package com.example.downloadui.managerView;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.downlaodui.infodata.AppContext;
import com.example.downloadui.R;
import com.example.downloadui.info.Downloadlineactivity;
import com.example.downloadui.info.Installrecordactiviy;
import com.example.downloadui.info.Upgradeactivity;

public class ManageView {
//public static ManageView manageview;
//	
//	public static ManageView  getinstence(){
//		if(manageview==null)
//		manageview=new ManageView ();
//		return manageview;
//	}
//	private ManageView(){}
	public void getmanageitem(List<View> list){
		LayoutInflater inflater = LayoutInflater.from(AppContext.getInstence());  
		LinearLayout viewitem1=(LinearLayout) inflater.inflate(R.layout.manaegeritem,null);
		TextView manageritems[]={(TextView)viewitem1.findViewById(R.id.downloadlinetext),
				(TextView)viewitem1.findViewById(R.id.upgradetext),
				(TextView)viewitem1.findViewById(R.id.installrecordtext)
				};
		for(int i=0;i<manageritems.length;i++)
		manageritems[i].setOnClickListener(new classifyitemonclicklistener());
		list.add(viewitem1);
	}
	
	public class classifyitemonclicklistener implements OnClickListener{
		private Context context=AppContext.main;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.downloadlinetext:
				Intent intent1=new Intent(context,Downloadlineactivity.class);
				context.startActivity(intent1);
				break;
			case R.id.upgradetext:
				Intent intent2=new Intent(context,Upgradeactivity.class);
				context.startActivity(intent2);
				break;
			case R.id.installrecordtext:
				Intent intent5=new Intent(context,Installrecordactiviy.class);
				context.startActivity(intent5);
				break;
			}
		}

	}
}

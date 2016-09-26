package com.example.downlaodui.infodata;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.downloadui.Viewholder;

public class Temporarydata {
	public static boolean stop=false;
//	public static Viewholder holdler=new Viewholder();
	public static boolean refreshitempd=false;
	public static boolean suijiinternectpd=false;
	public static boolean tuijianinternectpd=false;
	public static boolean adinternectpd=false;
	public static List<String> ids=new ArrayList<String>();
	public static List<LinearLayout> downloadprogresslayout=new ArrayList<LinearLayout>();
	public static List<LinearLayout> upgradelayoutlist=new ArrayList<LinearLayout>();
	public static List<TextView> alldownload=new ArrayList<TextView>();
	public static LinearLayout updatelayout;
	public static LinearLayout searchlayout;
	public static View recommend_waiting_downloaditem;
	public static View random_waiting_downloaditem;
	public static View random_n_downloaditem;
	public static View recommend_view;
}

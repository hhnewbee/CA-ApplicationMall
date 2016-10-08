package com.example.downloadui;

import java.util.ArrayList;
import java.util.List;

import com.example.downlaodui.infodata.Temporarydata;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author yangyu
 *	功能描述：主程序入口类
 */
public class AdViewpager  implements OnClickListener,OnPageChangeListener {
	//定义ViewPager对象
	private ViewPager viewPager;
	
	//定义ViewPager适配器
	private Myviewpageradapter vpAdapter;
	
	//定义一个ArrayList来存放View
	private ArrayList<View> list;

	//引导图片资源
    private List<ImageView> pics;
    
    //底部小点的图片
    private ImageView[] points;
    
    //记录当前选中位置
    private int currentIndex;
    private View view;
    private Context context;
    private boolean pd=false;
    private Handler mHandler = new Handler();
    
    public AdViewpager(Context context,View view,List<ImageView> image){
    	this.context=context;
    	this.view=view;
    	this.pics=image;
    	initView();
    	initData();	
    	mHandler.postDelayed(mImageTimerTask, 2000);
    }

	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化ArrayList对象
		list = new ArrayList<View>();
		
		//实例化ViewPager
		viewPager = (ViewPager)view.findViewById(R.id.adviewpager);
		
		//实例化ViewPager适配器
		vpAdapter =new Myviewpageradapter(list);
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		//定义一个布局并设置参数
		for(int i=0;i<pics.size();i++){
			list.add(pics.get(i));
		}
        //设置数据
        viewPager.setAdapter(vpAdapter);
        //设置监听
        viewPager.setOnPageChangeListener(this);
        
        //初始化底部小点
        initPoint();
	}
	
	/**
	 * 初始化底部小点
	 */
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.ll);       
		
        points = new ImageView[pics.size()];

        //循环取得小点图片
        for (int i = 0; i < pics.size(); i++) {
        	LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams
        			(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        	mParams.setMargins(10,5,10,0);
			ImageView iv = new ImageView(context);
			iv.setLayoutParams(mParams);
			iv.setImageResource(R.drawable.point);
        	points[i] =iv;
        	//添加到一个LinearLayout下面的每一个子元素
        	linearLayout.addView(iv);
        	//默认都设为灰色
        	points[i].setEnabled(true);
        	//给每个小点设置监听
        	points[i].setOnClickListener(this);
        	//设置位置tag，方便取出与当前位置对应
        	points[i].setTag(i);
        }
        
        //设置当面默认的位置
        currentIndex = 0;
        //设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
	}
	
	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
	/**
	 * 当当前页面被滑动时调用
	 */

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	
	/**
	 * 当新的页面被选中时调用
	 */

	@Override
	public void onPageSelected(int position) {
		//设置底部小点选中状态
        setCurDot(position);
	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	@Override
	public void onClick(View v) {
		 int position = (Integer)v.getTag();
         setCurView(position);
         setCurDot(position);		
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position){
         if (position < 0 || position >= pics.size()) {
             return;
         }
         viewPager.setCurrentItem(position);
     }

     /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon){

         if (positon < 0 || positon > pics.size() - 1 || currentIndex == positon) {
             return;
         }
         points[positon].setEnabled(false);
         points[currentIndex].setEnabled(true);

         currentIndex = positon;
     }
    private Runnable mImageTimerTask = new Runnable() {
		@Override
		public void run() {
			if (pics != null) {
				if(pd==false){
				viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
				if(viewPager.getCurrentItem()==pics.size()-1)
					pd=true;
				}
				else{
				viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
				if(viewPager.getCurrentItem()==0){
					pd=false;
					}
				}
				if(!(Temporarydata.stop)){  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环 
					mHandler.postDelayed(mImageTimerTask, 3000);
				}

			}
		}
	};

}

package com.example.downlaodui.infodata;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

public class Getpicture {
	public 	static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	public void getpicture(final String pictureurl,final ImageView imageview){
		
	    if (imageCache.containsKey(pictureurl)) {
            SoftReference<Bitmap> softReference = imageCache.get(pictureurl);
            if (softReference.get() != null) {
               imageview.setImageBitmap(softReference.get());
               return;
            }
        }
	    if(pictureurl==null||"".equals(pictureurl)){
	    	return;
	    }
		 OkHttpUtils
		    .get()
		    .url(pictureurl)
		    .build()
		    .execute(new BitmapCallback()
		    {
				@Override
				public void onError(Call arg0, Exception e, int arg2) {
					// TODO Auto-generated method stub
					System.out.println(e.getMessage());
				}
				@Override
				public void onResponse(Bitmap bitmap, int arg1) {
					// TODO Auto-generated method stub
					imageview.setImageBitmap(bitmap);
					imageCache.put(pictureurl, new SoftReference<Bitmap>(bitmap));
				}
		    });
	}
}

package com.example.downlaodui.upload;

import android.view.View;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

public class Uplaod {
	private HttpHandler<?> handler = null;
	public void startupload(View v) {    //post«Î«Û
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("name", "duerPost");
		params.put("pass", "125");
		finalHttp.post("http://192.168.191.1:8080/androidtest/test2", params,
				new AjaxCallBack<Object>() {
					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
					}

					@Override
					public void onLoading(long count, long current) {
						// TODO Auto-generated method stub
						super.onLoading(count, current);
					}

					@Override
					public void onSuccess(Object t) {
						// TODO Auto-generated method stub
					}
				});

	}
}

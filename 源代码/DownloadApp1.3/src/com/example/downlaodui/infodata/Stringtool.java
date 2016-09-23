package com.example.downlaodui.infodata;

public class Stringtool {
	public static String getclassifyurl(String index,String type){
		String url="http://ca.sise.com.cn:83/index.php/admin/Json_download?id=&category=&offset=&method=";
		String refreshcount=url.substring(68,76)+index+"&method=";
		String type1=url.substring(0,68)+type;
		String newurl=type1+refreshcount;
		return newurl;
	}
	public static String getdownloadcountsurl(String id){
		String url="http://ca.sise.com.cn:83/index.php/admin/Json_download?id=&category=&offset=&method=";
		String downloadcountsurl=url.substring(0,68)+id+"&category=&offset=&method=";
		return downloadcountsurl;
	}
	public static String gettuijianandsuijiurl(String index,String type){
		String url="http://ca.sise.com.cn:83/index.php/admin/Json_download?id=&category=&offset=&method=";
		String newurl=url.substring(0,76)+index+"&method="+type;
		return newurl;
	}
	public static String getupdateurl(String name){
		String url="http://ca.sise.com.cn:83/index.php/admin/Json_download?name=";
		String newurl=url+name;
		return newurl;
		
	}
	public static String getadpictureurl(){
		String url="http://ca.sise.com.cn:83/index.php/admin/json_download?advertisement=1";
		return url;
		
	}
	public static String getselfUpdateurl="http://ca.sise.com.cn:83/index.php/admin/Json_self";
	public static String getSuggestcontenturl="http://ca.sise.com.cn:83/index.php/admin/Json_proposal?proposal=";
	public static String getofficailurl="http://ca.sise.com.cn";
}

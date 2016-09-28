package com.example.downloadui.jsonservic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

	public class MySQL_user {  
		private static  MySQL_user db=null;
		private  MySQL_user(Context context) { 
		}
		public static  MySQL_user getInstance(Context context){
			if(db==null){
				db=new MySQL_user(context); 
			}
			return db;
		}
		public  SQLiteDatabase getConnection() {
		SQLiteDatabase sqliteDatabase = null;
		sqliteDatabase= MySQL.getInstance().getReadableDatabase();
			try { 
				//两者都具有读写功能，但如果盘满了，用write的话会报错，而read继续运行，http://www.bubuko.com/infodetail-280495.html
			} catch (Exception e) {  
				System.out.println("数据库打开失败！");
			}
			return sqliteDatabase;
		}
		
		public synchronized  String getdownloadinfourl(String table,String name,String type,String data) {
			
			SQLiteDatabase db=getConnection();
			String sql = "SELECT "+name+" FROM "+table+" WHERE "+type+"=? "; 
			Cursor cursor = db.rawQuery(sql, new String[] {data});
			String url=null;
			 if (cursor.getCount() > 0) {
		        	while (cursor.moveToNext()) {
		        		url=cursor.getString(0);
		        	}
		        	cursor.close();
				}
			 return url;
			
		}
		public synchronized  List<String> getdownloadinfourlist(String table,String name) {
			
			SQLiteDatabase db=getConnection();
			List<String> urllist=new ArrayList<String>();
			String sql = "SELECT "+name+" FROM "+table ; 
			Cursor cursor = db.rawQuery(sql, null);
			 if (cursor.getCount() > 0) {
		        	while (cursor.moveToNext()) {
		        		urllist.add(cursor.getString(0));
		        	}
		        	cursor.close();
				}
			 return urllist;
			
		}
		public synchronized void Insert(String table,String type,String id) {  
			
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时ss分");
		ContentValues values=new ContentValues();	
		String time=formatter.format(new Date(System.currentTimeMillis()));
		SQLiteDatabase db=getConnection();
		
		if(table.equals("record")){
			values.put("id",time+id);
		}else{
			values.put("id",id);
		}
		
        values.put("category",type);
        values.put("true_name",MemoryDb.getDefault().Query("true_name",type,id));
        values.put("name",MemoryDb.getDefault().Query("name",type,id));
        values.put("content",MemoryDb.getDefault().Query("content",type,id));
        values.put("size",MemoryDb.getDefault().Query("size",type,id));
        values.put("version",MemoryDb.getDefault().Query("version",type,id));
        values.put("download_times",MemoryDb.getDefault().Query("download_times",type,id));
        values.put("time_upload",MemoryDb.getDefault().Query("time_upload",type,id));
        values.put("directoty_soft",MemoryDb.getDefault().Query("directoty_soft",type,id));
        values.put("directory_img",MemoryDb.getDefault().Query("directory_img",type,id));
        values.put("directory_img_content_1",MemoryDb.getDefault().Query("directory_img_content_1",type,id));
        values.put("directory_img_content_2",MemoryDb.getDefault().Query("directory_img_content_2",type,id));
        values.put("directory_img_content_3",MemoryDb.getDefault().Query("directory_img_content_3",type,id));
        values.put("time",time);
        db.insert(table,null,values);  
        db.close();
        Log.d("TGA","记录表格写入成功");
    }  
		public synchronized void Insertupdate(String table,String type,String data,String type1,String data1) {
			ContentValues values=new ContentValues();	
			SQLiteDatabase db=getConnection();
			values.put(type,data);
			values.put(type1,data1);
			db.insert(table,null,values);  
		    db.close();
		}
//		public synchronized void Insert(String id,String version,String name) {  
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时");
//			ContentValues values=new ContentValues();	
//			SQLiteDatabase db=getConnection();
//			values.put("id",id);
//			values.put("version",version);
//			values.put("name",name);
//			db.insert("record",null,values);  
//		}
		
		public synchronized void upDownloading(String table,String size, String type,String data) {
			SQLiteDatabase db = getConnection();
			   try {  
		            db.beginTransaction();  
		            String sql = "UPDATE "+table+" SET size=? WHERE "+type+"=? ";  
		            db.execSQL(sql, new String[] {size,data});  
		            db.setTransactionSuccessful();  
		        } finally {  
		            db.endTransaction();  
		            db.close();  
		        }  
		}
		
		public synchronized int getcount() {
			SQLiteDatabase db=getConnection();
			String sql = "SELECT COUNT(*) FROM record";
			SQLiteStatement statement = db.compileStatement(sql);
			int count = (int) statement.simpleQueryForLong();
			return count;
	}
		public synchronized void deleteDownloading(String table,String type,String data) {
			SQLiteDatabase db= getConnection();
	        String sql = "DELETE FROM "+table+" WHERE "+type+"=?";  
	        db.execSQL(sql, new String[] {data});  
	        db.close();  
	}
}

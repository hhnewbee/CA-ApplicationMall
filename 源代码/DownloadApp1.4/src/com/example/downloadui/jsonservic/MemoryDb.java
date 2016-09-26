package com.example.downloadui.jsonservic;

import android.content.ContentValues;  
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.util.Log;
  
public final class MemoryDb {  
      
    private SQLiteDatabase mMemoryDb; 
    private String type;
      
    private MemoryDb(){  
        mMemoryDb = createMemoryDb();  
    }  
      
    private static MemoryDb sDefault;  
    //default的意思是违约，或者系统默认值
    public static MemoryDb getDefault(){  
    	if(sDefault==null){
    		sDefault=new MemoryDb();
    	}
        return sDefault;  
    }  
      
    private static final String test = "(id text primary key, "
            +"category text, "
            +"true_name text, "
            +"name text, "
            +"content text, "
            +"size text, "
            +"version text, "
            +"download_times text, "
            +"time_upload text, "
            +"directoty_soft text, "
            +"directory_img text, "
            +"directory_img_content_1 text, "
            +"directory_img_content_2 text, "
            +"directory_img_content_3 text)";

    public static final String CREATE_TUIJIAN = "create table tuijian " + test;
    public static final String CREATE_SUIJI = "create table suiji " + test;
    public static final String CREATE_QUANBU = "create table quanbu " + test;
    public static final String CREATE_JIXIE = "create table jixiechupin " + test;
    public static final String CREATE_BANGONG = "create table liaotiantongxun " + test;
    public static final String CREATE_LIAOTIAN = "create table yingyinbofang " + test;
    public static final String CREATE_YINGYIN = "create table tuxingtuxiang " + test;
    public static final String CREATE_TUXING = "create table youxiyule " + test;
    public static final String CREATE_DUOMEITI = "create table bianchengkaifa " + test;
    public static final String CREATE_BIANCHENG = "create table wangluoruanjian " + test;
    public static final String CREATE_WANGLUO = "create table shiyongruanjian " + test;
    public static final String CREATE_SHIYONG = "create table caozuoxitong" + test;
    public static final String CREATE_SEARCH = "create table search" + test;
    /** 
     * 创建内存数据库 
     */  
    private SQLiteDatabase createMemoryDb(){  
    	
        SQLiteDatabase db = SQLiteDatabase.create(null);  
        db.execSQL(CREATE_TUIJIAN);
        db.execSQL(CREATE_SUIJI);
        db.execSQL(CREATE_JIXIE);
        db.execSQL(CREATE_BANGONG);
        db.execSQL(CREATE_LIAOTIAN);
        db.execSQL(CREATE_YINGYIN);
        db.execSQL(CREATE_TUXING);
        db.execSQL(CREATE_DUOMEITI);
        db.execSQL(CREATE_BIANCHENG);
        db.execSQL(CREATE_WANGLUO);
        db.execSQL(CREATE_SHIYONG);
        db.execSQL(CREATE_SEARCH);
        db.execSQL(CREATE_QUANBU);
        return db;  
    }  
    /** 
     * 向内存数据库中插入一条数据 
     */
    public void Insert(ContentValues values,String type) {  
    	
        this.type=type;  
        
        SQLiteDatabase db = mMemoryDb;  
          
        check(db);  
          
        db.insert(type,null,values);  
         
        Log.d("TGA","表格写入成功");
    }  
    /** 
     * 查询内存数据库中的数据 
     */  
    public String Query(String result,String id){  
          
        SQLiteDatabase db = mMemoryDb;  
          
        check(db);  
        
        String sql = "SELECT "+result+" FROM " + type + " WHERE id=?"; 
        Cursor cursor = db.rawQuery(sql, new String[] {id});  
        
        String getresult=null;  
        while(cursor.moveToNext()){  
            getresult = cursor.getString(0);  
        }  
        return getresult;
    }  
    
    public String Query(String result,String type1,String id){  
        
        SQLiteDatabase db = mMemoryDb;  
          
        check(db);  
        
        String sql = "SELECT "+result+" FROM " + type1 + " WHERE id=?"; 
        Cursor cursor = db.rawQuery(sql, new String[] {id});  
        
        String getresult=null;  
        while(cursor.moveToNext()){  
            getresult = cursor.getString(0);  
        }  
        return getresult;
    }  
    
    public void releaseMemory(){  
        SQLiteDatabase db = mMemoryDb;  
        if(db!=null){  
            db.close();  
            mMemoryDb=null;
            sDefault=null;
        }  
    }  
      
    private void check(SQLiteDatabase db) {  
        if(db==null||!db.isOpen()){  
            throw new IllegalStateException("memory database already closed"); 
        }  
    }  
    
    @Override  
    protected void finalize() throws Throwable {  
          
        releaseMemory();  
          
        super.finalize();  
    }  
}  
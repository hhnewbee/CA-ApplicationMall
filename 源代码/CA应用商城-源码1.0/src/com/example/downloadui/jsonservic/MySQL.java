package com.example.downloadui.jsonservic;

import com.example.downlaodui.infodata.AppContext;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016/8/1.
 */
public class MySQL extends SQLiteOpenHelper{
	public static MySQL mInstance = null;
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
            +"directory_img_content_3 text,	"
            +"time text)";

    public static final String CREATE_RECORD = "create table record " + test;
    public static final String CREATE_DOWNLOADLINE = "create table downloadline " + test;
    public static final String CREATE_UPDATE = "create table updateline " + test;


   public static MySQL getInstance() {  
        if (mInstance == null) {  
            mInstance = new MySQL(AppContext.getInstence(),"SOFT.db",null,1);  
        }  
        return mInstance;  
        }  

    private MySQL(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD);
        db.execSQL(CREATE_DOWNLOADLINE);
        db.execSQL(CREATE_UPDATE);
        System.out.println("记录表建立成功");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

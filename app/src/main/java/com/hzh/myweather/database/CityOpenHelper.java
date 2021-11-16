package com.hzh.myweather.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class CityOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="city.db";
    private static final String TABLE_NAME_CITY="city_1";
    private static final String CREATE_TABLE_CITY_SQL ="create table "+ TABLE_NAME_CITY +"(id integer primary key autoincrement,content text);";

    public  CityOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<String> findAll() {
        ArrayList<String> cityList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CITY, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range") String city=cursor.getString(cursor.getColumnIndex("content"));
            cityList.add(city);
        }
        return cityList;
    }

    public long insert(String city) {
        SQLiteDatabase db=getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("content",city);
        return db.insert(TABLE_NAME_CITY,null,contentValues);
    }

    public List<String> findByCity(String cityName) {
        if(TextUtils.isEmpty(cityName)){
            return findAll();
        }
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> cityList=new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_CITY, null, "content like ?", new String[]{"%"+cityName+"%"}, null, null, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range") String city=cursor.getString(cursor.getColumnIndex("content"));
            cityList.add(city);
        }

        return cityList;

    }
}

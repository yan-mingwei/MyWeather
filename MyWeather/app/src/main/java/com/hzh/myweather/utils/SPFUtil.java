package com.hzh.myweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SPFUtil {

    /**
     * 保存数据
     */
    public static void save(Context context, HashSet<String> markCitySet){

        if(markCitySet==null||markCitySet.size()==0){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("markCity", context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //spf可以存取set集合
        Set<String> citySet=markCitySet;
        editor.putStringSet("cities",citySet);
        editor.apply();
        Log.d("存取", "save: "+citySet);
    }

    /**
     * 查询数据
     * @return
     */
    public static HashSet<String> query(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("markCity", context.MODE_PRIVATE);
        Set<String> citySet = sharedPreferences.getStringSet("cities",null);
        HashSet<String> cityHastSet= (HashSet<String>) citySet;
        Log.d("存取", "query: "+cityHastSet);
        return cityHastSet;
    }

    public static void saveList(Context context, ArrayList<String> list){
        if(list==null || list.size()==0){
            return;
        }
        HashSet<String> hashSet=TranUtil.listToSet(list);
        SharedPreferences sharedPreferences = context.getSharedPreferences("City", context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //spf可以存取set集合
        Set<String> citySet=hashSet;
        editor.putStringSet("cities",citySet);
        editor.apply();
    }

    public static ArrayList<String> queryList(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("City", context.MODE_PRIVATE);
        Set<String> citySet = sharedPreferences.getStringSet("cities",null);
        HashSet<String> cityHastSet= (HashSet<String>) citySet;
        Log.d("存取", "query: "+cityHastSet);
        return TranUtil.setToList(cityHastSet);
    }

}

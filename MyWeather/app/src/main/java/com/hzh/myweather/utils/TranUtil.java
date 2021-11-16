package com.hzh.myweather.utils;

import java.util.ArrayList;
import java.util.HashSet;

public class TranUtil {

    public static ArrayList<String> setToList(HashSet<String> set){
        ArrayList<String> list=new ArrayList<>();
        if(set!=null && set.size()>0){
            for (String s:
                 set) {
                list.add(s);
            }
        }
        return list;
    }

    public static HashSet<String> listToSet(ArrayList<String> list){
        HashSet<String> set=new HashSet<>();
        if(list!=null && list.size()>0){
            for (String s:
                 list) {
                set.add(s);
            }
        }
        return set;
    }
}

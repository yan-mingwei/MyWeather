package com.hzh.myweather.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.hzh.myweather.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLUtil {

    public static final ArrayList<String> getCity(Context context,int id){
        ArrayList<String> cityList=new ArrayList<>();

        //从xml资源文件夹加载movie.xml文件
        XmlResourceParser XmlParser = context.getResources().getXml(id);
        // 拿到第一个事件类型，事件类型为END_DOCUMENT，START_DOCUMENT，START_TAG,END_TAG等
        try {
            int type = XmlParser.getEventType();
            while (type != XmlResourceParser.END_DOCUMENT) {    //结束节点(即</Movies>),只要不是结束节点就遍历
                switch (type) {
                    case XmlResourceParser.START_DOCUMENT:      //开始节点(即<Movies>  )
                        break;
                    case XmlResourceParser.START_TAG:
                        if ("City".equals(XmlParser.getName())) {  //开始元素(<Movie>)
                            cityList.add(XmlParser.nextText());
                        }
                        break;
                    default:
                        break;
                }
                //获取下一个解析事件（（即开始文档，结束文档，开始标签，结束标签））
                type = XmlParser.next();
            }
        } catch (Exception e) {
                e.printStackTrace();
            }
        return cityList;
    }
}

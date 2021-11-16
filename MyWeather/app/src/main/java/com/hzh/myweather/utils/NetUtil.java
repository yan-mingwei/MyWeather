package com.hzh.myweather.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 访问网络不成功，可能是模拟器的问题
 */
public class NetUtil {

    private static final String URL_COM="https://tianqiapi.com/api?unescape=1&version=v1&appid=47866447&appsecret=d4b6Y99T";

    public static String doGet(String urlStr){
        String result = "";
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // 连接网络
        try {
            URL urL = new URL(urlStr);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 从连接中读取数据(二进制)
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            // 二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从缓存区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            Log.d("abc", "数据"+bufferedReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                Log.d("abc", "数据："+line);
            }
            Log.d("abc", "结果： "+stringBuilder);
            result = stringBuilder.toString();

        } catch (Exception e) {
            Log.d("abc", "网络有异常");
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String getWeatherOfCity(String city){
        String url=URL_COM + "&city=" + city;
        return doGet(url);
    }


}

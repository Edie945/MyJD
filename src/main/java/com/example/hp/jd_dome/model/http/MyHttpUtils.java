package com.example.hp.jd_dome.model.http;

import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyHttpUtils {
    private MyHttpUtils(){};
    private static OkHttpClient ok;
    public static OkHttpClient getInstance(){
        if(ok==null){
            synchronized (MyHttpUtils.class){
                if(ok==null){
                    ok= new OkHttpClient();
                }
            }
        }
        return ok;
    }
    public static void doGet(String path, Callback callback){
        OkHttpClient instance = getInstance();
        Request request = new Request.Builder().url(path).build();
        instance.newCall(request).enqueue(callback);
    }
    public static void doPost(String path, HashMap<String,String> map, Callback callback){
        OkHttpClient instance = getInstance();
        FormBody.Builder body = new FormBody.Builder();
        for(String key:map.keySet()){
            body.add(key,map.get(key));
        }
        Request request = new Request.Builder().url(path).post(body.build()).build();
        instance.newCall(request).enqueue(callback);
    }
}

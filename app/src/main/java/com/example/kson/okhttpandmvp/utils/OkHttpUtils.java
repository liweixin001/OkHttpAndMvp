package com.example.kson.okhttpandmvp.utils;

import com.example.kson.okhttpandmvp.common.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:工具类，java的23种设计模式之一单例模式
 */
public class OkHttpUtils {

    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;


    private OkHttpUtils(){

        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(2000, TimeUnit.MICROSECONDS)
                .build();


    }

    public static  OkHttpUtils getInstance(){
        if (okHttpUtils==null){
                okHttpUtils = new OkHttpUtils();
        }
        return okHttpUtils;
    }

    public  void postData(String url,HashMap<String,String> prams){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        if (prams!=null&&prams.size()>0){
            for (Map.Entry<String, String> stringStringEntry : prams.entrySet()) {

                formBodyBuilder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url).post(formBodyBuilder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}

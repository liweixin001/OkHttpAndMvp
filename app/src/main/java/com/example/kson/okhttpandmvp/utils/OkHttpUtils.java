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
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:工具类，java的23种设计模式之一单例模式
 */
public class OkHttpUtils {

    //
    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;


    //构造方法私有的？因为不能被调用者new的对象，只能给自己new
    private OkHttpUtils() {

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


    }

    /**
     * public 暴露给调用者，双重检验锁
     *
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }


//    /**
//     * get封装
//     * @param params
//     * @param url
//     * @param requestCallback
//     */
//    public void getData(String url,HashMap<String, String> params, final RequestCallback requestCallback) {
//
//        StringBuilder urlsb = new StringBuilder();
//        String allUrl = "";
//        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
//            urlsb.append("?").append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
//        }
//
//        allUrl = url+urlsb.toString().substring(0, urlsb.length() - 1);
//        System.out.println("url:" + allUrl);
//
//        final Request request = new Request.Builder()
//                .url(allUrl).get().build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            //请求失败
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (requestCallback != null) {
//                    requestCallback.failure(call, e);
//                }
//
//            }
//
//            //请求成功
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (requestCallback != null) {
//                    requestCallback.onResponse(call, response);
//                }
//
//            }
//        });
//    }

    /**
     * post请求方式
     * @param url
     * @param prams
     */
    public void postData(String url, HashMap<String, String> prams, final RequestCallback requestCallback) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        if (prams != null && prams.size() > 0) {
            for (Map.Entry<String, String> stringStringEntry : prams.entrySet()) {

                formBodyBuilder.add(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url).post(formBodyBuilder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback != null) {
                    requestCallback.failure(call, e);
                }

            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback != null) {
                    requestCallback.onResponse(call, response);
                }

            }
        });
    }
}

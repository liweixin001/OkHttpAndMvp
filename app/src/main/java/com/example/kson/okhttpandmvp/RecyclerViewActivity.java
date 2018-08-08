package com.example.kson.okhttpandmvp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.kson.okhttpandmvp.adapter.ProductAdapter;
import com.example.kson.okhttpandmvp.bean.ProductBean;
import com.example.kson.okhttpandmvp.common.Api;
import com.example.kson.okhttpandmvp.utils.OkHttpUtils;
import com.example.kson.okhttpandmvp.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private ProductBean productBean;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.productRv);

    }

    /**
     * 请求商品列表
     * @param view
     */
    public void requestProduct(View view) {

        HashMap<String,String> params = new HashMap<>();
        params.put("keywords","手机");

        OkHttpUtils.getInstance().getData(Api.PRODUCT_URL,params , new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                String result = null;
                if (response.code()==200){
                    try {
                        result = response.body().string();
                        System.out.println("result:"+result);
                        parseProductBean(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    /**
     * 解析
     * @param result
     */
    private void parseProductBean(String result) {


        productBean = new Gson().fromJson(result,ProductBean.class);

        handler.post(new Runnable() {
            @Override
            public void run() {
                fillDatas();
            }
        });
    }

    /**
     * 绘制列表,使用recyclerview
     */
    private void fillDatas() {
        //recyclervie 集listview，gridview和瀑布流效果的view
        productAdapter = new ProductAdapter(this,productBean.data);
        //设置线性列表
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(productAdapter);

    }
}

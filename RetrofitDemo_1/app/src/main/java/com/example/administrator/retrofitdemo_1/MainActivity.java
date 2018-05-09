package com.example.administrator.retrofitdemo_1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {

    private Context context = this;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRetrofit();
    }

    private void initView() {
        text = (TextView) findViewById(R.id.text);
    }

    private void initRetrofit()
    {
        //1.//获取Retrofit对象，设置地址
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.Url_Base)
            .build();
        //2.通过Retrofit实例创建接口服务对象
        RequestService requestService = retrofit.create(RequestService.class);
        //3.接口服务对象调用接口中方法，获得Call对象
        Call<ResponseBody> call = requestService.getString();
        //4.Call对象执行请求（异步、同步请求）
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess())
                {
                    Log.i("LHD",response.body().toString());
                    try {
                        //返回的结果保存在response.body()中
                        String result = response.body().string();
                        //onResponse方法是运行在主线程也就是UI线程的，所以我们可以在这里
                        //直接更新UI
                        text.setText(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

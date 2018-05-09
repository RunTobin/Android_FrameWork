package com.example.administrator.retrofitdemo_1;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public interface RequestService {
    @GET("index?type=yule&key=d7e184978722fe31928061a05ac7980c")
    Call<ResponseBody> getString();
}

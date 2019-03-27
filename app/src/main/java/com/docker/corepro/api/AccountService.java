package com.docker.corepro.api;

import android.arch.lifecycle.LiveData;

import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.corepro.vo.LoginVo;
import com.docker.corepro.vo.SpecLoginVo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhangxindang on 2018/10/22.
 */

public interface AccountService {



    @POST("user/register")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<LoginVo>>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    @POST
    @FormUrlEncoded
    LiveData<ApiResponse<SpecLoginVo>> login(@Url String url, @Field("username") String username, @Field("password") String password);


    @GET
    @Streaming
    Call<ResponseBody> downApk(@Url String url);
}

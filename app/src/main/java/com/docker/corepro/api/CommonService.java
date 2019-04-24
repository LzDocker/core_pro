package com.docker.corepro.api;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Update;

import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.core.util.versioncontrol.vo.UpdateInfo;
import com.docker.corepro.vo.LoginVo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by zhangxindang on 2018/10/22.
 */

public interface CommonService {


    //版本更新
    @POST("api.php?m=publics.checkVersion")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<UpdateInfo>>> systemUpdate(@Field("clientType") String clientType, @Field("appType") String appType, @Field("version") String version);


    @POST("user/login")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<LoginVo>>> login(@Field("username") String username, @Field("password") String password);


    @GET("f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk")
    @Streaming
    Call<ResponseBody> downApk();
}

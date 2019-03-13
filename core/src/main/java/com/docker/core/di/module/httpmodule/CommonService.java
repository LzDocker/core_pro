package com.docker.core.di.module.httpmodule;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhangxindang on 2018/10/22.
 */

public interface CommonService {

    @GET
    @Streaming
    Call<ResponseBody> download(@Url String url);
}

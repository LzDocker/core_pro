package com.docker.core.di.module.httpmodule.progress;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public interface ProgressListen {

    void ondownloadStart(Call call);

    Call onProcessUploadMethod(Map<String, RequestBody> params);
    /*主线程回调
     * */
    void onFailure(Call<ResponseBody> call, Throwable t);

    /*
     * 在子线程回调 使用mutablelivedata postvalue 发送到主线程操作UI
     * */
    void onProgress(long progress, long total, boolean done);

    /*主线程回调
     * */
    void onComplete(Response<ResponseBody> response);
}

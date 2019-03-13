package com.docker.core.di.module.httpmodule.progress;

import android.Manifest;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.docker.core.di.module.httpmodule.CommonService;
import com.docker.core.di.module.httpmodule.MHeader;
import com.docker.core.di.module.httpmodule.RequestInterceptor;
import com.docker.core.di.module.httpmodule.converter.GsonConverterFactory;
import com.docker.core.util.AppExecutors;
import com.docker.core.util.IOUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class ProgressManager {
    @Inject
    AppExecutors appExecutors;
    @Inject
    MHeader mHeader;

    @Inject
    public ProgressManager() {
    }


    @Inject
    OkHttpClient.Builder builder;

    @Inject
    Retrofit retrofit;

    /*
     *
     * 待进度的下载 入参：
     *
     *
     * */
    public void download(String filesavepath, String filename, String Url, ProgressListen progressListen) {
        if (FileUtils.isFileExists(filesavepath)) {
            Retrofit.Builder newbuilder = retrofit.newBuilder();
            newbuilder.baseUrl(mHeader.getBaseUrl());
            OkHttpClient client = builder.addInterceptor(new RequestInterceptor(null, mHeader))
                    .addNetworkInterceptor(chain -> {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());
                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), progressListen))
                                .build();
                    })
                    .build();
            Retrofit retrofit = newbuilder.client(client).build();
            Call call = retrofit.create(CommonService.class).download(Url);
            progressListen.ondownloadStart(call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    appExecutors.diskIO().execute(() -> {
                        File file = new File(/*Environment.getExternalStorageDirectory()*/filesavepath, filename);
                        FileIOUtils.writeFileFromIS(file, response.body().byteStream());
                        if (FileUtils.isFileExists(file)) {
                            appExecutors.mainThread().execute(() -> {
                                progressListen.onComplete(response);
                            });
                        } else {
                            appExecutors.mainThread().execute(() -> {
                                progressListen.onFailure(call, new IOException());
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressListen.onFailure(call, t);
                }
            });
        } else {
            throw new IllegalArgumentException("文件保存地址不存在");
        }
    }


    /*
     * 带进度的下载
     * */
    public void progressDownLoad(final String filename, ProgressListener progressListener) {
//        mHeader.getBaseUrl()
        /*"http://116.117.158.129/"*/


        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(mHeader.getBaseUrl()).addConverterFactory(GsonConverterFactory.create());
//        Retrofit.Builder builder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor(null, mHeader))
                .addNetworkInterceptor(chain -> {
                    okhttp3.Response orginalResponse = chain.proceed(chain.request());
                    return orginalResponse.newBuilder()
                            .body(new ProgressResponseBody(orginalResponse.body(), progressListener))
                            .build();
                })
                .build();
        Call call = progressListener.onProcessDownLoadMethod(builder.client(client).build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                appExecutors.diskIO().execute(() -> {
                    File file = new File(Environment.getExternalStorageDirectory(), filename);
                    FileIOUtils.writeFileFromIS(file, response.body().byteStream());
                    if (FileUtils.isFileExists(file)) {
                        appExecutors.mainThread().execute(() -> {
                            progressListener.onComplete(response);
                        });
                    } else {
                        appExecutors.mainThread().execute(() -> {
                            progressListener.onFailure(call, new IOException());
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressListener.onFailure(call, t);
            }
        });
    }


    /*
     * 带进度的上传
     * */
    /*
    *
    @Multipart
    @POST("uploadimage")
    Call<ImageReturnBean> uploadFiles(@Part("userid") String userid,@PartMap Map<String, RequestBody> params);
    *
    * */
    public void progressUpload(ArrayList<File> files, ProgressListener progressListener) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(mHeader.getBaseUrl()).addConverterFactory(GsonConverterFactory.create());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor(null, mHeader))
                .build();
        builder.client(client).build();
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            ProgressRequestBody fileRequestBody = new ProgressRequestBody(files.get(i), progressListener);
            params.put("file" + i + "\"; filename=\"" + files.get(i).getName(), fileRequestBody);
        }
        Call call = progressListener.onProcessUploadMethod(params);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                progressListener.onComplete(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressListener.onFailure(call, t);
            }
        });
    }


}



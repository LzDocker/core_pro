package com.docker.core.util.versioncontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.di.module.httpmodule.progress.ProgressListen;
import com.docker.core.di.module.httpmodule.progress.ProgressManager;
import com.docker.core.repository.Resource;
import com.docker.core.util.callback.NetBoundCallback;
import com.docker.core.util.callback.NetBoundObserver;
import com.docker.core.util.versioncontrol.vo.UpdateInfo;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/*
 * app 更新管理
 *
 * */
@Singleton
public class VersionManager implements LifecycleObserver {

    /*
     * 默认使用dialog的形式*/
    public final static int TYPE_DIALOG = 1003;
    /*
     * 通知栏更新
     * */
    public final static int TYPE_NOTIFYACTION = 1004;

    private int type;

    private Activity owner;
    private LiveData<Resource<UpdateInfo>> updateLiveData;
    private ProgressDialog dialog;
    @Inject
    ProgressManager progressManager;

    @Inject
    public VersionManager() { }

    public VersionManager Bind(Context context, LifecycleOwner lifecycleOwner, LiveData<Resource<UpdateInfo>> updateLiveData, int type) {
        this.owner = (Activity) context;
        this.updateLiveData = updateLiveData;
        this.type = type;
        checkVersion(lifecycleOwner);
        return this;
    }

    private void checkVersion(LifecycleOwner lifecycleOwner) {
        updateLiveData.observe(lifecycleOwner, new NetBoundObserver<>(new NetBoundCallback<UpdateInfo>() {
            @Override
            public void onBusinessError(Resource<UpdateInfo> resource) {
            }
            @Override
            public void onNetworkError(Resource<UpdateInfo> resource) {
            }
            @Override
            public void onComplete(Resource<UpdateInfo> resource) {
                super.onComplete(resource);
                if (resource.data != null) {
                    downloadApk();
                } else {
                    downloadApk();
                }
            }
        }));
    }



    private void downloadApk() {

        if(this.type == TYPE_DIALOG){
            progressManager.download(Environment.getExternalStorageDirectory().getPath(), "qq.apk", "http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk", new ProgressListen() {
                @Override
                public void ondownloadStart(Call call) {
                    initDialog();
                }
                @Override
                public Call onProcessUploadMethod(Map<String, RequestBody> params) {
                    return null;
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    ToastUtils.showShort("下载失败...");
                }
                @Override
                public void onProgress(long progress, long total, boolean done) {
                    if (dialog != null) {
                        dialog.setProgress(new Double((progress * 1.0 / total * 100)).intValue());
                    }
                }

                @Override
                public void onComplete(Response<ResponseBody> response) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    AppUtils.installApp(Environment.getExternalStorageDirectory() + "/qq.apk");
                }
            });

        }else{
            if(ServiceUtils.isServiceRunning(UpdateService.class)){
                return;
            }
            Intent intent = new Intent(owner,UpdateService.class);
            owner.startService(intent);
        }
    }


    public void initDialog() {
        dialog = new ProgressDialog(owner);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle(AppUtils.getAppName());
        dialog.setMax(100);
        dialog.setMessage("下载中...");
        dialog.show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
//        if (owner != null) {
//            owner.finish();
//        }
        this.owner = null;
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
            System.gc();
        }
    }
}

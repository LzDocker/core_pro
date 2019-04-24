package com.docker.core.util.versioncontrol;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.R;
import com.docker.core.di.module.httpmodule.progress.ProgressListen;
import com.docker.core.di.module.httpmodule.progress.ProgressManager;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.DaggerService;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UpdateService extends DaggerService {

    @Inject
    ProgressManager progressManager;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    //定义notification实用的ID
    private static final int NO_3 = 0x3;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        progressManager.download(Environment.getExternalStorageDirectory().getPath(), "qq.apk", "http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk", new ProgressListen() {
            @Override
            public void ondownloadStart(Call call) {
                initNoti();
            }

            @Override
            public Call onProcessUploadMethod(Map<String, RequestBody> params) {
                return null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShort("下载失败...");
                stopSelf();
            }

            @Override
            public void onProgress(long progress, long total, boolean done) {

                builder.setProgress(100, new Double((progress * 1.0 / total * 100)).intValue(), false);
                manager.notify(NO_3, builder.build());
                //下载进度提示
                builder.setContentText("下载" + new Double((progress * 1.0 / total * 100)).intValue() + "%");

            }

            @Override
            public void onComplete(Response<ResponseBody> response) {
//                builder.setContentTitle("开始安装");
//                builder.setContentText("安装中...");
//                //设置进度为不确定，用于模拟安装
//                builder.setProgress(0, 0, true);
//                manager.notify(NO_3, builder.build());
                AppUtils.installApp(Environment.getExternalStorageDirectory() + "/qq.apk");
                manager.cancel(NO_3);
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    NotificationCompat.Builder builder;
    NotificationManager manager;

    private void initNoti() {
        builder = new NotificationCompat.Builder(UpdateService.this,AppUtils.getAppPackageName());
        builder.setSmallIcon(R.mipmap.ic_back);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NO_3, builder.build());
        builder.setProgress(100, 0, false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

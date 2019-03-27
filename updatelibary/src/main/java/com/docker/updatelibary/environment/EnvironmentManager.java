package com.docker.updatelibary.environment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.di.module.httpmodule.MHeader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EnvironmentManager implements LifecycleObserver {

    String[] serverName;
    String[] serverAddress;
    String serverproAdd;
    FragmentActivity context;
    @Inject
    MHeader mHeader;

    @Inject
    public EnvironmentManager() { }

    public EnvironmentManager bind(FragmentActivity context, LifecycleOwner lifecycleOwner, String[] serverName, String[] serverAddress, String serverproAdd) {
        this.serverAddress = serverAddress;
        this.serverName = serverName;
        this.context = context;
        this.serverproAdd = serverproAdd;
        if (!AppUtils.isAppDebug()) {
            mHeader.setBaseUrl(serverproAdd);
        }
        return this;
    }

    public void selectEnvironment() {
        if (AppUtils.isAppDebug()) {
            String currentServername = SPUtils.getInstance("config").getString("SERVER_URL_NAME");
            String currentServerurl = SPUtils.getInstance("config").getString("SERVER_URL");
            if (TextUtils.isEmpty(currentServername) || TextUtils.isEmpty(currentServerurl)) {
                currentServername = "线上";
                SPUtils.getInstance("config").put("SERVER_URL", serverproAdd);
                SPUtils.getInstance("config").put("SERVER_URL_NAME", "线上");
                mHeader.setServerUrl(serverproAdd);
            }


            String[] strings = new String[serverName.length + 1];
            strings[0] = "当前环境是：" + currentServername + "(" + currentServerurl + ")";
            for (int i = 0; i < strings.length - 1; i++) {
                strings[i + 1] = serverName[i] + serverAddress[i];
            }

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
            bottomSheetDialog.setDataCallback(strings, new BottomSheetDialog.ChooseCallback() {
                @Override
                public void onClickOptions(int position) {
                    if (position == 0) {
                        return;
                    }
                    SPUtils.getInstance("config").put("SERVER_URL", serverAddress[position - 1]);
                    SPUtils.getInstance("config").put("SERVER_URL_NAME", serverName[position - 1]);
                    mHeader.setServerUrl(serverAddress[position - 1]);
                    String[] strings = new String[serverName.length + 1];
                    strings[0] = "当前环境是：" + serverName[position - 1] + "(" + serverAddress[position - 1] + ")";
                    for (int i = 0; i < strings.length - 1; i++) {
                        strings[i + 1] = serverName[i] + serverAddress[i];
                    }
                    bottomSheetDialog.replaceData(strings);
                    ToastUtils.showShort("当前环境是：" + serverName[position - 1]);
                    bottomSheetDialog.dismiss();
                }
            });
            bottomSheetDialog.show(context);
        } else {
            mHeader.setBaseUrl(serverproAdd);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ondestory() {

    }


}

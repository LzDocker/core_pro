package com.docker.corepro;

import com.docker.core.base.BaseApplication;
import com.docker.corepro.di.DaggerAppComponent;

public class simpleApp extends BaseApplication {

    @Override
    protected void injectApp() {
        DaggerAppComponent.builder()
                .appModule(getAppModule())
                .globalConfigModule(getGlobalConfigModule())
                .httpClientModule(getHttpClientModule())
                .cacheModule(getCacheModule())
                .build()
                .inject(this);
    }
}

package com.docker.corepro;

import android.content.Context;

import com.docker.core.base.BaseApplication;
import com.docker.core.di.module.httpmodule.GlobalConfigModule;
import com.docker.core.di.module.httpmodule.HttpRequestHandler;
import com.docker.core.widget.refresh.SmartRefreshLayout;
import com.docker.core.widget.refresh.api.DefaultRefreshFooterCreator;
import com.docker.core.widget.refresh.api.DefaultRefreshHeaderCreator;
import com.docker.core.widget.refresh.api.RefreshFooter;
import com.docker.core.widget.refresh.api.RefreshHeader;
import com.docker.core.widget.refresh.api.RefreshLayout;
import com.docker.core.widget.refresh.footer.ClassicsFooter;
import com.docker.core.widget.refresh.header.ClassicsHeader;
import com.docker.corepro.api.ServiceConfig;
import com.docker.corepro.di.DaggerAppComponent;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class simpleApp extends BaseApplication {

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.colorPrimary);//全局设置主题颜色
                return new ClassicsHeader(context)/*.setTimeFormat(new SimpleDateFormat("更新于 %s"))*/;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });

//        int[] imgres = {R.drawable.audio_placeholder,R.drawable.audio_placeholder,R.drawable.audio_placeholder,};
//        String[] textstr = {"加载中...","请重试","暂无数据"};
//        int[] textcolor = {R.color.bar_grey,R.color.colorPrimary,R.color.colorPrimaryDark};
//        EmptyLayout.setDefaultViewRes(imgres,textstr,textcolor);
    }

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

    @Override
    protected GlobalConfigModule getGlobalConfigModule() {
//        return super.getGlobalConfigModule();
        return GlobalConfigModule.buidler()
                .baseurl(ServiceConfig.SERVER_URL_PRO)
                .globeHttpHandler(new HttpRequestHandler() {
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        return response;
                    }
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        return request;
                    }
                }).build();
    }
}

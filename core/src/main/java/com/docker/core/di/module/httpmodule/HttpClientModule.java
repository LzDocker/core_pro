package com.docker.core.di.module.httpmodule;

import android.content.Context;
import com.docker.core.di.module.cookie.CookieJarImpl;
import com.docker.core.di.module.cookie.PersistentCookieStore;
import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class HttpClientModule {

    private Context context;

    public HttpClientModule(Context context) {
        this.context = context;
    }


    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
        return builder
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(com.docker.core.di.module.httpmodule.converter.GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, Interceptor intercept
            , List<Interceptor> interceptors, CookieJar cookieJar) {
        OkHttpClient.Builder builder = okHttpClient
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(intercept);
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }


    @Provides
    CookieJar providerCookieJar() {
        return new CookieJarImpl(new PersistentCookieStore(context));
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public MHeader provideHeader(HttpUrl httpUrl) {
        MHeader reauestHeader = new MHeader();
        reauestHeader.setBaseUrl(httpUrl.toString());
        reauestHeader.setServerUrl(httpUrl.toString());
        return reauestHeader;
    }

    @Singleton
    @Provides
    Interceptor provideIntercept(RequestInterceptor interceptor) {
        return interceptor;
    }

}

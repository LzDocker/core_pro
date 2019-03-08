package com.docker.core.di.module.httpmodule;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zxd on 17/5/15.
 */
@Singleton
public class RequestInterceptor implements Interceptor {

    private HttpRequestHandler mHttpRequestHandler;
    private MHeader mHeader;

    @Inject
    public RequestInterceptor(HttpRequestHandler httpRequestHandler, MHeader mHeader) {
        this.mHttpRequestHandler = httpRequestHandler;
        this.mHeader = mHeader;
    }

    public Response intercept(Chain chain) throws IOException {

        Request originrequest = chain.request();
        HttpUrl originHttpUrl = originrequest.url();
        Request.Builder newBuilder;
        HttpUrl newBaseUrl;
        if (!TextUtils.isEmpty(mHeader.getServerUrl())
                && !TextUtils.isEmpty(mHeader.getBaseUrl())
                && !mHeader.getBaseUrl().equals(mHeader.getServerUrl())
                && !originHttpUrl.toString().startsWith(mHeader.getServerUrl())) {
            newBuilder = originrequest.newBuilder();
            newBaseUrl = HttpUrl.parse(originHttpUrl.toString().replace(mHeader.getBaseUrl(), mHeader.getServerUrl()));
            newBuilder.url(newBaseUrl);
            Request request = newBuilder.build();
            if (mHttpRequestHandler != null) {
                // do something before http request like adding specific headers.
                mHttpRequestHandler.onHttpRequestBefore(chain, request);
            }

            Response originalResponse = chain.proceed(request);
            ResponseBody responseBody = originalResponse.body();

            if (mHttpRequestHandler != null) {
                mHttpRequestHandler.onHttpResultResponse(responseBody.toString(), chain, originalResponse);
            }
            Log.d("Request: " , request.toString()+ "Headers:-----" + bodyToString(request.headers()) + "----Params:" + bodyToString(request.body()));

            return originalResponse;
        } else {
            if (mHttpRequestHandler != null) {
                // do something before http request like adding specific headers.
                mHttpRequestHandler.onHttpRequestBefore(chain, originrequest);
            }
            Response originalResponse = chain.proceed(originrequest);
            ResponseBody responseBody = originalResponse.body();
            if (mHttpRequestHandler != null) {
                mHttpRequestHandler.onHttpResultResponse(responseBody.toString(), chain, originalResponse);
            }
            Log.d("Request: " ,  originrequest.toString()+"Headers:------" + bodyToString(originrequest.headers()) + "----Params:" + bodyToString(originrequest.body()));

            return originalResponse;
        }


    }

    private static String bodyToString(RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "";
            }
        } catch (IOException var3) {
            return "did not work";
        }
    }

    private static String bodyToString(Headers headers) {
        Map<String, List<String>> params = headers.toMultimap();
        StringBuilder stringBuilder = new StringBuilder();
        Iterator var3 = params.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
            stringBuilder.append((String)entry.getKey()).append(":").append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}

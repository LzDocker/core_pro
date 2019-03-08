package com.docker.core.di.module.httpmodule;

import retrofit2.http.Streaming;

public class MHeader {


    public MHeader() {
    }

    private String baseUrl;
    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

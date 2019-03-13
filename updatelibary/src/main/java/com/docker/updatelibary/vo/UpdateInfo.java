package com.docker.updatelibary.vo;

import java.io.Serializable;

public class UpdateInfo implements Serializable {


    private String clientVersion;
    private String applink;
    private boolean isRequired;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getApplink() {
        return applink;
    }

    public void setApplink(String applink) {
        this.applink = applink;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}

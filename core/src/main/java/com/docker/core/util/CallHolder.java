package com.docker.core.util;

import java.util.HashMap;

import retrofit2.Call;

public class CallHolder {

    private CallHolder() { }

    private static volatile CallHolder callHolder;
    private static HashMap<String, Call>  callHashMap = new HashMap<>();

    public static CallHolder getInstance() {
        if (callHolder == null) {
            synchronized (CallHolder.class) {
                return new CallHolder();
            }
        }
        return callHolder;
    }


}

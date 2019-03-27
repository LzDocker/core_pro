package com.docker.core.util;

import android.support.annotation.Nullable;

/*
*
* vm --> activity (view层交互的数据源)
*
* T ------------- vm 传递给activity的数据bean
* message ------- 提示类数据
* eveType ------- 事件类型
* */
public class ViewEventResouce<T> {

    @Nullable
    public final T data;
    @Nullable
    public final String message;

    public final int eventType;

    public ViewEventResouce(@Nullable int eventType, @Nullable String message, @Nullable T data) {
        this.eventType = eventType;

        this.message = message;

        this.data = data;
    }

}

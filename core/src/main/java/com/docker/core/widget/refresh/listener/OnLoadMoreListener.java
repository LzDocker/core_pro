package com.docker.core.widget.refresh.listener;

import android.support.annotation.NonNull;

import com.docker.core.widget.refresh.api.RefreshLayout;


/**
 * 加载更多监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}

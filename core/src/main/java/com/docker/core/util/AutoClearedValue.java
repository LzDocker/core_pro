package com.docker.core.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by zhangxindang on 2018/8/31.
 * <p>
 * <p>
 * fragment 中反注册 databing
 */

public class AutoClearedValue<T> {

    private T value;

//    public AutoClearedValue(final Fragment fragment, T value) {
//        final FragmentManager fragmentManager = fragment.getFragmentManager();
//        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
//            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
//                if(f == fragment) {
//                    AutoClearedValue.this.value = null;
//                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
//                }
//            }
//        }, false);
//        this.value = value;
//    }

    public T get() {
        return this.value;
    }

    public AutoClearedValue(final Fragment fragment, T value) {
        FragmentManager manager = fragment.getFragmentManager();
        manager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
                if (f == fragment) {
                    AutoClearedValue.this.value = null;
                    manager.unregisterFragmentLifecycleCallbacks(this);
                }
            }
        }, false);
        this.value = value;
    }
}

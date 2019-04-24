package com.docker.core.widget.emptylayout;

import android.databinding.BindingAdapter;


/**
 *
 */
public class EmptyBindingAdapters {
    @BindingAdapter(value = {"onRetryCommand"})
    public static void setonRetryCommand(EmptyLayout view, final EmptyCommand onRetryCommand) {
        view.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                onRetryCommand.exectue();
            }
        });
    }
}

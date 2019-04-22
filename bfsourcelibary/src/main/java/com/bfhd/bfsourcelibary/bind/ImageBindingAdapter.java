package com.bfhd.bfsourcelibary.bind;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by zhangxindang on 2018/12/18.
 */

public class ImageBindingAdapter {

    private static RequestOptions options = new RequestOptions();

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().dontAnimate();
        Glide.with(imageView.getContext()).load(url).apply(options.placeholder(holderDrawable).error(errorDrawable)).into(imageView);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String url) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().dontAnimate();
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }


}

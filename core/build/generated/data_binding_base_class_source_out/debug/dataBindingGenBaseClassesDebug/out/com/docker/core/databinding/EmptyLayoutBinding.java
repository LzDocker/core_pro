package com.docker.core.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class EmptyLayoutBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout commonEmptyCoutainer;

  @NonNull
  public final LinearLayout commonEmptyView;

  @NonNull
  public final LinearLayout commonErrView;

  @NonNull
  public final ImageView commonIvError;

  @NonNull
  public final ImageView commonIvLoading;

  @NonNull
  public final ImageView commonIvNodata;

  @NonNull
  public final LinearLayout commonLoadingView;

  @NonNull
  public final TextView commonTvError;

  @NonNull
  public final TextView commonTvLoading;

  @NonNull
  public final TextView commonTvNodata;

  @NonNull
  public final ProgressBar progressBar;

  protected EmptyLayoutBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, LinearLayout commonEmptyCoutainer, LinearLayout commonEmptyView,
      LinearLayout commonErrView, ImageView commonIvError, ImageView commonIvLoading,
      ImageView commonIvNodata, LinearLayout commonLoadingView, TextView commonTvError,
      TextView commonTvLoading, TextView commonTvNodata, ProgressBar progressBar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.commonEmptyCoutainer = commonEmptyCoutainer;
    this.commonEmptyView = commonEmptyView;
    this.commonErrView = commonErrView;
    this.commonIvError = commonIvError;
    this.commonIvLoading = commonIvLoading;
    this.commonIvNodata = commonIvNodata;
    this.commonLoadingView = commonLoadingView;
    this.commonTvError = commonTvError;
    this.commonTvLoading = commonTvLoading;
    this.commonTvNodata = commonTvNodata;
    this.progressBar = progressBar;
  }

  @NonNull
  public static EmptyLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static EmptyLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<EmptyLayoutBinding>inflate(inflater, com.docker.core.R.layout.empty_layout, root, attachToRoot, component);
  }

  @NonNull
  public static EmptyLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static EmptyLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<EmptyLayoutBinding>inflate(inflater, com.docker.core.R.layout.empty_layout, null, false, component);
  }

  public static EmptyLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static EmptyLayoutBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (EmptyLayoutBinding)bind(component, view, com.docker.core.R.layout.empty_layout);
  }
}

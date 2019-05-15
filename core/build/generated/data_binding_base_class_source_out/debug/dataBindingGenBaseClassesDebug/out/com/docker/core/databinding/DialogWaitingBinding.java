package com.docker.core.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public abstract class DialogWaitingBinding extends ViewDataBinding {
  @NonNull
  public final ProgressBar progressBar;

  @Bindable
  protected String mMessage;

  protected DialogWaitingBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ProgressBar progressBar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.progressBar = progressBar;
  }

  public abstract void setMessage(@Nullable String message);

  @Nullable
  public String getMessage() {
    return mMessage;
  }

  @NonNull
  public static DialogWaitingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static DialogWaitingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<DialogWaitingBinding>inflate(inflater, com.docker.core.R.layout.dialog_waiting, root, attachToRoot, component);
  }

  @NonNull
  public static DialogWaitingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static DialogWaitingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<DialogWaitingBinding>inflate(inflater, com.docker.core.R.layout.dialog_waiting, null, false, component);
  }

  public static DialogWaitingBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static DialogWaitingBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (DialogWaitingBinding)bind(component, view, com.docker.core.R.layout.dialog_waiting);
  }
}

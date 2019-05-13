package com.docker.core.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class DialogFragmentChooseLayoutBinding extends ViewDataBinding {
  @NonNull
  public final TextView dialogFragmentChooseLayoutTvCancel;

  @NonNull
  public final RecyclerView recycle;

  protected DialogFragmentChooseLayoutBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView dialogFragmentChooseLayoutTvCancel, RecyclerView recycle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dialogFragmentChooseLayoutTvCancel = dialogFragmentChooseLayoutTvCancel;
    this.recycle = recycle;
  }

  @NonNull
  public static DialogFragmentChooseLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static DialogFragmentChooseLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<DialogFragmentChooseLayoutBinding>inflate(inflater, com.docker.core.R.layout.dialog_fragment_choose_layout, root, attachToRoot, component);
  }

  @NonNull
  public static DialogFragmentChooseLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static DialogFragmentChooseLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<DialogFragmentChooseLayoutBinding>inflate(inflater, com.docker.core.R.layout.dialog_fragment_choose_layout, null, false, component);
  }

  public static DialogFragmentChooseLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static DialogFragmentChooseLayoutBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (DialogFragmentChooseLayoutBinding)bind(component, view, com.docker.core.R.layout.dialog_fragment_choose_layout);
  }
}

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
import android.widget.TextView;
import java.lang.String;

public abstract class ItemChooseOptionsBinding extends ViewDataBinding {
  @NonNull
  public final TextView itemChooseOptionsTv;

  @Bindable
  protected String mItem;

  protected ItemChooseOptionsBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView itemChooseOptionsTv) {
    super(_bindingComponent, _root, _localFieldCount);
    this.itemChooseOptionsTv = itemChooseOptionsTv;
  }

  public abstract void setItem(@Nullable String item);

  @Nullable
  public String getItem() {
    return mItem;
  }

  @NonNull
  public static ItemChooseOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemChooseOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemChooseOptionsBinding>inflate(inflater, com.docker.core.R.layout.item_choose_options, root, attachToRoot, component);
  }

  @NonNull
  public static ItemChooseOptionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemChooseOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemChooseOptionsBinding>inflate(inflater, com.docker.core.R.layout.item_choose_options, null, false, component);
  }

  public static ItemChooseOptionsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemChooseOptionsBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemChooseOptionsBinding)bind(component, view, com.docker.core.R.layout.item_choose_options);
  }
}

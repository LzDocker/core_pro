package com.docker.core;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.docker.core.databinding.DialogFragmentChooseLayoutBindingImpl;
import com.docker.core.databinding.EmptyLayoutBindingImpl;
import com.docker.core.databinding.ItemChooseOptionsBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_DIALOGFRAGMENTCHOOSELAYOUT = 1;

  private static final int LAYOUT_EMPTYLAYOUT = 2;

  private static final int LAYOUT_ITEMCHOOSEOPTIONS = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.core.R.layout.dialog_fragment_choose_layout, LAYOUT_DIALOGFRAGMENTCHOOSELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.core.R.layout.empty_layout, LAYOUT_EMPTYLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.core.R.layout.item_choose_options, LAYOUT_ITEMCHOOSEOPTIONS);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_DIALOGFRAGMENTCHOOSELAYOUT: {
          if ("layout/dialog_fragment_choose_layout_0".equals(tag)) {
            return new DialogFragmentChooseLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_fragment_choose_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_EMPTYLAYOUT: {
          if ("layout/empty_layout_0".equals(tag)) {
            return new EmptyLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for empty_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMCHOOSEOPTIONS: {
          if ("layout/item_choose_options_0".equals(tag)) {
            return new ItemChooseOptionsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_choose_options is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "item");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/dialog_fragment_choose_layout_0", com.docker.core.R.layout.dialog_fragment_choose_layout);
      sKeys.put("layout/empty_layout_0", com.docker.core.R.layout.empty_layout);
      sKeys.put("layout/item_choose_options_0", com.docker.core.R.layout.item_choose_options);
    }
  }
}

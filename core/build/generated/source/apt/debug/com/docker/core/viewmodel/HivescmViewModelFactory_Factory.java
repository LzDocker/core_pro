package com.docker.core.viewmodel;

import android.arch.lifecycle.ViewModel;
import dagger.internal.Factory;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HivescmViewModelFactory_Factory implements Factory<HivescmViewModelFactory> {
  private final Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider;

  public HivescmViewModelFactory_Factory(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    assert creatorsProvider != null;
    this.creatorsProvider = creatorsProvider;
  }

  @Override
  public HivescmViewModelFactory get() {
    return new HivescmViewModelFactory(creatorsProvider.get());
  }

  public static Factory<HivescmViewModelFactory> create(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    return new HivescmViewModelFactory_Factory(creatorsProvider);
  }
}

// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.docker.core.base.basehivs;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import com.docker.core.util.Empty;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.inject.Provider;

public final class HivsBaseActivity_MembersInjector<
        VM extends HivsBaseViewModel, VB extends ViewDataBinding>
    implements MembersInjector<HivsBaseActivity<VM, VB>> {
  private final Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.app.Fragment>>
      frameworkFragmentInjectorProvider;

  private final Provider<Empty> emptyProvider;

  public HivsBaseActivity_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.app.Fragment>> frameworkFragmentInjectorProvider,
      Provider<Empty> emptyProvider) {
    assert supportFragmentInjectorProvider != null;
    this.supportFragmentInjectorProvider = supportFragmentInjectorProvider;
    assert frameworkFragmentInjectorProvider != null;
    this.frameworkFragmentInjectorProvider = frameworkFragmentInjectorProvider;
    assert emptyProvider != null;
    this.emptyProvider = emptyProvider;
  }

  public static <VM extends HivsBaseViewModel, VB extends ViewDataBinding>
      MembersInjector<HivsBaseActivity<VM, VB>> create(
          Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
          Provider<DispatchingAndroidInjector<android.app.Fragment>>
              frameworkFragmentInjectorProvider,
          Provider<Empty> emptyProvider) {
    return new HivsBaseActivity_MembersInjector<VM, VB>(
        supportFragmentInjectorProvider, frameworkFragmentInjectorProvider, emptyProvider);
  }

  @Override
  public void injectMembers(HivsBaseActivity<VM, VB> instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    com.docker.core.base.BaseInjectActivity_MembersInjector.injectSupportFragmentInjector(
        instance, supportFragmentInjectorProvider);
    com.docker.core.base.BaseInjectActivity_MembersInjector.injectFrameworkFragmentInjector(
        instance, frameworkFragmentInjectorProvider);
    com.docker.core.base.BaseActivity_MembersInjector.injectEmpty(instance, emptyProvider);
    instance.empty = emptyProvider.get();
  }

  public static <VM extends HivsBaseViewModel, VB extends ViewDataBinding> void injectEmpty(
      HivsBaseActivity<VM, VB> instance, Provider<Empty> emptyProvider) {
    instance.empty = emptyProvider.get();
  }
}

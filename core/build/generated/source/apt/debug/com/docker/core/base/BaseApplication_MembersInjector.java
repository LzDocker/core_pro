package com.docker.core.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseApplication_MembersInjector implements MembersInjector<BaseApplication> {
  private final Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider;

  private final Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
      supportFragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<BroadcastReceiver>>
      broadcastReceiverInjectorProvider;

  private final Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider;

  private final Provider<DispatchingAndroidInjector<ContentProvider>>
      contentProviderInjectorProvider;

  public BaseApplication_MembersInjector(
      Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
          supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<BroadcastReceiver>> broadcastReceiverInjectorProvider,
      Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider,
      Provider<DispatchingAndroidInjector<ContentProvider>> contentProviderInjectorProvider) {
    assert activityInjectorProvider != null;
    this.activityInjectorProvider = activityInjectorProvider;
    assert fragmentInjectorProvider != null;
    this.fragmentInjectorProvider = fragmentInjectorProvider;
    assert supportFragmentInjectorProvider != null;
    this.supportFragmentInjectorProvider = supportFragmentInjectorProvider;
    assert broadcastReceiverInjectorProvider != null;
    this.broadcastReceiverInjectorProvider = broadcastReceiverInjectorProvider;
    assert serviceInjectorProvider != null;
    this.serviceInjectorProvider = serviceInjectorProvider;
    assert contentProviderInjectorProvider != null;
    this.contentProviderInjectorProvider = contentProviderInjectorProvider;
  }

  public static MembersInjector<BaseApplication> create(
      Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
          supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<BroadcastReceiver>> broadcastReceiverInjectorProvider,
      Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider,
      Provider<DispatchingAndroidInjector<ContentProvider>> contentProviderInjectorProvider) {
    return new BaseApplication_MembersInjector(
        activityInjectorProvider,
        fragmentInjectorProvider,
        supportFragmentInjectorProvider,
        broadcastReceiverInjectorProvider,
        serviceInjectorProvider,
        contentProviderInjectorProvider);
  }

  @Override
  public void injectMembers(BaseApplication instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.activityInjector = activityInjectorProvider.get();
    instance.fragmentInjector = fragmentInjectorProvider.get();
    instance.supportFragmentInjector = supportFragmentInjectorProvider.get();
    instance.broadcastReceiverInjector = broadcastReceiverInjectorProvider.get();
    instance.serviceInjector = serviceInjectorProvider.get();
    instance.contentProviderInjector = contentProviderInjectorProvider.get();
  }

  public static void injectActivityInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider) {
    instance.activityInjector = activityInjectorProvider.get();
  }

  public static void injectFragmentInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider) {
    instance.fragmentInjector = fragmentInjectorProvider.get();
  }

  public static void injectSupportFragmentInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
          supportFragmentInjectorProvider) {
    instance.supportFragmentInjector = supportFragmentInjectorProvider.get();
  }

  public static void injectBroadcastReceiverInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<BroadcastReceiver>> broadcastReceiverInjectorProvider) {
    instance.broadcastReceiverInjector = broadcastReceiverInjectorProvider.get();
  }

  public static void injectServiceInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider) {
    instance.serviceInjector = serviceInjectorProvider.get();
  }

  public static void injectContentProviderInjector(
      BaseApplication instance,
      Provider<DispatchingAndroidInjector<ContentProvider>> contentProviderInjectorProvider) {
    instance.contentProviderInjector = contentProviderInjectorProvider.get();
  }
}

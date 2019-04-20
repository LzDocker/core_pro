package com.docker.core.di.module.httpmodule;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HttpClientModule_ProvideClientFactory implements Factory<OkHttpClient> {
  private final HttpClientModule module;

  private final Provider<OkHttpClient.Builder> okHttpClientProvider;

  private final Provider<Interceptor> interceptProvider;

  private final Provider<List<Interceptor>> interceptorsProvider;

  private final Provider<CookieJar> cookieJarProvider;

  public HttpClientModule_ProvideClientFactory(
      HttpClientModule module,
      Provider<OkHttpClient.Builder> okHttpClientProvider,
      Provider<Interceptor> interceptProvider,
      Provider<List<Interceptor>> interceptorsProvider,
      Provider<CookieJar> cookieJarProvider) {
    assert module != null;
    this.module = module;
    assert okHttpClientProvider != null;
    this.okHttpClientProvider = okHttpClientProvider;
    assert interceptProvider != null;
    this.interceptProvider = interceptProvider;
    assert interceptorsProvider != null;
    this.interceptorsProvider = interceptorsProvider;
    assert cookieJarProvider != null;
    this.cookieJarProvider = cookieJarProvider;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.provideClient(
            okHttpClientProvider.get(),
            interceptProvider.get(),
            interceptorsProvider.get(),
            cookieJarProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(
      HttpClientModule module,
      Provider<OkHttpClient.Builder> okHttpClientProvider,
      Provider<Interceptor> interceptProvider,
      Provider<List<Interceptor>> interceptorsProvider,
      Provider<CookieJar> cookieJarProvider) {
    return new HttpClientModule_ProvideClientFactory(
        module, okHttpClientProvider, interceptProvider, interceptorsProvider, cookieJarProvider);
  }

  /**
   * Proxies {@link HttpClientModule#provideClient(OkHttpClient.Builder, Interceptor, List,
   * CookieJar)}.
   */
  public static OkHttpClient proxyProvideClient(
      HttpClientModule instance,
      OkHttpClient.Builder okHttpClient,
      Interceptor intercept,
      List<Interceptor> interceptors,
      CookieJar cookieJar) {
    return instance.provideClient(okHttpClient, intercept, interceptors, cookieJar);
  }
}
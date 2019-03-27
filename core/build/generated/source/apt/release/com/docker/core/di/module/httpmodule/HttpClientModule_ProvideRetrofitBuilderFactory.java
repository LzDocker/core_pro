package com.docker.core.di.module.httpmodule;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HttpClientModule_ProvideRetrofitBuilderFactory
    implements Factory<Retrofit.Builder> {
  private final HttpClientModule module;

  public HttpClientModule_ProvideRetrofitBuilderFactory(HttpClientModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Retrofit.Builder get() {
    return Preconditions.checkNotNull(
        module.provideRetrofitBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Retrofit.Builder> create(HttpClientModule module) {
    return new HttpClientModule_ProvideRetrofitBuilderFactory(module);
  }

  /** Proxies {@link HttpClientModule#provideRetrofitBuilder()}. */
  public static Retrofit.Builder proxyProvideRetrofitBuilder(HttpClientModule instance) {
    return instance.provideRetrofitBuilder();
  }
}

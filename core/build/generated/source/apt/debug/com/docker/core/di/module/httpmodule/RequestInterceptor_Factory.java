package com.docker.core.di.module.httpmodule;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RequestInterceptor_Factory implements Factory<RequestInterceptor> {
  private final Provider<HttpRequestHandler> httpRequestHandlerProvider;

  private final Provider<MHeader> mHeaderProvider;

  public RequestInterceptor_Factory(
      Provider<HttpRequestHandler> httpRequestHandlerProvider, Provider<MHeader> mHeaderProvider) {
    assert httpRequestHandlerProvider != null;
    this.httpRequestHandlerProvider = httpRequestHandlerProvider;
    assert mHeaderProvider != null;
    this.mHeaderProvider = mHeaderProvider;
  }

  @Override
  public RequestInterceptor get() {
    return new RequestInterceptor(httpRequestHandlerProvider.get(), mHeaderProvider.get());
  }

  public static Factory<RequestInterceptor> create(
      Provider<HttpRequestHandler> httpRequestHandlerProvider, Provider<MHeader> mHeaderProvider) {
    return new RequestInterceptor_Factory(httpRequestHandlerProvider, mHeaderProvider);
  }
}

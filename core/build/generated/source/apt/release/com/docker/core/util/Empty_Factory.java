package com.docker.core.util;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Empty_Factory implements Factory<Empty> {
  private static final Empty_Factory INSTANCE = new Empty_Factory();

  @Override
  public Empty get() {
    return new Empty();
  }

  public static Factory<Empty> create() {
    return INSTANCE;
  }
}

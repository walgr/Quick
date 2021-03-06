package com.wpf.app.quick.runtime;

import androidx.annotation.UiThread;

/** An unbinder contract that will unbind views when called. */
public interface Unbinder {
  @UiThread
  void unbind();

  Unbinder EMPTY = () -> { };
}

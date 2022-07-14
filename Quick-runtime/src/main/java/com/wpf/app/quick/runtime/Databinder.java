package com.wpf.app.quick.runtime;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

/**
 * 可以获取参数值
 */
public interface Databinder extends Unbinder {

  default @Nullable
  Object getFieldValue(String fieldName) {
    try {
      Field findF = getClass().getDeclaredField(fieldName);
      findF.setAccessible(true);
      return findF.get(this);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }
    return null;
  }
}

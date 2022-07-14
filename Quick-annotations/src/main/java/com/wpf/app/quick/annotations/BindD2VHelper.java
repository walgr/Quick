package com.wpf.app.quick.annotations;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by 王朋飞 on 2022/7/6.
 */
public interface BindD2VHelper<V extends View, Data> {

    void initView(@NonNull V view, @NonNull Data data);
}

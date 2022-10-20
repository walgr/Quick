package com.wpf.app.quick.annotations;

//import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by 王朋飞 on 2022/7/6.
 */
public interface BindD2VHelper<VH, V, Data> {

    void initView(@Nullable VH viewHolder, @NonNull V view, @NonNull Data data);
}

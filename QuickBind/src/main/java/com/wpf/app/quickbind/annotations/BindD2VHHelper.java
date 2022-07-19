package com.wpf.app.quickbind.annotations;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wpf.app.quick.annotations.BindD2VHelper;

/**
 * Created by 王朋飞 on 2022/7/6.
 */
public interface BindD2VHHelper<V extends View, Data> extends BindD2VHelper<RecyclerView.ViewHolder, V, Data> {

    void initView(@Nullable RecyclerView.ViewHolder viewHolder, @NonNull V view, @NonNull Data data);
}

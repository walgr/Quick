package com.wpf.app.quick.annotations;

import androidx.annotation.IdRes;
import com.wpf.app.quick.annotations.internal.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 王朋飞 on 2022/7/5.
 * 如果不传id 代表的是整体的view
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindData2View {
    @IdRes int id() default Constants.NO_RES_ID;

    Class<? extends BindD2VHelper> helper() default BindD2VHelper.class;
}

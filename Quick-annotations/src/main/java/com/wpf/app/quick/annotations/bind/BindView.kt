package com.wpf.app.quick.annotations.bind

import androidx.annotation.IdRes

/**
 * Bind a field to the view for the specified ID. The view will automatically be cast to the field
 * type.
 * <pre>`
 * @BindView(R.id.title) TextView title;
`</pre> *
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindView(
    /** View ID to which the field will be bound.  */
    @IdRes val value: Int
)
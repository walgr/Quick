package com.wpf.app.quick.annotations.transform

import kotlin.reflect.KClass
@Suppress("unused")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ClassMixin(
    val mixClass: KClass<out Any>
)
@Suppress("unused")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MixinSrc
@Suppress("unused")
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
annotation class InterfaceMixin
@Suppress("unused")
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class FieldMixin
@Suppress("unused")
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class PropertyMixin
@Suppress("unused")
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.BINARY)
annotation class FunctionMixin

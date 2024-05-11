package com.wpf.app.quick.annotations.transform

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ClassMixin(
    val mixClass: KClass<out Any>
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MixinSrc

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
annotation class InterfaceMixin

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class FieldMixin

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class PropertyMixin

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.BINARY)
annotation class FunctionMixin

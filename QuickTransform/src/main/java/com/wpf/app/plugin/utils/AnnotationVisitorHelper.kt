package com.wpf.app.plugin.utils

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

fun annotationVisitor(
    api: Int = Opcodes.ASM7,
    visit: (name: String?, value: Any?) -> Unit
): AnnotationVisitor {
    return object : AnnotationVisitor(api) {
        override fun visit(name: String?, value: Any?) {
            super.visit(name, value)
            visit.invoke(name, value)
        }
    }
}
package com.wpf.app.plugin

import com.android.build.api.instrumentation.ClassContext
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class QuickClassVisitor(
    private val classContext: ClassContext,
    nextClassVisitor: ClassVisitor,
): ClassVisitor(Opcodes.ASM7, nextClassVisitor) {

    init {
        println("正在处理：${classContext.currentClassData.className}")
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        super.visitEnd()
        println("处理：${classContext.currentClassData.className}结束")
    }
}
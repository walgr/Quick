package com.wpf.app.plugin.factory.mixin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.wpf.app.quick.annotations.transform.MixinSrc
import org.objectweb.asm.ClassVisitor

abstract class QuickSaveClassAsmFactory : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor,
    ): ClassVisitor {
        return QuickSaveClassVisitor(classContext, nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return classData.classAnnotations.contains(MixinSrc::class.qualifiedName)
    }
}

class QuickSaveClassVisitor(
    classContext: ClassContext,
    nextClassVisitor: ClassVisitor,
) : MixinFlowClassVisitor(classContext, nextClassVisitor, isSrcClass = true)
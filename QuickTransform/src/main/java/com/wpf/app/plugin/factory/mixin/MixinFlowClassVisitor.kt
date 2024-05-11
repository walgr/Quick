package com.wpf.app.plugin.factory.mixin

import com.android.build.api.instrumentation.ClassContext
import com.wpf.app.plugin.utils.DataHelper
import com.wpf.app.quick.annotations.transform.FieldMixin
import com.wpf.app.quick.annotations.transform.FunctionMixin
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.StaticInitMerger
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode

open class MixinFlowClassVisitor(
    private val classContext: ClassContext,
    private val nextClassVisitor: ClassVisitor,
    private val isSrcClass: Boolean = true,
) : ClassNode(Opcodes.ASM7) {

    private val KEY_MIXIN_FLOW_SRC = "KEY_MIXIN_FLOW_SRC"
    private val KEY_MIXIN_FLOW_DST = "KEY_MIXIN_FLOW_DST"

    init {
        if (isSrcClass) {
            DataHelper[KEY_MIXIN_FLOW_SRC] = this
        } else {
            getDstClassVisitorList()[this] = nextClassVisitor
        }
    }

    private fun getSrcClassVisitor() = DataHelper[KEY_MIXIN_FLOW_SRC] as ClassNode

    private fun getDstClassVisitorList() =
        DataHelper.getNullPut<MutableMap<ClassNode, ClassVisitor>>(
            KEY_MIXIN_FLOW_DST,
            mutableMapOf()
        )

    private fun canDeal(): Boolean {
        return DataHelper[KEY_MIXIN_FLOW_SRC] != null && getDstClassVisitorList().isNotEmpty()
    }

    init {
        if (isSrcClass) {
            println("正在保存：${classContext.currentClassData.className}")
        } else {
            println("正在合并：${classContext.currentClassData.className}")
        }
    }

    private val KEY_MIXIN_FLOW_SRC_FIELDS = "KEY_MIXIN_FLOW_SRC_FIELDS"
    private val KEY_MIXIN_FLOW_SRC_METHODS = "KEY_MIXIN_FLOW_SRC_METHODS"
    private val KEY_MIXIN_FLOW_SRC_INTERFACES = "KEY_MIXIN_FLOW_SRC_INTERFACES"
    override fun visitEnd() {
        super.visitEnd()
        if (isSrcClass) {
            val classNode = this as ClassNode
            DataHelper[KEY_MIXIN_FLOW_SRC_FIELDS] = classNode.fields.filter {
                it.invisibleAnnotations?.find { annotationNode ->
                    annotationNode.desc.contains(FieldMixin::class.simpleName!!)
                } != null
            }
            DataHelper[KEY_MIXIN_FLOW_SRC_METHODS] = classNode.methods.filter {
                it.invisibleAnnotations?.find { annotationNode ->
                    annotationNode.desc.contains(FunctionMixin::class.simpleName!!)
                } != null
            }
            DataHelper[KEY_MIXIN_FLOW_SRC_INTERFACES] = classNode.interfaces
            classNode.accept(nextClassVisitor)
            println("保存：${classContext.currentClassData.className}结束")
        }
        if (canDeal()) {
            getDstClassVisitorList().keys.forEach {
                DataHelper.getT<List<String>>(KEY_MIXIN_FLOW_SRC_INTERFACES)?.let { interfaceList ->
                    InterfaceVisitor(nextClassVisitor, interfaceList).visit(
                        it.version,
                        it.access,
                        it.name,
                        it.signature,
                        it.superName,
                        it.interfaces.toTypedArray()
                    )
                }
                DataHelper.deleteData(KEY_MIXIN_FLOW_SRC_INTERFACES)
                DataHelper.getT<List<FieldNode>>(KEY_MIXIN_FLOW_SRC_FIELDS)?.forEach { node ->
                    node.invisibleAnnotations.remove(node.invisibleAnnotations.find {
                        it.desc.contains(FieldMixin::class.simpleName!!)
                    })
                    node.accept(nextClassVisitor)
                }
                DataHelper.deleteData(KEY_MIXIN_FLOW_SRC_FIELDS)
                DataHelper.getT<List<MethodNode>>(KEY_MIXIN_FLOW_SRC_METHODS)?.forEach { node ->
                    val mergeVisitor = StaticInitMerger("", nextClassVisitor)
                    node.invisibleAnnotations.remove(node.invisibleAnnotations.find {
                        it.desc.contains(FunctionMixin::class.simpleName!!)
                    })
                    node.accept(mergeVisitor)
                }
                DataHelper.deleteData(KEY_MIXIN_FLOW_SRC_METHODS)
            }
            if (!isSrcClass) {
                println("合并：${classContext.currentClassData.className}结束")
            }
        }

    }
}
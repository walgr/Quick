package com.wpf.app.plugin.factory.mixin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

class InterfaceVisitor(
    private val srcClassVisitor: ClassVisitor,
    private val interfaceList: List<String>
): ClassVisitor(Opcodes.ASM7, srcClassVisitor) {

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        val newInterfaceList = mutableListOf<String>()
        interfaces?.let {
            newInterfaceList.addAll(it)
        }
        newInterfaceList.addAll(interfaceList)
        srcClassVisitor.visit(version, access, name, signature, superName, newInterfaceList.toTypedArray())
    }
}
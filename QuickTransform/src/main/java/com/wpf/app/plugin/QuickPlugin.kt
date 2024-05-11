package com.wpf.app.plugin

import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.wpf.app.plugin.factory.mixin.QuickClassMixinAsmFactory
import org.gradle.api.Plugin
import org.gradle.api.Project

class QuickPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.getByType(AndroidComponentsExtension::class.java).apply {
            onVariants {
                it.instrumentation.apply {
                    transformClassesWith(
                        QuickClassMixinAsmFactory::class.java,
                        InstrumentationScope.PROJECT
                    ) {}
                }
            }
        }
    }
}
package com.wpf.app.plugin

import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class QuickPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants {
            it.instrumentation.transformClassesWith(
                QuickAsmFactory::class.java,
                InstrumentationScope.PROJECT
            ) {}
        }
    }

}
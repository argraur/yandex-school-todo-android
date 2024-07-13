package dev.argraur.yandex.todo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import dev.argraur.yandex.todo.gradle.utils.libs

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.devtools.ksp")
            apply("com.google.dagger.hilt.android")
        }

        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("implementation", libs.findLibrary("hilt-navigation-compose").get())
            add("ksp" , libs.findLibrary("hilt-android-compiler").get())
        }
    }
}
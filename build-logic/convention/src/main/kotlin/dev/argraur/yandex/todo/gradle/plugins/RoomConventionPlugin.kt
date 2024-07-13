package dev.argraur.yandex.todo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import dev.argraur.yandex.todo.gradle.utils.libs

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.devtools.ksp")
            apply("androidx.room")
        }

        dependencies {
            add("implementation", libs.findLibrary("room-runtime").get())
            add("implementation", libs.findLibrary("room-ktx").get())
            add("annotationProcessor", libs.findLibrary("room-compiler").get())
            add("ksp" , libs.findLibrary("room-compiler").get())
        }
    }
}
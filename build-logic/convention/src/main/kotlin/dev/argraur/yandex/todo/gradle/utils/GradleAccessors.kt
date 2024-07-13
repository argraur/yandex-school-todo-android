package dev.argraur.yandex.todo.gradle.utils

import com.android.build.gradle.BaseExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun BaseExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
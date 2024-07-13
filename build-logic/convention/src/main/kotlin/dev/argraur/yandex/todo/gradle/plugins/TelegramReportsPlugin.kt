package dev.argraur.yandex.todo.gradle.plugins

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import dev.argraur.yandex.todo.gradle.tasks.configureUploadArtifactTask
import dev.argraur.yandex.todo.gradle.tasks.configureVerifyArtifactSizeTask
import dev.argraur.yandex.todo.gradle.utils.VERSION_CODE
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

class TelegramReportsPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val extension = project.extensions.create("telegramReports", TelegramReportsExtension::class)

        extensions.configure<ApplicationAndroidComponentsExtension> {
            configureVerifyArtifactSizeTask(
                this,
                extension.token,
                extension.chatId,
                extension.skipApkSizeCheck,
                extension.apkSizeLimitMb
            )
            configureUploadArtifactTask(
                this,
                VERSION_CODE,
                extension.token,
                extension.chatId
            )
        }
    }
}

interface TelegramReportsExtension {
    val skipApkSizeCheck: Property<Boolean>
    val apkSizeLimitMb: Property<Int>
    val token: Property<String>
    val chatId: Property<String>
}
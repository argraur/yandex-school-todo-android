package dev.argraur.yandex.todo.gradle.tasks

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.BuiltArtifactsLoader
import com.android.build.gradle.internal.tasks.factory.dependsOn
import dev.argraur.yandex.todo.gradle.telegram.TelegramApi
import dev.argraur.yandex.todo.gradle.utils.APK_NAME_PREFIX
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import java.io.File
import java.time.LocalDateTime

internal fun Project.configureUploadArtifactTask(
    extension: AndroidComponentsExtension<*, *, *>,
    versionCode: Lazy<Int>,
    token: Property<String>,
    chatId: Property<String>
) {
    extension.onVariants { variant ->
        val loader = variant.artifacts.getBuiltArtifactsLoader()
        val artifact = variant.artifacts.get(SingleArtifact.APK)
        tasks.register("upload${variant.name.uppercaseFirstChar()}Artifact", UploadArtifactTask::class.java) {
            apkFolder = artifact
            builtArtifactsLoader = loader
            variantName = variant.name
            this.versionCode = versionCode
            this.token = token
            this.chatId = chatId
        }.dependsOn("verify${variant.name.uppercaseFirstChar()}ArtifactSize")
    }
}

internal abstract class UploadArtifactTask : DefaultTask() {
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputDirectory
    abstract val apkFolder: DirectoryProperty

    @get:Internal
    abstract val builtArtifactsLoader: Property<BuiltArtifactsLoader>

    @get:Input
    abstract val variantName: Property<String>

    @get:Input
    abstract var versionCode: Lazy<Int>

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        val telegramApi = TelegramApi(token.get(), chatId.get())

        val apkSendName = "${APK_NAME_PREFIX}-${variantName.get()}-${versionCode.value}.apk"

        val builtArtifacts = builtArtifactsLoader.get().load(apkFolder.get())
            ?: throw RuntimeException("Cannot load APKs")
        if (builtArtifacts.elements.size != 1)
            throw RuntimeException("Expected one APK !")

        val apk = File(builtArtifacts.elements.single().outputFile)
        println("Uploading ${variantName.get()} artifact to Telegram channel ${chatId.get()}...")
        telegramApi.sendFile(apk, apkSendName, "*Build timestamp* `${LocalDateTime.now()}`\n*File size* `${apk.length() / 1024} KB`")
    }
}
package dev.argraur.yandex.todo.gradle.tasks

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.BuiltArtifactsLoader
import dev.argraur.yandex.todo.gradle.telegram.TelegramApi
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
import org.gradle.work.DisableCachingByDefault
import java.io.File

internal fun Project.configureVerifyArtifactSizeTask(extension: AndroidComponentsExtension<*, *, *>, tokenProperty: Property<String>, chatIdProperty: Property<String>, skipApkSizeCheck: Property<Boolean>, apkSizeLimitMb: Property<Int>) {
    extension.onVariants { variant ->
        val loader = variant.artifacts.getBuiltArtifactsLoader()
        val artifact = variant.artifacts.get(SingleArtifact.APK)

        tasks.register(
            "verify${variant.name.uppercaseFirstChar()}ArtifactSize",
            VerifyArtifactSizeTask::class.java
        ) {
            apkFolder = artifact
            builtArtifactsLoader = loader
            variantName = variant.name
            shouldSkipApkSizeCheck = skipApkSizeCheck
            apkMaxSize = apkSizeLimitMb
            token = tokenProperty
            chatId = chatIdProperty
        }
    }
}

@DisableCachingByDefault(because = "Prints output")
internal abstract class VerifyArtifactSizeTask : DefaultTask() {
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputDirectory
    abstract val apkFolder: DirectoryProperty

    @get:Internal
    abstract val builtArtifactsLoader: Property<BuiltArtifactsLoader>

    @get:Input
    abstract val variantName: Property<String>

    @get:Input
    abstract val shouldSkipApkSizeCheck: Property<Boolean>

    @get:Input
    abstract val apkMaxSize: Property<Int>

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun taskAction() {
        if (shouldSkipApkSizeCheck.get()) {
            println("Skipping artifact size verification")
            return
        }

        val telegramApi = TelegramApi(token.get(), chatId.get())

        val builtArtifacts = builtArtifactsLoader.get().load(apkFolder.get())
            ?: throw RuntimeException("Cannot load APKs")
        if (builtArtifacts.elements.size != 1)
            throw RuntimeException("Expected one APK !")

        val apk = File(builtArtifacts.elements.single().outputFile)

        val size = apk.length() / 1024 / 1024
        if (size > apkMaxSize.get()) {
            telegramApi.sendMessage("APK size exceeded limit of ${apkMaxSize.get()} MB. APK size: $size MB. Bailing out!")
            throw IllegalStateException("APK size cannot exceed ${apkMaxSize.get()} MB! APK size: $size MB")
        }
        println("Passed ${variantName.get()} artifact size verification")
    }
}
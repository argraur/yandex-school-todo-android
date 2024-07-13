plugins {
    alias(libs.plugins.todo.android.application.compose)
    alias(libs.plugins.todo.hilt)
    alias(libs.plugins.todo.telegram.reports)
}

android {
    defaultConfig {
        applicationId = "dev.argraur.yandex.todo"
        versionCode = 1
        versionName = "1.0"
    }
}

telegramReports {
    token = providers.environmentVariable("TGBOT_TOKEN")
    chatId = providers.environmentVariable("TGBOT_CHATID")
    skipApkSizeCheck = false
    apkSizeLimitMb = 24
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":ui"))
    implementation(project(":data"))
    implementation(project(":domain"))
}
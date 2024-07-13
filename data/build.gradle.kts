plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
    alias(libs.plugins.todo.room)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.secrets.gradle.plugin)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "dev.argraur.yandex.todo.data"
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        all {
            buildConfigField("String", "SERVER_BASE_URL", property("serverBaseUrl").toString())
            buildConfigField("String", "SERVER_TOKEN", "\"${providers.environmentVariable("SERVER_TOKEN").get()}\"")
        }
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktorfit.lib)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.datastore.preferences)
    implementation(project(":domain"))
    api(project(":core"))
    implementation(project(":ui"))
}
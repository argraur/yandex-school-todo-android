plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.android.library.compose)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "dev.argraur.yandex.todo.ui"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(kotlin("reflect"))
}
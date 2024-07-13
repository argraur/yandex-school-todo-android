plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "dev.argraur.yandex.todo.domain"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}
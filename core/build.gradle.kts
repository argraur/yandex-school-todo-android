plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "dev.argraur.yandex.todo.core"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}
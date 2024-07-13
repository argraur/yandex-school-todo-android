import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "dev.argraur.yandex.todo.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.agp)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(libs.telegram)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "todo.android.application.compose"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.AndroidApplicationComposeConventionPlugin"
        }
    }
    plugins {
        register("androidLibrary") {
            id = "todo.android.library"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.AndroidLibraryConventionPlugin"
        }
    }
    plugins {
        register("androidLibraryCompose") {
            id = "todo.android.library.compose"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.AndroidLibraryComposeConventionPlugin"
        }
    }
    plugins {
        register("hilt") {
            id = "todo.hilt"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.HiltConventionPlugin"
        }
    }
    plugins {
        register("room") {
            id = "todo.room"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.RoomConventionPlugin"
        }
    }
    plugins {
        register("telegramReports") {
            id = "todo.telegram.reports"
            implementationClass = "dev.argraur.yandex.todo.gradle.plugins.TelegramReportsPlugin"
        }
    }
}
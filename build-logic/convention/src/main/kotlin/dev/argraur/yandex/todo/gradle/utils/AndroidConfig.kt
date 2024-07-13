package dev.argraur.yandex.todo.gradle.utils

import com.android.build.gradle.BaseExtension

fun BaseExtension.baseAndroidConfig() {
    namespace = NAMESPACE
    compileSdkVersion(COMPILE_SDK)

    defaultConfig {
        minSdk = MIN_SDK

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    
    buildTypes{
        getByName("release") {
            isMinifyEnabled = ENABLE_MINIFY_FOR_RELEASES
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JAVA_VERSION_COMPAT
        targetCompatibility = JAVA_VERSION_COMPAT
    }

    kotlinOptions {
        jvmTarget = KOTLIN_JVM_TARGET
    }
}
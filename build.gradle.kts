// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.plugin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
    alias(libs.plugins.detekt)
}
package dev.argraur.yandex.todo

import android.app.Application
import dagger.Component

@Component
interface AppComponent

class App : Application() {
    val appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }
}
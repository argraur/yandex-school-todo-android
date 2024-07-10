package dev.argraur.yandex.todo.data.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.argraur.yandex.todo.data.database.TodoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext applicationContext: Context): TodoDatabase =
        Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "todo-database")
            .build()
}
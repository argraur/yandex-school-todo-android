package dev.argraur.yandex.todo.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.argraur.yandex.todo.data.database.TodoLocalDataSource
import dev.argraur.yandex.todo.data.metadata.MetadataStorage
import dev.argraur.yandex.todo.data.network.TodoRemoteDataSource
import dev.argraur.yandex.todo.data.repository.TodoItemsRepositoryImpl
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideTodoItemsRepository(
        localDataSource: TodoLocalDataSource,
        remoteDataSource: TodoRemoteDataSource
    ): TodoItemsRepository = TodoItemsRepositoryImpl(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideMetadataStorage(@ApplicationContext applicationContext: Context) =
        MetadataStorage(applicationContext)
}
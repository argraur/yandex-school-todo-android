package dev.argraur.yandex.todo.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dev.argraur.yandex.todo.data.repository.TodoItemsRepositoryImpl
import dev.argraur.yandex.todo.data.source.MockTodoItemsDataSource
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideMockTodoItemsDataSource() = MockTodoItemsDataSource()

    @Singleton
    @Provides
    fun provideTodoItemsRepository(
        mockTodoItemsDataSource: MockTodoItemsDataSource
    ): TodoItemsRepository = TodoItemsRepositoryImpl(mockTodoItemsDataSource)
}
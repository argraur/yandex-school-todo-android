package dev.argraur.yandex.todo.data.workers

/*

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// не работает, womp womp
// почему-то HiltWorkerFactory не инжектится в Application
// может дело в KSP?


@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    private val todoItemsRepository: TodoItemsRepository,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = runCatching {
        withContext(Dispatchers.IO) {
            todoItemsRepository.refreshRepository()
        }
        Result.success()
    }.getOrElse {
        Result.retry()
    }
}

*/
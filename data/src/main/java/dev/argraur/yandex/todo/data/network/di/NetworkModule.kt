package dev.argraur.yandex.todo.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.ktorfit
import dev.argraur.yandex.todo.data.BuildConfig
import dev.argraur.yandex.todo.data.metadata.MetadataStorage
import dev.argraur.yandex.todo.data.network.TodoRemoteDataSource
import dev.argraur.yandex.todo.data.network.TodoService
import dev.argraur.yandex.todo.data.network.createTodoService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val MAX_RETRIES = 5
    private const val RETRY_INTERVAL = 1000L

    @Singleton
    @Provides
    fun provideTodoService(metadataStorage: MetadataStorage) = ktorfit {
        baseUrl(BuildConfig.SERVER_BASE_URL)
        httpClient(HttpClient {
            install(ContentNegotiation) {
                json( Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(MAX_RETRIES)
                delayMillis { RETRY_INTERVAL }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = metadataStorage.getToken()
                        BearerTokens(token, token)
                    }
                }
            }
        })
    }.createTodoService()

    @Singleton
    @Provides
    fun provideTodoRemoteDataSource(todoService: TodoService, metadataStorage: MetadataStorage) =
        TodoRemoteDataSource(todoService, metadataStorage)
}
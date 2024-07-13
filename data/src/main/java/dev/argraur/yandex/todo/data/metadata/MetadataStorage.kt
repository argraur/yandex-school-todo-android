package dev.argraur.yandex.todo.data.metadata

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.argraur.yandex.todo.data.BuildConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val TOKEN: String = BuildConfig.SERVER_TOKEN
const val DATASTORE_NAME = "metadata"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class MetadataStorage @Inject constructor(
    private val applicationContext: Context
) {
    private val revisionKey = intPreferencesKey("revision")
    private val deviceIdKey = stringPreferencesKey("device_id")
    private val tokenKey = stringPreferencesKey("token")

    suspend fun getRevision(): Int =
        applicationContext.dataStore.data.map { it[revisionKey] ?: 0 }.first()

    suspend fun getDeviceId(): String =
        applicationContext.dataStore.data.map { it[deviceIdKey] ?: "" }.first()

    suspend fun getToken(): String =
        applicationContext.dataStore.data.map { it[tokenKey] ?: TOKEN }.first()

    suspend fun setToken(token: String) {
        applicationContext.dataStore.edit {
            it[tokenKey] = token
        }
    }

    suspend fun setRevision(revision: Int) {
        applicationContext.dataStore.edit {
            it[revisionKey] = revision
        }
    }

    suspend fun setDeviceId(deviceId: String) {
        applicationContext.dataStore.edit {
            it[deviceIdKey] = deviceId
        }
    }
}
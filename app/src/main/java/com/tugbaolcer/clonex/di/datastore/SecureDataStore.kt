package com.tugbaolcer.clonex.di.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.config.TinkConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SecureDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences>

    private val KEY_SESSION_ID = stringPreferencesKey("session_id")

    private val cipher: AesCipher

    init {
        // Tink register
        TinkConfig.register()

        // Keyset
        val keysetHandle = EncryptionKeyManager.getOrCreateKeysetHandle(context)
        val aead = keysetHandle.getPrimitive(Aead::class.java)

        cipher = AesCipher(aead)

        dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                File(context.filesDir, "secure_prefs.preferences_pb")
            }
        )
    }

    suspend fun saveSessionId(sessionId: String) {
        val encrypted = cipher.encrypt(sessionId)
        dataStore.edit { it[KEY_SESSION_ID] = encrypted }
    }

    val sessionIdFlow: Flow<String?> = dataStore.data
        .map { prefs ->
            prefs[KEY_SESSION_ID]?.let { cipher.decrypt(it) }
        }

    suspend fun getSessionId(): String? = sessionIdFlow.firstOrNull()

    suspend fun clearSessionId() {
        dataStore.edit { it.remove(KEY_SESSION_ID) }
    }
}

package com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SimplePreferencesManagerImpl(
    private val datastore: DataStore<Preferences>,
) : SimplePreferencesManager {

    override suspend fun save(key: String, value: String) {
        datastore.updateData {
            it.toMutablePreferences().apply {
                this[stringPreferencesKey(key)] = value
            }
        }
    }

    override fun get(key: String): Flow<String?> = datastore.data.map {
        it[stringPreferencesKey(key)]
    }
}
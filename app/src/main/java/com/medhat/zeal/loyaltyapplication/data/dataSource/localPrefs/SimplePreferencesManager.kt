package com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs

import kotlinx.coroutines.flow.Flow

interface SimplePreferencesManager {
    suspend fun save(key: String, value: String)
    fun get(key: String): Flow<String?>
}
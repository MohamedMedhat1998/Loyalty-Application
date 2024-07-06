package com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs

interface SimplePreferencesManager {
    fun save(key: String, value: String)
    fun get(key: String): String?
}
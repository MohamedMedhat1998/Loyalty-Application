package com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs

import android.content.SharedPreferences

class SimplePreferencesManagerImpl(
    private val preferences: SharedPreferences,
) : SimplePreferencesManager {

    override fun save(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun get(key: String): String? = preferences.getString(key, null)
}
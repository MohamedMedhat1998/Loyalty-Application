package com.medhat.zeal.loyaltyapplication.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManager
import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManagerImpl
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepo
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepoImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(androidApplication())
    }
    factory<SimplePreferencesManager> {
        SimplePreferencesManagerImpl(get())
    }
    factory<DiscountRepo> {
        DiscountRepoImpl(get())
    }
}
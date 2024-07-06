package com.medhat.zeal.loyaltyapplication.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManager
import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManagerImpl
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepo
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepoImpl
import com.medhat.zeal.loyaltyapplication.utils.dataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<DataStore<Preferences>> {
        androidApplication().dataStore
    }
    factory<SimplePreferencesManager> {
        SimplePreferencesManagerImpl(get())
    }
    factory<DiscountRepo> {
        DiscountRepoImpl(get())
    }
}
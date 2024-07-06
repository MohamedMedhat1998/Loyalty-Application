package com.medhat.zeal.loyaltyapplication.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.AppDataBase
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.CardDao
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.DB_NAME
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
    single<AppDataBase> {
        Room.databaseBuilder(androidApplication(), AppDataBase::class.java, DB_NAME).build()
    }
    single<CardDao> {
        val appDataBase = get<AppDataBase>(AppDataBase::class)
        appDataBase.cardDao()
    }
    factory<SimplePreferencesManager> {
        SimplePreferencesManagerImpl(get())
    }
    factory<DiscountRepo> {
        DiscountRepoImpl(get())
    }
}
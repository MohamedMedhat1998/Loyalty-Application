package com.medhat.zeal.loyaltyapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase

const val DB_NAME = "loyalty_db"

@Database(entities = [Card::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}
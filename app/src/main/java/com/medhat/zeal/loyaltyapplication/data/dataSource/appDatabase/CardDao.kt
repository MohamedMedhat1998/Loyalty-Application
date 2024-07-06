package com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CardDao {

    @Query("SELECT * FROM Card WHERE cardNumber = :cardNumber")
    fun getCard(cardNumber: String): Card?

    @Upsert
    fun upsertCard(card: Card)
}
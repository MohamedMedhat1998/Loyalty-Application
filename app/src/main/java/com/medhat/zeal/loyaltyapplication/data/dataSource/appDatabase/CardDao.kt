package com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {

    @Query("SELECT * FROM Card WHERE cardNumber = :cardNumber")
    fun getCard(cardNumber: String): Card

    @Query("SELECT purchasesCount FROM Card WHERE cardNumber = :cardNumber")
    fun getPurchasesCountFirCard(cardNumber: String): Int

    @Insert
    fun insertCard(card: Card)
}
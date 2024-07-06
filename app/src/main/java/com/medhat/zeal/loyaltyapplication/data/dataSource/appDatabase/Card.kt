package com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey val cardNumber: String,
    val purchasesCount: Int
)
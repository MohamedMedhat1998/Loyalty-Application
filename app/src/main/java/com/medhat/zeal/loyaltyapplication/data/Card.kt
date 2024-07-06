package com.medhat.zeal.loyaltyapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey val cardNumber: String,
    val purchasesCount: Int
)
package com.medhat.zeal.loyaltyapplication.data.repo

import kotlinx.coroutines.flow.Flow

interface DiscountRepo {
    suspend fun saveDiscount(discountValue: Long)
    fun getDiscount(): Flow<Long?>
}
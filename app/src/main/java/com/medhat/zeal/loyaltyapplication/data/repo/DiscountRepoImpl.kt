package com.medhat.zeal.loyaltyapplication.data.repo

import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DISCOUNT_KEY = "discount"

class DiscountRepoImpl(
    private val simplePreferencesManager: SimplePreferencesManager
) : DiscountRepo {

    override suspend fun saveDiscount(discountValue: Long) {
        simplePreferencesManager.save(DISCOUNT_KEY, discountValue.toString())
    }

    override fun getDiscount(): Flow<Long?> = simplePreferencesManager.get(DISCOUNT_KEY).map {
        it?.toLongOrNull()
    }
}
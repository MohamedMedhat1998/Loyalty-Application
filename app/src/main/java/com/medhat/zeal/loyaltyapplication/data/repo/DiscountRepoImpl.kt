package com.medhat.zeal.loyaltyapplication.data.repo

import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManager

private const val DISCOUNT_KEY = "discount"

class DiscountRepoImpl(
    private val simplePreferencesManager: SimplePreferencesManager
) : DiscountRepo {

    override fun saveDiscount(discountValue: Long) {
        simplePreferencesManager.save(DISCOUNT_KEY, discountValue.toString())
    }

    override fun getDiscount(): Long? = simplePreferencesManager.get(DISCOUNT_KEY)?.toLongOrNull()
}
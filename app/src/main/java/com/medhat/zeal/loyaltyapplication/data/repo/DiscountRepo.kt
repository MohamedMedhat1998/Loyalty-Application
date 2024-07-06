package com.medhat.zeal.loyaltyapplication.data.repo

interface DiscountRepo {
    fun saveDiscount(discountValue: Long)
    fun getDiscount(): Long?
}
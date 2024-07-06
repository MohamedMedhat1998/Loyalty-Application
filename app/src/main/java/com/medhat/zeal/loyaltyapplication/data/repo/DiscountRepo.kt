package com.medhat.zeal.loyaltyapplication.data.repo

interface DiscountRepo {
    fun saveDiscount(discountValue: Float)
    fun getDiscount(): Float?
}
package com.medhat.zeal.loyaltyapplication.provider.utils

object Contract {
    /**
     * The authority of the loyalty content provider.
     */
    const val AUTHORITY = "com.medhat.zeal.loyaltyapplication.provider"

    /**
     * The path for the discount eligibility check.
     */
    const val DISCOUNT_ELIGIBILITY_CHECK_PATH = "card/is_discount_eligible/#"

    /**
     * Contains all possible columns that can be found in the result cursor object.
     */
    object CursorColumns {
        /**
         * Represents the column that tells whether the card is eligible for a discount or not.
         */
        const val IS_ELIGIBLE_FOR_DISCOUNT = "is_eligible_for_discount"
    }

    const val DISCOUNT_ELIGIBLE = 1
    const val NOT_DISCOUNT_ELIGIBLE = 0
}
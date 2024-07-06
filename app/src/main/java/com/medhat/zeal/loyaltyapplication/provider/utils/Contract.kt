package com.medhat.zeal.loyaltyapplication.provider.utils

object Contract {
    /**
     * The authority of the loyalty content provider.
     */
    const val AUTHORITY = "com.medhat.zeal.loyaltyapplication.provider"

    /**
     * The path for the discount eligibility check.
     */
//    const val DISCOUNT_ELIGIBILITY_CHECK_PATH = "card/is_discount_eligible/#"

    /**
     * The path for the amount after the discount is applied.
     */
    const val AMOUNT_AFTER_DISCOUNT_PATH = "amount_after_discount"

    /**
     * Contains all possible columns that can be found in the result cursor object.
     */
    object CursorColumns {
        /**
         * Represents the column that tells whether the card is eligible for a discount or not.
         */
//        const val IS_ELIGIBLE_FOR_DISCOUNT = "is_eligible_for_discount"

        /**
         * Represents the column that hold the adjusted price after applying the discount.
         */
        const val ADJUSTED_AMOUNT = "adjusted_amount"
    }

    object QueryParameters {
        const val ORIGINAL_AMOUNT = "original_amount"
    }

//    const val DISCOUNT_ELIGIBLE = 1
//    const val NOT_DISCOUNT_ELIGIBLE = 0
}
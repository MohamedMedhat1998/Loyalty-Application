package com.medhat.zeal.loyaltyapplication.provider.utils

object Contract {
    /**
     * The authority of the loyalty content provider.
     */
    const val AUTHORITY = "com.medhat.zeal.loyaltyapplication.provider"

    /**
     * The path for the amount after the discount is applied.
     */
    const val AMOUNT_AFTER_DISCOUNT_PATH = "amount_after_discount"

    /**
     * The path to increase specific card's purchases count
     */
    const val INCREASE_CARD_PURCHASES_COUNT = "increase_purchases_count"

    /**
     * Contains all possible columns that can be found in the result cursor object.
     */
    object CursorColumns {
        /**
         * Represents the column that holds the adjusted price after applying the discount.
         */
        const val ADJUSTED_AMOUNT = "adjusted_amount"

        /**
         * Represents the column that holds whether the operation succeeded or not.
         */
        const val RESULT = "result"
    }

    object QueryParameters {
        const val ORIGINAL_AMOUNT = "original_amount"
        const val CARD_NUMBER = "card_number"
    }

    const val OP_SUCCESS = 0
    const val OP_FAILED = 1
}
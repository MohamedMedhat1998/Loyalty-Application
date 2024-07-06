package com.medhat.zeal.loyaltyapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.preference.PreferenceManager
import com.medhat.zeal.loyaltyapplication.data.dataSource.localPrefs.SimplePreferencesManagerImpl
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepo
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepoImpl
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract.QueryParameters.ORIGINAL_AMOUNT
import com.medhat.zeal.loyaltyapplication.provider.utils.RecognizedCodes
import kotlin.math.max

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
//    addURI(
//        Contract.AUTHORITY,
//        Contract.DISCOUNT_ELIGIBILITY_CHECK_PATH,
//        RecognizedCodes.DISCOUNT_ELIGIBILITY_CHECK
//    )
    addURI(
        Contract.AUTHORITY,
        Contract.AMOUNT_AFTER_DISCOUNT_PATH,
        RecognizedCodes.AMOUNT_AFTER_DISCOUNT
    )
}

// TODO take thread-safety into consideration
class LoyaltyProvider : ContentProvider() {

    //    private lateinit var appDataBase: AppDataBase
//    private var cardDao: CardDao? = null
    private lateinit var discountRepo: DiscountRepo

    override fun onCreate(): Boolean {
        val currentContext = context
//        return if (currentContext != null) {
//            appDataBase =
//                Room.databaseBuilder(currentContext, AppDataBase::class.java, DB_NAME).build()
//            cardDao = appDataBase.cardDao()
//            true
//        } else {
//            false
//        }
        return if (currentContext != null) {
            discountRepo = DiscountRepoImpl(
                SimplePreferencesManagerImpl(
                    PreferenceManager.getDefaultSharedPreferences(
                        currentContext
                    )
                )
            )
            true
        } else {
            false
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        when (sUriMatcher.match(uri)) {
//            RecognizedCodes.DISCOUNT_ELIGIBILITY_CHECK -> {
//                val cardNumber = ContentUris.parseId(uri).toString()
//                return getEligibilityResultCursor(cardNumber)
//            }
            RecognizedCodes.AMOUNT_AFTER_DISCOUNT -> {

                val originalAmount = uri.getQueryParameter(ORIGINAL_AMOUNT)
                    ?: throw IllegalArgumentException("Missing $ORIGINAL_AMOUNT parameter")

                val numericAmount = originalAmount.toLongOrNull()
                    ?: throw IllegalArgumentException("Invalid $ORIGINAL_AMOUNT parameter, make sure to pass a number")

                return getAmountAfterDiscountCursor(numericAmount)
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    /**
     * @return A [Cursor] object that has a single column that contains the adjusted amount after
     * discount.
     */
    private fun getAmountAfterDiscountCursor(originalAmount: Long): Cursor {
        val discount = discountRepo.getDiscount()
        val updatedPrice = if (discount == null) {
            originalAmount
        } else {
            max(originalAmount - discount, 0)
        }
        val cursor = MatrixCursor(
            arrayOf(
                Contract.CursorColumns.ADJUSTED_AMOUNT
            )
        )
        cursor.addRow(
            arrayOf(
                updatedPrice
            )
        )
        return cursor
    }

    /**
     * @return A [Cursor] object that contains only a single column that tells whether the card is
     * eligible for a discount or not.
     */
//    private fun getEligibilityResultCursor(cardNumber: String): Cursor {
//        println("XYZ: cardNumber: $cardNumber")
//        val cardPurchasesCount = cardDao?.getCard(cardNumber)?.purchasesCount ?: 0
//        val cursor = MatrixCursor(
//            arrayOf(Contract.CursorColumns.IS_ELIGIBLE_FOR_DISCOUNT)
//        )
//        cursor.addRow(
//            arrayOf(
//                // IS_ELIGIBLE_FOR_DISCOUNT
//                if (cardPurchasesCount >= Constants.DISCOUNT_ELIGIBILITY_PURCHASE_COUNT_THRESHOLD) {
//                    DISCOUNT_ELIGIBLE
//                } else {
//                    NOT_DISCOUNT_ELIGIBLE
//                }
//            )
//        )
//        return cursor
//    }
}
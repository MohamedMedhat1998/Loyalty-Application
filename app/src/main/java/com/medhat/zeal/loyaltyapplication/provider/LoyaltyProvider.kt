package com.medhat.zeal.loyaltyapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.Card
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.CardDao
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepo
import com.medhat.zeal.loyaltyapplication.di.dataModule
import com.medhat.zeal.loyaltyapplication.di.viewModelModule
import com.medhat.zeal.loyaltyapplication.provider.utils.Constants
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract.QueryParameters.CARD_NUMBER
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract.QueryParameters.ORIGINAL_AMOUNT
import com.medhat.zeal.loyaltyapplication.provider.utils.OpCodes
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.math.max

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(
        Contract.AUTHORITY,
        Contract.AMOUNT_AFTER_DISCOUNT_PATH,
        OpCodes.AMOUNT_AFTER_DISCOUNT
    )
    addURI(
        Contract.AUTHORITY,
        Contract.INCREASE_CARD_PURCHASES_COUNT,
        OpCodes.INCREASE_PURCHASES_COUNT
    )
}

class LoyaltyProvider : ContentProvider() {

    private val cardDao: CardDao by inject()
    private val discountRepo: DiscountRepo by inject()

    override fun onCreate(): Boolean {
        startKoin {
            androidLogger()
            androidContext(context!!)
            modules(dataModule, viewModelModule)
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        when (sUriMatcher.match(uri)) {

            OpCodes.AMOUNT_AFTER_DISCOUNT -> {
                val originalAmount = uri.getQueryParameter(ORIGINAL_AMOUNT)
                    ?: throw IllegalArgumentException("Missing $ORIGINAL_AMOUNT parameter")

                val cardNumber = uri.getQueryParameter(CARD_NUMBER)
                    ?: throw IllegalArgumentException("Missing $CARD_NUMBER parameter")

                val numericAmount = originalAmount.toFloatOrNull()
                    ?: throw IllegalArgumentException("Invalid $ORIGINAL_AMOUNT parameter, make sure to pass a number")

                return getAmountAfterDiscountCursor(
                    cardNumber = cardNumber,
                    originalAmount = numericAmount
                )
            }

            OpCodes.INCREASE_PURCHASES_COUNT -> {
                val cardNumber = uri.getQueryParameter(CARD_NUMBER)
                    ?: throw IllegalArgumentException("Missing $CARD_NUMBER parameter")

                return getIncreaseCardPurchasesCountCursor(cardNumber)
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    /**
     * @return A [Cursor] object that has a single column that contains the adjusted amount after
     * discount.
     */
    private fun getAmountAfterDiscountCursor(cardNumber: String, originalAmount: Float): Cursor {
        val discount = discountRepo.getDiscount()

        val updatedAmount = if (isEligibleForDiscount(cardNumber) && discount != null) {
            max(originalAmount - discount, 0f)
        } else {
            originalAmount
        }

        return MatrixCursor(
            arrayOf(
                Contract.CursorColumns.ADJUSTED_AMOUNT
            )
        ).apply {
            addRow(
                arrayOf(
                    updatedAmount
                )
            )
        }
    }

    private fun getIncreaseCardPurchasesCountCursor(cardNumber: String): Cursor {
        val card = cardDao.getCard(cardNumber) ?: Card(cardNumber, 0)
        cardDao.upsertCard(card.copy(purchasesCount = card.purchasesCount + 1))
        return MatrixCursor(
            arrayOf(
                Contract.CursorColumns.RESULT
            )
        ).apply {
            addRow(
                arrayOf(
                    Contract.OP_SUCCESS
                )
            )
        }
    }

    private fun isEligibleForDiscount(cardNumber: String): Boolean {
        val purchasesCount = cardDao.getCard(cardNumber)?.purchasesCount ?: 0
        return purchasesCount >= Constants.DISCOUNT_ELIGIBILITY_PURCHASE_COUNT_THRESHOLD
    }
}
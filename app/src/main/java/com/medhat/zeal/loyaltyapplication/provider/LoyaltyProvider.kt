package com.medhat.zeal.loyaltyapplication.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.room.Room
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.AppDataBase
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.CardDao
import com.medhat.zeal.loyaltyapplication.data.dataSource.appDatabase.DB_NAME
import com.medhat.zeal.loyaltyapplication.provider.utils.Constants
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract.DISCOUNT_ELIGIBLE
import com.medhat.zeal.loyaltyapplication.provider.utils.Contract.NOT_DISCOUNT_ELIGIBLE
import com.medhat.zeal.loyaltyapplication.provider.utils.RecognizedCodes

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(
        Contract.AUTHORITY,
        Contract.DISCOUNT_ELIGIBILITY_CHECK_PATH,
        RecognizedCodes.DISCOUNT_ELIGIBILITY_CHECK
    )
}

// TODO take thread-safety into consideration
class LoyaltyProvider : ContentProvider() {

    private lateinit var appDataBase: AppDataBase
    private var cardDao: CardDao? = null

    override fun onCreate(): Boolean {
        val currentContext = context
        return if (currentContext != null) {
            appDataBase =
                Room.databaseBuilder(currentContext, AppDataBase::class.java, DB_NAME).build()
            cardDao = appDataBase.cardDao()
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
    ): Cursor? {
        when (sUriMatcher.match(uri)) {
            RecognizedCodes.DISCOUNT_ELIGIBILITY_CHECK -> {
                val cardNumber = ContentUris.parseId(uri).toString()
                return getEligibilityResultCursor(cardNumber)
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
     * @return A [Cursor] object that contains only a single column that tells whether the card is
     * eligible for a discount or not.
     */
    private fun getEligibilityResultCursor(cardNumber: String): Cursor {
        println("XYZ: cardNumber: $cardNumber")
        val cardPurchasesCount = cardDao?.getCard(cardNumber)?.purchasesCount ?: 0
        val cursor = MatrixCursor(
            arrayOf(Contract.CursorColumns.IS_ELIGIBLE_FOR_DISCOUNT)
        )
        cursor.addRow(
            arrayOf(
                // IS_ELIGIBLE_FOR_DISCOUNT
                if (cardPurchasesCount >= Constants.DISCOUNT_ELIGIBILITY_PURCHASE_COUNT_THRESHOLD) {
                    DISCOUNT_ELIGIBLE
                } else {
                    NOT_DISCOUNT_ELIGIBLE
                }
            )
        )
        return cursor
    }
}
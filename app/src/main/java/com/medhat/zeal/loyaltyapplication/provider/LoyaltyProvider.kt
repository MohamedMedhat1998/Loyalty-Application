package com.medhat.zeal.loyaltyapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.medhat.zeal.loyaltyapplication.provider.Contract.AUTHORITY

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(AUTHORITY, Contract.ALL_CARDS, RecognizedCodes.ALL_CARDS)
    addURI(AUTHORITY, Contract.SINGLE_CARD, RecognizedCodes.SINGLE_CARD)
}

class LoyaltyProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // TODO take thread-safety into consideration
        when (sUriMatcher.match(uri)) {
            RecognizedCodes.ALL_CARDS -> {

            }
            RecognizedCodes.SINGLE_CARD -> {

            }
            else -> {

            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}
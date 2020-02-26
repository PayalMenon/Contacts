package com.example.mycontacts.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.paging.PositionalDataSource
import com.example.mycontacts.model.Contact
import javax.inject.Inject

class ContactsDataSource @Inject constructor(private val contentResolver: ContentResolver) :
    PositionalDataSource<Contact>() {

    companion object {
        val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP,
            ContactsContract.Contacts.STARRED
        )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Contact>) {
        callback.onResult(fetchContacts(params.startPosition, params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Contact>) {
        callback.onResult(fetchContacts(params.requestedStartPosition, params.requestedLoadSize), 0)
    }

    private fun fetchContacts(offset: Int, limit: Int): List<Contact> {
        val queryCursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC LIMIT " + limit + " OFFSET " + offset
        )

        val contacts: MutableList<Contact> = mutableListOf()
        queryCursor?.let { cursor: Cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val contact = Contact(
                    cursor.getLong(cursor.getColumnIndex(PROJECTION[0])),
                    cursor.getString(cursor.getColumnIndex(PROJECTION[1])),
                    cursor.getString(cursor.getColumnIndex(PROJECTION[2])),
                    cursor.getString(cursor.getColumnIndex(PROJECTION[3])),
                    cursor.getString(cursor.getColumnIndex(PROJECTION[4]))
                )
                contacts.add(contact)
                cursor.moveToNext()
            }
        }
        queryCursor?.close()
        return contacts.sortedByDescending { it.isFavorite }
    }
}
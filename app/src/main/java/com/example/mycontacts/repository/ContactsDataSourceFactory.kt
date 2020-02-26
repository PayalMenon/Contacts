package com.example.mycontacts.repository

import androidx.paging.DataSource
import com.example.mycontacts.model.Contact
import javax.inject.Inject


class ContactsDataSourceFactory @Inject constructor(val contactsDataSource: ContactsDataSource) : DataSource.Factory<Int, Contact>() {

    override fun create(): DataSource<Int, Contact> {
        return contactsDataSource
    }
}
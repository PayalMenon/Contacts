package com.example.mycontacts.dagger.modules

import android.content.ContentResolver
import android.content.Context
import com.example.mycontacts.repository.ContactsDataSource
import com.example.mycontacts.repository.ContactsDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideContactsDataSource(contentResolver: ContentResolver): ContactsDataSource {
        return ContactsDataSource(contentResolver)
    }

    @Singleton
    @Provides
    fun provideContentResolver(context: Context): ContentResolver {
        return context.applicationContext.contentResolver
    }

    @Provides
    @Singleton
    fun provideContactDataFactory(contactsDataSource: ContactsDataSource): ContactsDataSourceFactory {
        return ContactsDataSourceFactory(contactsDataSource)
    }
}
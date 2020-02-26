package com.example.mycontacts.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mycontacts.model.Contact
import com.example.mycontacts.repository.ContactsDataSourceFactory
import com.example.mycontacts.util.Event
import javax.inject.Inject

class ContactsViewModel @Inject constructor(val contactsDataSourceFactory: ContactsDataSourceFactory,
                                            application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PAGE_SIZE = 25
    }

    // this livedata needs to be observed for the factory create to be called
    var contacts: LiveData<PagedList<Contact>>? = null

    // check if needed to show the permissions dialog to the user
    fun initialize(androidVersion : Int) {
        if(showPermissionDialog(androidVersion)) {
           _showPermissionDilog.value = Event(Unit)
        } else {
            _showLoading.postValue(Event(Unit))
            fetchContacts()
        }
    }

    fun showPermissionDialog(androidVersion : Int) : Boolean {
        return if(androidVersion < android.os.Build.VERSION_CODES.M) {
            false
        } else {
            !(ContextCompat.checkSelfPermission(getApplication<Application>().applicationContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        }
    }

    fun fetchContacts() {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        contacts = LivePagedListBuilder(contactsDataSourceFactory, pageConfig).build()
        _showListFragment.postValue(Event(Unit))
    }


    fun getContactsList() : LiveData<PagedList<Contact>>? {
        return contacts
    }

    fun permissionNotRequestedBefore() {
        _requestPermission.value = Event(Unit)
    }

    fun permissionDeniedBefore() {
        _showMoreInfoDialog.value = Event(Unit)
    }

    fun onPermissionGranted() {
        fetchContacts()
    }

    fun onPermissionDenied() {
        _showPermissionFragment.value = Event(Unit)
    }

    fun onContactItemClicked(contact : Contact) {
        _openMessagingApp.postValue(Event(contact.name))
    }
    // Live Events
    private val _showLoading = MutableLiveData<Event<Unit>>()
    val showLoading: LiveData<Event<Unit>>
        get() = _showLoading

    private val _showPermissionDilog = MutableLiveData<Event<Unit>>()
    val showPermissionDilog : LiveData<Event<Unit>>
        get() = _showPermissionDilog

    private val _requestPermission = MutableLiveData<Event<Unit>>()
    val requestPermission : LiveData<Event<Unit>>
        get() = _requestPermission

    private val _showMoreInfoDialog = MutableLiveData<Event<Unit>>()
    val showMoreInfoDialog : LiveData<Event<Unit>>
        get() = _showMoreInfoDialog

    private val _showPermissionFragment = MutableLiveData<Event<Unit>>()
    val showPermissionFragment : LiveData<Event<Unit>>
        get() = _showPermissionFragment

    private val _showListFragment = MutableLiveData<Event<Unit>>()
    val showListFragment : LiveData<Event<Unit>>
        get() = _showListFragment

    private val _openMessagingApp = MutableLiveData<Event<String>>()
    val openMessagingApp : LiveData<Event<String>>
        get() = _openMessagingApp
}
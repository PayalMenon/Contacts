package com.example.mycontacts.ui

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.example.mycontacts.R
import com.example.mycontacts.model.Contact
import com.example.mycontacts.util.EventObserver
import com.example.mycontacts.viewmodels.ContactsViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ContactsActivity : DaggerAppCompatActivity() {

    companion object {
        private const val LOADING = "loading"
        private const val LIST = "list"
        private const val PERMISSION = "permission"
        private const val CONTACT_PERMISSION_REQUEST_CODE = 511
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var contactsViewModel: ContactsViewModel

    private var contactsList: PagedList<Contact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        contactsViewModel.initialize(android.os.Build.VERSION.SDK_INT)

        setObservers()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CONTACT_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactsViewModel.onPermissionGranted()
                } else {
                    contactsViewModel.onPermissionDenied()
                }
            }
        }
    }

    private fun setObservers() {
        contactsViewModel.showLoading.observe(this, EventObserver {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, LoadingFragment(), LOADING).commit()
        })

        contactsViewModel.contacts?.observe(this, Observer { list ->
            contactsList = list
        })

        contactsViewModel.showPermissionDilog.observe(this, Observer {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {
                contactsViewModel.permissionDeniedBefore()
            } else {
                contactsViewModel.permissionNotRequestedBefore()
            }
        })

        contactsViewModel.requestPermission.observe(this, Observer {
            requestPermission()
        })

        contactsViewModel.showMoreInfoDialog.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.permission_info)
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                    requestPermission()
                    dialog.dismiss()
                }).show()
        })

        contactsViewModel.showPermissionFragment.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PermissionFragment(), PERMISSION).commit()
        })

        contactsViewModel.showListFragment.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ContactListFragment(), LIST).commit()
        })
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACT_PERMISSION_REQUEST_CODE
        )
    }
}

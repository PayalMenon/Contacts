package com.example.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mycontacts.R
import com.example.mycontacts.viewmodels.ContactsViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.permission.view.*
import javax.inject.Inject

class PermissionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        super.onCreateView(inflater, container, savedInstanceState)

        contactsViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(ContactsViewModel::class.java)

        return inflater.inflate(R.layout.permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.exit.setOnClickListener {
            requireActivity().finish()
        }
        view.ok.setOnClickListener {
            contactsViewModel.initialize(android.os.Build.VERSION.SDK_INT)
        }
    }
}
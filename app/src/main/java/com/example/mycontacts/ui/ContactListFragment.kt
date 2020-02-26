package com.example.mycontacts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mycontacts.R
import com.example.mycontacts.model.Contact
import com.example.mycontacts.util.DateUtil
import com.example.mycontacts.viewmodels.ContactsViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list.view.*
import javax.inject.Inject


class ContactListFragment : DaggerFragment() {

    companion object {
        private const val SMS_BODY = "sms_body"
    }
    @Inject
    lateinit var dateUtil : DateUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var adapter: ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        super.onCreateView(inflater, container, savedInstanceState)

        contactsViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            ContactsViewModel::class.java)

        contactsViewModel.openMessagingApp.observe(this, Observer { number ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
            intent.putExtra(SMS_BODY, R.string.test_message)
            startActivity(intent)
        })

        return inflater.inflate(R.layout.list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactListAdapter(dateUtil) { contact: Contact -> onContactItemClicked(contact) }
        view.list_view.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(view.context, LinearLayout.VERTICAL)
        view.list_view.addItemDecoration(dividerItemDecoration)

        contactsViewModel.getContactsList()?.observe(this, Observer { contacts ->
            adapter.submitList(contacts)
        })
    }

    private fun onContactItemClicked(contact : Contact) {
        contactsViewModel.onContactItemClicked(contact)
    }
}
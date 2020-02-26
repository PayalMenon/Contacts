package com.example.mycontacts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontacts.R
import com.example.mycontacts.model.Contact
import com.example.mycontacts.ui.ContactListAdapter.Companion.dateFormat
import com.example.mycontacts.util.DateUtil
import kotlinx.android.synthetic.main.contact_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ContactListAdapter @Inject constructor(val  dateUtil: DateUtil, val clickListener: (Contact) -> Unit) : PagedListAdapter<Contact, ContactListViewHolder>(ContactDiffUtil()) {

    companion object {
        val dateFormat : DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactListViewHolder(view,  dateUtil)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        getItem(position)?.let { contact ->
            holder.setContactInfo(contact,clickListener)
        }
    }

}

class ContactListViewHolder(itemView: View,  val dateUtil : DateUtil) : RecyclerView.ViewHolder(itemView) {

    fun setContactInfo(contact: Contact, clickListener: (Contact) -> Unit) {
        itemView.name.text = contact.name
        itemView.date.text = dateUtil.getDate(dateFormat, contact.date)
        itemView.message.setOnClickListener { clickListener(contact) }
    }
}
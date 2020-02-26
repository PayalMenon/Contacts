package com.example.mycontacts.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.mycontacts.model.Contact

class ContactDiffUtil  : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

}
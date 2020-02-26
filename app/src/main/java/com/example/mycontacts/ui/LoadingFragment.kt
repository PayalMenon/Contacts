package com.example.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mycontacts.R
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

class LoadingFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.loading, container, false)
    }
}
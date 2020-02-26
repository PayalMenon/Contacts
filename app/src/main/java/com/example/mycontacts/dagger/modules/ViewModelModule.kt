package com.example.mycontacts.dagger.modules

import androidx.lifecycle.ViewModel
import com.example.mycontacts.dagger.ViewModelKey
import com.example.mycontacts.viewmodels.ContactsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindIntroViewModel(viewModel: ContactsViewModel): ViewModel
}
package com.example.mycontacts.dagger.modules

import com.example.mycontacts.dagger.ViewModelBuilder
import com.example.mycontacts.ui.ContactListFragment
import com.example.mycontacts.ui.ContactsActivity
import com.example.mycontacts.ui.LoadingFragment
import com.example.mycontacts.ui.PermissionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun introActivity(): ContactsActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun loadingFragment(): LoadingFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun permissionFragment(): PermissionFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun contactListFragment(): ContactListFragment
}
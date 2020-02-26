package com.example.mycontacts.dagger.modules

import android.app.Application
import android.content.Context
import com.example.mycontacts.ContactsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        RepoModule::class,
        UtilModule::class]
)
interface ApplicationComponent : AndroidInjector<ContactsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: Application)
}
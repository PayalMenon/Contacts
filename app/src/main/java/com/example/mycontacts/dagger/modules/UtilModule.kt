package com.example.mycontacts.dagger.modules

import com.example.mycontacts.util.DateUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Singleton
    @Provides
    fun provideDateUtil(): DateUtil {
        return DateUtil()
    }
}
package com.cheocharm.local.di

import com.cheocharm.local.SharedPrefManager
import com.cheocharm.local.SharedPrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SharedPreferenceModule {

    @Binds
    @Singleton
    abstract fun bindSharedPrefManager(sharedPrefManagerImpl: SharedPrefManagerImpl): SharedPrefManager
}

package com.cheocharm.local.di

import com.cheocharm.data.local.source.AuthLocalDataSource
import com.cheocharm.local.source.AuthLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(dataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource
}

package com.cheocharm.local.di

import com.cheocharm.data.source.LoginLocalDataSource
import com.cheocharm.local.source.LoginLocalDataSourceImpl
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
    abstract fun bindLoginLocalDataSource(dataSource: LoginLocalDataSourceImpl): LoginLocalDataSource
}

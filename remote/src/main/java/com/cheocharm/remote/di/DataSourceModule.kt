package com.cheocharm.remote.di

import com.cheocharm.data.source.LoginRemoteDataSource
import com.cheocharm.remote.source.LoginRemoteDataSourceImpl
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
    abstract fun bindLoginRemoteDataSource(dataSource: LoginRemoteDataSourceImpl): LoginRemoteDataSource
}

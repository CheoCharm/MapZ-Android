package com.cheocharm.data.di

import com.cheocharm.data.local.source.AuthLocalDataSource
import com.cheocharm.data.local.source.AuthLocalDataSourceImpl
import com.cheocharm.data.local.source.MyGroupsLocalDataSource
import com.cheocharm.data.local.source.MyGroupsLocalDataSourceImpl
import com.cheocharm.data.remote.source.AuthRemoteDataSource
import com.cheocharm.data.remote.source.AuthRemoteDataSourceImpl
import com.cheocharm.data.remote.source.GroupRemoteDataSource
import com.cheocharm.data.remote.source.GroupRemoteDataSourceImpl
import com.cheocharm.data.remote.source.LoginRemoteDataSource
import com.cheocharm.data.remote.source.LoginRemoteDataSourceImpl
import com.cheocharm.data.remote.source.MyGroupsRemoteDataSource
import com.cheocharm.data.remote.source.MyGroupsRemoteDataSourceImpl
import com.cheocharm.data.remote.source.WriteRemoteDataSource
import com.cheocharm.data.remote.source.WriteRemoteDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(dataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(dataSource: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGroupRemoteDataSource(dataSource: GroupRemoteDataSourceImpl): GroupRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyGroupsLocalDataSource(dataSource: MyGroupsLocalDataSourceImpl): MyGroupsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMyGroupsRemoteDataSource(dataSource: MyGroupsRemoteDataSourceImpl): MyGroupsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWriteRemoteDataSource(dataSource: WriteRemoteDataSourceImpl): WriteRemoteDataSource
}

package com.cheocharm.remote.di

import com.cheocharm.data.remote.source.GroupRemoteDataSource
import com.cheocharm.remote.source.GroupRemoteDataSourceImpl
import com.cheocharm.data.remote.source.AuthRemoteDataSource
import com.cheocharm.data.remote.source.LoginRemoteDataSource
import com.cheocharm.data.remote.source.MyGroupsRemoteDataSource
import com.cheocharm.data.remote.source.WriteRemoteDataSource
import com.cheocharm.remote.source.AuthRemoteDataSourceImpl
import com.cheocharm.remote.source.LoginRemoteDataSourceImpl
import com.cheocharm.remote.source.MyGroupsRemoteDataSourceImpl
import com.cheocharm.remote.source.WriteRemoteDataSourceImpl
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
    abstract fun bindAuthRemoteDataSource(dataSource: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGroupRemoteDataSource(dataSource: GroupRemoteDataSourceImpl): GroupRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyGroupsRemoteDataSource(dataSource: MyGroupsRemoteDataSourceImpl): MyGroupsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWriteRemoteDataSource(dataSource: WriteRemoteDataSourceImpl): WriteRemoteDataSource
}

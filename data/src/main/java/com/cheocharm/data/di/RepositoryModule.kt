package com.cheocharm.data.di

import com.cheocharm.data.repository.AuthRepositoryImpl
import com.cheocharm.data.repository.GroupRepositoryImpl
import com.cheocharm.data.repository.LoginRepositoryImpl
import com.cheocharm.data.repository.MyGroupsRepositoryImpl
import com.cheocharm.data.repository.WriteRepositoryImpl
import com.cheocharm.domain.repository.AuthRepository
import com.cheocharm.domain.repository.GroupRepository
import com.cheocharm.domain.repository.LoginRepository
import com.cheocharm.domain.repository.MyGroupsRepository
import com.cheocharm.domain.repository.WriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(repo: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repo: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(repo: GroupRepositoryImpl): GroupRepository

    @Binds
    @Singleton
    abstract fun bindMyGroupsRepository(repo: MyGroupsRepositoryImpl): MyGroupsRepository

    @Binds
    @Singleton
    abstract fun bindWriteRepository(repository: WriteRepositoryImpl): WriteRepository
}

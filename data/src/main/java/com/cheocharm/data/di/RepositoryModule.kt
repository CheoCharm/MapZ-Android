package com.cheocharm.data.di

import com.cheocharm.data.repository.AuthRepositoryImpl
import com.cheocharm.data.repository.GroupRepositoryImpl
import com.cheocharm.data.repository.LoginRepositoryImpl
import com.cheocharm.domain.repository.AuthRepository
import com.cheocharm.domain.repository.GroupRepository
import com.cheocharm.domain.repository.LoginRepository
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
}

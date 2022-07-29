package com.cheocharm.data.di

import com.cheocharm.data.repository.LoginRepositoryImpl
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
}

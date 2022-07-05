package com.cheocharm.remote.di

import com.cheocharm.remote.api.LoginApi
import com.cheocharm.remote.network.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithoutAuthOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteModule {

    @AuthOkHttpClient
    @Provides
    fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(authInterceptor)
            .build()
    }

    @WithoutAuthOkHttpClient
    @Provides
    fun provideWithoutAuthOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApi(@WithoutAuthOkHttpClient okHttpClient: OkHttpClient): LoginApi {
        return MapZRetrofit.createService(LoginApi::class.java, okHttpClient)
    }
}

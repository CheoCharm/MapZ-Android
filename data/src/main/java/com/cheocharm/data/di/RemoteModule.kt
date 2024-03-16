package com.cheocharm.data.di

import com.cheocharm.data.remote.api.GroupApi
import com.cheocharm.data.remote.api.LoginApi
import com.cheocharm.data.remote.api.TokenApi
import com.cheocharm.data.remote.api.DiaryApi
import com.cheocharm.data.remote.network.AuthInterceptor
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

    @Provides
    @Singleton
    fun provideTokenApi(@WithoutAuthOkHttpClient okHttpClient: OkHttpClient): TokenApi {
        return MapZRetrofit.createService(TokenApi::class.java, okHttpClient)
    }

    @Provides
    @Singleton
    fun provideGroupApi(@AuthOkHttpClient okHttpClient: OkHttpClient): GroupApi {
        return MapZRetrofit.createService(GroupApi::class.java, okHttpClient)
    }

    @Provides
    @Singleton
    fun provideWriteApi(@AuthOkHttpClient okHttpClient: OkHttpClient): DiaryApi {
        return MapZRetrofit.createService(DiaryApi::class.java, okHttpClient)
    }
}

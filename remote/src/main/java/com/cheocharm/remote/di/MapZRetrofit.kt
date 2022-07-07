package com.cheocharm.remote.di

import com.cheocharm.remote.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MapZRetrofit {

    fun <T> createService(service: Class<T>, okHttpClient: OkHttpClient): T =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(service)
}

package com.cheocharm.remote.network

import com.cheocharm.domain.repository.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = authRepository.fetchAccessToken() ?: ""
        val tokenHeaderRequest = chain.request().newBuilder()
            .addHeader(ACCESS_TOKEN, accessToken)
            .build()
        return chain.proceed(tokenHeaderRequest)
    }

    companion object {
        private const val TAG = "AuthInterceptor"
        private const val ACCESS_TOKEN = "accessToken"
    }
}

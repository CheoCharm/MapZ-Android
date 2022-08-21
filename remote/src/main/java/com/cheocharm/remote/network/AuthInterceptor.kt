package com.cheocharm.remote.network

import android.util.Log
import com.cheocharm.domain.repository.AuthRepository
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = authRepository.fetchAccessToken() ?: ""
        val tokenHeaderRequest = chain.request().newBuilder()
            .addHeader(ACCESS_TOKEN, accessToken)
            .build()
        val response = chain.proceed(tokenHeaderRequest)

        runCatching {
            if (response.code == 401) {
                val refreshToken = authRepository.fetchRefreshToken() ?: ""

                runBlocking {
                    authRepository.refreshAccessToken(refreshToken)
                }.onSuccess { newAccessToken ->
                    if (newAccessToken.isEmpty().not()) {
                        authRepository.saveTokens(newAccessToken, refreshToken)
                        // TODO: 만약에 서버에서 accessToken 재발급할 때 refreshToken도 재발급하면 그것도 저장해야 함
                        response.close()

                        val request = chain.request().newBuilder()
                            .addHeader(ACCESS_TOKEN, newAccessToken)
                            .build()
                        return chain.proceed(request)
                    }
                }.onFailure {
                    // TODO: 로그아웃?
                }
            }
        }.onFailure {
            Log.e(TAG, "intercept: accessToken 재발급 실패\n $it")
        }

        return response
    }

    companion object {
        private const val TAG = "AuthInterceptor"
        private const val ACCESS_TOKEN = "accessToken"
    }
}

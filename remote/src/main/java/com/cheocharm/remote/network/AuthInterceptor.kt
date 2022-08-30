package com.cheocharm.remote.network

import com.cheocharm.domain.repository.AuthRepository
import android.util.Log
import com.cheocharm.domain.event_bus.EventBus
import com.cheocharm.domain.event_bus.MapZEvent
import kotlinx.coroutines.runBlocking
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
        val response = chain.proceed(tokenHeaderRequest)

        runCatching {
            if (response.code == 401) {
                val refreshToken = authRepository.fetchRefreshToken() ?: ""

                runBlocking {
                    authRepository.refreshAccessToken(refreshToken)
                }.onSuccess { token ->
                    val newAccessToken = token.accessToken ?: return@runCatching
                    val newRefreshToken = token.refreshToken ?: return@runCatching
                    authRepository.saveTokens(newAccessToken, newRefreshToken)
                    response.close()

                    val request = chain.request().newBuilder()
                        .addHeader(ACCESS_TOKEN, newAccessToken)
                        .build()
                    return chain.proceed(request)

                }.onFailure {
                    runBlocking {
                        EventBus.invokeEvent(MapZEvent.SIGN_OUT)
                    }
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

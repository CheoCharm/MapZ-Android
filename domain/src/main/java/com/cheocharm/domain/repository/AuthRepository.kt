package com.cheocharm.domain.repository

import com.cheocharm.domain.model.MapZSign

interface AuthRepository {

    fun saveTokens(mapZSign: MapZSign)

    fun saveIsAutoSignIn(isAutoSignIn: Boolean)

    fun fetchAccessToken(): String?

    fun fetchRefreshToken(): String?

    fun fetchIsAutoSignIn(): Boolean

    fun removeAccessToken()

    fun removeRefreshToken()

    fun removeIsAutoSignIn()
}

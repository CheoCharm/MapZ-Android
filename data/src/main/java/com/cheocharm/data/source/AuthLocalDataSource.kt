package com.cheocharm.data.source

import com.cheocharm.domain.model.MapZSign

interface AuthLocalDataSource {

    fun saveTokens(mapZSign: MapZSign)

    fun saveIsAutoSignIn(isAutoSignIn: Boolean)

    fun fetchAccessToken(): String?

    fun fetchRefreshToken(): String?

    fun fetchIsAutoSignIn(): Boolean

    fun removeAccessToken()

    fun removeRefreshToken()

    fun removeIsAutoSignIn()
}

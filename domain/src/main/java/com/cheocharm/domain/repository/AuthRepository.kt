package com.cheocharm.domain.repository

import com.cheocharm.domain.model.MapZSign

interface AuthRepository {

    fun saveTokens(mapZSign: MapZSign)

    fun saveIsAutoSignIn(isAutoSignIn: Boolean)
}

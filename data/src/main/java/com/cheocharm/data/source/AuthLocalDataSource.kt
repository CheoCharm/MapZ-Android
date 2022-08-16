package com.cheocharm.data.source

import com.cheocharm.domain.model.MapZSign

interface AuthLocalDataSource {

    fun saveTokens(mapZSign: MapZSign)
}

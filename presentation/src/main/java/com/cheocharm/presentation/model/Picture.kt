package com.cheocharm.presentation.model

import android.net.Uri
import com.cheocharm.presentation.util.toCoordString
import com.google.android.gms.maps.model.LatLng

data class Picture(val uri: Uri, val latLng: LatLng?) {
    var address: String? = null

    fun getLocationString(): String {
        return address ?: latLng.toCoordString() ?: "현재 위치"
    }
}

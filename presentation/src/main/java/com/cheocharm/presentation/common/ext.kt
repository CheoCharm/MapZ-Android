package com.cheocharm.presentation.common

import com.google.android.gms.maps.model.LatLng

fun LatLng?.toCoordString() = this?.let {
    with("%.5f") {
        val lat = format(it.latitude)
        val lng = format(it.longitude)
        "($lat, $lng)"
    }
}

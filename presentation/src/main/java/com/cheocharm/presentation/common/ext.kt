package com.cheocharm.presentation.common

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng() = LatLng(this.latitude, this.longitude)

fun LatLng?.toCoordString() = this?.let {
    with("%.5f") {
        val lat = format(it.latitude)
        val lng = format(it.longitude)
        "($lat, $lng)"
    }
}

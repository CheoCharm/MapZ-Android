package com.cheocharm.presentation.common

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng() = LatLng(this.latitude, this.longitude)

fun LatLng.toCoordString(): String {
    with("%.5f") {
        val lat = format(latitude)
        val lng = format(longitude)

        return "($lat, $lng)"
    }
}

package com.cheocharm.presentation.common

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng() = LatLng(this.latitude, this.longitude)

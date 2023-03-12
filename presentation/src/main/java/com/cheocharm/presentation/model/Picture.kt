package com.cheocharm.presentation.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class Picture(val uri: Uri, val latLng: LatLng?) {
    var address: String? = null
}

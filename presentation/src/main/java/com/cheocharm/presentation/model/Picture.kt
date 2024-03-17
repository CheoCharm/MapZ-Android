package com.cheocharm.presentation.model

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(val uri: Uri, val latLng: LatLng?): Parcelable {
    @IgnoredOnParcel
    var address: String? = null
}

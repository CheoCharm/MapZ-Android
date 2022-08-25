package com.cheocharm.presentation.util

import android.content.Context
import android.location.Geocoder
import com.cheocharm.presentation.model.Picture
import com.google.android.gms.maps.model.LatLng
import java.util.*

object GeocodeUtil {
    fun execute(
        context: Context,
        picture: Picture
    ) {
        picture.latLng?.let {
            try {
                val geocoder = Geocoder(context, Locale.KOREAN)
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                if (addresses != null && addresses.isEmpty().not()) {
                    val fetchedAddress = addresses.first()
                    val address = fetchedAddress.getAddressLine(0)
                    picture.address = address
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun execute(context: Context, latLng: LatLng): String? {
        try {
            val geocoder = Geocoder(context, Locale.KOREAN)
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (addresses != null && addresses.isEmpty().not()) {
                val fetchedAddress = addresses.first()
                return fetchedAddress.getAddressLine(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}

package com.cheocharm.presentation.util

import android.content.Context
import android.location.Geocoder
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object GeocodeUtil {
    suspend fun execute(
        context: Context,
        picture: Picture
    ) = withContext(Dispatchers.IO) {
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

    suspend fun execute(
        context: Context,
        latLng: LatLng,
        type: LatLngSelectionType,
        callback: (LatLng, LatLngSelectionType, String?) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.KOREAN)
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (addresses != null && addresses.isEmpty().not()) {
                val fetchedAddress = addresses.first()
                val address = fetchedAddress.getAddressLine(0)
                callback(latLng, type, address)
                return@withContext
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        callback(latLng, type, null)
    }
}

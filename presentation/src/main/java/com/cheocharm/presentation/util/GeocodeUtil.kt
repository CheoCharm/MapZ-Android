package com.cheocharm.presentation.util

import android.content.Context
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GeocodeUtil(context: Context) {
    private val geocoder = Geocoder(context, Locale.KOREAN)

    suspend fun execute(picture: Picture, geocodeListener: GeocodeListener? = null) =
        withContext(ioDispatcher) {
            picture.latLng?.let {
                runCatching {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && geocodeListener != null) {
                        geocoder.getFromLocation(it.latitude, it.longitude, 1, geocodeListener)
                    } else {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                        if (addresses != null && addresses.isEmpty().not()) {
                            val fetchedAddress = addresses.first()
                            val address = fetchedAddress.getAddressLine(0)
                            picture.address = address
                        }
                    }
                }
            }
        }

    suspend fun execute(
        latLng: DoubleArray,
        type: LatLngSelectionType,
        callback: (DoubleArray, LatLngSelectionType, String?) -> Unit,
        geocodeListener: GeocodeListener? = null
    ) = withContext(ioDispatcher) {
        runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && geocodeListener != null) {
                geocoder.getFromLocation(latLng[0], latLng[1], 1, geocodeListener)
            } else {
                val addresses = geocoder.getFromLocation(latLng[0], latLng[1], 1)

                if (addresses != null && addresses.isEmpty().not()) {
                    val fetchedAddress = addresses.first()
                    val address = fetchedAddress.getAddressLine(0)
                    callback(latLng, type, address)
                    return@withContext
                }
            }
        }

        callback(latLng, type, null)
    }

    companion object {
        private val ioDispatcher = Dispatchers.IO
    }
}

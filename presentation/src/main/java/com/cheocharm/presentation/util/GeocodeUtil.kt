package com.cheocharm.presentation.util

import android.content.Context
import android.location.Geocoder
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GeocodeUtil(private val context: Context) {

    suspend fun execute(picture: Picture) = withContext(ioDispatcher) {
        picture.latLng?.let {
            runCatching {
                val geocoder = Geocoder(context, Locale.KOREAN)
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                if (addresses != null && addresses.isEmpty().not()) {
                    val fetchedAddress = addresses.first()
                    val address = fetchedAddress.getAddressLine(0)
                    picture.address = address
                }
            }
        }
    }

    suspend fun execute(
        latLng: DoubleArray,
        type: LatLngSelectionType,
        callback: (DoubleArray, LatLngSelectionType, String?) -> Unit
    ) = withContext(ioDispatcher) {
        runCatching {
            val geocoder = Geocoder(context, Locale.KOREAN)
            val addresses = geocoder.getFromLocation(latLng[0], latLng[1], 1)

            if (addresses != null && addresses.isEmpty().not()) {
                val fetchedAddress = addresses.first()
                val address = fetchedAddress.getAddressLine(0)
                callback(latLng, type, address)
                return@withContext
            }
        }

        callback(latLng, type, null)
    }

    companion object {
        private val ioDispatcher = Dispatchers.IO
    }
}

package com.cheocharm.presentation.util

import android.content.Context
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class GeocodeUtil(
    context: Context,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val geocoder = Geocoder(context, Locale.KOREAN)

    suspend fun execute(picture: Picture, geocodeListener: GeocodeListener? = null) =
        withContext(coroutineDispatcher) {
            picture.latLng?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && geocodeListener != null) {
                    runCatching {
                        geocoder.getFromLocation(it.latitude, it.longitude, 1, geocodeListener)
                    }.onFailure { throwable ->
                        throwable.printStackTrace()
                    }
                } else {
                    runCatching {
                        geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    }.onSuccess { addresses ->
                        if (addresses != null && addresses.isEmpty().not()) {
                            val fetchedAddress = addresses.first()
                            val address = fetchedAddress.getAddressLine(0)
                            picture.address = address
                        }
                    }.onFailure { throwable ->
                        throwable.printStackTrace()
                    }
                }
            }
        }

    suspend fun execute(
        latLng: DoubleArray,
        type: LatLngSelectionType,
        callback: (DoubleArray, LatLngSelectionType, String?) -> Unit,
        geocodeListener: GeocodeListener? = null
    ) = withContext(coroutineDispatcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && geocodeListener != null) {
            runCatching {
                geocoder.getFromLocation(latLng[0], latLng[1], 1, geocodeListener)
            }.onFailure { throwable ->
                throwable.printStackTrace()
            }
        } else {
            runCatching {
                geocoder.getFromLocation(latLng[0], latLng[1], 1)
            }.onSuccess { addresses ->
                if (addresses != null && addresses.isEmpty().not()) {
                    val fetchedAddress = addresses.first()
                    val address = fetchedAddress.getAddressLine(0)
                    callback(latLng, type, address)
                    return@withContext
                } else {
                    callback(latLng, type, null)
                }
            }.onFailure { throwable ->
                throwable.printStackTrace()
            }
        }
    }
}

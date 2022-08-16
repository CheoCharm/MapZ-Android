package com.cheocharm.presentation.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.cheocharm.presentation.model.Picture
import java.util.*

object GeocodeUtil {
    fun execute(
        context: Context,
        picture: Picture
    ) {
        picture.latLng?.let {
            try {
                val addresses: MutableList<Address>
                val geocoder = Geocoder(context, Locale.KOREAN)
                addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                if (addresses.isEmpty().not()) {
                    val fetchedAddress = addresses.first()
                    val index = fetchedAddress.maxAddressLineIndex

                    if (index >= 0) {
                        val address = fetchedAddress.getAddressLine(0)
                        picture.address = address
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

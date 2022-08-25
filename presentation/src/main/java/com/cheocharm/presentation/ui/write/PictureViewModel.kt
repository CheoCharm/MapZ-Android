package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.presentation.common.toCoordString
import com.cheocharm.presentation.enum.SelectedLatLngType
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor() : ViewModel() {
    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture

    private val _selectedLatLng = MutableLiveData<LatLng>()
    val selectedLocation: LiveData<LatLng> = _selectedLatLng

    private val _latLngString = MutableLiveData<String>()
    val latLngString: LiveData<String> = _latLngString

    fun setPicture(picture: Picture) {
        _picture.value = picture
    }

    fun setSelectedLatLng(latLng: LatLng, type: SelectedLatLngType, address: String? = null) {
        _selectedLatLng.value = latLng
        _latLngString.value =
            if (type == SelectedLatLngType.DEFAULT || type == SelectedLatLngType.CURRENT) {
                type.str
            } else {
                address ?: latLng.toCoordString()
            }
    }
}

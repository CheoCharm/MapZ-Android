package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.usecase.write.RequestWriteImagesUseCase
import com.cheocharm.presentation.common.Event
import com.cheocharm.presentation.common.TestValues
import com.cheocharm.presentation.model.Sticker
import com.cheocharm.presentation.common.toCoordString
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val requestWriteImagesUseCase: RequestWriteImagesUseCase
) : ViewModel() {
    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _locationSelectedEvent = MutableLiveData<Event<TempDiary>>()
    val locationSelectedEvent: LiveData<Event<TempDiary>> = _locationSelectedEvent

    var stickers: List<Sticker> = TestValues.testStickers
        private set

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture

    private val _selectedLatLng = MutableLiveData<LatLng>()
    val selectedLocation: LiveData<LatLng> = _selectedLatLng

    private val _latLngString = MutableLiveData<String>()
    val latLngString: LiveData<String> = _latLngString

    fun loadPicture(picture: Picture, geocodeUtil: GeocodeUtil) {
        _picture.value = picture

        if (picture.address == null) {
            viewModelScope.launch {
                geocodeUtil.execute(picture)
            }
        }
    }

    fun setSelectedLatLng(latLng: LatLng, type: LatLngSelectionType, address: String? = null) {
        val locationString: String =
            if (type == LatLngSelectionType.DEFAULT || type == LatLngSelectionType.CURRENT) {
                type.locationString
            } else {
                address ?: latLng.toCoordString()
            }

        _selectedLatLng.postValue(latLng)
        _latLngString.postValue(locationString)
    }

    fun uploadImages(
        groupId: Long,
        address: String?,
        lat: Double?,
        lng: Double?,
        images: List<File>
    ) {
        viewModelScope.launch {
            // 테스트
//            _toastText.value = Event("TEST: 이미지 업로드 실패")
//            _locationSelectedEvent.value = Event(TempDiary(38, TestValues.testImages))

            requestWriteImagesUseCase.invoke(
                WriteImageRequest(
                    groupId,
                    address ?: TEST_ADDRESS,
                    lat ?: TEST_LAT,
                    lng ?: TEST_LNG,
                    images
                )
            ).onSuccess {
                _locationSelectedEvent.value = Event(it)
            }.onFailure {
                _toastText.value = Event(it.message ?: "")
            }
        }
    }

    companion object {
        // TODO: 기본 위치 설정
        private const val TEST_LAT = 0.0
        private const val TEST_LNG = 0.0
        private const val TEST_ADDRESS = ""
    }
}

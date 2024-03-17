package com.cheocharm.presentation.ui.write.location

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.usecase.write.RequestWriteImagesUseCase
import com.cheocharm.presentation.common.Event
import com.cheocharm.presentation.common.TestValues
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.model.Sticker
import com.cheocharm.presentation.util.GeocodeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val requestWriteImagesUseCase: RequestWriteImagesUseCase
) : ViewModel() {
    private var isDefaultLocation = handle.get<Boolean>(KEY_IS_DEFAULT_LOCATION) ?: false
        set(value) {
            handle[KEY_IS_DEFAULT_LOCATION] = value
            field = value
        }
    val isDefaultLocationLiveData: LiveData<Boolean> = handle.getLiveData(KEY_IS_DEFAULT_LOCATION, false)

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _locationSelectedEvent = MutableLiveData<Event<TempDiary>>()
    val locationSelectedEvent: LiveData<Event<TempDiary>> = _locationSelectedEvent

    var stickers: List<Sticker> = TestValues.testStickers
        private set

    private val _pictures: MutableLiveData<List<Picture>> = handle.getLiveData(KEY_PICTURES)
    val pictures: LiveData<List<Picture>> = _pictures

    private val _locationString = MutableLiveData<String>()
    val locationString: LiveData<String> = _locationString

    fun saveIsDefaultLocation(boolean: Boolean) {
        isDefaultLocation = boolean
    }

    fun loadPicture(pictures: List<Picture>, geocodeUtil: GeocodeUtil) {
        _pictures.value = pictures
        handle[KEY_PICTURES] = pictures

        viewModelScope.launch(Dispatchers.IO) {
            geocodeUtil.execute(pictures[0])
        }
    }

    fun geocode(geocodeUtil: GeocodeUtil, latLng: DoubleArray, type: LatLngSelectionType) {
        viewModelScope.launch(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocodeUtil.execute(latLng, type, ::setSelectedLatLng) {
                    _locationString.postValue(
                        if (type != LatLngSelectionType.SPECIFIED) {
                            type.locationString
                        } else {
                            it[0].getAddressLine(0) ?: latLng.toCoordString()
                        }
                    )
                }
            } else {
                geocodeUtil.execute(latLng, type, ::setSelectedLatLng)
            }
        }
    }

    fun setSelectedLatLng(latLng: DoubleArray, type: LatLngSelectionType, address: String? = null) {
        _locationString.postValue(
            if (type == LatLngSelectionType.SPECIFIED) {
                address ?: latLng.toCoordString()
            } else {
                type.locationString
            }
        )
    }

    private fun DoubleArray.toCoordString(): String {
        val format = "%.5f"
        val lat = format.format(get(0))
        val lng = format.format(get(1))

        return "($lat, $lng)"
    }

    fun confirmLocation(
        groupId: Long,
        address: String?,
        lat: Double?,
        lng: Double?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val images = getFiles() ?: return@launch

//            requestWriteImagesUseCase.invoke(
//                WriteImageRequest(
//                    groupId,
//                    address ?: TEST_ADDRESS,
//                    lat ?: TEST_LAT,
//                    lng ?: TEST_LNG,
//                    images
//                )
//            ).onSuccess {
//                _locationSelectedEvent.postValue(Event(it))
//            }.onFailure {
//                _toastText.postValue(Event(it.message ?: ""))
//            }

            _locationSelectedEvent.postValue(Event(TempDiary(0, listOf())))
        }
    }

    private fun getFiles(): List<File> ?{
        val pictures = pictures.value

        return pictures?.map {
            File(it.uri.toString())
        }
    }

    companion object {
        private const val KEY_PICTURES = "pictures"
        private const val KEY_IS_DEFAULT_LOCATION = "isDefaultLocation"

        // TODO: 기본 위치 설정
        private const val TEST_LAT = 0.0
        private const val TEST_LNG = 0.0
        private const val TEST_ADDRESS = ""
    }
}

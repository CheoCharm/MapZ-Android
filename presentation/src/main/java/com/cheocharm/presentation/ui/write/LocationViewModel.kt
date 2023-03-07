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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val requestWriteImagesUseCase: RequestWriteImagesUseCase
) : ViewModel() {
    private val _toastText = MutableLiveData<Event<String>?>()
    val toastText: LiveData<Event<String>?> = _toastText

    private val _locationSelectedEvent = MutableLiveData<Event<TempDiary>?>()
    val locationSelectedEvent: LiveData<Event<TempDiary>?> = _locationSelectedEvent

    var stickers: List<Sticker> = TestValues.testStickers
        private set

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
        private const val TEST_LAT = 0.0
        private const val TEST_LNG = 0.0
        private const val TEST_ADDRESS = ""
    }
}

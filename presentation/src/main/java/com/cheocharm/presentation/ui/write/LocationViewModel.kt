package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.DiaryTemp
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.usecase.write.RequestWriteImagesUseCase
import com.cheocharm.presentation.common.Event
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

    private val _locationSelectedEvent = MutableLiveData<Event<DiaryTemp>?>()
    val locationSelectedEvent: LiveData<Event<DiaryTemp>?> = _locationSelectedEvent

    var stickers: List<Sticker> = testStickers
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
//            _locationSelectedEvent.value = Event(AttachedImages(38, testImages))

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

        private val testImages = listOf(
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary//5eda379c-a8be-4c41-bfbf-a1e984c457ff",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/afd156ea-6257-4c30-9d06-f8b1ebf37609",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/f4424c08-dfd3-461b-b9e3-3cf09f984f8a",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/fe1189fb-c83d-4792-9c45-6e3bfbd77cda",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/f3284be0-c6d9-43be-8399-e6ebd30603ae"
        )

        // TODO: 서버에 스티커 파일 업로드 후 URL 변경
        private val testStickers = listOf(
            Sticker(
                "IMG_6148.JPG",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG"
            ),
            Sticker(
                "5eda379c-a8be-4c41-bfbf-a1e984c457ff",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary//5eda379c-a8be-4c41-bfbf-a1e984c457ff"
            ),
            Sticker(
                "afd156ea-6257-4c30-9d06-f8b1ebf37609",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/afd156ea-6257-4c30-9d06-f8b1ebf37609"
            ),
            Sticker(
                "f4424c08-dfd3-461b-b9e3-3cf09f984f8a",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/f4424c08-dfd3-461b-b9e3-3cf09f984f8a"
            ),
            Sticker(
                "fe1189fb-c83d-4792-9c45-6e3bfbd77cda",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/fe1189fb-c83d-4792-9c45-6e3bfbd77cda"
            ),
            Sticker(
                "f3284be0-c6d9-43be-8399-e6ebd30603ae",
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/f3284be0-c6d9-43be-8399-e6ebd30603ae"
            )
        )
    }
}

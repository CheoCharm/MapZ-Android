package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.AttachedImages
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.usecase.write.RequestWriteImagesUseCase
import com.cheocharm.presentation.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val requestWriteImagesUseCase: RequestWriteImagesUseCase
) : ViewModel() {
    private val _result = MutableLiveData<Result<AttachedImages>>()
    val result: LiveData<Result<AttachedImages>> = _result

    fun uploadImages(
        groupId: Long,
        address: String?,
        lat: Double?,
        lng: Double?,
        images: List<File>
    ) {
        viewModelScope.launch {
            requestWriteImagesUseCase.invoke(
                WriteImageRequest(
                    groupId,
                    address ?: TEST_ADDRESS,
                    lat ?: TEST_LAT,
                    lng ?: TEST_LNG,
                    images
                )
            ).onSuccess {
                _result.value = Result(true, data = it)
            }.onFailure {
                _result.value = Result(false, it.message)
            }
        }
    }

    companion object {
        private const val TEST_LAT = 0.0
        private const val TEST_LNG = 0.0
        private const val TEST_ADDRESS = ""
    }
}

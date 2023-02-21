package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.usecase.write.RequestWriteImagesUseCase
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val requestWriteImagesUseCase: RequestWriteImagesUseCase
) : ViewModel() {
    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result

    fun setPicture(picture: Picture) {
        _picture.value = picture
    }

    fun uploadImages(images: List<File>) {
        viewModelScope.launch {
            requestWriteImagesUseCase.invoke(WriteImageRequest(25, "주소", 0.0, 0.0, images))
                .onSuccess {
                    _result.value = Result(true)
                }
                .onFailure {
                    _result.value = Result(false, it.message)
                }
        }
    }
}

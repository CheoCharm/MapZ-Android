package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.presentation.model.Picture

class PictureViewModel : ViewModel() {
    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture

    fun setPicture(picture: Picture) {
        _picture.value = picture
    }
}

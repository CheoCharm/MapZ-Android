package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.presentation.model.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor() : ViewModel() {
    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture

    fun setPicture(picture: Picture) {
        _picture.value = picture
    }
}

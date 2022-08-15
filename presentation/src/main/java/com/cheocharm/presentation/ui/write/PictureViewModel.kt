package com.cheocharm.presentation.ui.write

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor() : ViewModel() {
    private val _picture = MutableLiveData<Uri>()
    val picture: LiveData<Uri> = _picture

    fun setPicture(uri: Uri) {
        _picture.value = uri
    }
}

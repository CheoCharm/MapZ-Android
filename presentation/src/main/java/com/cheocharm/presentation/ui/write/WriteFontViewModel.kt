package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteFontViewModel : ViewModel() {
    private val _selectedFontFamily = MutableLiveData(1)
    val selectedFontFamily: LiveData<Int> = _selectedFontFamily

    private val _selectedFontSize = MutableLiveData(2)
    val selectedFontSize: LiveData<Int> = _selectedFontSize

    fun selectFont(id: Int) {
        _selectedFontFamily.value = id
    }

    fun selectFontSize(id: Int) {
        _selectedFontSize.value = id
    }
}

package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteFontObjectViewModel : ViewModel() {
    private val _selectedFont = MutableLiveData(1)
    val selectedFont: LiveData<Int> = _selectedFont

    private val _selectedFontSize = MutableLiveData(2)
    val selectedFontSize: LiveData<Int> = _selectedFontSize

    fun selectFont(id: Int) {
        _selectedFont.value = id
    }

    fun selectFontSize(id: Int) {
        _selectedFontSize.value = id
    }
}

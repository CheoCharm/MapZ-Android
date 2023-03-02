package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteFontObjectViewModel : ViewModel() {
    private val _selectedFont = MutableLiveData(1)
    val selectedFont: LiveData<Int> = _selectedFont

    fun selectFont(id: Int) {
        _selectedFont.value = id
    }
}

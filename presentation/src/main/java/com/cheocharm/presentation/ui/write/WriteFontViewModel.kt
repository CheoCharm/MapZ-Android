package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.presentation.model.FontSize

class WriteFontViewModel : ViewModel() {
    private val _selectedFontFamily = MutableLiveData(1)
    val selectedFontFamily: LiveData<Int> = _selectedFontFamily

    private val _selectedFontSize = MutableLiveData(FontSize.Twelve)
    val selectedFontSize: LiveData<FontSize> = _selectedFontSize

    fun selectFont(id: Int) {
        _selectedFontFamily.value = id
    }

    fun selectFontSize(fontSize: FontSize) {
        _selectedFontSize.value = fontSize
    }
}

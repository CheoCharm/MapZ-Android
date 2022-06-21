package com.cheocharm.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun countUp() {
        _count.value = count.value?.plus(1) ?: 1
    }
}

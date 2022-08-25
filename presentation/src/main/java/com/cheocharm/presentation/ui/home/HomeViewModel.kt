package com.cheocharm.presentation.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun updateLocation(location: Location) {
        _location.value = location
    }

    fun countUp() {
        _count.value = count.value?.plus(1) ?: 1
    }
}

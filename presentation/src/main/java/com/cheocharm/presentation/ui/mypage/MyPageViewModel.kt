package com.cheocharm.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.domain.usecase.RequestSignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val requestSignOutUseCase: RequestSignOutUseCase
) : ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun countUp() {
        _count.value = count.value?.plus(1) ?: 1
    }

    fun requestSignOut() {
        requestSignOutUseCase.invoke()
    }
}

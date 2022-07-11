package com.cheocharm.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor() : ViewModel() {

    private val _agreementItem1 = MutableLiveData(false)
    val agreementItem1: LiveData<Boolean>
        get() = _agreementItem1

    private val _agreementItem2 = MutableLiveData(false)
    val agreementItem2: LiveData<Boolean>
        get() = _agreementItem2

    private val _agreementItem3 = MutableLiveData(false)
    val agreementItem3: LiveData<Boolean>
        get() = _agreementItem3

    private val _agreementItem4 = MutableLiveData(false)
    val agreementItem4: LiveData<Boolean>
        get() = _agreementItem4

    fun onAgreementItem1Clicked() {
        _agreementItem1.value = agreementItem1.value?.not()
    }

}

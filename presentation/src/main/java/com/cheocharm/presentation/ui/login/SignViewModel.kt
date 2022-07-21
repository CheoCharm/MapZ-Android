package com.cheocharm.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor() : ViewModel() {

    // Agreement
    private val _agreementItem1 = MutableLiveData(false)
    val agreementItem1: LiveData<Boolean>
        get() = _agreementItem1

    private val _agreementItem2 = MutableLiveData(false)
    val agreementItem2: LiveData<Boolean>
        get() = _agreementItem2

    private val _agreementItem3 = MutableLiveData(false)
    val agreementItem3: LiveData<Boolean>
        get() = _agreementItem3

    private val _agreementItemAll = MutableLiveData(false)
    val agreementItemAll: LiveData<Boolean>
        get() = _agreementItemAll

    private val _isAgreementSatisfied = MutableLiveData(false)
    val isAgreementSatisfied: LiveData<Boolean>
        get() = _isAgreementSatisfied

    // Agreement
    fun onAgreementItem1Clicked() {
        _agreementItem1.value = agreementItem1.value?.not()
        if (agreementItem1.value == false) {
            _agreementItemAll.value = false
        }
        checkAgreementSatisfied()
    }

    fun onAgreementItem2Clicked() {
        _agreementItem2.value = agreementItem2.value?.not()
        if (agreementItem2.value == false) {
            _agreementItemAll.value = false
        }
        checkAgreementSatisfied()
    }

    fun onAgreementItem3Clicked() {
        _agreementItem3.value = agreementItem3.value?.not()
        if (agreementItem3.value == false) {
            _agreementItemAll.value = false
        }
        checkAgreementSatisfied()
    }

    fun onAgreementItemAllClicked() {
        _agreementItemAll.value = true
        _agreementItem1.value = true
        _agreementItem2.value = true
        _agreementItem3.value = true
        checkAgreementSatisfied()
    }

    private fun checkAgreementSatisfied() {
        _isAgreementSatisfied.value = agreementItem1.value == true && agreementItem2.value == true
    }

}

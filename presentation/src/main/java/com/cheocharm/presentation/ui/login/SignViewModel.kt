package com.cheocharm.presentation.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.domain.usecase.RequestCertNumberUseCase
import com.cheocharm.domain.usecase.RequestMapZSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val requestCertNumberUseCase: RequestCertNumberUseCase,
    private val requestMapZSignUpUseCase: RequestMapZSignUpUseCase
) : ViewModel() {

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

    // SignUp
    private val _email = MutableLiveData("")
    val email: LiveData<String>
        get() = _email

    private val _isEmailValid = MutableLiveData(false)
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private val _emailCertNumber = MutableLiveData("")
    val emailCertNumber: LiveData<String>
        get() = _emailCertNumber

    private val _emailCertNumUserFilled = MutableLiveData("")
    val emailCertNumUserFilled: LiveData<String>
        get() = _emailCertNumUserFilled

    private val _isCertNumberVerified = MutableLiveData<Boolean>()
    val isCertNumberVerified: LiveData<Boolean>
        get() = _isCertNumberVerified

    private val _pwd = MutableLiveData("")
    val pwd: LiveData<String>
        get() = _pwd

    private val _pwdCheck = MutableLiveData("")
    val pwdCheck: LiveData<String>
        get() = _pwdCheck

    private val _isPwdVerified = MutableLiveData<Boolean>()
    val isPwdVerified: LiveData<Boolean>
        get() = _isPwdVerified

    private val _isPwdSame = MutableLiveData<Boolean>()
    val isPwdSame: LiveData<Boolean>
        get() = _isPwdSame

    private val _isSignUpEnabled = MutableLiveData(false)
    val isSignUpEnabled: LiveData<Boolean>
        get() = _isSignUpEnabled

    // Profile
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _profileImage = MutableLiveData<File>()
    val profileImage: LiveData<File>
        get() = _profileImage

    private val _isProfileEnabled = MutableLiveData<Boolean>()
    val isProfileEnabled: LiveData<Boolean>
        get() = _isProfileEnabled

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

    // SignUp
    fun setEmail(email: String) {
        _email.value = email
    }

    fun checkEmailValid() {
        _isEmailValid.value =
            Patterns.EMAIL_ADDRESS.matcher(email.value ?: return).matches() == true
    }

    fun requestEmailCertNumber() {
        viewModelScope.launch {
            email.value?.let {
                requestCertNumberUseCase.invoke(it)
                    .onSuccess {
                        _emailCertNumber.value = it
                    }
                    .onFailure {
                        // TODO: 테스트하는 동안만 1234
                        _emailCertNumber.value = "1234"
                    }
            }
        }
    }

    fun setEmailCertNumUserFilled(certNum: String) {
        _emailCertNumUserFilled.value = certNum
    }

    fun checkEmailCertNumber() {
        _isCertNumberVerified.value = emailCertNumber.value == emailCertNumUserFilled.value
    }

    fun setPwd(pwd: String) {
        _pwd.value = pwd
    }

    fun setPwdCheck(pwdCheck: String) {
        _pwdCheck.value = pwdCheck
    }

    fun checkPwdVerified() {
        val pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,50}$")
        _isPwdVerified.value = pattern.matcher(pwd.value ?: return).find() == true
    }

    fun checkPwdSame() {
        _isPwdSame.value = pwd.value == pwdCheck.value
    }

    fun checkSignUpEnabled() {
        _isSignUpEnabled.value =
            isEmailValid.value == true && isCertNumberVerified.value == true && isPwdVerified.value == true && isPwdSame.value == true
    }

    // Profile
    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setProfileImage(profileImage: File) {
        _profileImage.value = profileImage
        checkProfileEnabled()
    }

    fun checkProfileEnabled() {
        _isProfileEnabled.value = nickname.value.isNullOrEmpty().not() && profileImage.value != null
    }

    fun requestMapZSignUp() {
        val email = email.value ?: return
        val pwd = pwd.value ?: return
        val nickname = nickname.value ?: return
        val profileImage = profileImage.value ?: return
        val mapZSignUp = MapZSignUpRequest(email, pwd, nickname, profileImage)

        viewModelScope.launch {
            requestMapZSignUpUseCase.invoke(mapZSignUp)
                .onSuccess {
                    // TODO: 토큰 sharedpreference 저장
                }
                .onFailure {

                }
        }
    }
}

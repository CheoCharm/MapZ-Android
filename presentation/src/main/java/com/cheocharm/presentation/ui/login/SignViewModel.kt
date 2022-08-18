package com.cheocharm.presentation.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.domain.usecase.RequestCertNumberUseCase
import com.cheocharm.domain.usecase.RequestGoogleSignUpUseCase
import com.cheocharm.domain.usecase.RequestMapZSignUpUseCase
import com.cheocharm.presentation.common.Event
import com.cheocharm.presentation.model.SignType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val requestCertNumberUseCase: RequestCertNumberUseCase,
    private val requestMapZSignUpUseCase: RequestMapZSignUpUseCase,
    private val requestGoogleSignUpUseCase: RequestGoogleSignUpUseCase
) : ViewModel() {

    lateinit var signUpType: SignType
        private set

    private var googleIdToken: String? = null

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

    private val _signUpToastMessage = MutableLiveData<String>()
    val signUpToastMessage: LiveData<String>
        get() = _signUpToastMessage

    // Profile
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private var isNicknameVerified = false

    private val _profileImage = MutableLiveData<File>()
    val profileImage: LiveData<File>
        get() = _profileImage

    private val _isProfileEnabled = MutableLiveData<Boolean>()
    val isProfileEnabled: LiveData<Boolean>
        get() = _isProfileEnabled

    private val _goToSignIn = MutableLiveData<Event<Unit>>()
    val goToSignIn: LiveData<Event<Unit>>
        get() = _goToSignIn

    private val _profileToastMessage = MutableLiveData<String>()
    val profileToastMessage: LiveData<String>
        get() = _profileToastMessage

    fun setSignUpType(signUpType: SignType) {
        this.signUpType = signUpType
    }

    fun setGoogleIdToken(idToken: String) {
        googleIdToken = idToken
    }

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
                    .onSuccess { string ->
                        _emailCertNumber.value = string
                    }
                    .onFailure { throwable ->
                        // TODO: 테스트하는 동안만 1234
                        when (throwable) {
                            is Error.MapZCertNumberUnavailable -> setSignUpToastMessage(throwable.message)
                            else -> setSignUpToastMessage("이메일 인증번호 발급을 실패하였습니다.")
                        }
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

    private fun setSignUpToastMessage(message: String) {
        _signUpToastMessage.value = message
    }

    // Profile
    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun checkNicknameVerified() {
        val pattern = Pattern.compile("^[가-힣a-zA-Z0-9]{2,12}$")
        isNicknameVerified = pattern.matcher(nickname.value ?: return).find() == true
    }

    fun setProfileImage(profileImage: File) {
        _profileImage.value = profileImage
        checkProfileEnabled()
    }

    fun checkProfileEnabled() {
        _isProfileEnabled.value = nickname.value.isNullOrEmpty()
            .not() && profileImage.value != null && isNicknameVerified == true
    }

    fun requestSignUp() {
        when (signUpType) {
            SignType.MAPZ -> requestMapZSignUp()
            SignType.GOOGLE -> requestGoogleSignUp()
        }
    }

    private fun requestMapZSignUp() {
        val email = email.value ?: return
        val pwd = pwd.value ?: return
        val nickname = nickname.value ?: return
        val pushAgreement = agreementItem3.value ?: return
        val profileImage = profileImage.value ?: return
        val mapZSignUp = MapZSignUpRequest(email, pwd, nickname, pushAgreement, profileImage)

        viewModelScope.launch {
            requestMapZSignUpUseCase.invoke(mapZSignUp)
                .onSuccess {
                    setProfileToastMessage("회원가입을 완료하였습니다.")
                    _goToSignIn.value = Event(Unit)
                }
                .onFailure {
                    when (it) {
                        is Error.MapZSignUpUnavailable -> setProfileToastMessage(it.message)
                        else -> setProfileToastMessage("회원가입에 실패하였습니다.")
                    }
                }
        }
    }

    private fun requestGoogleSignUp() {
        val nickname = nickname.value ?: return
        val idToken = googleIdToken ?: return
        val pushAgreement = agreementItem3.value ?: return
        val image = profileImage.value ?: return

        viewModelScope.launch {
            requestGoogleSignUpUseCase.invoke(GoogleSignUpRequest(nickname, idToken, pushAgreement, image))
                .onSuccess {
                    setProfileToastMessage("구글 회원가입을 완료하였습니다.")
                    _goToSignIn.value = Event(Unit)
                }
                .onFailure {
                    when (it) {
                        is Error.GoogleSignInUnavailable -> setProfileToastMessage(it.message)
                        else -> setProfileToastMessage("구글 회원가입에 실패하였습니다.")
                    }
                }
        }
    }

    private fun setProfileToastMessage(message: String) {
        _profileToastMessage.value = message
    }
}

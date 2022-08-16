package com.cheocharm.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.usecase.RequestMapZSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val requestMapZSignInUseCase: RequestMapZSignInUseCase
) : ViewModel() {

    // SignIn
    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _pwd = MutableLiveData<String>()
    val pwd: LiveData<String>
        get() = _pwd

    private val _isSignInEnabled = MutableLiveData<Boolean>()
    val isSignInEnabled: LiveData<Boolean>
        get() = _isSignInEnabled

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    // SignIn
    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPwd(pwd: String) {
        _pwd.value = pwd
    }

    fun checkSignInEnabled() {
        _isSignInEnabled.value =
            email.value.isNullOrEmpty().not() && pwd.value.isNullOrEmpty().not()
    }

    fun requestMapZSignIn() {
        val emailValue = email.value ?: return
        val pwdValue = pwd.value ?: return
        viewModelScope.launch {
            requestMapZSignInUseCase.invoke(MapZSignInRequest(emailValue, pwdValue))
                .onSuccess {
                    // TODO sharedpreference 저장
                    // 메인 화면으로 이동
                }
                .onFailure {
                    when(it) {
                        is Error.MapZSignInAvailable ->  _toastMessage.value = it.message
                        else -> _toastMessage.value = "로그인에 실패하였습니다."
                    }
                }
        }
    }
}

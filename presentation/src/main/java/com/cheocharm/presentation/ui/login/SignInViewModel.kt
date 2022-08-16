package com.cheocharm.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.usecase.RequestMapZSignInUseCase
import com.cheocharm.domain.usecase.SaveAutoSignInUseCase
import com.cheocharm.domain.usecase.SaveTokenUseCase
import com.cheocharm.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val requestMapZSignInUseCase: RequestMapZSignInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveAutoSignInUseCase: SaveAutoSignInUseCase
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _pwd = MutableLiveData<String>()
    val pwd: LiveData<String>
        get() = _pwd

    private var isAutoSignIn = true

    private val _isSignInEnabled = MutableLiveData<Boolean>()
    val isSignInEnabled: LiveData<Boolean>
        get() = _isSignInEnabled

    private val _goToMain = MutableLiveData<Event<Unit>>()
    val goToMain: LiveData<Event<Unit>>
        get() = _goToMain

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPwd(pwd: String) {
        _pwd.value = pwd
    }

    fun setIsAutoSignIn(isAutoSignIn: Boolean) {
        this.isAutoSignIn = isAutoSignIn
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
                    saveTokenUseCase.invoke(it)
                    saveAutoSignInUseCase.invoke(isAutoSignIn)
                    _goToMain.value = Event(Unit)
                }
                .onFailure {
                    when (it) {
                        is Error.MapZSignInAvailable -> setToastMessage(it.message)
                        else -> setToastMessage("로그인에 실패하였습니다.")
                    }
                }
        }
    }

    private fun setToastMessage(message: String) {
        _toastMessage.value = message
    }
}

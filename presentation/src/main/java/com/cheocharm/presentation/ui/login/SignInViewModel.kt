package com.cheocharm.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.usecase.*
import com.cheocharm.presentation.common.Event
import com.cheocharm.presentation.model.SignType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val requestMapZSignInUseCase: RequestMapZSignInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveAutoSignInUseCase: SaveAutoSignInUseCase,
    private val checkAutoSignInUseCase: CheckAutoSignInUseCase,
    private val requestGoogleSignInUseCase: RequestGoogleSignInUseCase,
    private val saveSignInTypeUseCase: SaveSignInTypeUseCase
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

    private val _goToMain = MutableLiveData<Event<Boolean>>()
    val goToMain: LiveData<Event<Boolean>>
        get() = _goToMain

    private val _goToGoogleSignUpWithIdToken = MutableLiveData<Event<String>>()
    val goToGoogleSignUpWithIdToken: LiveData<Event<String>>
        get() = _goToGoogleSignUpWithIdToken

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
                    saveTokenUseCase.invoke(
                        it.accessToken ?: return@launch,
                        it.refreshToken ?: return@launch
                    )
                    saveAutoSignInUseCase.invoke(isAutoSignIn)
                    saveSignInTypeUseCase.invoke(SignType.MAPZ.str)
                    _goToMain.value = Event(true)
                }
                .onFailure {
                    when (it) {
                        is Error.MapZSignInUnavailable -> setToastMessage(it.message)
                        else -> setToastMessage("로그인에 실패하였습니다.")
                    }
                }
        }
    }

    fun checkAutoSignIn() {
        val signInCheck = checkAutoSignInUseCase.invoke()
        if (signInCheck.accessToken.isNullOrEmpty() || signInCheck.refreshToken.isNullOrEmpty()) {
            _goToMain.value = Event(false)
            return
        }

        if (signInCheck.isAutoSignIn) _goToMain.value = Event(true)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        runCatching {
            val account = completedTask.getResult(ApiException::class.java)
            viewModelScope.launch {
                account.idToken?.let { idToken ->
                    requestGoogleSignInUseCase.invoke(idToken)
                        .onSuccess { mapZSign ->
                            if (mapZSign.accessToken == null) {
                                _goToGoogleSignUpWithIdToken.value = Event(idToken)
                            } else {
                                saveTokenUseCase.invoke(
                                    mapZSign.accessToken ?: return@launch,
                                    mapZSign.refreshToken ?: return@launch
                                )
                                saveAutoSignInUseCase.invoke(isAutoSignIn)
                                saveSignInTypeUseCase.invoke(SignType.GOOGLE.str)
                                _goToMain.value = Event(true)
                            }
                        }
                        .onFailure { throwable ->
                            when (throwable) {
                                is Error.GoogleSignInUnavailable -> setToastMessage(throwable.message)
                                else -> setToastMessage("구글 로그인에 실패하였습니다.")
                            }
                        }
                }
            }
        }.onFailure {
            Log.w("handleSignInResult", "${it.message}")
        }
    }

    private fun setToastMessage(message: String) {
        _toastMessage.value = message
    }
}

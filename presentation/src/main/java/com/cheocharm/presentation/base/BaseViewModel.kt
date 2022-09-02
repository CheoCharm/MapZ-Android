package com.cheocharm.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.event_bus.EventBus
import com.cheocharm.domain.event_bus.MapZEvent
import com.cheocharm.domain.usecase.RequestSignOutUseCase
import com.cheocharm.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val requestSignOutUseCase: RequestSignOutUseCase
) : ViewModel() {

    private val _signOut = MutableLiveData<Event<Unit>>()
    val signOut: LiveData<Event<Unit>>
        get() = _signOut

    init {
        viewModelScope.launch {
            EventBus.subscribeEvent(MapZEvent.SIGN_OUT) {
                signOut()
            }
        }
    }

    private fun signOut() {
        requestSignOutUseCase.invoke()
        _signOut.value = Event(Unit)
    }
}

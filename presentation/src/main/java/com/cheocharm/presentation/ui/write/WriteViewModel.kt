package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.usecase.write.GetMyGroupsUseCase
import com.cheocharm.presentation.common.ErrorMessage
import com.cheocharm.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getMyGroupsUseCase: GetMyGroupsUseCase
) : ViewModel() {

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groups

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    init {
        fetchMyGroups()
    }

    fun fetchMyGroups() {
        viewModelScope.launch {
            val result = getMyGroupsUseCase()

            result
                .onSuccess { groups ->
                    _groups.value = groups
                }
                .onFailure { throwable ->
                    _toastText.value =
                        Event(throwable.message ?: ErrorMessage.FETCH_MY_GROUPS_FAILED)
                }
        }
    }
}

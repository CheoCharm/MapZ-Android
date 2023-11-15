package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.domain.model.Group
import com.cheocharm.presentation.common.TestValues
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor() : ViewModel() {
    private val _groups = MutableLiveData(TestValues.testGroups)
    val groups: LiveData<List<Group>> = _groups
}

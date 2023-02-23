package com.cheocharm.presentation.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class GroupCreateViewModel @Inject constructor(

) : ViewModel() {

    private val _groupImage = MutableLiveData<File>()
    val groupImage: LiveData<File>
        get() = _groupImage

    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String>
        get() = _groupName

    private val _groupBio = MutableLiveData<String>()
    val groupBio: LiveData<String>
        get() = _groupBio

    private var isGroupNameVerified = false

    val isSearchEnabled = MutableLiveData<Boolean>()

    private val _isGroupEnabled = MutableLiveData(false)
    val isGroupEnabled: LiveData<Boolean>
        get() = _isGroupEnabled

    fun setGroupImage(groupImage: File) {
        _groupImage.value = groupImage
    }

    fun setGroupName(name: String) {
        _groupName.value = name
    }

    fun setGroupBio(bio: String) {
        _groupBio.value = bio
    }

    fun checkGroupNameVerified() {
        val pattern = Pattern.compile("^[가-힣a-zA-Z0-9]{2,12}$")
        isGroupNameVerified = pattern.matcher(groupName.value ?: return).find() == true
    }

    fun checkGroupEnabled() {
        _isGroupEnabled.value = groupImage.value != null && groupName.value.isNullOrEmpty()
            .not() && groupBio.value.isNullOrEmpty().not() && isGroupNameVerified
    }
}

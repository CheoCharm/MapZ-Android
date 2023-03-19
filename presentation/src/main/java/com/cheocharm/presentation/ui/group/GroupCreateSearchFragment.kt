package com.cheocharm.presentation.ui.group

import androidx.fragment.app.activityViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentGroupCreateSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCreateSearchFragment :
    BaseFragment<FragmentGroupCreateSearchBinding>(R.layout.fragment_group_create_search) {

    private val groupCreateViewModel by activityViewModels<GroupCreateViewModel>()

}

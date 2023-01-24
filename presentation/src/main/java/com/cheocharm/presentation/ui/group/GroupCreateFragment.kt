package com.cheocharm.presentation.ui.group

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentGroupCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCreateFragment :
    BaseFragment<FragmentGroupCreateBinding>(R.layout.fragment_group_create) {

    private val groupCreateViewModel: GroupCreateViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = groupCreateViewModel

    }
}

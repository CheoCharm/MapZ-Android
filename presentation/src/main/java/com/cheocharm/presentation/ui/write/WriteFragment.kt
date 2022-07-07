package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.base.BaseFragment
import com.cheocharm.domain.Group
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentWriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {
    private val writeViewModel: WriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = writeViewModel

        val groupsAdapter = GroupsAdapter { group -> adapterOnClick(group) }
        val recyclerView: RecyclerView = binding.rvWriteGroups
        recyclerView.adapter = groupsAdapter

        writeViewModel.groups.observe(viewLifecycleOwner) {
            it?.let {
                groupsAdapter.submitList(it as MutableList<Group>)
            }
        }
    }

    private fun adapterOnClick(group: Group) {
        // TODO: 사진 선택 화면 or 일기 작성 화면으로 이동
    }
}

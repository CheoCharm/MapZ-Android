package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSearchGroupBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.ui.write.MembersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGroupFragment :
    BaseFragment<FragmentSearchGroupBinding>(R.layout.fragment_search_group) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)

    private lateinit var memberAdapter: MembersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRecyclerView()
        initObservers()
    }

    private fun initView() {
        binding.toolbarSearchGroupClose.apply {
            (activity as MainActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_close_square)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initRecyclerView() {
        memberAdapter = MembersAdapter()
        binding.rvSearchGroup.adapter = memberAdapter
    }

    private fun initObservers() {
        searchViewModel.selectedGroup.observe(viewLifecycleOwner) {
            binding.group = it
            memberAdapter.submitList(it.members)
        }
    }
}

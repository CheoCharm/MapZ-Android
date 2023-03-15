package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.common.EventObserver
import com.cheocharm.presentation.databinding.ActivityMainBinding
import com.cheocharm.presentation.databinding.FragmentSearchResultBinding
import com.cheocharm.presentation.model.GroupModel
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var groupResultListAdapter: GroupsPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = searchViewModel

        mainActivityBinding = (activity as MainActivity).getBinding()

        initView()
        initRecyclerView()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        mainActivityBinding.fragmentMainMap.visibility = View.GONE
        mainActivityBinding.bottomNavMain.visibility = View.VISIBLE
    }

    private fun initView() {
        binding.toolbarSearchResult.apply {
            (activity as MainActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initObservers() {
        searchViewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
        searchViewModel.groupSearchResultList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                groupResultListAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        groupResultListAdapter = GroupsPagingAdapter { group -> onGroupClicked(group) }

        binding.rvSearchResult.apply {
            addItemDecoration(decoration)
            adapter = groupResultListAdapter
        }

        lifecycleScope.launch {
            groupResultListAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvSearchResult.scrollToPosition(0) }
        }

        groupResultListAdapter.addLoadStateListener {
            binding.pbSearchResult.isVisible = it.source.refresh is LoadState.Loading
        }
    }

    private fun onGroupClicked(group: GroupModel) {
        searchViewModel.setSelectedGroup(group)
        findNavController().navigate(R.id.action_searchResultFragment_to_searchGroupFragment)
    }
}

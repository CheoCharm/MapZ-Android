package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.cheocharm.base.BaseFragment
import com.cheocharm.domain.model.Group
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSearchResultBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.ui.write.GroupsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)
    private lateinit var groupResultListAdapter: GroupsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = searchViewModel

        initView()
        initRecyclerView()
        initObservers()
    }

    private fun initView() {
        binding.toolbarSearchResult.apply {
            (activity as MainActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.bottomNavMain.visibility = View.GONE
    }

    private fun initObservers() {
        searchViewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        searchViewModel.groupSearchResultList.observe(viewLifecycleOwner) {
            groupResultListAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        groupResultListAdapter = GroupsAdapter { group -> onGroupClicked(group) }

        binding.rvSearchResult.apply {
            addItemDecoration(decoration)
            adapter = groupResultListAdapter
        }
    }

    private fun onGroupClicked(group: Group) {
        searchViewModel.setSelectedGroup(group)
        findNavController().navigate(R.id.action_searchResultFragment_to_searchGroupFragment)
    }
}

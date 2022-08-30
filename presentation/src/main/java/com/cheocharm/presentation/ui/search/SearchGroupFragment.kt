package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.common.EventObserver
import com.cheocharm.presentation.common.GROUP_JOIN_REQUEST_BOTTOM
import com.cheocharm.presentation.databinding.ActivityMainBinding
import com.cheocharm.presentation.databinding.FragmentSearchGroupBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.ui.write.MembersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGroupFragment :
    BaseFragment<FragmentSearchGroupBinding>(R.layout.fragment_search_group) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)
    private lateinit var mainActivityBinding: ActivityMainBinding

    private lateinit var memberAdapter: MembersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityBinding = (activity as MainActivity).getBinding()

        initView()
        initRecyclerView()
        initObservers()
        initButtons()
    }

    override fun onResume() {
        super.onResume()

        mainActivityBinding.bottomNavMain.visibility = View.GONE
    }

    private fun initView() {
        binding.toolbarSearchGroupClose.apply {
            (activity as MainActivity).setSupportActionBar(this)
            (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
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
        searchViewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
        searchViewModel.searchGroupJoinBottom.observe(viewLifecycleOwner, EventObserver {
            SearchGroupBottomSheetFragment.newInstance(bundleOf(GROUP_JOIN_REQUEST_BOTTOM to searchViewModel.searchGroupName.value))
                .show(requireActivity().supportFragmentManager, null)
        })
    }

    private fun initButtons() {
        binding.btnSearchGroupJoin.setOnClickListener {
            searchViewModel.joinGroup()
        }
    }
}

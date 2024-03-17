package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cheocharm.domain.model.Group
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.common.EventObserver
import com.cheocharm.presentation.databinding.FragmentGroupsBinding
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupsFragment : BaseFragment<FragmentGroupsBinding>(R.layout.fragment_groups) {
    private val groupsViewModel: GroupsViewModel by hiltNavGraphViewModels(R.id.write)

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = groupsViewModel

        (requireActivity() as MainActivity).setStatusBarColor(R.color.map_z_red_500, false)

        setupSwipeRefreshLayout()
        setupRecyclerView()
        setupToast()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.containerGroupsRefresh

        swipeRefreshLayout.setOnRefreshListener {
            groupsViewModel.fetchMyGroups()
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.rvGroupsGroups
        val groupsAdapter = GroupsAdapter { group -> adapterOnClick(group) }
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        with(recyclerView) {
            addItemDecoration(decoration)
            adapter = groupsAdapter
        }

        groupsViewModel.groups.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false

            it?.let {
                Log.d(logTag, it.toString())
                groupsAdapter.submitList(it)
            }
        }

        groupsViewModel.fetchMyGroups()
    }

    private fun adapterOnClick(group: Group) {
        val action = GroupsFragmentDirections.actionWriteDestToPictureFragment()
        findNavController().navigate(action)
    }

    private fun setupToast() {
        groupsViewModel.toastText.observe(viewLifecycleOwner, EventObserver {
            swipeRefreshLayout.isRefreshing = false

            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.e(logTag, it)
        })
    }

    companion object {
        private val logTag = GroupsFragment::class.java.name
    }
}

package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSearchBinding
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = searchViewModel

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = false

        initEditTexts()
        initObservers()
    }

    private fun initEditTexts() {
        binding.etSearchSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.setSearchGroupName(textView.text.toString())
                searchViewModel.searchGroup(textView.text.toString())
                findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment)
            }
            false
        }
    }

    private fun initObservers() {

    }
}

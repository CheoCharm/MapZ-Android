package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSearchBinding
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = searchViewModel

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = false

        searchViewModel.countUp()
    }
}

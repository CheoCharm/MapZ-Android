package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.ActivityMainBinding
import com.cheocharm.presentation.databinding.FragmentSearchBinding
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by hiltNavGraphViewModels(R.id.search)
    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = searchViewModel

        val mainActivity = requireActivity() as MainActivity
        mainActivity.setMapVisible(false)
        mainActivityBinding = mainActivity.getBinding()

        initEditTexts()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        mainActivityBinding.bottomNavMain.visibility = View.VISIBLE
    }

    private fun initEditTexts() {
        binding.etSearchSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (textView.text.isNullOrEmpty().not()) {
                    searchViewModel.setSearchGroupName(textView.text.toString())
                    findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment)
                }
            }
            false
        }
    }

    private fun initObservers() {

    }
}

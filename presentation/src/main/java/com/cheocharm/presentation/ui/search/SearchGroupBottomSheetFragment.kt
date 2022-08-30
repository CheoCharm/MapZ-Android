package com.cheocharm.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.cheocharm.presentation.R
import com.cheocharm.presentation.common.GROUP_JOIN_REQUEST_BOTTOM
import com.cheocharm.presentation.databinding.FragmentSearchGroupBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGroupBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSearchGroupBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_group_bottom_sheet,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            binding.tvSearchGroupRequest.text = String.format(
                requireActivity().getString(R.string.search_group_join_request),
                it[GROUP_JOIN_REQUEST_BOTTOM]
            )
            it.remove(GROUP_JOIN_REQUEST_BOTTOM)
        }

        initButtons()
    }

    private fun initButtons() {
        binding.btnSearchGroupConfirm.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): SearchGroupBottomSheetFragment {
            return SearchGroupBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}

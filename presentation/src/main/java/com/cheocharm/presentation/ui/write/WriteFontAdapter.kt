package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.WriteFontFamilyBinding
import com.cheocharm.presentation.databinding.WriteFontSizeBinding

class WriteFontAdapter(fragment: WriteFragment) : FragmentStateAdapter(fragment) {
    val fragments = listOf(WriteFontFamilyFragment(), WriteFontSizeFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}

class WriteFontFamilyFragment : BaseFragment<WriteFontFamilyBinding>(R.layout.write_font_family) {
    val viewModel by viewModels<WriteFontViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
    }
}

class WriteFontSizeFragment : BaseFragment<WriteFontSizeBinding>(R.layout.write_font_size) {
    private val viewModel by viewModels<WriteFontViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
    }
}

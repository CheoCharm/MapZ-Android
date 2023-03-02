package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.WriteFontObject1Binding
import com.cheocharm.presentation.databinding.WriteFontObject2Binding

class WriteFontAdapter(fragment: WriteFragment) : FragmentStateAdapter(fragment) {
    val fragments = listOf(WriteFontObjectFirstFragment(), WriteFontObjectSecondFragment())

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}

class WriteFontObjectFirstFragment :
    BaseFragment<WriteFontObject1Binding>(R.layout.write_font_object_1) {
    private val viewModel: WriteFontObjectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
    }
}

class WriteFontObjectSecondFragment :
    BaseFragment<WriteFontObject2Binding>(R.layout.write_font_object_2) {
    private val viewModel: WriteFontObjectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
    }
}

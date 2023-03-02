package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.WriteFontObject1Binding

class WriteFontAdapter(fragment: WriteFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            WriteFontObjectFirstFragment()
        } else {
            WriteFontObjectSecondFragment()
        }
    }
}

class WriteFontObjectFirstFragment :
    BaseFragment<WriteFontObject1Binding>(R.layout.write_font_object_1) {

    private val viewModel = WriteFontObjectViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
    }
}

class WriteFontObjectSecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.write_font_object_2, container, false)
    }
}

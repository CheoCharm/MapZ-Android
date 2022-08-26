package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.View
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentWriteBinding

class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("WriteFragment")
    }
}

package com.cheocharm.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSignUpProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpProfileFragment :
    BaseFragment<FragmentSignUpProfileBinding>(R.layout.fragment_sign_up_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }

    private fun initButton() {
        binding.btnSignUpProfileBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

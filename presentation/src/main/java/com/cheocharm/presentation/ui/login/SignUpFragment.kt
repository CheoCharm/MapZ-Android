package com.cheocharm.presentation.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val signViewModel by activityViewModels<SignViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initObservers()
    }

    private fun initButton() {
        binding.btnSignUpBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUpNext.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signUpProfileFragment)
        }
        binding.etSignUpEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                signViewModel.setEmail(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                signViewModel.checkEmailValid()
            }
        })
        binding.btnSignUpCertRequest.setOnClickListener {
            signViewModel.requestEmailCertNumber()
        }
        binding.etSignUpCertRequest.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun initObservers() {
        signViewModel.isEmailValid.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSignUpCertRequest.visibility = View.VISIBLE
                binding.etSignUpCertRequest.visibility = View.VISIBLE
            } else {
                binding.btnSignUpCertRequest.visibility = View.INVISIBLE
                binding.etSignUpCertRequest.visibility = View.INVISIBLE
            }
        }
        signViewModel.emailCertNumber.observe(viewLifecycleOwner) {
            if (it.length >= 4) binding.tvSignUpTimeRemain.visibility = View.VISIBLE
        }
    }
}

package com.cheocharm.presentation.ui.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
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

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                signViewModel.setEmailCertNumUserFilled(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 4) signViewModel.checkEmailCertNumber()
            }
        })
        binding.etSignUpPwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                signViewModel.setPwd(p0.toString())
                signViewModel.checkPwdVerified()
                signViewModel.checkPwdSame()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etSignUpPwdCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                signViewModel.setPwdCheck(p0.toString())
                signViewModel.checkPwdSame()
            }

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
        signViewModel.isCertNumberVerified.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSignUpCertRequest.visibility = View.INVISIBLE
                binding.etSignUpCertRequest.visibility = View.INVISIBLE
                binding.tvSignUpCertComplete.visibility = View.VISIBLE
                binding.tvSignUpPwd.visibility = View.VISIBLE
                binding.etSignUpPwd.visibility = View.VISIBLE
                binding.tvSignUpPwdCondition.visibility = View.VISIBLE
                binding.tvSignUpPwdCheck.visibility = View.VISIBLE
                binding.etSignUpPwdCheck.visibility = View.VISIBLE
                binding.tvSignUpTimeRemain.visibility = View.INVISIBLE
                binding.tvSignUpCertWrong.visibility = View.INVISIBLE

                binding.btnSignUpCertRequest.isEnabled = false
                binding.etSignUpEmail.isEnabled = false
                binding.etSignUpCertRequest.isEnabled = false
                hideEtCertNumber()
            } else {
                binding.tvSignUpCertWrong.isVisible = true
            }
        }
        signViewModel.isPwdVerified.observe(viewLifecycleOwner) {
            binding.tvSignUpPwdErr.isVisible = it.not()
        }
        signViewModel.isPwdSame.observe(viewLifecycleOwner) {
            binding.tvSignUpPwdCheckErr.isVisible = it.not()
        }
        signViewModel.isSignUpEnabled.observe(viewLifecycleOwner) {
            binding.btnSignUpNext.isEnabled = it
        }
    }

    private fun hideEtCertNumber() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSignUpCertRequest.windowToken, 0)
    }
}

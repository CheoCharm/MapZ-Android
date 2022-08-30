package com.cheocharm.presentation.ui.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.presentation.base.BaseFragment
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
        binding.etSignUpCertRequest.doOnTextChanged { text, start, before, count ->
            signViewModel.setEmailCertNumUserFilled(text.toString())
            if (text?.length == 4) signViewModel.checkEmailCertNumber()
        }
        binding.etSignUpPwd.doOnTextChanged { text, start, before, count ->
            signViewModel.setPwd(text.toString())
            signViewModel.checkPwdVerified()
            signViewModel.checkPwdSame()
            signViewModel.checkSignUpEnabled()
        }
        binding.etSignUpPwdCheck.doOnTextChanged { text, start, before, count ->
            signViewModel.setPwdCheck(text.toString())
            signViewModel.checkPwdSame()
            signViewModel.checkSignUpEnabled()
        }
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
        signViewModel.signUpToastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideEtCertNumber() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSignUpCertRequest.windowToken, 0)
    }
}

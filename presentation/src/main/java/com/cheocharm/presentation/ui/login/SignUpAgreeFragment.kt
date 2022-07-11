package com.cheocharm.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentSignUpAgreeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpAgreeFragment :
    BaseFragment<FragmentSignUpAgreeBinding>(R.layout.fragment_sign_up_agree) {

    private val signViewModel by activityViewModels<SignViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initButton()
    }

    private fun initButton() {
        binding.btnSignUpAgreeBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUpAgreeNext.setOnClickListener {
            findNavController().navigate(R.id.action_signUpAgreeFragment_to_signUpFragment)
        }
    }

    private fun initView() {
        binding.containerSignUpAgreeItem1.tvSignUpAgreeDescription.visibility = View.GONE

        binding.containerSignUpAgreeItem2.tvSignUpAgreeDescription.visibility = View.GONE
        binding.containerSignUpAgreeItem2.tvSignUpAgreeItem.text =
            getString(R.string.sign_up_agree_item_2)

        binding.containerSignUpAgreeItem3.tvSignUpAgreeDescription.visibility = View.GONE
        binding.containerSignUpAgreeItem3.tvSignUpAgreeItem.text =
            getString(R.string.sign_up_agree_item_3)
        binding.containerSignUpAgreeItem3.tvSignUpAgreeCheck.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.gray_dark
            )
        )
        binding.containerSignUpAgreeItem3.tvSignUpAgreeCheck.text =
            getString(R.string.sign_up_agree_optional)

        binding.containerSignUpAgreeAll.tvSignUpAgreeCheck.visibility = View.GONE
        binding.containerSignUpAgreeAll.tvSignUpAgreeSeeDetail.visibility = View.GONE
        binding.containerSignUpAgreeAll.dividerSignUp.visibility = View.GONE
        binding.containerSignUpAgreeAll.tvSignUpAgreeItem.text =
            getString(R.string.sign_up_agree_all)
    }
}

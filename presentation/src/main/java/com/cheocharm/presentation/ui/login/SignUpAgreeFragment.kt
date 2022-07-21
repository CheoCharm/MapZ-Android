package com.cheocharm.presentation.ui.login

import android.content.res.ColorStateList
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
        initObservers()
    }

    private fun initButton() {
        binding.btnSignUpAgreeBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUpAgreeNext.setOnClickListener {
            findNavController().navigate(R.id.action_signUpAgreeFragment_to_signUpFragment)
        }
        binding.containerSignUpAgreeItem1.btnSignUpAgree.setOnClickListener {
            signViewModel.onAgreementItem1Clicked()
        }
        binding.containerSignUpAgreeItem2.btnSignUpAgree.setOnClickListener {
            signViewModel.onAgreementItem2Clicked()
        }
        binding.containerSignUpAgreeItem3.btnSignUpAgree.setOnClickListener {
            signViewModel.onAgreementItem3Clicked()
        }
        binding.containerSignUpAgreeAll.btnSignUpAgree.setOnClickListener {
            signViewModel.onAgreementItemAllClicked()
        }
    }

    private fun initObservers() {
        signViewModel.isAgreementSatisfied.observe(viewLifecycleOwner) {
            binding.btnSignUpAgreeNext.isEnabled = it
            if (it) {
                binding.btnSignUpAgreeNext.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.map_z_red_500
                    )
                )
                binding.btnSignUpAgreeNext.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
            } else {
                binding.btnSignUpAgreeNext.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.gray_extra_300
                    )
                )
                binding.btnSignUpAgreeNext.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.black
                    )
                )
            }
        }
        signViewModel.agreementItem1.observe(viewLifecycleOwner) {
            binding.containerSignUpAgreeItem1.btnSignUpAgree.isChecked = it
        }
        signViewModel.agreementItem2.observe(viewLifecycleOwner) {
            binding.containerSignUpAgreeItem2.btnSignUpAgree.isChecked = it
        }
        signViewModel.agreementItem3.observe(viewLifecycleOwner) {
            binding.containerSignUpAgreeItem3.btnSignUpAgree.isChecked = it
        }
        signViewModel.agreementItemAll.observe(viewLifecycleOwner) {
            binding.containerSignUpAgreeAll.btnSignUpAgree.isChecked = it
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

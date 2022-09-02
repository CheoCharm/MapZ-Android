package com.cheocharm.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.common.GOOGLE_ID_TOKEN
import com.cheocharm.presentation.common.SIGN_UP_TYPE
import com.cheocharm.presentation.databinding.FragmentSignUpAgreeBinding
import com.cheocharm.presentation.model.SignType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpAgreeFragment :
    BaseFragment<FragmentSignUpAgreeBinding>(R.layout.fragment_sign_up_agree) {

    private val signViewModel by activityViewModels<SignViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpType: SignType = (arguments?.get(SIGN_UP_TYPE) ?: SignType.MAPZ) as SignType
        val googleIdToken: String? = arguments?.get(GOOGLE_ID_TOKEN) as String?
        signViewModel.setSignUpType(signUpType)
        googleIdToken?.let {
            signViewModel.setGoogleIdToken(it)
        }

        initView()
        initButton()
        initObservers()
    }

    private fun initButton() {
        binding.btnSignUpAgreeBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUpAgreeNext.setOnClickListener {
            when (signViewModel.signUpType) {
                SignType.MAPZ -> findNavController().navigate(R.id.action_signUpAgreeFragment_to_signUpFragment)
                SignType.GOOGLE -> findNavController().navigate(R.id.action_signUpAgreeFragment_to_signUpProfileFragment)
            }
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

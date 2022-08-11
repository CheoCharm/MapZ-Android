package com.cheocharm.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentMyPageBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.ui.login.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = myPageViewModel

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = false

        initButton()
        myPageViewModel.countUp()
    }

    private fun initButton() {
        binding.btnMyPageSignOut.setOnClickListener {
            myPageViewModel.requestSignOut()
            val intent = Intent(requireActivity(), SignActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }
}

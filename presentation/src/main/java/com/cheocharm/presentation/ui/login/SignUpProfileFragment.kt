package com.cheocharm.presentation.ui.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.util.UriUtil
import com.cheocharm.presentation.databinding.FragmentSignUpProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpProfileFragment :
    BaseFragment<FragmentSignUpProfileBinding>(R.layout.fragment_sign_up_profile) {

    private val signViewModel by activityViewModels<SignViewModel>()
    private lateinit var galleryImageLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initObservers()
        initGalleryLauncher()
    }

    private fun initButton() {
        binding.btnSignUpProfileBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivSignUpProfile.setOnClickListener {
            selectProfileImage()
        }
        binding.ivSignUpProfileUser.setOnClickListener {
            selectProfileImage()
        }
        binding.btnSignUpProfileGallery.setOnClickListener {
            selectProfileImage()
        }
        binding.etSignUpProfileNickname.doOnTextChanged { text, start, before, count ->
            signViewModel.setNickname(text.toString())
            signViewModel.checkNicknameVerified()
            signViewModel.checkProfileEnabled()
        }
        binding.btnSignUpProfileComplete.setOnClickListener {
            signViewModel.requestSignUp()
        }
    }

    private fun initObservers() {
        signViewModel.isProfileEnabled.observe(viewLifecycleOwner) {
            binding.btnSignUpProfileComplete.isEnabled = it
        }
        signViewModel.profileToastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        signViewModel.goToSignIn.observe(viewLifecycleOwner) {
            val intent = requireActivity().intent
            requireActivity().finish()
            startActivity(intent)
        }
    }

    private fun initGalleryLauncher() {
        galleryImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK && activityResult.data != null) {
                    val imageUri = activityResult.data?.data
                    runCatching {
                        imageUri?.let { uri ->
                            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(
                                    requireActivity().contentResolver, uri
                                )
                            } else {
                                val source = ImageDecoder.createSource(
                                    requireActivity().contentResolver,
                                    uri
                                )
                                ImageDecoder.decodeBitmap(source)
                            }
                            val file = UriUtil.getFileFromUri(requireActivity(), uri)
                            signViewModel.setProfileImage(file)
                            signViewModel.checkProfileEnabled()
                            binding.ivSignUpProfile.setImageBitmap(bitmap)
                            binding.ivSignUpProfile.background = null
                            binding.ivSignUpProfileUser.isVisible = false
                        }
                    }.onFailure {
                        Log.e("SignUpProfileFragment", "${it.message}")
                    }
                } else if (activityResult.resultCode == RESULT_CANCELED) {
                    Toast.makeText(requireActivity(), "사진 선택 취소", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun selectProfileImage() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryImageLauncher.launch(intent)
    }
}

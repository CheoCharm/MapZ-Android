package com.cheocharm.presentation.ui.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
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
        binding.etSignUpProfileNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                signViewModel.setNickname(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun initObservers() {

    }

    private fun initGalleryLauncher() {
        galleryImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK && activityResult.data != null) {
                    val imageUri = activityResult.data?.data
                    runCatching {
                        imageUri.let { uri ->
                            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(
                                    requireActivity().contentResolver, uri
                                )
                            } else {
                                val source = ImageDecoder.createSource(
                                    requireActivity().contentResolver,
                                    imageUri ?: return@registerForActivityResult
                                )
                                ImageDecoder.decodeBitmap(source)
                            }
                            binding.ivSignUpProfileUser.setImageBitmap(bitmap)
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

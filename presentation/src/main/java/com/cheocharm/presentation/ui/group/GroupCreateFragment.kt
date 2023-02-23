package com.cheocharm.presentation.ui.group

import android.app.Activity
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
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentGroupCreateBinding
import com.cheocharm.presentation.util.UriUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCreateFragment :
    BaseFragment<FragmentGroupCreateBinding>(R.layout.fragment_group_create) {

    private val groupCreateViewModel: GroupCreateViewModel by activityViewModels()
    private lateinit var galleryImageLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = groupCreateViewModel

        initButtons()
        initGalleryLauncher()
    }

    private fun initButtons() {
        binding.toolbarGroupCreate.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.location_confirm -> {
                    if (groupCreateViewModel.isGroupEnabled.value == true) findNavController().navigate(
                        R.id.action_groupCreateFragment_to_groupCreateSearchFragment
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.toolbarGroupCreate.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.ivGroupCreateGroup.setOnClickListener {
            selectGroupImage()
        }
        binding.etGroupCreateName.doOnTextChanged { text, start, before, count ->
            groupCreateViewModel.setGroupName(text.toString())
            groupCreateViewModel.checkGroupNameVerified()
            groupCreateViewModel.checkGroupEnabled()
        }
        binding.etGroupCreateBio.doOnTextChanged { text, start, before, count ->
            groupCreateViewModel.setGroupBio(text.toString())
            groupCreateViewModel.checkGroupEnabled()
        }
    }

    private fun initGalleryLauncher() {
        galleryImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK && activityResult.data != null) {
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
                            groupCreateViewModel.setGroupImage(file)
                            binding.ivGroupCreateGroup.setImageBitmap(bitmap)
                            binding.tvGroupCreateImage.isVisible = false
                            binding.ivGroupCreateImage.isVisible = false
                        }
                    }.onFailure {
                        Log.e("SignUpProfileFragment", "${it.message}")
                    }
                } else if (activityResult.resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(requireActivity(), "사진 선택 취소", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun selectGroupImage() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryImageLauncher.launch(intent)
    }
}

package com.cheocharm.presentation.ui.write

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentPictureBinding

const val PICK_IMAGE = 1111

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {
    private val pictureViewModel: PictureViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.toolbarPicture) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = PictureFragmentDirections.actionPictureFragmentToWriteDest()
                findNavController().navigate(action)
            }
        }

        binding.btnPictureGet.setOnClickListener {
            getPicture()
        }

        pictureViewModel.picture.observe(viewLifecycleOwner) {
            binding.ivPictureSelected.apply {
                binding.groupPictureDesc.isVisible = false
                setImageURI(it)
            }
        }
    }

    private fun getPicture() {
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }

        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImage = data?.data

            selectedImage?.let {
                pictureViewModel.setPicture(selectedImage)
            }
        }
    }
}

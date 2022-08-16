package com.cheocharm.presentation.ui.write

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentPictureBinding
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.model.LatLng

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {
    private val pictureViewModel: PictureViewModel by navGraphViewModels(R.id.write)

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                requireContext().contentResolver.openInputStream(uri)?.let { inputStream ->
                    val exif = ExifInterface(inputStream)
                    val latLng = exif.latLong?.let {
                        LatLng(it[0], it[1])
                    }
                    val picture = Picture(uri, latLng)

                    pictureViewModel.setPicture(picture)
                    GeocodeUtil.execute(requireContext(), picture)
                    inputStream.close()
                }

                navigateToLocationFragment()
            }
        }

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
            getContent.launch("image/*")
        }
    }

    private fun navigateToLocationFragment() {
        val action = PictureFragmentDirections.actionPictureFragmentToLocationFragment()
        findNavController().navigate(action)
    }
}

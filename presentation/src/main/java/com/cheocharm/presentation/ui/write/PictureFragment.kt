package com.cheocharm.presentation.ui.write

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentPictureBinding
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.model.LatLng

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write)

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach {
                if (it.value.not()) {
                    TODO("권한 없을 때 처리")
                }
            }
        }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                requireContext().contentResolver.openInputStream(uri)?.let { inputStream ->
                    val exif = ExifInterface(inputStream)
                    val latLng = exif.latLong?.let { array ->
                        LatLng(array[0], array[1])
                    }
                    val picture = Picture(uri, latLng)
                    val geocodeUtil = GeocodeUtil(requireContext())

                    locationViewModel.loadPicture(picture, geocodeUtil)
                    inputStream.close()
                }

                navigateToLocationFragment()
            }
        }

    private fun navigateToLocationFragment() {
        val action = PictureFragmentDirections.actionPictureFragmentToLocationFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchPermissionRequest()

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

    private fun launchPermissionRequest() {
        requestPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            )
        )
    }
}

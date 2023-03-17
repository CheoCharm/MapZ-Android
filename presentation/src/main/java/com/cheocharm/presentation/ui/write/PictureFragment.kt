package com.cheocharm.presentation.ui.write

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write)

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.any { it.value.not() }) {
                val snackbar = Snackbar.make(
                    binding.containerPicture,
                    R.string.picture_set_permission,
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction(R.string.picture_snackbar_action) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
                snackbar.show()
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
                    val geocodeUtil = GeocodeUtil(requireContext(), Dispatchers.IO)

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

        setupToolbar()

        requestPermissions()

        binding.btnPictureGetPicture.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun setupToolbar() {
        with(binding.toolbarPicture) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = PictureFragmentDirections.actionPictureFragmentToWriteDest()
                findNavController().navigate(action)
            }
        }
    }

    private fun requestPermissions() {
        when {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Toast.makeText(
                    context,
                    R.string.picture_permission_rationale,
                    Toast.LENGTH_SHORT
                ).show()

                launchPermissionRequest()
            }
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED -> {
                launchPermissionRequest()
            }
        }
    }

    private fun launchPermissionRequest() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        requestPermissionsLauncher.launch(permissions)
    }
}

package com.cheocharm.presentation.ui.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentPictureBinding
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {
    private val locationViewModel: LocationViewModel by hiltNavGraphViewModels(R.id.write)

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value.not() }) {
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
    private val getContentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val pictures = mutableListOf<Picture>()
                val clipData = it?.data?.clipData
                val geocodeUtil = GeocodeUtil(requireContext(), Dispatchers.IO)

                if (clipData == null) {
                    val uri = it?.data?.data
                    val picture = uri?.toPicture()

                    picture?.let { pic ->
                        pictures.add(pic)
                    }
                } else {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        val picture = uri.toPicture()

                        picture?.let { pic ->
                            pictures.add(pic)
                        }
                    }

                }

                locationViewModel.loadPicture(pictures, geocodeUtil)
                navigateToLocationFragment()
            }
        }

    private fun Uri.toPicture(): Picture? {
        var picture: Picture? = null

        requireContext().contentResolver.openInputStream(this)?.let { inputStream ->
            val exif = ExifInterface(inputStream)
            val latLng = exif.latLong?.let { array ->
                LatLng(array[0], array[1])
            }

            picture = Picture(this, latLng)

            inputStream.close()
        }

        return picture
    }

    private fun navigateToLocationFragment() {
        val action = PictureFragmentDirections.actionPictureFragmentToLocationFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupButton()

        requestPermissions()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setStatusBarColor(R.color.white, true)

        with(binding.toolbarPicture) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = PictureFragmentDirections.actionPictureFragmentToWriteDest()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupButton() {
        binding.btnPictureGetPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.CONTENT_TYPE
            )
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            getContentLauncher.launch(intent)
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

package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentLocationBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.util.UriUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location),
    MenuProvider {
    private val pictureViewModel: PictureViewModel by navGraphViewModels(R.id.write)
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write) { defaultViewModelProviderFactory }

    private var draggableMarker: Marker? = null
    private var address: String? = null
    private var location: LatLng? = null
    private var file: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val picturesAdapter = PicturesAdapter()

        binding.viewmodel = pictureViewModel
        binding.rvLocationPictures.apply {
            adapter = picturesAdapter
        }

        with(binding.toolbarLocation) {
            (activity as MainActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = LocationFragmentDirections.actionLocationFragmentToPictureFragment()
                findNavController().navigate(action)
            }
        }

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = true

        val mapFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_main_map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapLoadedCallback {
                // TODO: 마커 생성
            }
        }

        pictureViewModel.picture.observe(viewLifecycleOwner) { picture ->
            picture?.let { pic ->
                picturesAdapter.submitList(listOf(pic))

                address = pic.address
                location = pic.latLng

                activity?.applicationContext?.let { context ->
                    file = UriUtil.getFileFromUri(context, pic.uri)
                }

//                val selectedLocation = pic.latLng
//                if (selectedLocation != null) {
//                    val markerOptions = MarkerOptions()
//                        .position(selectedLocation)
//                        .draggable(true)
//                    draggableMarker = it.addMarker(markerOptions)
//                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15F))
//                } else {
//                    // TODO: 사진에 장소 정보가 없으면 기본 위치로 카메라 이동
//                }
            }
        }

        locationViewModel.result.observe(viewLifecycleOwner) {
            if (locationViewModel.updated) {
                it?.let { result ->
                    if (result.isSuccessful) {
                        val action = LocationFragmentDirections.actionLocationFragmentToWriteFragment()
                        findNavController().navigate(action)

                        locationViewModel.updated = false
                    } else {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        locationViewModel.updated = false
        draggableMarker?.remove()
        super.onDestroyView()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_base, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_base_confirm -> {
                file?.let {
                    locationViewModel.uploadImages(
                        TEST_GROUP_ID,
                        address,
                        location?.latitude,
                        location?.longitude,
                        listOf(it)
                    )
                }

                true
            }
            else -> false
        }
    }

    companion object {
        private const val TEST_GROUP_ID = 25L
    }
}

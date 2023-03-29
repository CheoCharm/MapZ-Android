package com.cheocharm.presentation.ui.write

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.common.DEFAULT_ZOOM_LEVEL
import com.cheocharm.presentation.common.EventObserver
import com.cheocharm.presentation.common.SOUTH_KOREA_LAT
import com.cheocharm.presentation.common.SOUTH_KOREA_LNG
import com.cheocharm.presentation.common.SOUTH_KOREA_ZOOM_LEVEL
import com.cheocharm.presentation.common.toLatLng
import com.cheocharm.presentation.databinding.FragmentLocationBinding
import com.cheocharm.presentation.enum.LatLngSelectionType
import com.cheocharm.presentation.model.Picture
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.util.GeocodeUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location),
    MenuProvider {
    private val locationViewModel by navGraphViewModels<LocationViewModel>(R.id.write) { defaultViewModelProviderFactory }
    private val writeViewModel by navGraphViewModels<WriteViewModel>(R.id.write) { defaultViewModelProviderFactory }

    private lateinit var map: GoogleMap
    private lateinit var geocodeUtil: GeocodeUtil

    private var initialLatLng: LatLng? = null
    private var initialType: LatLngSelectionType? = null

    private var draggableMarker: Marker? = null
    private var address: String? = null
    private var location: LatLng? = null
    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geocodeUtil = GeocodeUtil(requireContext())

        setHasOptionsMenu(true)
    }

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

        binding.viewmodel = locationViewModel
        binding.rvLocationPictures.apply {
            adapter = picturesAdapter
        }

        val mainActivity = requireActivity() as MainActivity
        mainActivity.setMapVisible(true)

        with(binding.toolbarLocation) {
            mainActivity.setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                mainActivity.onBackPressed()
            }
        }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val uri = getString(KEY_URI)
                val lat = getString(KEY_LAT)?.toDoubleOrNull()
                val lng = getString(KEY_LNG)?.toDoubleOrNull()
                val isDefaultLocation = getBoolean(KEY_IS_DEFAULT_LOCATION)

                if (uri != null) {
                    val latLng = if (lat != null && lng != null) LatLng(lat, lng) else null
                    val picture = Picture(Uri.parse(uri), latLng)

                    locationViewModel.loadPicture(picture, geocodeUtil)
                }

                if (isDefaultLocation) {
                    val typeDefault = LatLngSelectionType.DEFAULT
                    initialType = typeDefault
                    locationViewModel.setSelectedLatLng(defaultLatLng.toDoubleArray(), typeDefault)
                }
            }
        }

        val mapFragment = mainActivity.getMap()
        mapFragment?.getMapAsync { googleMap ->
            map = googleMap

            googleMap.setOnMapLoadedCallback {
                val top = binding.toolbarLocation.height
                val bottom = binding.containerLocationPictures.height
                googleMap.setPadding(0, top, 0, bottom)

                locationViewModel.picture.observe(viewLifecycleOwner) { picture ->
                    picture?.let { pic ->
                        picturesAdapter.submitList(listOf(pic))

                        val selectedLatLng = pic.latLng

                        if (selectedLatLng != null) {
                            initTypeToSpecified(selectedLatLng)
                        } else if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            val locationClient = mainActivity.getLocationClient()
                            locationClient?.lastLocation?.addOnSuccessListener {
                                it?.let { location ->
                                    initTypeToCurrent(location.toLatLng())
                                }
                            }
                        } else {
                            initTypeToDefault()
                        }
                    }
                }
            }

            googleMap.setOnCameraMoveListener {
                val latLng = map.cameraPosition.target
                locationViewModel.setSelectedLatLng(
                    latLng.toDoubleArray(),
                    LatLngSelectionType.SPECIFIED
                )
            }

            googleMap.setOnCameraIdleListener {
                val latLng = map.cameraPosition.target
                val type = if (initialLatLng != null &&
                    distanceBetween(initialLatLng!!, latLng) <= 1
                ) {
                    initialType ?: LatLngSelectionType.SPECIFIED
                } else {
                    LatLngSelectionType.SPECIFIED
                }
                val array = latLng.toDoubleArray()

                if (type == LatLngSelectionType.SPECIFIED) {
                    locationViewModel.geocode(geocodeUtil, array, type)
                } else {
                    locationViewModel.setSelectedLatLng(array, type)
                }
            }
        }

        setupToast()
        setupNavigation()
    }

    private fun initTypeToSpecified(latLng: LatLng) {
        locationViewModel.geocode(
            geocodeUtil,
            latLng.toDoubleArray(),
            LatLngSelectionType.SPECIFIED
        )
    }

    private fun setupToast() {
        locationViewModel.toastText.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initTypeToCurrent(latLng: LatLng) {
        locationViewModel.setSelectedLatLng(latLng.toDoubleArray(), LatLngSelectionType.CURRENT)

        initialLatLng = latLng
        initialType = LatLngSelectionType.CURRENT

        createMarkerAndMoveCamera(latLng, DEFAULT_ZOOM_LEVEL)
    }

    private fun initTypeToDefault() {
        locationViewModel.setSelectedLatLng(
            defaultLatLng.toDoubleArray(),
            LatLngSelectionType.DEFAULT
        )

        initialLatLng = defaultLatLng
        initialType = LatLngSelectionType.DEFAULT

        createMarkerAndMoveCamera(defaultLatLng, SOUTH_KOREA_ZOOM_LEVEL)
    }

    private fun LatLng.toDoubleArray(): DoubleArray = doubleArrayOf(latitude, longitude)

    private fun createMarkerAndMoveCamera(latLng: LatLng, zoomLevel: Float) {
        val markerOptions = MarkerOptions()
            .position(latLng)
            .draggable(true)
        draggableMarker = map.addMarker(markerOptions)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
    }

    private fun setupNavigation() {
        locationViewModel.locationSelectedEvent.observe(viewLifecycleOwner, EventObserver {
            writeViewModel.temp = it
            writeViewModel.stickers = locationViewModel.stickers

            val action = LocationFragmentDirections.actionLocationFragmentToWriteFragment()
            findNavController().navigate(action)
        })
    }

    private fun distanceBetween(latLng1: LatLng, latLng2: LatLng): Float {
        val result = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude,
            latLng1.longitude,
            latLng2.latitude,
            latLng2.longitude,
            result
        )

        return result.first()
    }

    override fun onDestroyView() {
        draggableMarker?.remove()
        super.onDestroyView()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_base, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_base_confirm -> {
                // TODO: file을 여기서 초기화하면 전역변수로 두지 않아도 될듯
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

    override fun onSaveInstanceState(outState: Bundle) {
        val picture = locationViewModel.picture.value

        if (picture != null) {
            with(outState) {
                putString(KEY_URI, picture.uri.toString())

                if (picture.latLng != null) {
                    putString(KEY_LAT, picture.latLng.latitude.toString())
                    putString(KEY_LNG, picture.latLng.longitude.toString())
                }

                val latLng = map.cameraPosition.target
                val isDefaultLocation = distanceBetween(defaultLatLng, latLng) <= 1
                putBoolean(KEY_IS_DEFAULT_LOCATION, isDefaultLocation)
            }
        }

        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        with(map) {
            setOnCameraMoveListener(null)
            setOnCameraIdleListener(null)
        }

        super.onDestroy()
    }

    companion object {
        private val defaultLatLng = LatLng(SOUTH_KOREA_LAT, SOUTH_KOREA_LNG)

        private const val KEY_URI = "uri"
        private const val KEY_LAT = "lat"
        private const val KEY_LNG = "lng"
        private const val KEY_IS_DEFAULT_LOCATION = "isDefaultLocation"
        private const val TEST_GROUP_ID = 1L
    }
}

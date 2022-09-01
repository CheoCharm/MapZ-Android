package com.cheocharm.presentation.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.common.DEFAULT_ZOOM_LEVEL
import com.cheocharm.presentation.common.SOUTH_KOREA_LAT
import com.cheocharm.presentation.common.SOUTH_KOREA_LNG
import com.cheocharm.presentation.common.SOUTH_KOREA_ZOOM_LEVEL
import com.cheocharm.presentation.common.toLatLng
import com.cheocharm.presentation.databinding.FragmentHomeBinding
import com.cheocharm.presentation.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
            }
            else -> {
                // TODO: 위치 권한을 얻지 못했을 때
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = homeViewModel

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = true

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val mapFragment = (activity as MainActivity).getMap()
        mapFragment?.getMapAsync { map ->
            map.setOnMapLoadedCallback {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                location.toLatLng(),
                                DEFAULT_ZOOM_LEVEL
                            )
                        )
                    }
                } else {
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(SOUTH_KOREA_LAT, SOUTH_KOREA_LNG),
                            SOUTH_KOREA_ZOOM_LEVEL
                        )
                    )
                }
            }
        }

        homeViewModel.countUp()
    }
}

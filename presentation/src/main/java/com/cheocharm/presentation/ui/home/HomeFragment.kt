package com.cheocharm.presentation.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = homeViewModel

        val mainActivity = requireActivity() as MainActivity
        mainActivity.setMapVisible(true)

        val mapFragment = (activity as MainActivity).getMap()
        mapFragment?.getMapAsync { map ->
            map.setOnMapLoadedCallback {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val locationClient = mainActivity.getLocationClient()
                    locationClient?.lastLocation?.addOnSuccessListener { location ->
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

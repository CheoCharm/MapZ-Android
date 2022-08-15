package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentLocationBinding
import com.cheocharm.presentation.ui.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {
    private val pictureViewModel: PictureViewModel by navGraphViewModels(R.id.write)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        mapFragment?.getMapAsync { map ->
            map.setOnMapLoadedCallback {
                pictureViewModel.picture.observe(viewLifecycleOwner) { picture ->
                    picture?.let {
                        picturesAdapter.submitList(listOf(it))

                        val selectedLocation = it.latLng
                        if (selectedLocation != null) {
                            val draggableMarker = MarkerOptions()
                                .position(selectedLocation)
                                .draggable(true)
                            map.addMarker(draggableMarker)
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15F))
                        } else {
                            // TODO: 사진에 장소 정보가 없으면 기본 위치로 카메라 이동
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_location, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.location_confirm -> {
                val action = LocationFragmentDirections.actionLocationFragmentToWriteFragment()
                findNavController().navigate(action)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

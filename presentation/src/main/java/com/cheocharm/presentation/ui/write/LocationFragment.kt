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
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentLocationBinding
import com.cheocharm.presentation.ui.MainActivity
import com.cheocharm.presentation.util.UriUtil
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location),
    MenuProvider {
    private val pictureViewModel by navGraphViewModels<PictureViewModel>(R.id.write)
    private val locationViewModel by navGraphViewModels<LocationViewModel>(R.id.write) { defaultViewModelProviderFactory }
    private val writeViewModel by navGraphViewModels<WriteViewModel>(R.id.write) { defaultViewModelProviderFactory }

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
            val mainActivity = activity as MainActivity

            mainActivity.setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                mainActivity.onBackPressed()
            }
        }

        val mainActivityBinding = (activity as MainActivity).getBinding()
        mainActivityBinding.fragmentMainMap.isVisible = true

        val mapFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_main_map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapLoadedCallback {
                // TODO: 마커 생성
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

        pictureViewModel.picture.observe(viewLifecycleOwner) { picture ->
            picture?.let { pic ->
                picturesAdapter.submitList(listOf(pic))

                address = pic.address
                location = pic.latLng

                activity?.applicationContext?.let { context ->
                    file = UriUtil.getFileFromUri(context, pic.uri)
                }
            }
        }

        setupToast()
        setupNavigation()
    }

    private fun setupToast() {
        locationViewModel.toastText.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, it.getContentIfNotHandled(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNavigation() {
        locationViewModel.locationSelectedEvent.observe(viewLifecycleOwner) {
            it?.let {
                it.getContentIfNotHandled()?.let { temp ->
                    writeViewModel.temp = temp
                    writeViewModel.stickers = locationViewModel.stickers

                    val action = LocationFragmentDirections.actionLocationFragmentToWriteFragment()
                    findNavController().navigate(action)
                }
            }
        }
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

    companion object {
        private const val TEST_GROUP_ID = 25L
    }
}

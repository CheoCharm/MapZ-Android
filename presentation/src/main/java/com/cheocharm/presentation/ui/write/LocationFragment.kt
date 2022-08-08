package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentLocationBinding

class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {
    private val pictureViewModel: PictureViewModel by navGraphViewModels(R.id.write)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val picturesAdapter = PicturesAdapter()

        binding.viewmodel = pictureViewModel
        binding.rvLocationPictures.apply {
            adapter = picturesAdapter
        }
        with(binding.toolbarLocation) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = LocationFragmentDirections.actionLocationFragmentToPictureFragment()
                findNavController().navigate(action)
            }
        }

        pictureViewModel.picture.observe(viewLifecycleOwner) {
            it?.let {
                picturesAdapter.submitList(listOf(it))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.location_confirm -> {
                // TODO: 일기 작성하기 화면으로 이동
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

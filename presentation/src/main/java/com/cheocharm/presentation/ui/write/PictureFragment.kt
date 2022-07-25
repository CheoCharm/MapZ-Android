package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.FragmentPictureBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PictureFragment : BaseFragment<FragmentPictureBinding>(R.layout.fragment_picture) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_main)
        nav.selectedItemId = R.id.write_dest

        println("write id: ${R.id.write_dest}")
        println("my id: ${R.id.my_page_dest}")
        println("selected item id: ${nav.selectedItemId}")

        with(binding.toolbarPicture) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = PictureFragmentDirections.actionPictureFragmentToWriteDest()
                findNavController().navigate(action)
            }
        }
    }
}

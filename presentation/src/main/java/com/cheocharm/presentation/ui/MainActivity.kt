package com.cheocharm.presentation.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cheocharm.base.BaseActivity
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
            val navController = host.navController

            binding.bottomNavMain.setupWithNavController(navController)
        }

        binding.fragmentMainMap.isVisible = false
    }

    fun getBinding(): ActivityMainBinding = binding
}

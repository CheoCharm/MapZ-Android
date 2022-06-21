package com.cheocharm.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cheocharm.base.BaseActivity
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavMain
        bottomNav.setupWithNavController(navController)
    }
}

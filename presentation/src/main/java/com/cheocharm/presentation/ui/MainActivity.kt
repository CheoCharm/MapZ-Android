package com.cheocharm.presentation.ui

import android.Manifest
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseActivity
import com.cheocharm.presentation.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var mapFragment: SupportMapFragment? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this)
            }
            else -> {
                // TODO: 위치 권한을 얻지 못했을 때
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        with(binding) {
            bottomNavMain.setupWithNavController(navController)
            fragmentMainMap.isVisible = false
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.writeFragment) {
                binding.bottomNavMain.visibility = View.GONE
            } else {
                binding.bottomNavMain.visibility = View.VISIBLE
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_main_map) as? SupportMapFragment
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboardWhenOutsideTouched(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboardWhenOutsideTouched(ev: MotionEvent?) {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val rect = Rect()
                v.getGlobalVisibleRect(rect)
                if (!rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
    }

    fun getBinding(): ActivityMainBinding = binding

    fun setMapVisible(boolean: Boolean) {
        binding.fragmentMainMap.isVisible = boolean
    }

    fun getMap(): SupportMapFragment? = mapFragment

    fun getLocationClient(): FusedLocationProviderClient = fusedLocationClient
}

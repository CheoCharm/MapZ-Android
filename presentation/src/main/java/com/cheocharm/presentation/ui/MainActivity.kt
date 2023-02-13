package com.cheocharm.presentation.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.core.view.isVisible
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cheocharm.presentation.base.BaseActivity
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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
}

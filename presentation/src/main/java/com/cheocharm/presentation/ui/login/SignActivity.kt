package com.cheocharm.presentation.ui.login

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseActivity
import com.cheocharm.presentation.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUp()
    }

    private fun setUp() {
        val host: NavHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login)) as NavHostFragment
        val navController = host.navController
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
}

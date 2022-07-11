package com.cheocharm.presentation.ui.login

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.cheocharm.base.BaseActivity
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUp()
    }

    private fun setUp() {
        val host: NavHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login)) as NavHostFragment
        val navController = host.navController
    }
}

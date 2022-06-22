package com.cheocharm.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.cheocharm.base.BaseActivity
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var intentResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        intentResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            println("Activity Result: $it")
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleSignInResult(task)
            }
        }
        initButton()
    }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        println("onStart: $account")
    }

    private fun initButton() {
        binding.btnSignInGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            intentResult.launch(intent)
        }

        binding.btnSignOutGoogle.setOnClickListener {
            googleSignInClient.signOut()
                .addOnCompleteListener {
                    println("google log out")
                }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Log.d("handleSignInResult", "$account")
//            updateUI()
        } catch (e: ApiException) {
            Log.w("handleSignInResult", "${e.message}")
        }
    }
}

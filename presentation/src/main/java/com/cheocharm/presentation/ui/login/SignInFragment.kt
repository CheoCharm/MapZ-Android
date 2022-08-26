package com.cheocharm.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cheocharm.base.BaseFragment
import com.cheocharm.presentation.BuildConfig
import com.cheocharm.presentation.R
import com.cheocharm.presentation.common.EventObserver
import com.cheocharm.presentation.common.GOOGLE_ID_TOKEN
import com.cheocharm.presentation.common.SIGN_UP_TYPE
import com.cheocharm.presentation.databinding.FragmentSignInBinding
import com.cheocharm.presentation.model.SignType
import com.cheocharm.presentation.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signViewModel by viewModels<SignViewModel>()
    private val signInViewModel by viewModels<SignInViewModel>()

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var intentResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_LOGIN_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        intentResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                signInViewModel.handleSignInResult(task)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initEditText()
        initObservers()
        signInViewModel.checkAutoSignIn()
    }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
    }

    private fun initButton() {
        binding.btnSignInGoogleSignUp.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            intentResult.launch(intent)
        }
        binding.btnSignInSignUp.setOnClickListener {
            findNavController().navigate(
                R.id.action_signInFragment_to_signUpAgreeFragment, bundleOf(
                    SIGN_UP_TYPE to SignType.MAPZ
                )
            )
        }
        binding.cbSignInKeepLogin.setOnCheckedChangeListener { button, checked ->
            signInViewModel.setIsAutoSignIn(checked)
            if (checked) binding.tvSignInKeepLogin.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.map_z_red_500
                )
            )
            else binding.tvSignInKeepLogin.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.gray_extra_500
                )
            )
        }
        binding.btnSignIn.setOnClickListener {
            signInViewModel.requestMapZSignIn()
        }
    }

    private fun initEditText() {
        binding.etSignInEmail.doOnTextChanged { text, start, before, count ->
            signInViewModel.setEmail(text.toString())
            signInViewModel.checkSignInEnabled()
        }
        binding.etSignInPwd.doOnTextChanged { text, start, before, count ->
            signInViewModel.setPwd(text.toString())
            signInViewModel.checkSignInEnabled()
        }
    }

    private fun initObservers() {
        signInViewModel.isSignInEnabled.observe(viewLifecycleOwner) {
            binding.btnSignIn.isEnabled = it
        }
        signInViewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        signInViewModel.goToMain.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                googleSignInClient.signOut()
            }
        })
        signInViewModel.goToGoogleSignUpWithIdToken.observe(
            viewLifecycleOwner,
            EventObserver { idToken ->
                findNavController().navigate(
                    R.id.action_signInFragment_to_signUpAgreeFragment, bundleOf(
                        SIGN_UP_TYPE to SignType.GOOGLE,
                        GOOGLE_ID_TOKEN to idToken
                    )
                )
            })
    }
}

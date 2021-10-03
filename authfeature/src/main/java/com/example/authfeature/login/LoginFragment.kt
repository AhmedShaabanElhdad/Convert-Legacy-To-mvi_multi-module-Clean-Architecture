package com.example.authfeature.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.authfeature.R
import com.example.authfeature.databinding.FragmentLoginBinding
import com.example.common_ui.LoadingDialog
import com.example.common_ui.getLoadingDialog
import com.example.common_ui.showToast
import com.example.navigation.DeepLinkDestination
import com.example.navigation.deepLinkNavigateTo
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    val loginViewModel: LoginViewModel by viewModels()


    lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        binding.loginButton.setOnClickListener(View.OnClickListener {

            startLogin()
        })

        checkIfLoggedIn()


        initObservers()
    }



    private fun checkIfLoggedIn() {
        loginViewModel.setEvent(
            LoginContract.LoginEvent.refreshToken
        )
    }


    private fun startLogin() {
        loginViewModel.setEvent(
            LoginContract.LoginEvent.login(
                username = binding.usernameEt.text.toString(),
                password = binding.passwordEt.text.toString()
            )
        )
    }


    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.uiState.collect {
                when (it.loginViewState) {
                    is LoginContract.LoginViewState.Idle -> {

                        hideLoading()
                    }
                    is LoginContract.LoginViewState.Loading -> {
                        showLoading()
                    }
                    is LoginContract.LoginViewState.Success -> {
                        hideLoading()
                        val gson = Gson()
                        var profile = gson.toJson(it.loginViewState.profile)
                        findNavController().deepLinkNavigateTo(DeepLinkDestination.Product(profile),true)
                    }
                    is LoginContract.LoginViewState.LoginFormState -> {
                        hideLoading()
                        if (it.loginViewState.usernameError != null) {
                            binding.usernameEt.error = getString(it.loginViewState.usernameError)
                        }
                        if (it.loginViewState.passwordError != null) {
                            binding.passwordEt.error = getString(it.loginViewState.passwordError)
                        }
                    }
                    is LoginContract.LoginViewState.showPage -> {
                        binding.linearLoading.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            loginViewModel.effect.collect {
                when (it) {
                    is LoginContract.LoginEffect.Error -> {
                        hideLoading()
                        showToast(it.message)
                    }
                }
            }
        }
    }




    private var mProgressDialog: LoadingDialog? = null
    fun showLoading() {
        mProgressDialog = getLoadingDialog(requireContext())
        mProgressDialog?.showDialog()
    }


    fun hideLoading() {
        mProgressDialog?.dismiss()
    }
}
package com.example.authfeature.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.authfeature.R
import com.example.authfeature.databinding.FragmentLoginBinding
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


//        val login = Login()
        binding.loginButton.setOnClickListener(View.OnClickListener {

            startLogin()
        })


        initObservers()
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

//                        hideLoading()
                    }
                    is LoginContract.LoginViewState.Loading -> {
                        showToast("Loading")
//                        showLoading()
                    }
                    is LoginContract.LoginViewState.Success -> {
                        showToast("Success")
                        val gson = Gson()
                        var profile = gson.toJson(it.loginViewState.profile)
                        findNavController().deepLinkNavigateTo(DeepLinkDestination.Product(profile))
                    }
                    is LoginContract.LoginViewState.LoginFormState -> {

                        if (it.loginViewState.usernameError != null) {
                            binding.usernameEt.error = getString(it.loginViewState.usernameError)
                        }
                        if (it.loginViewState.passwordError != null) {
                            binding.passwordEt.error = getString(it.loginViewState.passwordError)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            loginViewModel.effect.collect {
                when (it) {
                    is LoginContract.LoginEffect.Error -> {
//                        hideLoading()
                        showToast("Error")
                    }
                }
            }
        }
    }

    private fun showToast(error: String) {
        Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
    }
}
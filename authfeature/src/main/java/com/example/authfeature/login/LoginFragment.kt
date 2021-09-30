package com.example.authfeature.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.authfeature.depecated.Login
import com.example.authfeature.R
import com.example.entity.Profile
import com.example.navigation.DeepLinkDestination
import com.example.navigation.deepLinkNavigateTo
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    val loginViewModel: LoginViewModel by viewModels()

    lateinit var userNameEt: EditText
    lateinit var  passwordEt: EditText
    lateinit var  loginBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userNameEt = view.findViewById(R.id.username_et)
        passwordEt = view.findViewById(R.id.password_et)
        loginBtn = view.findViewById(R.id.login_button)

//        val login = Login()
        loginBtn.setOnClickListener(View.OnClickListener {

            loginViewModel.setEvent(
                LoginContract.LoginEvent.login(
                    username = userNameEt.text.toString(),
                    password = passwordEt.text.toString()
                )
            )
        })


        initObservers()
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
                            userNameEt.error = getString(it.loginViewState.usernameError)
                        }
                        if (it.loginViewState.passwordError != null) {
                            passwordEt.error = getString(it.loginViewState.passwordError)
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
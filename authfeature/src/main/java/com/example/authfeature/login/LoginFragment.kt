package com.example.authfeature.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.authfeature.Login
import com.example.authfeature.R
import com.example.authfeature.databinding.FragmentLoginBinding
import com.example.navigation.DeepLinkDestination
import com.example.navigation.deepLinkNavigateTo

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userNameEt = view.findViewById<EditText>(R.id.username_et)
        val passwordEt = view.findViewById<EditText>(R.id.password_et)
        val loginBtn = view.findViewById<Button>(R.id.login_button)

        val login = Login()
        loginBtn.setOnClickListener(View.OnClickListener {
            login.login(
                userNameEt.text.toString(),
                passwordEt.text.toString(),
                requireContext().applicationContext
            ){
                findNavController().deepLinkNavigateTo(DeepLinkDestination.Product(it))
            }
        })
    }
}
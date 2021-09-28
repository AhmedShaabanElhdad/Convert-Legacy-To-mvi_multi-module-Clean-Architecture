package com.example.authfeature.login

import android.widget.EditText
import com.example.authfeature.Login
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.authfeature.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userNameEt: EditText = findViewById(R.id.username_et)
        val passwordEt: EditText = findViewById(R.id.password_et)
        val loginBtn: Button = findViewById(R.id.login_button)

        val login = Login()
        loginBtn.setOnClickListener(View.OnClickListener {
            login!!.login(
                userNameEt!!.getText().toString(), passwordEt.getText().toString(), applicationContext
            )
        })
    }
}
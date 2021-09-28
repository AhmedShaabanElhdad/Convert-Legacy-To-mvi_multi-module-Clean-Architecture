package com.example.authfeature

import android.content.Context
import com.example.authfeature.LoginTask

// Call async LoginTask
class Login {
    fun login(username: String?, password: String?, context: Context?,success:(String)->Unit) {
        val loginTask = LoginTask(context!!,success)
        loginTask.execute(username, password)
    }
}
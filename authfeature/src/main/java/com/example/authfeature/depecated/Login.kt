package com.example.authfeature.depecated

import android.content.Context

// Call async LoginTask
class Login {
    fun login(username: String?, password: String?, context: Context?,success:(String)->Unit) {
        val loginTask = LoginTask(context!!,success)
        loginTask.execute(username, password)
    }
}
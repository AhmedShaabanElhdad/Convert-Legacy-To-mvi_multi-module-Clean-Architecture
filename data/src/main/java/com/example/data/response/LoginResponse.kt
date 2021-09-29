package com.example.data.response

import com.example.entity.Profile

data class LoginResponse (
    val token: String? = null,
    val status: String? = null,
    val message: String? = null,
    val profile: Profile
)
package com.example.entity

import java.io.Serializable

data class Profile(
    var username: String? = null,
    var image: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
) : Serializable
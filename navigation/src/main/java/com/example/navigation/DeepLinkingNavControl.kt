package com.example.navigation

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

fun buildDeepLink(destination: DeepLinkDestination) =
    NavDeepLinkRequest.Builder
        .fromUri(destination.address.toUri())
        .build()

fun NavController.deepLinkNavigateTo(
    deepLinkDestination: DeepLinkDestination,
    popUpTo: Boolean = false
) {
    val builder = NavOptions.Builder()

    if (popUpTo) {
        builder.setPopUpTo(graph.startDestination, true)
    }

    navigate(
        buildDeepLink(deepLinkDestination),
        builder.build()
    )
}

const val DOMAIN = "example://"

sealed class DeepLinkDestination(val address: String) {
    object Auth : DeepLinkDestination("${DOMAIN}auth")
    object Product : DeepLinkDestination("${DOMAIN}product")
}
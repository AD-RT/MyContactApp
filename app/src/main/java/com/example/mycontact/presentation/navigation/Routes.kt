package com.example.mycontact.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object HomeScreen : Routes()

    @Serializable
    object AddContactUI : Routes()

    @Serializable
    object profile: Routes()

}
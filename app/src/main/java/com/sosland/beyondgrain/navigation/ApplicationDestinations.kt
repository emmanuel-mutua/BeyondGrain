package com.sosland.beyondgrain.navigation

sealed class ApplicationDestinations(val route: String) {
    object Home : ApplicationDestinations(route = "home_screen")
    object Authentication : ApplicationDestinations(route = "authentication")
}

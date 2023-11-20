package com.sosland.beyondgrain.presentation.screens.home.components
import com.sosland.beyondgrain.R

sealed class HomeDestinations(
    val route: String,
    val label: String,
    val icon: Int,
) {
    object Home : HomeDestinations(
        route = "home",
        label = "Home",
        icon = R.drawable.baseline_home_24,
    )

    object Categories : HomeDestinations(
        route = "categories",
        label = "Categories",
        icon = R.drawable.baseline_category_24,
    )

    object Cart : HomeDestinations(
        route = "cart",
        label = "Cart",
        icon = R.drawable.baseline_shopping_cart_24,
    )

    object Notifications : HomeDestinations(
        route = "notifications",
        label = "Notifications",
        icon = R.drawable.baseline_notifications_24,
    )

    object Account : HomeDestinations(
        route = "account",
        label = "Account",
        icon = R.drawable.baseline_person_24,
    )
}

package com.sosland.beyondgrain.presentation.screens.home.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sosland.beyondgrain.presentation.screens.home.components.BottomNavigationWithBackStack
import com.sosland.beyondgrain.presentation.screens.home.components.HomeAppBar
import com.sosland.beyondgrain.presentation.screens.home.components.HomeDestinations
import com.sosland.beyondgrain.presentation.screens.home.components.NavigationDrawer
import com.sosland.beyondgrain.presentation.screens.home.components.TestScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
) {
    val navController = rememberNavController()
    NavigationDrawer(drawerState = drawerState, onSignOutClicked = onSignOutClicked) {
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .navigationBarsPadding(),
            topBar = {
                HomeAppBar(
                    onMenuClicked = onMenuClicked,
                )
            },
            bottomBar = {
                BottomNavigationWithBackStack(navController = navController)
            },
        ) {
            NavHost(navController = navController, startDestination = HomeDestinations.Home.route) {
                homeContent()
                categories()
                cart()
                notifications()
                profile()
            }
        }
    }
}

fun NavGraphBuilder.homeContent() {
    composable(route = HomeDestinations.Home.route) {
        TestScreen(text = "Home")
    }
}

fun NavGraphBuilder.categories() {
    composable(route = HomeDestinations.Categories.route) {
        TestScreen(text = "Categories")
    }
}

fun NavGraphBuilder.cart() {
    composable(route = HomeDestinations.Cart.route) {
        TestScreen(text = "Cart")
    }
}

fun NavGraphBuilder.notifications() {
    composable(route = HomeDestinations.Notifications.route) {
        TestScreen(text = "Notifications")
    }
}

fun NavGraphBuilder.profile() {
    composable(route = HomeDestinations.Account.route) {
        TestScreen(text = "Profile")
    }
}

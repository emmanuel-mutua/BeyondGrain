package com.sosland.beyondgrain.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

// Home, Category, Cart, Notification, Profile
@Composable
fun BottomNavigationWithBackStack(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navigationItems: List<HomeDestinations> = listOf(
        HomeDestinations.Home,
        HomeDestinations.Categories,
        HomeDestinations.Notifications,
        HomeDestinations.Account,
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var previousBottomNav by remember {
        mutableStateOf<HomeDestinations>(HomeDestinations.Home)
    }
    val currentlySelectedItem by remember {
        derivedStateOf {
            currentBackStackEntry?.let {
                val route = it.destination.route
                previousBottomNav = when (route) {
                    HomeDestinations.Home.route -> HomeDestinations.Home
                    HomeDestinations.Categories.route -> HomeDestinations.Categories
                    HomeDestinations.Notifications.route -> HomeDestinations.Notifications
                    HomeDestinations.Account.route -> HomeDestinations.Account
                    else -> previousBottomNav
                }
                previousBottomNav
            } ?: previousBottomNav
        }
    }
    BottomAppBar(
        modifier = modifier.background(Color.Transparent),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        navigationItems.forEach { screen ->
            NavigationBarItem(
                alwaysShowLabel = true,
                interactionSource = MutableInteractionSource(),
                selected = screen == currentlySelectedItem,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "${screen.label} icon",
                        tint = if (screen == currentlySelectedItem) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        },
                    )
                },
                label = {
                    Text(
                        color = if (screen == currentlySelectedItem) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        },
                        text = screen.label,
                    )
                },
            )
        }
    }
}

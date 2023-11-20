package com.sosland.beyondgrain.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sosland.beyondgrain.presentation.components.DisplayAlertDialog
import com.sosland.beyondgrain.presentation.screens.auth.AuthenticationScreen
import com.sosland.beyondgrain.presentation.screens.auth.AuthenticationViewModel
import com.sosland.beyondgrain.presentation.screens.auth.rememberOneTapSignInState
import com.sosland.beyondgrain.presentation.screens.home.homeScreen.HomeScreen
import com.sosland.beyondgrain.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(ApplicationDestinations.Home.route)
            },
        )
        homeRoute(
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(ApplicationDestinations.Authentication.route)
            },
        )
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
) {
    composable(route = ApplicationDestinations.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val isAuthenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        var message by remember {
            mutableStateOf("")
        }
        AuthenticationScreen(
            oneTapState = oneTapState,
            loadingState = loadingState,
            onButtonClicked = {
                viewModel.setLoadingState(true)
                oneTapState.open()
            },
            onDialogDismissed = {
                message = it
            },
            onTokenReceived = {
//                Log.d("TOKEN", "$it")
                viewModel.loginWithMongodbAtlas(
                    tokenId = it,
                    onSuccess = {
                        message = "Success"
                    },
                    onError = {
                        message = it.message.toString()
                    },
                )
                viewModel.setLoadingState(false)
            },
            isAuthenticated = isAuthenticated,
            navigateToHome = navigateToHome,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToAuth: () -> Unit,
) {
    composable(route = ApplicationDestinations.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var dialogOpened by remember { mutableStateOf(false) }
        HomeScreen(
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            drawerState = drawerState,
            onSignOutClicked = {
                dialogOpened = true
            },
        )
        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want to sign out with Google Account?",
            dialogOpened = dialogOpened,
            onCloseDialog = {
                dialogOpened = false
            },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    if (user != null) {
                        user.logOut()
                    }
                    withContext(Dispatchers.Main) {
                        navigateToAuth()
                    }
                }
            },
        )
    }
}

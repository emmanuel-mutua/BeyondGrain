package com.sosland.beyondgrain.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(
    oneTapState: OneTapSignInState,
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    isAuthenticated: Boolean,
    navigateToHome: () -> Unit,
) {
    Scaffold(
        content = {
            AuthenticationContent(
                loadingState = loadingState,
                onButtonClicked = onButtonClicked,
            )
        },
    )
    OneTapSignIn(
        state = oneTapState,
        onTokenIdReceived = { tokenId ->
            onTokenReceived(tokenId)
            onDialogDismissed("Success")
        },
        onDialogDismissed = {
            onDialogDismissed(it)
        },
    )
    LaunchedEffect(key1 = isAuthenticated) {
        if (isAuthenticated) {
            navigateToHome()
        }
    }
}

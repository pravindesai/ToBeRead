package com.onebitcompany.toberead.navigation.loginNavigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.loginScreen.LoginScreen
import com.onebitcompany.toberead.ui.splashScreen.AnimatedSplashScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    onSignInClick:(methods:String)->Unit
) {
    navigation(startDestination = AppScreen.Splash.route,
        route = Constants.LOGIN_ROUTE) {
        composable(AppScreen.Splash.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(AppScreen.Login.route) {
            LoginScreen(navController = navController, onSignInClick, bottomBarState)
        }
    }
}
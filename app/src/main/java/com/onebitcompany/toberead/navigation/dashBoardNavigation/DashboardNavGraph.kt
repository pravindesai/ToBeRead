package com.onebitcompany.toberead.navigation.dashBoardNavigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.bookListScreen.BookListScreen
import com.onebitcompany.toberead.ui.homeScreen.view.HomeScreen
import com.onebitcompany.toberead.ui.settingsScreen.SettingsScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    onSignInClick:()->Unit,onSignOutClick:()->Unit
) {
    navigation(startDestination = AppScreen.HOME.route, route = Constants.DASHBOARD_ROUTE) {
        composable(AppScreen.HOME.route) {
            HomeScreen(navController = navController, bottomBarState)
        }
        composable(AppScreen.BOOKLIST.route) {
            BookListScreen(navController = navController, bottomBarState)
        }
        composable(AppScreen.SETTINGS.route) {
            SettingsScreen(navController = navController, bottomBarState, onSignInClick, onSignOutClick)
        }
    }
}
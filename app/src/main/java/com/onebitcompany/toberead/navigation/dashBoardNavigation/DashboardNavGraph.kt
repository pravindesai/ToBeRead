package com.onebitcompany.toberead.navigation.dashBoardNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.bookListScreen.BookListScreen
import com.onebitcompany.toberead.ui.homeScreen.HomeScreen
import com.onebitcompany.toberead.ui.settingsScreen.SettingsScreen

fun NavGraphBuilder.dashboardNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.HOME.route, route = Constants.DASHBOARD_ROUTE) {
        composable(AppScreen.HOME.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreen.BOOKLIST.route) {
            BookListScreen(navController = navController)
        }
        composable(AppScreen.SETTINGS.route) {
            SettingsScreen(navController = navController)
        }
    }
}
package com.onebitcompany.toberead.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.navigation.dashBoardNavigation.dashboardNavGraph
import com.onebitcompany.toberead.navigation.loginNavigation.loginNavGraph

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Constants.LOGIN_ROUTE,
        route = Constants.ROOT_ROUTE
    ) {
        loginNavGraph(navController)
        dashboardNavGraph(navController)
    }
}
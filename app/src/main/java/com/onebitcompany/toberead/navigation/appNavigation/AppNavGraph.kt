package com.onebitcompany.toberead.navigation.appNavigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.data.dto.Book
import com.onebitcompany.toberead.navigation.dashBoardNavigation.dashboardNavGraph
import com.onebitcompany.toberead.navigation.loginNavigation.loginNavGraph

@ExperimentalMaterial3Api
@Composable
fun AppNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    onSocialSignInClick: (methods: String) -> Unit,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onBookClick:(book: Book)->Unit

) {
    NavHost(
        navController = navController,
        startDestination = Constants.LOGIN_ROUTE,
        route = Constants.ROOT_ROUTE
    ) {
        loginNavGraph(navController, bottomBarState, onSocialSignInClick)
        dashboardNavGraph(navController, bottomBarState, onSignInClick, onSignOutClick, onBookClick)
    }
}
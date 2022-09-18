package com.onebitcompany.toberead.navigation.dashBoardNavigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.data.dto.Book
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.BookScreen
import com.onebitcompany.toberead.ui.bookListScreen.BookListScreen
import com.onebitcompany.toberead.ui.homeScreen.view.HomeScreen
import com.onebitcompany.toberead.ui.settingsScreen.SettingsScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onBookClick: (book: Book) -> Unit
) {
    navigation(startDestination = AppScreen.HOME.route, route = Constants.DASHBOARD_ROUTE) {
        composable(AppScreen.HOME.route) {
            HomeScreen(navController = navController, bottomBarState, onBookClick = onBookClick)
        }
        composable(AppScreen.BOOKLIST.route) {
            BookListScreen(navController = navController, bottomBarState)
        }
        composable(AppScreen.SETTINGS.route) {
            SettingsScreen(
                navController = navController,
                bottomBarState,
                onSignInClick,
                onSignOutClick
            )
        }
        composable(
            AppScreen.BOOK.route
        ) {
            val book = navController.previousBackStackEntry?.savedStateHandle?.get<Book>("book")
            BookScreen(navController = navController, book = book)
        }
    }
}
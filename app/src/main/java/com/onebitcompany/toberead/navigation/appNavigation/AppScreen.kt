package com.onebitcompany.toberead.navigation.appNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.onebitcompany.toberead.common.Constants

sealed class AppScreen(val route:String, val title:String?=null, val icon:ImageVector?=null){
    object Splash: AppScreen(Constants.SPLASH_SCREEN)
    object Login: AppScreen(Constants.LOGIN_SCREEN)
    object HOME: AppScreen(Constants.HOME_SCREEN, "Home", Icons.Outlined.Home)
    object BOOKLIST: AppScreen(Constants.BOOK_LIST_SCREEN, "My List", Icons.Outlined.List)
    object SETTINGS: AppScreen(Constants.SETTINGS_SCREEN, "Settings", Icons.Outlined.Settings)

    object BottomBarItems{
        val ItemList = listOf(
            HOME,BOOKLIST,SETTINGS
        )
    }
}

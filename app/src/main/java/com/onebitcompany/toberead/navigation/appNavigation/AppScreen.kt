package com.onebitcompany.toberead.navigation.appNavigation

import com.onebitcompany.toberead.common.Constants

sealed class AppScreen(val route:String){
    object Splash: AppScreen(Constants.SPLASH_SCREEN)
    object Login: AppScreen(Constants.LOGIN_SCREEN)
    object HOME: AppScreen(Constants.HOME_SCREEN)
}

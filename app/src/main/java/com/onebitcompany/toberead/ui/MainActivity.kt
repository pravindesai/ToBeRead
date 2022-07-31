package com.onebitcompany.toberead.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.navigation.appNavigation.AppNavGraph
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.navigation.appNavigation.CustomBottomNavigationBar
import com.onebitcompany.toberead.ui.theme.ToBeReadTheme
import dagger.hilt.android.AndroidEntryPoint


//Planning to name this application BookDhi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(
            application, "cec373d5-c86d-48ce-a8ae-9a1b01ede269",
            Analytics::class.java, Crashes::class.java
        )

        setContent {
            val navController = rememberNavController()
            val currentScreen = remember {
                mutableStateOf<AppScreen>(AppScreen.HOME)
            }
            val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

            ToBeReadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.wrapContentHeight(),
                        bottomBar = {
                            CustomBottomNavigationBar(
                                bottomBarState = bottomBarState,
                                currentScreenId = currentScreen.value.route,
                            ) {
                                currentScreen.value = it
                                Log.i("**", currentScreen.value.route)
                                navController.navigate(currentScreen.value.route){
                                    popUpTo(it.route)
                                    launchSingleTop = true
                                }
                            }
                        },
                        content = {
                            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                                when (destination.route) {
                                    Constants.SPLASH_SCREEN -> bottomBarState.value = false
                                    Constants.LOGIN_SCREEN -> bottomBarState.value = false
                                    Constants.HOME_SCREEN -> {
                                        bottomBarState.value = true
                                        currentScreen.value = AppScreen.HOME
                                    }
                                    Constants.BOOK_LIST_SCREEN -> {
                                        bottomBarState.value = true
                                        currentScreen.value = AppScreen.BOOKLIST
                                    }
                                    Constants.SETTINGS_SCREEN -> {
                                        bottomBarState.value = true
                                        currentScreen.value = AppScreen.SETTINGS
                                    }
                                }
                            }
                            AppNavGraph(navController = navController, bottomBarState)
                            Log.i("**", it.toString())
                        },
                    )
                }
            }
        }
    }

}

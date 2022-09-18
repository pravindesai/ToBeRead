package com.onebitcompany.toberead.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.common.Constants.IS_GUEST
import com.onebitcompany.toberead.common.Constants.userSessionKey
import com.onebitcompany.toberead.common.SessionManager
import com.onebitcompany.toberead.navigation.appNavigation.AppNavGraph
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.navigation.appNavigation.CustomBottomNavigationBar
import com.onebitcompany.toberead.socialLoginModule.GoogleSignUp
import com.onebitcompany.toberead.socialLoginModule.User
import com.onebitcompany.toberead.socialLoginModule.UserLoginListener
import com.onebitcompany.toberead.ui.theme.ToBeReadTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


//Planning to name this application BookDhi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity(), UserLoginListener {
    lateinit var activityViewModel:ActivityViewModel
    var navController:NavHostController? = null
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        val googleSignUp = GoogleSignUp(this, this)


        setContent {
            navController = rememberNavController()
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
                                navController?.navigate(currentScreen.value.route){
                                    popUpTo(it.route)
                                    launchSingleTop = true
                                }
                            }
                        },
                        content = {
                            navController?.addOnDestinationChangedListener { controller, destination, arguments ->
                                when (destination.route) {
                                    Constants.SPLASH_SCREEN -> {
                                        bottomBarState.value = false
                                        currentScreen.value = AppScreen.Splash
                                    }
                                    Constants.LOGIN_SCREEN -> {
                                        bottomBarState.value = false
                                        currentScreen.value = AppScreen.Login
                                    }
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
                                    Constants.BOOK_SCREEN->{
                                        bottomBarState.value = false
                                        currentScreen.value = AppScreen.BOOK
                                    }
                                }
                            }
                            navController?.let {
                                AppNavGraph(navController = it, bottomBarState,
                                    onSocialSignInClick = {onSocialSignInMethodClick->
                                        when(onSocialSignInMethodClick){
                                            Constants.SIGN_IN_METHOD_GOOGLE->{
                                                googleSignUp.signIn()
                                            }
                                            Constants.SIGN_IN_METHOD_FACEBOOK->{
                                                Log.e("***", "Facebook")
                                            }
                                        }
                                    },
                                    onSignInClick = {
                                        navController?.popBackStack()
                                        navController?.navigate(AppScreen.Login.route)
                                    },
                                    onSignOutClick = {
                                        navController?.popBackStack()
                                        navController?.navigate(AppScreen.Login.route)
                                        SessionManager.removeUser()
                                        SessionManager.saveBoolean(Constants.IS_GUEST, true)
                                        googleSignUp.signOut()
                                    },
                                onBookClick = {book->
                                    navController?.currentBackStackEntry?.savedStateHandle?.set(
                                        "book",book
                                    )
                                    navController?.navigate(AppScreen.BOOK.route)
                                })
                            }

                            Log.i("**", it.toString())
                        },
                    )
                }
            }
        }
    }

    override fun alreadySignedIn(user: User) {
        SessionManager.saveUser(user)
        SessionManager.saveBoolean(IS_GUEST, false)
        gotoHome()
    }

    override fun signedInSuccessfully(account: Any?) {
        when(account){
            is GoogleSignInAccount->{
                Log.e("***Google signIn ", account.email.toString())
                val user = User(account.id, account.displayName, account.email, account.photoUrl.toString(),"Google",account.idToken)
                SessionManager.saveUser(user)
                SessionManager.saveBoolean(IS_GUEST, false)
                gotoHome()
            }
        }
    }

    override fun signedOutSuccessfully() {
        SessionManager.delete(userSessionKey)
        SessionManager.saveBoolean(IS_GUEST, true)
    }

    override fun failed(e: Exception) {
        Log.e("***FAILED  ", e.message.toString())
    }

    fun gotoHome() {
        navController?.popBackStack()
        navController?.navigate(Constants.DASHBOARD_ROUTE)
    }

}

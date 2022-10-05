package com.onebitcompany.toberead.ui.splashScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.common.SessionManager
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.theme.PrimaryLight
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val animation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )

    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)


        if (SessionManager.containsKey(Constants.IS_GUEST) || true){
            navController.popBackStack()
            navController.navigate(Constants.DASHBOARD_ROUTE)
        }else{
            navController.popBackStack()
            navController.navigate(AppScreen.Login.route)
        }

    }

    SplashScreen(animation.value)
}

@Composable
fun SplashScreen(alpha:Float) {
    Box(
        modifier = Modifier
            .background(
                color = PrimaryLight
            )
            .fillMaxSize()
    ) {
        Icon(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center).alpha(alpha),
            imageVector = Icons.Default.Face, contentDescription = null, tint = Color.White
        )
    }
}
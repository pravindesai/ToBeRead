package com.onebitcompany.toberead.ui.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.onebitcompany.toberead.ui.theme.PrimaryDark
import com.onebitcompany.toberead.ui.theme.PrimaryLight
import com.onebitcompany.toberead.ui.theme.SecondaryLight

@Composable
fun SettingsScreen(navController: NavController, bottomBarState: MutableState<Boolean>) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(PrimaryLight) )
}
package com.onebitcompany.toberead.ui.loginScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onebitcompany.toberead.R
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.common.SessionManager
import com.onebitcompany.toberead.navigation.appNavigation.AppScreen
import com.onebitcompany.toberead.ui.theme.PrimaryDark
import com.onebitcompany.toberead.ui.theme.PrimaryLight

@Composable
fun LoginScreen(navController: NavController) {
    LoginUi(navController = navController)
}

@Composable
fun LoginUi(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    30.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(10f)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Logo()
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column() {
                    SocialButtons(navController)
                    GuestButton(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .background(PrimaryLight),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(PrimaryLight),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedCard(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(2.dp),
                border = BorderStroke(1.dp, PrimaryDark),

                ) {
                Row(
                    modifier = Modifier
                        .background(PrimaryLight)
                        .padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .width(40.dp)
                            .background(PrimaryLight),
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "content description",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = Constants.GOOGLE,
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(PrimaryLight)
                            .padding(0.dp, 5.dp, 10.dp, 5.dp)
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedCard(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(2.dp),
                border = BorderStroke(1.dp, PrimaryDark),

                ) {
                Row(
                    modifier = Modifier
                        .background(PrimaryLight)
                        .padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .width(40.dp)
                            .background(PrimaryLight),
                        painter = painterResource(R.drawable.ic_facebook),
                        contentDescription = "content description",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = Constants.FACEBOOK,
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(PrimaryLight)
                            .padding(0.dp, 5.dp, 10.dp, 5.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuestButton(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        OutlinedCard(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(2.dp),
            border = BorderStroke(1.dp, PrimaryDark),
            onClick = {
                gotoHome(navController, false)
            }
        ) {
            Text(
                text = Constants.GUEST,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(PrimaryLight)
                    .padding(10.dp, 5.dp, 10.dp, 5.dp)
            )
        }
    }
}

@Composable
private fun Logo() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            modifier = Modifier
                .size(150.dp),
            imageVector = Icons.Default.Face, contentDescription = null, tint = Color.White
        )
        Text(text = Constants.BOOK_NAME, color = Color.White, fontSize = 20.sp)
    }
}

fun gotoHome(navController: NavController, isLoggedIn: Boolean) {
    navController.popBackStack()
    SessionManager.saveBoolean(Constants.IS_GUEST, !isLoggedIn)
    navController.navigate(Constants.DASHBOARD_ROUTE)
}
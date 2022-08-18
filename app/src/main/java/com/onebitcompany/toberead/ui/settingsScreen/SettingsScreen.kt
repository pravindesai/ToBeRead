package com.onebitcompany.toberead.ui.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.onebitcompany.toberead.common.SessionManager
import com.onebitcompany.toberead.socialLoginModule.User
import com.onebitcompany.toberead.ui.theme.PrimaryLight
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.onebitcompany.toberead.R
import com.onebitcompany.toberead.ui.theme.PrimaryTooDark


@Composable
fun SettingsScreen(navController: NavController, bottomBarState: MutableState<Boolean>, onSignInClick:()->Unit,onSignOutClick:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryLight)
    ) {
        if (SessionManager.getUser()!=null){
            val user = SessionManager.getUser()!!
            LoggedInUser(user,onSignOutClick)
        }else{
            GuestView(onSignInClick)
        }
    }
}


@Composable
fun LoggedInUser(user: User, onSignOutClick: () -> Unit) =
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp, top = 100.dp)) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(CircleShape)
                        .clickable(enabled = true, onClick = {

                        }),
                    imageModel = user.profileImgUrl.toString(),
                    contentScale = ContentScale.Crop,
                    circularReveal = CircularReveal(duration = 250),
                    previewPlaceholder = R.drawable.ic_profile

                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = user.name.toString().uppercase(), modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp), fontSize = 25.sp, fontFamily = FontFamily.SansSerif, fontStyle = FontStyle.Italic)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = user.email.toString().lowercase(), modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp),
                            fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontStyle = FontStyle.Italic)
                    }
                }
            }

            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = PrimaryTooDark),
                    onClick = {
                onSignOutClick()
                    },
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign Out")
                }
            }
        }



}

@Composable
fun GuestView(onSignInClick: () -> Unit) =
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 80.dp),
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
            containerColor = PrimaryTooDark),
                    onClick = {
                        onSignInClick() },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
    }

}
package com.onebitcompany.toberead.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.onebitcompany.toberead.data.dto.Book

@Composable
fun BookScreen(navController: NavController, book: Book?) {
    Log.e("***Book", "$book")



}

@Composable
fun BookDetails(){

}
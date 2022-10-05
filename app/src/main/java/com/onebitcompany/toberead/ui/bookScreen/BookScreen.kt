package com.onebitcompany.toberead.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onebitcompany.toberead.data.dto.Book
import com.onebitcompany.toberead.states.BookContentState
import com.onebitcompany.toberead.ui.bookScreen.BookViewModel

@Composable
fun BookScreen(
    navController: NavController, book: Book?,
    bookViewModel: BookViewModel = hiltViewModel()
) {
    Log.e("***Book", "$book")

    val bookContentState = remember {
        bookViewModel.bookContentState
    }

    book?.BookId?.toInt()?.let { bookViewModel.getBookContentTags(it) } ?: run {
        Log.e("***", "Book empty")
    }

    BookDetails(bookContentState.value)

}

@Composable
fun BookDetails(content: BookContentState) {

    if (content.isLoading){
//        Log.e("***", "Loading...")
    }
    if (content.error?.isNotEmpty() == true){
        Log.e("***", "Error...")
    }
    if ((content.content?.BookContent?.size ?: 0) > 0){
        Log.e("***", "Content not received ...content pages ${content.content?.BookContent?.size}")
    }

}
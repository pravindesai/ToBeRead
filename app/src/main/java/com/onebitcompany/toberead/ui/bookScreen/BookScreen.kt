package com.onebitcompany.toberead.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onebitcompany.toberead.R
import com.onebitcompany.toberead.data.dto.Book
import com.onebitcompany.toberead.states.BookContentState
import com.onebitcompany.toberead.ui.bookScreen.BookViewModel
import com.onebitcompany.toberead.ui.theme.PrimaryLight
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import query.GetBookContentForBookQuery

@Composable
fun BookScreen(
    navController: NavController, book: Book?,
    bookViewModel: BookViewModel = hiltViewModel()
) {
    Log.e("***Book", "$book")

    val bookContentState = remember {
        book?.BookId?.toInt()?.let {
            bookViewModel.getBookContent(it)
        }
        bookViewModel.bookContentState
    }

    BookDetails(bookContentState.value)


}

@Composable
fun BookDetails(content: BookContentState) {
    if (content.isLoading) {
        ShowLoader()
    } else if (content.error?.isNotEmpty() == true) {
        Log.e("***E", "Error... ${content.error}")
        ErrorUI()
    } else {
        if (content.content?.BookContent?.isEmpty() == true || content.content?.BookContent == null) {
            ErrorUI()
        } else {
            BookContent(content.content.BookContent)
        }
        Log.e("***CONTENT ", "${content.content?.BookContent}")
    }
}

@Composable
fun ShowLoader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = PrimaryLight), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.White,
            strokeWidth = 10.dp
        )
    }
}

@Composable
fun ErrorUI() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White), contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.bad_request),
            modifier = Modifier
                .width(250.dp)
                .height(250.dp),
            contentDescription = null
        )
    }
}

@Composable
fun BookContent(bookContent: List<GetBookContentForBookQuery.BookContent>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = PrimaryLight)) {

        LazyColumn(modifier = Modifier.wrapContentHeight()){
            item{
                Spacer(modifier = Modifier.height(15.dp))
            }

            bookContent.firstOrNull()?.Book?.BookImageUrl?.let {imgUrl->
                item {
                    Row(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalArrangement = Arrangement.Center) {
                        GlideImage(
                            imageModel = imgUrl,

                            circularReveal = CircularReveal(duration = 350),
                            modifier = Modifier.width(200.dp).height(200.dp).clip(CircleShape),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                Box(modifier = Modifier.matchParentSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                            failure = {
                                Text(text = "Image request failed.")}
                        )
                    }
                }
            }


            item{
                Spacer(modifier = Modifier.height(15.dp))
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp))
                {
                    bookContent.firstOrNull()?.Book?.BookTitle?.let {
                        BookTitle(title = it)
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.height(15.dp))
            }
            item {
                bookContent.forEach {page->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp)
                    ) {
                        bookContent.firstOrNull()?.Book?.BookTitle?.let {
                            BookContent(content = page.pageContent)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

    }
}

@Composable
fun BookTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = Color.Black,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun BookContent(content: String) {
    Text(
        text = content,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = Color.Black,
        fontSize = 18.sp,
        textAlign = TextAlign.Justify,
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal
    )
}
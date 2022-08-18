package com.onebitcompany.toberead.ui.homeScreen.view

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.onebitcompany.toberead.R
import com.onebitcompany.toberead.data.dto.Book
import com.onebitcompany.toberead.data.dto.Genre
import com.onebitcompany.toberead.data.dto.Tag
import com.onebitcompany.toberead.states.TrendingBooksListState
import com.onebitcompany.toberead.ui.homeScreen.viewModel.HomeViewModel
import com.onebitcompany.toberead.ui.theme.*
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavController? = null,
    bottomBarState: MutableState<Boolean>? = null,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchText = remember { mutableStateOf("") }
    val searchBarState = rememberSaveable { mutableStateOf(true) }

    val titleBarState = rememberSaveable { mutableStateOf(false) }

    val tagsListState = homeViewModel.tagListState
    val genreListState = homeViewModel.genreListState
    val trendingBooksListState = homeViewModel.trendingBooksListState
    val discoverBooksListState = homeViewModel.discoverBooksListState

    val mainListState = rememberLazyListState()
    val timer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            bottomBarState?.value = true
//            searchBarState.value = true
//            titleBarState.value = true
        }
    }
    when (mainListState.interactionSource.collectIsDraggedAsState().value) {
        true -> {
            bottomBarState?.value = false
//            searchBarState.value = false
//            titleBarState.value = false
        }
        false -> {
            timer.start()
        }
    }

    initData(homeViewModel, trendingBooksListState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryLight)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            TitleBar(titleBarState)
            Spacer(modifier = Modifier.height(15.dp))
            SearchBar(searchText, searchBarState, keyboardController)
            LazyColumn(
                state = mainListState,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    TagChipGroup(tagList = tagsListState.value.tags?.map {
                        Tag(
                            it.TagId,
                            it.TagName
                        )
                    }, searchText)
                    Spacer(modifier = Modifier.height(10.dp))
                    GenreChipGroup(genreList = genreListState.value.genres?.map {
                        Genre(
                            it.GenreID,
                            it.GenreName
                        )
                    }, searchText)
                }

                item {
                    TrendingSection(
                        booksList = trendingBooksListState.value.books,
                        searchText = searchText
                    )
                }

                discoverBooksListState.value.books?.let {
                    item {
                        Text(
                            text = "Discover Books",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    it.forEachIndexed { index, book ->
                        item {
                            BookRow(
                                book = book,
                                searchText = searchText
                            ) {
                                Log.e("***Book ", "$it")
                            }
                        }
                    }
                }

            }
        }
    }
}

fun initData(homeViewModel: HomeViewModel, trendingBooksListState: State<TrendingBooksListState>) =
    with(homeViewModel) {
        if (trendingBooksListState.value.books == null) {
            getAvailableTags()
            getAllGeneres()
            getAllTrendingBooks()
        }
    }

@Composable
fun TitleBar(titleBarState: MutableState<Boolean>) = AnimatedVisibility(
    visible = titleBarState.value,
    enter = slideInVertically(),
    exit = slideOutVertically()
) {
    Column(modifier = Modifier.wrapContentHeight(), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            NotificationIcon(modifier = Modifier.wrapContentSize())
        }

    }
}

@Composable
fun NotificationIcon(modifier: Modifier) =
    Column(modifier = modifier.padding(15.dp, 15.dp, 15.dp, 0.dp)) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .wrapContentHeight()
                .height(30.dp)
                .width(30.dp)
        )
    }

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    searchString: MutableState<String>,
    visibilityState: MutableState<Boolean>,
    keyboardController: SoftwareKeyboardController?
) =
    AnimatedVisibility(
        visible = visibilityState.value,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 10.dp)
                .border(width = 0.5.dp, color = PrimaryDark, shape = CircleShape)
        ) {

            BasicTextField(
                value = searchString.value,
                onValueChange = {
                    searchString.value = it
                },
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .padding(10.dp, 3.dp, 0.dp, 3.dp)
                    .height(40.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "image",
                            tint = PrimaryDark,
                            modifier = Modifier.height(30.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp, 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchString.value == TextFieldValue("").text) Text(
                                "Search"
                            )
                            innerTextField()
                        }
                        if (searchString.value != TextFieldValue("").text) {
                            IconButton(
                                onClick = {
                                    searchString.value = TextFieldValue("").text
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "image",
                                    tint = PrimaryDark,
                                    modifier = Modifier.height(15.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }


@Composable
fun CustomChip(text: String, OnClick: (text: String) -> Unit = {}) = Column() {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable {
                OnClick(text.uppercase())
            }
            .indication(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = PrimaryLight)
            )
    ) {
        Text(
            text = text.uppercase(),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun TagChipGroup(tagList: List<Tag?>?, searchText: MutableState<String>) =
    Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)) {
        tagList?.let {
            Text(
                text = "Search By Available Tags",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White
            )
            FlowRow(
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
                mainAxisAlignment = MainAxisAlignment.Center,
                lastLineMainAxisAlignment = FlowMainAxisAlignment.Center
            ) {
                it.forEach { tag ->
                    tag?.let {
                        CustomChip(it.TagName.toString()) {
                            searchText.value = it
                        }
                    }
                }
            }
        }

    }

@Composable
fun GenreChipGroup(genreList: List<Genre?>?, searchText: MutableState<String>) =
    Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)) {
        genreList?.let {
            Text(
                text = "Search By Available Genre",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White
            )
            FlowRow(
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
                mainAxisAlignment = MainAxisAlignment.Center,
                lastLineMainAxisAlignment = FlowMainAxisAlignment.Center
            ) {
                it.forEach { genre ->
                    genre?.let {
                        CustomChip(text = it.GenreName.toString()) {
                            searchText.value = it
                        }
                    }
                }
            }
        }

    }

@ExperimentalMaterial3Api
@Composable
fun BookCard(
    book: Book,
    searchText: MutableState<String>? = null,
    position: Int? = null,
    OnClick: (book: Book) -> Unit = {}
) = Column(
    modifier = Modifier
        .wrapContentHeight()
        .padding(
            start = if (position == 0) 15.dp else 4.dp,
            end = 3.dp,
            top = 3.dp,
            bottom = 3.dp
        )
        .width(120.dp)
) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxSize()
                .clickable { OnClick(book) }
        ) {
            GlideImage(
                imageModel = book.BookImageUrl,
                circularReveal = CircularReveal(duration = 350),
                modifier = Modifier
                    .fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(text = "Image request failed.")
//                    Image(bitmap =ImageBitmap.imageResource(R.drawable.ic_book) , contentDescription = null)
                },

            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp, top = 5.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f),
                ) {
                    Image(
                        painterResource(R.drawable.ic_trending),
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp)
                            .alpha(if (book.isTrending == true) 1f else 0f),
                        contentDescription = null
                    )
                }

                Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                    Image(
                        painterResource(R.drawable.ic_premium),
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp)
                            .alpha(if (book.IsPremium == true) 1f else 0f),
                        contentDescription = null
                    )
                }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(
                    if (book.genres?.GenreName
                            .toString()
                            .isBlank()
                    ) 0f else 1f
                ),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                FilledCustomChip(text = book.genres?.GenreName.toString()) {
                    searchText?.value = it
                }
            }
        }
    }

    Text(
        text = book.BookTitle.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 5.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@ExperimentalMaterial3Api
@Composable
fun FilledCustomChip(
    text: String,
    chipColor: Color? = null,
    OnClick: (text: String) -> Unit = {}
) = Column() {
    val chipColorList = listOf(ChipColor1, ChipColor2, ChipColor3, ChipColor4, ChipColor5)
    val bgColor = chipColor ?: chipColorList.random()
    Box(
        modifier = Modifier
            .padding(5.dp)
            .indication(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = PrimaryLight)
            )
    ) {
        ElevatedCard(modifier = Modifier.clickable {
            OnClick(text.lowercase())
        }) {
            Text(
                modifier = Modifier
                    .background(bgColor)
                    .padding(3.dp),
                text = text.lowercase(),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Italic
            )
        }

    }
}


@ExperimentalMaterial3Api
@Composable
fun TrendingSection(booksList: List<Book>?, searchText: MutableState<String>? = null) = Column(
    Modifier.alpha(if ((booksList?.size ?: 0) >= 1) 1f else 0f)
) {
    val trendingScrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    booksList?.let {
        Text(
            text = "Trending Books",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = trendingScrollState

        ) {

            it.forEachIndexed { index, book ->
                item {
                    BookCard(
                        book = book,
                        position = index,
                        searchText = searchText
                    ) {
                        Log.e("***Book ", "$it")
                    }
                }
            }
        }
        coroutineScope.launch {
            if(trendingScrollState.firstVisibleItemIndex==0){
                trendingScrollState.animateScrollToItem(0)
                delay(3000)
                trendingScrollState.animateScrollBy(100f)
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun BookRow(
    book: Book,
    searchText: MutableState<String>? = null,
    position: Int? = null,
    modifier: Modifier = Modifier,
    OnClick: (book: Book) -> Unit = {}
) = Column(
    modifier = modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(
            start = 15.dp,
            end = 3.dp,
            top = 3.dp,
            bottom = 10.dp
        )

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .wrapContentSize()
                .clickable { OnClick(book) }
        ) {
            GlideImage(
                imageModel = book.BookImageUrl,
                circularReveal = CircularReveal(duration = 350),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(text = "Image request failed.")
//                    Image(bitmap =ImageBitmap.imageResource(R.drawable.ic_book) , contentDescription = null)

                }
            )

        }

        Box(modifier = Modifier.fillMaxHeight()) {

                Column(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        text = book.BookTitle.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    book.description?.let{
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp, top = 5.dp),
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                Box(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End) {
                        Column(modifier = Modifier.padding(5.dp)
                        ){
                            FilledCustomChip(
                                text = book.genres?.GenreName.toString(),
                                chipColor = Secondary
                            ){
                                searchText?.value = it
                            }
                        }
                        if (book.isTrending == true){
                            Column(
                                modifier = Modifier.padding(start = 5.dp),
                            ) {
                                Image(
                                    painterResource(R.drawable.ic_trending),
                                    modifier = Modifier
                                        .width(15.dp)
                                        .height(15.dp),
                                        contentDescription = null
                                )
                            }
                        }

                        if(book.IsPremium == true){
                            Column(
                                modifier = Modifier.padding(start = 5.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.ic_premium),
                                    modifier = Modifier
                                        .width(15.dp)
                                        .height(15.dp),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
        }
    }
}

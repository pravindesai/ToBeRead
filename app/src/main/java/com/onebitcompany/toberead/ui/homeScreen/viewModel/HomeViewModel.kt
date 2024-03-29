package com.onebitcompany.toberead.ui.homeScreen.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.onebitcompany.toberead.common.Resources
import com.onebitcompany.toberead.data.dto.Genre
import com.onebitcompany.toberead.data.dto.Tag
import com.onebitcompany.toberead.data.dto.getDTOBook
import com.onebitcompany.toberead.data.repository.bookRepo.BookRepository
import com.onebitcompany.toberead.data.repository.genreRepo.GenreRepo
import com.onebitcompany.toberead.data.repository.tagRepo.TagRepo
import com.onebitcompany.toberead.data.repository.userRepo.UserRepository
import com.onebitcompany.toberead.states.DiscoverBooksListState
import com.onebitcompany.toberead.states.GenreListState
import com.onebitcompany.toberead.states.TagListState
import com.onebitcompany.toberead.states.TrendingBooksListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import type.Book_bool_exp
import type.Boolean_comparison_exp
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val tagRepo: TagRepo,
    private val genreRepository: GenreRepo
) : ViewModel() {

    private val _tagListState: MutableState<TagListState> = mutableStateOf(TagListState())
    val tagListState: State<TagListState> = _tagListState

    private val _genreListState: MutableState<GenreListState> = mutableStateOf(GenreListState())
    val genreListState: State<GenreListState> = _genreListState

    private val _trendingBooksListState: MutableState<TrendingBooksListState> =
        mutableStateOf(TrendingBooksListState())
    val trendingBooksListState: State<TrendingBooksListState> = _trendingBooksListState


    private val _discoverBooksListState: MutableState<DiscoverBooksListState> =
        mutableStateOf(DiscoverBooksListState())
    val discoverBooksListState: State<DiscoverBooksListState> = _discoverBooksListState


    init {
        getAvailableTags()
        getAllGeneres()
        getAllTrendingBooks()
        getDiscoveredBooks()
    }

    fun getAvailableTags() {
        viewModelScope.launch {
            tagRepo.getTags().catch { e ->
                Log.e("***", e.message.toString())
            }.collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _tagListState.value = TagListState(isLoading = true)
                    }
                    is Resources.Success -> {
                        _tagListState.value = TagListState(
                            isLoading = false,
                            tags = result.data?.Tag?.map { Tag(it.TagId.toString(), it.TagName) }
                                ?.toMutableList()
                        )
                    }
                    is Resources.Error -> {
                        Log.e("***", "Failed...${result.message}")
                        _tagListState.value = TagListState(error = result.message)
                    }
                }
            }
        }
    }

    fun getAllGeneres() {
        viewModelScope.launch {
            genreRepository.getGenre().catch { e ->
                Log.e("***", e.message.toString())
            }.collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _genreListState.value = GenreListState(isLoading = true)
                    }
                    is Resources.Success -> {
                        _genreListState.value = GenreListState(
                            isLoading = false,
                            genres = result.data?.Genre?.map {
                                Genre(
                                    it.GenreID.toString(),
                                    it.GenreName
                                )
                            }
                                ?.toMutableList()
                        )
                    }
                    is Resources.Error -> {
                        Log.e("***", "Failed...${result.message}")
                        _genreListState.value = GenreListState(error = result.message)
                    }
                }
            }
        }
    }

    fun getAllTrendingBooks() {
        viewModelScope.launch {
            bookRepository.getFilteredBooks(
                Book_bool_exp(
                    isTrending = Optional.presentIfNotNull(
                        Boolean_comparison_exp(_eq = Optional.presentIfNotNull(true))
                    )
                )
            ).catch { e ->
                Log.e("***", e.message.toString())
            }.collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _trendingBooksListState.value = TrendingBooksListState(isLoading = true)
                    }
                    is Resources.Success -> {
                        _trendingBooksListState.value = TrendingBooksListState(
                            isLoading = false,
                            books = result.data?.Book?.map { it.getDTOBook() }?.toMutableList()
                        )
                    }
                    is Resources.Error -> {
                        Log.e("***", "Failed...${result.message}")
                        _trendingBooksListState.value = TrendingBooksListState(error = result.message)
                    }
                }
            }
        }
    }

    fun getDiscoveredBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooks().catch { e ->
                Log.e("***", e.message.toString())
            }.collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _discoverBooksListState.value = DiscoverBooksListState(isLoading = true)
                    }
                    is Resources.Success -> {
                        _discoverBooksListState.value = DiscoverBooksListState(
                            isLoading = false,
                            books = result.data?.Book?.map { it.getDTOBook() }?.toMutableList()
                        )
                    }
                    is Resources.Error -> {
                        Log.e("***", "Failed...${result.message}")
                        _discoverBooksListState.value = DiscoverBooksListState(error = result.message)
                    }
                }
            }
        }
    }

}
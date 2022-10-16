package com.onebitcompany.toberead.ui.bookScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onebitcompany.toberead.common.Resources
import com.onebitcompany.toberead.data.repository.bookRepo.BookRepository
import com.onebitcompany.toberead.states.BookContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _bookContentState: MutableState<BookContentState> =
        mutableStateOf(BookContentState())
    val bookContentState: State<BookContentState> = _bookContentState


    fun getBookContent(bookId: Int) {
        viewModelScope.launch {
            bookRepository.getBookContent(bookId).catch { e ->
                Log.e("***", e.message.toString())
            }.collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _bookContentState.value = BookContentState(isLoading = true)
                    }
                    is Resources.Success -> {
                        _bookContentState.value = BookContentState(
                            isLoading = false,
                            content = result.data
                        )
                    }
                    is Resources.Error -> {
                        Log.e("***", "Failed...${result.message}")
                        _bookContentState.value =
                            BookContentState(isLoading = false,
                                error = result.message)
                    }
                }
            }
        }
    }


}
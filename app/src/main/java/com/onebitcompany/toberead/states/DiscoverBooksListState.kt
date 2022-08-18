package com.onebitcompany.toberead.states

import com.onebitcompany.toberead.data.dto.Book

data class DiscoverBooksListState(
    val isLoading:Boolean = false,
    val books:MutableList<Book>? = null,
    val error:String? = ""
)

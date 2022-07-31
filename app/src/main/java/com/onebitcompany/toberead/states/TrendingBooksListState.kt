package com.onebitcompany.toberead.states

import com.onebitcompany.toberead.data.dto.Book

data class TrendingBooksListState(
    val isLoading:Boolean = false,
    val books:MutableList<Book>? = mutableListOf(),
    val error:String? = ""
)

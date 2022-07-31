package com.onebitcompany.toberead.states

import com.onebitcompany.toberead.data.dto.Genre

data class GenreListState(
    val isLoading:Boolean = false,
    val genres:MutableList<Genre>? = mutableListOf(),
    val error:String? = ""
)

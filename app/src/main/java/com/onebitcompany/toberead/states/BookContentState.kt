package com.onebitcompany.toberead.states

import query.GetBookContentForBookQuery

data class BookContentState(
    val isLoading:Boolean = false,
    val content: GetBookContentForBookQuery.Data? = null,
    val error:String? = null
)

package com.onebitcompany.toberead.states

import com.onebitcompany.toberead.data.dto.Tag

data class TagListState(
    val isLoading:Boolean = false,
    val tags:MutableList<Tag>? = mutableListOf(),
    val error:String? = ""
)

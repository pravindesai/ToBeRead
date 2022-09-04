package com.onebitcompany.toberead.states

data class UserDataState(
    val isLoading:Boolean = false,
    val tags:MutableList<Any?>? = null,
    val error:String? = ""
)

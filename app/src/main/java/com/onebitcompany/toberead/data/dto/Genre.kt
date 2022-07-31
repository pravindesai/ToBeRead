package com.onebitcompany.toberead.data.dto

import query.BooksByFilterQuery

data class Genre(
    val GenreID:String?=null,
    val GenreName:String?=null,
)

fun BooksByFilterQuery.Genre.getGenreDTO():Genre{
    return Genre(GenreID.toString(),GenreName)
}
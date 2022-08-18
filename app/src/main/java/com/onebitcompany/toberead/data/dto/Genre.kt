package com.onebitcompany.toberead.data.dto

import query.BooksByFilterQuery
import query.GetAllBooksQuery

data class Genre(
    val GenreID:String?=null,
    val GenreName:String?=null,
)

fun BooksByFilterQuery.Genre.getGenreDTO():Genre{
    return Genre(GenreID.toString(),GenreName)
}
fun GetAllBooksQuery.Genre.getGenreDTO():Genre{
    return Genre(GenreID.toString(),GenreName)
}
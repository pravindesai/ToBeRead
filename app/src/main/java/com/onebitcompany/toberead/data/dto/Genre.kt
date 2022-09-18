package com.onebitcompany.toberead.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import query.BooksByFilterQuery
import query.GetAllBooksQuery

@Parcelize
data class Genre(
    val GenreID:String?=null,
    val GenreName:String?=null,
) : Parcelable

fun BooksByFilterQuery.Genre.getGenreDTO():Genre{
    return Genre(GenreID.toString(),GenreName)
}
fun GetAllBooksQuery.Genre.getGenreDTO():Genre{
    return Genre(GenreID.toString(),GenreName)
}
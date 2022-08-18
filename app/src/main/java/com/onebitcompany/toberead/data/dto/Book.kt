package com.onebitcompany.toberead.data.dto

import query.BooksByFilterQuery
import query.GetAllBooksQuery

data class Book(
    val BookId:String?=null,
    val BookTitle:String?=null,
    val description:String?=null,
    val BookPrice:String?=null,
    val BookImageUrl:String?=null,
    val IsPremium:Boolean?=null,
    val isTrending:Boolean?=null,
    val genres: Genre?=null,
    val tags:MutableList<Tag>?=null
)
fun BooksByFilterQuery.Book.getDTOBook():Book{
    return Book(
        BookId= this.BookId.toString(),
                BookTitle= this.BookTitle,
                BookPrice= this.BookPrice.toString(),
                BookImageUrl= this.BookImageUrl,
                IsPremium= this.IsPremium,
                isTrending= this.isTrending,
                genres= this.Genre?.getGenreDTO()
    )
}
fun GetAllBooksQuery.Book.getDTOBook():Book{
    return Book(
        BookId= this.BookId.toString(),
        BookTitle= this.BookTitle,
        BookPrice= this.BookPrice.toString(),
        BookImageUrl= this.BookImageUrl,
        IsPremium= this.IsPremium,
        isTrending= this.isTrending,
        genres= this.Genre?.getGenreDTO()
    )
}
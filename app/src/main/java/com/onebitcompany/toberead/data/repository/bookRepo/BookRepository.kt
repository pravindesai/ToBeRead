package com.onebitcompany.toberead.data.repository.bookRepo

import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.Flow
import mutation.*
import query.BooksByFilterQuery
import query.GetAllBooksQuery
import query.GetBookContentForBookQuery
import type.*

interface BookRepository {
    fun addBookToFavourite(favouriteBookInsertInput: FavouriteBooks_insert_input): Flow<Resources<AddBookToFavouriteMutation.Data?>>
    fun addBookToTBR(tbrInsertInput: TBR_insert_input): Flow<Resources<AddBookToTBRMutation.Data?>>
    fun assignBookToUser(assignBookInsertInput: User_Book_insert_input): Flow<Resources<AssignBookToUserMutation.Data?>>

    fun uploadBook(bookInsertInput: Book_insert_input): Flow<Resources<InsertBookMutation.Data?>>
    fun uploadBookContent(booksContentInsertInputArray: List<BookContent_insert_input>): Flow<Resources<InsertBookContentMutation.Data?>>

    fun getAllBooks(): Flow<Resources<GetAllBooksQuery.Data?>>
    fun getBookContent(bookId: Int): Flow<Resources<GetBookContentForBookQuery.Data?>>
    fun getFilteredBooks(bookBoolExp: Book_bool_exp):Flow<Resources<BooksByFilterQuery.Data?>>
}
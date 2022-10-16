package com.onebitcompany.toberead.data.repository.bookRepo

import com.apollographql.apollo3.ApolloClient
import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import mutation.*
import query.BooksByFilterQuery
import query.GetAllBooksQuery
import query.GetBookContentForBookQuery
import type.*
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    BookRepository {
    override fun addBookToFavourite(favouriteBookInsertInput: FavouriteBooks_insert_input): Flow<Resources<AddBookToFavouriteMutation.Data?>> =
        flow {
            val apolloCall =
                apolloClient.mutation(AddBookToFavouriteMutation(listOf(favouriteBookInsertInput)))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun addBookToTBR(tbrInsertInput: TBR_insert_input): Flow<Resources<AddBookToTBRMutation.Data?>> =
        flow {
            val apolloCall = apolloClient.mutation(AddBookToTBRMutation(listOf(tbrInsertInput)))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun assignBookToUser(assignBookInsertInput: User_Book_insert_input): Flow<Resources<AssignBookToUserMutation.Data?>> =
        flow {
            val apolloCall =
                apolloClient.mutation(AssignBookToUserMutation(listOf(assignBookInsertInput)))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun uploadBook(bookInsertInput: Book_insert_input): Flow<Resources<InsertBookMutation.Data?>> =
        flow {
            val apolloCall = apolloClient.mutation(InsertBookMutation(bookInsertInput))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun uploadBookContent(booksContentInsertInputArray: List<BookContent_insert_input>): Flow<Resources<InsertBookContentMutation.Data?>> =
        flow {
            val apolloCall =
                apolloClient.mutation(InsertBookContentMutation(booksContentInsertInputArray))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun getAllBooks(): Flow<Resources<GetAllBooksQuery.Data?>> =
        flow {
            val apolloCall = apolloClient.query(GetAllBooksQuery())
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun getBookContent(bookId: Int): Flow<Resources<GetBookContentForBookQuery.Data?>> =
        flow {
            val apolloCall = apolloClient.query(GetBookContentForBookQuery(bookId))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect{
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun getFilteredBooks(bookBoolExp: Book_bool_exp): Flow<Resources<BooksByFilterQuery.Data?>> =
        flow {
            val apolloCall = apolloClient.query(BooksByFilterQuery(bookBoolExp))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }
}
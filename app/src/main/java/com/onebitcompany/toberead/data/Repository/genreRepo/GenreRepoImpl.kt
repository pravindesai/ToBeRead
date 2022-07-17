package com.onebitcompany.toberead.data.Repository.genreRepo

import com.apollographql.apollo3.ApolloClient
import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mutation.InsertGenreMutation
import query.GetGenresQuery
import type.Genre_insert_input
import javax.inject.Inject

class GenreRepoImpl @Inject constructor(private val apolloClient: ApolloClient) : GenreRepo {
    override fun insertGenre(genreInsertInput: List<Genre_insert_input>): Flow<Resources<InsertGenreMutation.Data?>> =
        flow {
            val apolloCall = apolloClient.mutation(InsertGenreMutation(genreInsertInput))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun getGenre(): Flow<Resources<GetGenresQuery.Data?>> = flow {
        val apolloCall = apolloClient.query(GetGenresQuery())
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
package com.onebitcompany.toberead.data.Repository.genreRepo

import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.Flow
import mutation.InsertGenreMutation
import query.GetGenresQuery
import type.Genre_insert_input

interface GenreRepo {
    fun insertGenre(genreInsertInput: List<Genre_insert_input>): Flow<Resources<InsertGenreMutation.Data?>>
    fun getGenre(): Flow<Resources<GetGenresQuery.Data?>>
}
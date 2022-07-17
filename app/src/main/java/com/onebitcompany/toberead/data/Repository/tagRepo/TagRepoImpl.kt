package com.onebitcompany.toberead.data.Repository.tagRepo

import com.apollographql.apollo3.ApolloClient
import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mutation.InsertTagMutation
import query.GetTagsQuery
import type.Tag_insert_input
import javax.inject.Inject

class TagRepoImpl @Inject constructor(private val apolloClient: ApolloClient) : TagRepo {
    override fun insertTags(tagInsertInputList: List<Tag_insert_input>): Flow<Resources<InsertTagMutation.Data?>> =
        flow {
            val apolloCall = apolloClient.mutation(InsertTagMutation(tagInsertInputList))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }

    override fun getTags(): Flow<Resources<GetTagsQuery.Data?>> = flow {
        val apolloCall = apolloClient.query(GetTagsQuery())
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
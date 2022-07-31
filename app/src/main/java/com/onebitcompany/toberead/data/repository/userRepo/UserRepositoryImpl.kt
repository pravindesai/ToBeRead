package com.onebitcompany.toberead.data.repository.userRepo

import com.apollographql.apollo3.ApolloClient
import com.onebitcompany.toberead.common.Resources
import com.onebitcompany.toberead.data.dto.SocialUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mutation.InsertUserMutation
import query.GetUserDataQuery
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    UserRepository {

    override suspend fun createUser(socialUser: SocialUser): Flow<Resources<InsertUserMutation.Data?>> =
        flow {
            val apolloCall =
                apolloClient.mutation(InsertUserMutation(listOf(socialUser.toUserInsertInput())))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect() {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }


    override suspend fun getUserData(userId: String): Flow<Resources<GetUserDataQuery.Data?>> =
        flow {
            val apolloCall = apolloClient.query(GetUserDataQuery(userId))
            try {
                emit(Resources.Loading())
                apolloCall.toFlow().collect {
                    emit(Resources.Success(it.data))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)

}
package com.onebitcompany.toberead.data.repository.userRepo

import com.onebitcompany.toberead.common.Resources
import com.onebitcompany.toberead.data.dto.SocialUser
import kotlinx.coroutines.flow.Flow
import mutation.InsertUserMutation
import query.GetUserDataQuery

interface UserRepository {
    suspend fun createUser(socialUser: SocialUser): Flow<Resources<InsertUserMutation.Data?>>
    suspend fun getUserData(userId: String): Flow<Resources<GetUserDataQuery.Data?>>
}
package com.onebitcompany.toberead.data.repository.tagRepo

import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.Flow
import mutation.InsertTagMutation
import query.GetTagsQuery
import type.Tag_insert_input

interface TagRepo {
    fun insertTags(tagInsertInputList: List<Tag_insert_input>): Flow<Resources<InsertTagMutation.Data?>>
    fun getTags(): Flow<Resources<GetTagsQuery.Data?>>
}
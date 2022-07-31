package com.onebitcompany.toberead.data.repository.tagRepo

import android.util.Log
import com.onebitcompany.toberead.common.Resources
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

class TagRepoImplTest {
    @Inject
    lateinit var tagRepo: TagRepo

    @Test
    fun insertTags() {
//        val tagInputList = listOf(
//            Tag_insert_input(TagName = Optional.presentIfNotNull("TestTag1")),
//            Tag_insert_input(TagName = Optional.presentIfNotNull("TestTag2"))
//        )
//        runBlocking {
//            tagRepo.insertTags(tagInputList).collectLatest {
//                when(it){
//                    is Resources.Success ->{
//                        Log.e("**", "Success" )
//                        assertTrue(true)
//                    }
//                    is Resources.Loading ->{
//                        Log.e("**", "Loading" )
//                    }
//                    is Resources.Error->{
//                        Log.e("**", "Error" )
//                        assertFalse(true)
//                    }
//                }
//            }
//        }

    }

    @Test
    fun getTags() {
        runBlocking {
            tagRepo.getTags().collectLatest {
                when(it){
                    is Resources.Success ->{
                        Log.e("**", "Success" )
                        assertTrue(true)
                    }
                    is Resources.Loading ->{
                        Log.e("**", "Loading" )
                    }
                    is Resources.Error->{
                        Log.e("**", "Error" )
                        assertFalse(true)
                    }
                }
            }
        }

    }
}
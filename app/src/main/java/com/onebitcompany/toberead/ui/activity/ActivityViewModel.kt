package com.onebitcompany.toberead.ui.activity

import androidx.lifecycle.ViewModel
import com.onebitcompany.toberead.socialLoginModule.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor():ViewModel() {

    fun createUserIfNotExists(user: User){

    }

}
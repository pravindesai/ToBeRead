package com.onebitcompany.toberead.ui.activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.onebitcompany.toberead.data.repository.userRepo.UserRepository
import com.onebitcompany.toberead.socialLoginModule.User
import com.onebitcompany.toberead.states.TagListState
import com.onebitcompany.toberead.states.UserDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    ):ViewModel() {
    private val _userDataState: MutableState<UserDataState> = mutableStateOf(UserDataState())
    val userDataState: State<UserDataState> = _userDataState

    fun createUserIfNotExists(user: User){

    }
}
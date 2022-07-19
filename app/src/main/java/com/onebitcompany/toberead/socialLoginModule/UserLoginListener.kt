package com.onebitcompany.toberead.socialLoginModule

import java.lang.Exception

interface UserLoginListener{
    fun alreadySignedIn(user: User)
    fun signedInSuccessfully(account: Any?) // LoginResult? for FB or GoogleSignInAccount for google
    fun signedOutSuccessfully()
    fun failed(e: Exception)
}
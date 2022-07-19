package com.onebitcompany.toberead.socialLoginModule

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.lang.Exception
import java.util.*

class FacebookSignUp(private val activity: AppCompatActivity,
                     private val mFacebookLoginListener: UserLoginListener) {

   val callbackManager:CallbackManager = CallbackManager.Factory.create()
   fun getAccessToken(): AccessToken = AccessToken.getCurrentAccessToken()
   fun isUserSignedIn():Boolean  = (getAccessToken()!=null && !getAccessToken().isExpired)

   val accountRequest: GraphRequest = GraphRequest.newMeRequest(getAccessToken()) { accountJson, response ->

      if (response.error==null){
         val id = accountJson?.getString("id")
         val fullName = accountJson?.getString("name")
         val email = accountJson?.getString("email")
         val token = getAccessToken().token
         val imgUrl = "https://graph.facebook.com/$id/picture?type=large"

         val user = User(id, fullName, email, imgUrl.toString(),"Facebook" ,token)
         mFacebookLoginListener.alreadySignedIn(user)
      }else{
         mFacebookLoginListener.failed(response.error.exception)
      }

   }.also {
      val parameters = Bundle()
      parameters.putString("fields", "id,name,email,link")
      it.parameters = parameters
   }

   init {
      LoginManager.getInstance().registerCallback(callbackManager, LocalFacebookCallback())
      if (isUserSignedIn()) {
         accountRequest.executeAsync()
      }
   }

   fun signIn(){
      LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
   }

   fun signOut(){
      LoginManager.getInstance().logOut()
      mFacebookLoginListener.signedOutSuccessfully()
   }

   private val launcher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
      callbackManager.onActivityResult(1, it.resultCode, it.data);
   }

   inner class LocalFacebookCallback: FacebookCallback<LoginResult> {
      override fun onSuccess(result: LoginResult?) {
         mFacebookLoginListener.signedInSuccessfully(result)
      }

      override fun onCancel() {
         mFacebookLoginListener.failed(Exception("Login Cancelled"))
      }

      override fun onError(error: FacebookException?) {
         mFacebookLoginListener.failed(error?: Exception("Login Error"))
      }

   }

}
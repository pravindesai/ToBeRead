package com.onebitcompany.toberead.socialLoginModule

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.onebitcompany.toberead.R

class GoogleSignUp(private val activity: ComponentActivity,
                   private val mGoogleSignInLoginListener: UserLoginListener
) {

    val gso: GoogleSignInOptions =   GoogleSignInOptions
                                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(activity.getString(R.string.google_client_id))
                                    .requestEmail().build()
    val gsc: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)

    init {
        if (isUserSignedIn()){
            getSignedInUser()?.let {it:GoogleSignInAccount->
                val user = User(it.id, it.displayName, it.email, it.photoUrl.toString(),"Google",it.idToken)
                mGoogleSignInLoginListener.alreadySignedIn(user)
            }
        }
    }

    fun isUserSignedIn():Boolean{
        val signedInAccount:GoogleSignInAccount?  = GoogleSignIn.getLastSignedInAccount(activity)
        return signedInAccount != null
    }

    fun getSignedInUser():GoogleSignInAccount?{
        if (isUserSignedIn()){
            return GoogleSignIn.getLastSignedInAccount(activity)
        }else {
            return null
        }
    }

    fun signOut(){
        gsc.signOut().addOnFailureListener {
            mGoogleSignInLoginListener.failed(it)
        }.addOnSuccessListener {
            mGoogleSignInLoginListener.signedOutSuccessfully()
        }
    }

    fun signIn() {
        val gscIntent:Intent = gsc.signInIntent
        gscLauncher.launch(gscIntent)
    }

    private val gscLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val googleAccount: GoogleSignInAccount = task.getResult(ApiException::class.java)
            mGoogleSignInLoginListener.signedInSuccessfully(googleAccount)
        }catch (e:Exception){
            mGoogleSignInLoginListener.failed(e)
        }
    }

}
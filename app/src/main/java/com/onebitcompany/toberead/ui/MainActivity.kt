    package com.onebitcompany.toberead.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.onebitcompany.toberead.data.Repository.userRepo.UserRepository
import com.onebitcompany.toberead.ui.theme.ToBeReadTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

    @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository:UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToBeReadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Android")
                    lifecycleScope.launch {
                        userRepository.getUserData("abcd1").collectLatest {
                            Log.e("**", it.data.toString())
                        }
                    }
                }
            }
        }
    }
}

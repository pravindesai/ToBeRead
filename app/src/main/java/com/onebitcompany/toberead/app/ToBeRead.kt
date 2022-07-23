package com.onebitcompany.toberead.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToBeRead: Application(){
    companion object{
        lateinit var appContext:Context
        private fun setContext(context: Context){
            appContext = context
        }
    }
    override fun onCreate() {
        super.onCreate()
        setContext(this)
    }
}
package com.onebitcompany.toberead.common

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.onebitcompany.toberead.app.ToBeRead
import com.onebitcompany.toberead.socialLoginModule.User
import java.lang.Exception

object SessionManager {
    private val TAG: String = "**"+this::class.java.simpleName
    val PRIVATE_MODE = 0
    val gson: Gson

    private val sharedPref:SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPref = ToBeRead.appContext.getSharedPreferences(Constants.KEY_SHARED_PREF_FILE, PRIVATE_MODE)
        editor = sharedPref.edit()
        gson = Gson()
    }

    fun saveBoolean(key:String, value:Boolean){
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String):Boolean = sharedPref.getBoolean(key, false)

    fun saveInt(key:String, value:Int){
        editor.putInt(key, value)
        editor.commit()
    }
    fun getInt(key: String): Int = sharedPref.getInt(key, "".toInt())


    fun saveFloat(key:String, value:Float){
        editor.putFloat(key, value)
        editor.commit()
    }
    fun getFloat(key: String): Float = sharedPref.getFloat(key, "".toFloat())


    fun saveLong(key:String, value:Long){
        editor.putLong(key, value)
        editor.commit()
    }
    fun getLong(key: String): Long = sharedPref.getLong(key, "".toLong())


    fun saveString(key:String, value:String){
        editor.putString(key, value)
        editor.commit()
    }
    fun getString(key: String): String? = sharedPref.getString(key, "")

    fun delete(key: String){
        editor.remove(key)
        editor.commit()
    }

    fun containsKey(key: String):Boolean{
        return sharedPref.contains(key)
    }

    fun saveUser(key: String, user: User){
        val serializedCustomer:String = gson.toJson(user)
        editor.putString(key, serializedCustomer)
        editor.apply()
    }

    fun getUser(key: String):User{
        val serializedCustomer:String? = sharedPref.getString(key,"")
        val deserializedCustomer:User = gson.fromJson(serializedCustomer, User::class.java)
        return deserializedCustomer
    }


    fun clearSession():Boolean{
        try {
            editor.clear()
            editor.apply()
            return true
        }catch (e:Exception){
            Log.e(TAG, "clearSession: FAILED --> "+e.localizedMessage)
            editor.commit()
            return false
        }
    }



}

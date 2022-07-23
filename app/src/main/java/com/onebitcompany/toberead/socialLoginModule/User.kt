package com.onebitcompany.toberead.socialLoginModule

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String?,
    val name: String?,
    val email: String?,
    val profileImgUrl: String?,
    val type: String,
    val token: String?
): Parcelable {
    override fun toString(): String {
        return "\nId:    $id \n" +
                "Name:  $name \n" +
                "Email: $email \n" +
                "Type:  $type \n" +
                "Token: $token \n" +
                "*******************\n"
    }


}

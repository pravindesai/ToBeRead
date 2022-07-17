package com.onebitcompany.toberead.data.dto

import com.apollographql.apollo3.api.Optional
import type.User_insert_input

data class SocialUser(
    val email: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val phoneNumber: String? = "",
    val userId: String? = ""
) {

    fun toUserInsertInput(): User_insert_input =
        User_insert_input(
            Email = Optional.presentIfNotNull(email),
            FirstName = Optional.presentIfNotNull(firstName),
            LastName = Optional.presentIfNotNull(lastName),
            PhoneNumber = Optional.presentIfNotNull(phoneNumber),
            UserId = Optional.presentIfNotNull(userId),
        )

}
package com.onebitcompany.toberead.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val TagId:String?=null,
    val TagName:String?=null,
) : Parcelable

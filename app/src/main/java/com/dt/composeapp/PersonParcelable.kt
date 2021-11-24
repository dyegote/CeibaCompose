package com.dt.composeapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PersonParcelable(
    val identification: String,
    val name: String
) : Parcelable
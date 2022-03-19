package com.hdipin.computer.science.application.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for User with required fields.
 */
@Parcelize
data class User(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = ""
) : Parcelable
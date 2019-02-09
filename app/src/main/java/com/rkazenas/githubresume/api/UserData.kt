package com.rkazenas.githubresume.api
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(val login: String,
                    val html_url: String,
                    val avatar_url: String) : Parcelable


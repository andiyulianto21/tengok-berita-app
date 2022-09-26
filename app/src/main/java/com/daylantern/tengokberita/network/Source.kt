package com.daylantern.tengokberita.network


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String = ""
) : Parcelable
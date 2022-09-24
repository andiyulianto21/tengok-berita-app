package com.daylantern.tengokberita.network


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: Any = Any(),
    @SerializedName("name")
    val name: String = ""
)
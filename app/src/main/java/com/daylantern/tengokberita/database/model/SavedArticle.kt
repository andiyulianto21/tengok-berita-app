package com.daylantern.tengokberita.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article")
data class SavedArticle(
    @PrimaryKey(autoGenerate = false) val url: String,
    val title: String,
    val urlToImage: String,
    val publishedAt: String?,
    val savedAt: Long?,
    val isSaved: Boolean? = false
//    val sourceId: String?,
//    val sourceName: String?,
//    val content: String?,
//    val author: String?,
//    val description: String?
) : Parcelable

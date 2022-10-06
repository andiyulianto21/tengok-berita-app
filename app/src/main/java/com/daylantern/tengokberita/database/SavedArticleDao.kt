package com.daylantern.tengokberita.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.daylantern.tengokberita.database.model.SavedArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticleDao {

    @Query("SELECT * FROM article ORDER BY savedAt DESC")
    fun getSavedArticles(): List<SavedArticle>

    @Query("SELECT EXISTS(SELECT * FROM article WHERE url = :url) ")
    fun getArticle(url: String): Boolean

    @Insert(onConflict = REPLACE)
    suspend fun saveArticle(article: SavedArticle)

    @Query("DELETE FROM article WHERE url = :url")
    suspend fun deleteArticle(url: String)
}
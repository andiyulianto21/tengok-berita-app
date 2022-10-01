package com.daylantern.tengokberita.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daylantern.tengokberita.database.model.SavedArticle

@Database(entities = [SavedArticle::class], version = 1, exportSchema = false)
abstract class SavedArticleDb: RoomDatabase() {
    abstract fun savedArticleDao(): SavedArticleDao
}
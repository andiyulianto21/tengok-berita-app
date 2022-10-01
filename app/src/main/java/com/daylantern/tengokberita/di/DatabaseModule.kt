package com.daylantern.tengokberita.di

import android.content.Context
import androidx.room.Room
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.database.SavedArticleDao
import com.daylantern.tengokberita.database.SavedArticleDb
import com.daylantern.tengokberita.network.NewsApi
import com.daylantern.tengokberita.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSavedArticleDb(@ApplicationContext context: Context): SavedArticleDb {
        return Room.databaseBuilder(context, SavedArticleDb::class.java, Constants.SAVED_ARTICLE_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSavedArticleDao(db: SavedArticleDb): SavedArticleDao = db.savedArticleDao()

    @Provides
    @Singleton
    fun provideRepository(newsApi: NewsApi, dao: SavedArticleDao): Repository = Repository(newsApi, dao)
}
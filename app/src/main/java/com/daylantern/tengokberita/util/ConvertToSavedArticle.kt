package com.daylantern.tengokberita.util

import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.network.Article

object ConvertClass{
    fun toSavedArticle(article: Article): SavedArticle{
        return SavedArticle(article.url, article.title, article.urlToImage, article.publishedAt, savedAt = null)
    }
}
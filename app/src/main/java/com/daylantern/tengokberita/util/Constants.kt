package com.daylantern.tengokberita.util

class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val SAVED_ARTICLE_DB = "savedArticleDB"
        const val SHARE_URL = "shareUrl"
        const val SHARED_PREF_NAME = "sharedPref"
        const val PREF_CHIP_SELECTED = "prefChipSelected"
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 2

        private const val SECOND = 1
        const val MINUTE = 60 * SECOND
        const val HOUR = 60 * MINUTE
        const val DAY = 24 * HOUR
        const val MONTH = 30 * DAY
        const val YEAR = 12 * MONTH

    }
}
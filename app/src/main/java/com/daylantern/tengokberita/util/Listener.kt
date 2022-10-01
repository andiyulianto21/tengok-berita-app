package com.daylantern.tengokberita.util

import com.daylantern.tengokberita.network.Article

interface Listener {

    fun onClick(article: Article)

}
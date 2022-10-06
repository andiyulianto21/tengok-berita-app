package com.daylantern.tengokberita.util

import android.annotation.SuppressLint
import com.daylantern.tengokberita.util.Constants.Companion.DAY
import com.daylantern.tengokberita.util.Constants.Companion.HOUR
import com.daylantern.tengokberita.util.Constants.Companion.MINUTE
import com.daylantern.tengokberita.util.Constants.Companion.MONTH
import com.daylantern.tengokberita.util.Constants.Companion.YEAR
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConvertDateTime {

    private fun currentDate(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToLong(time: String): Long? {
        var result: Long? = null
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = sdf.parse(time) as Date
            result = date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }

    fun Long.toTimeAgo(): String {
        val time = this
        val now = currentDate()

        // convert back to second
        val diff = (now - time) / 1000

        return when {
            diff < MINUTE -> "Just now"
            diff < 2 * MINUTE -> "A minute ago"
            diff < 60 * MINUTE -> "${diff / MINUTE} minutes ago"
            diff < 2 * HOUR -> "An hour ago"
            diff < 24 * HOUR -> "${diff / HOUR} hours ago"
            diff < 2 * DAY -> "Yesterday"
            diff < 30 * DAY -> "${diff / DAY} days ago"
            diff < 2 * MONTH -> "A month ago"
            diff < 12 * MONTH -> "${diff / MONTH} months ago"
            diff < 2 * YEAR -> "A year ago"
            else -> "${diff / YEAR} years ago"
        }
    }

}
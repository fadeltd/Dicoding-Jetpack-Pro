package id.nerdstudio.moviecatalogue.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.IllegalArgumentException
import java.util.*

fun String.parseDate(): DateTime {
    return try {
        DateTime(this)
    } catch (e: IllegalArgumentException) {
        DateTime.parse(this.trim(), DateTimeFormat.forPattern("MMM dd, yyyy"))
    }
}

fun String?.toFormattedDate(): String {
    return if (this.isNullOrEmpty()) ""
    else this.parseDate().toString("E, dd MMM yyyy", Locale.US)
}
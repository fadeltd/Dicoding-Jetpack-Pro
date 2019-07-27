package id.nerdstudio.moviecatalogue.util

fun Int.toRuntime(): String {
    val hours = this / 60
    val minutes = this - (60 * hours)
    return if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
}
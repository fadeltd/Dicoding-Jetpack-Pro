package id.nerdstudio.moviecatalogue.util

fun Int.toRuntime(): String {
    val hours = this / 60
    val minutes = this - (60 * hours)
    return "${hours}h ${minutes}m"
}
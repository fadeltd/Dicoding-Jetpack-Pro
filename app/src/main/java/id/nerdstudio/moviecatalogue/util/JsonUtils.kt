package id.nerdstudio.moviecatalogue.util

import android.app.Application
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import java.io.IOException

fun JsonObject.safe(key: String) = this.has(key) && !this[key].isJsonNull

fun JsonObject.safeLong(key: String) = if (this.safe(key)) this[key].asLong else 0L

fun JsonObject.safeFloat(key: String) = if (this.safe(key)) this[key].asFloat else 0F

inline fun <reified T> JsonObject.toObject(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T : Any> Any.cast(): T = this as T

inline fun <reified T> fromJson(value: String): T = Gson().fromJson(value, T::class.java)

inline fun <reified T> toJson(value: T): String = Gson().toJson(value)

class JsonUtils(private val application: Application) {

    private fun readFile(fileName: String): String? {
        return try {
            val inputStream = application.assets.open(fileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> loadItems(type: Type): List<T> {
        val jsonFile = if (type == Type.MOVIE) "movies" else "tv_shows"
        val jsonString = readFile("$jsonFile.json")
        var list = listOf<T>()
        jsonString?.run {
            val contentArray = JsonParser().parse(this).asJsonArray
            for (i in 0 until contentArray.size()) {
                val item = contentArray[i].asJsonObject

                val id = item.safeLong("id")
                val voteAverage = item.safeFloat("vote_average")
                val title = item["title"].asString
                val posterPath = item["poster_path"].asString
                val overview = item["overview"].asString
                val releaseDate = item["release_date"].asString

                val catalogue =
                    if (type == Type.MOVIE) {
                        Movie(
                            id = id,
                            voteAverage = voteAverage,
                            title = title,
                            posterPath = posterPath,
                            overview = overview,
                            releaseDate = releaseDate
                        )
                    } else {
                        TvShow(
                            id = id,
                            voteAverage = voteAverage,
                            name = title,
                            posterPath = posterPath,
                            overview = overview,
                            firstAirDate = releaseDate
                        )
                    }
                list = list + (catalogue as T)
            }
        }
        return list
    }
}

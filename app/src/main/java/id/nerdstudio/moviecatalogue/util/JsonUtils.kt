package id.nerdstudio.moviecatalogue.util

import android.app.Application
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import java.io.IOException

class JsonUtils(private val application: Application) {

    private fun JsonObject.safeLong(key: String): Long {
        return if(this.has(key) && !this.isJsonNull) this[key].asLong else 0L
    }

    private fun JsonObject.safeFloat(key: String): Float {
        return if(this.has(key) && !this.isJsonNull) this[key].asFloat else 0F
    }

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

    fun loadItems(type: Type): List<Item> {
        val jsonFile = if (type == Type.MOVIE) "movies" else "tv_shows"
        val jsonString = readFile("$jsonFile.json")
        var list = listOf<Item>()
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

                list += Item(id, voteAverage, title, posterPath, overview, releaseDate)
            }
        }
        return list
    }

}

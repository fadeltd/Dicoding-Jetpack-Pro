package id.nerdstudio.moviecatalogue.api

import android.content.Context
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response
import id.nerdstudio.moviecatalogue.config.AppConfig.getCurrentMovies
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.util.safe
import id.nerdstudio.moviecatalogue.util.toObject

fun apiLoader(
    context: Context, url: String,
    onComplete: (e: Exception?, reponse: Response<JsonObject>) -> Unit
) {
    Ion.with(context)
        .load(url)
        .asJsonObject()
        .withResponse()
        .setCallback { e, response ->
            onComplete(e, response)
        }
}

fun loadMovies(
    context: Context,
    onComplete: (() -> Unit)? = null,
    onSuccess: ((movies: List<Item>) -> Unit)? = null,
    onFailed: ((message: String?) -> Unit)? = null
) {
    apiLoader(context, getCurrentMovies()) { e, response ->
        onComplete?.invoke()
        if (e != null) {
            onFailed?.invoke(e.message)
        } else {
            val code = response.headers?.code()
            if (code == 200) {
                val result = response.result
                var list = listOf<Item>()
                if (result.safe("results")) {
                    val movies = result["results"].asJsonArray
                    for (i in 0 until movies.size()) {
                        val movie = movies[i].asJsonObject.toObject<Item>()
                        list += movie
                    }
                    onSuccess?.invoke(list)
                } else {
                    onFailed?.invoke("http error code: $code")
                }
            } else {
                onFailed?.invoke("http error code: $code")
            }
        }
    }
}

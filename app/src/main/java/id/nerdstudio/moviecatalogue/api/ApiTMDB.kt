package id.nerdstudio.moviecatalogue.api

import id.nerdstudio.moviecatalogue.config.AppConfig
import id.nerdstudio.moviecatalogue.config.AppConfig.getCredits
import id.nerdstudio.moviecatalogue.config.AppConfig.getDetail
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.util.safe
import id.nerdstudio.moviecatalogue.util.toObject

class ApiTMDB(private val loader: ApiLoader) {

    private fun loadList(
        url: String,
        type: Type,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((items: List<*>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        loader.loadChecker(url, onComplete, { result ->
            var list = if (type == Type.MOVIE) listOf<Movie>() else listOf<TvShow>()
            if (result.safe("results")) {
                val items = result["results"].asJsonArray
                for (i in 0 until items.size()) {
                    val obj = items[i].asJsonObject
                    val item = if (type == Type.MOVIE) obj.toObject<Movie>() else obj.toObject<TvShow>()
                    list = list + item
                }
                onSuccess?.invoke(list)
            } else {
                val resource = type.name.replace("_", " ").toLowerCase().capitalize()
                onFailed?.invoke("The $resource you requested could not be found.")
            }
        }, { message ->
            onFailed?.invoke(message)
        })
    }

    fun loadShows(
        type: Type,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((items: List<*>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        val url = if (type == Type.MOVIE) AppConfig.getMovies() else AppConfig.getTvShows()
        loadList(url, type, onComplete, onSuccess, onFailed)
    }

    fun loadSimilar(
        type: Type,
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((shows: List<*>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        val url = AppConfig.getSimilar(type, id)
        loadList(url, type, onComplete, onSuccess, onFailed)
    }

    fun <T> loadDetail(
        type: Type,
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((show: T) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        val url = getDetail(type, id)
        loader.loadChecker(url, onComplete, { result ->
            if (result.safe("status_code") && result.safe("status_message")) {
                val message = result["status_message"].asString
                onFailed?.invoke(message)
            } else {
                val show = if (type == Type.MOVIE) result.toObject<Movie>()
                else result.toObject<TvShow>()
                try {
                    @Suppress("UNCHECKED_CAST")
                    onSuccess?.invoke(show as T)
                } catch (e: ClassCastException) {
                    onFailed?.invoke("${e.message}")
                }
            }
        }, { message ->
            onFailed?.invoke(message)
        })
    }

    fun loadCredits(
        type: Type,
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((cast: List<Cast>, crew: List<Crew>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        val url = getCredits(type, id)
        loader.loadChecker(url, onComplete, { result ->
            if (result.safe("status_code") && result.safe("status_message")) {
                val message = result["status_message"].asString
                onFailed?.invoke(message)
            } else {
                var castList = listOf<Cast>()
                if (result.safe("cast")) {
                    val castResponse = result["cast"].asJsonArray
                    for (i in 0 until castResponse.size()) {

                        val item = castResponse[i].asJsonObject.toObject<Cast>()
                        castList = castList + item
                    }
                }
                var crewList = listOf<Crew>()
                if (result.safe("crew")) {
                    val crewResponse = result["crew"].asJsonArray
                    for (i in 0 until crewResponse.size()) {
                        val item = crewResponse[i].asJsonObject.toObject<Crew>()
                        crewList = crewList + item
                    }
                }
                onSuccess?.invoke(castList, crewList)
            }
        }, { message ->
            onFailed?.invoke(message)
        })
    }
}
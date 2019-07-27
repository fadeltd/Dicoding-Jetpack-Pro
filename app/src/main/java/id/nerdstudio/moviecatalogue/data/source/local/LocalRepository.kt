package id.nerdstudio.moviecatalogue.data.source.local

import android.os.Handler
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.util.EspressoIdlingResource
import id.nerdstudio.moviecatalogue.util.JsonUtils

class LocalRepository private constructor(private val jsonUtils: JsonUtils) {

    private fun <T> getAllItems(
        type: Type,
        onReceived: ((itemResponses: List<T>) -> Unit)? = null
    ) {
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed({
            onReceived?.invoke(jsonUtils.loadItems(type))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getAllMovies(
        onReceived: ((itemResponses: List<Movie>) -> Unit)? = null
    ) {
        getAllItems(Type.MOVIE, onReceived)
    }

    fun getAllTvShow(
        onReceived: ((itemResponses: List<TvShow>) -> Unit)? = null
    ) {
        getAllItems(Type.TV_SHOW, onReceived)
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
        private var INSTANCE: LocalRepository? = null

        fun getInstance(utils: JsonUtils): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(utils)
            }
            return INSTANCE as LocalRepository
        }
    }
}

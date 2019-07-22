package id.nerdstudio.moviecatalogue.data.source.remote

import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.api.ApiTMDB
import id.nerdstudio.moviecatalogue.data.entity.Cast
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.data.entity.Crew
import id.nerdstudio.moviecatalogue.util.EspressoIdlingResource

class RemoteRepository(private val loader: ApiLoader) {

    private val apiMovieDb by lazy {
        ApiTMDB(loader)
    }

    fun getAllShows(
        type: Type,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((items: List<*>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        EspressoIdlingResource.increment()
        apiMovieDb.loadShows(type, {
            EspressoIdlingResource.decrement()
            onComplete?.invoke()
        }, onSuccess, onFailed)
    }

    fun getMovieDetail(
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((show: Movie) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        EspressoIdlingResource.increment()
        apiMovieDb.loadDetail(Type.MOVIE, id, {
            EspressoIdlingResource.decrement()
            onComplete?.invoke()
        }, onSuccess, onFailed)
    }

    fun getTvShowDetail(
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((show: TvShow) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        EspressoIdlingResource.increment()
        apiMovieDb.loadDetail(Type.TV_SHOW, id, {
            EspressoIdlingResource.decrement()
            onComplete?.invoke()
        }, onSuccess, onFailed)
    }

    fun getCredits(
        type: Type,
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((castList: List<Cast>, crewList: List<Crew>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ){
        EspressoIdlingResource.increment()
        apiMovieDb.loadCredits(type, id, {
            EspressoIdlingResource.decrement()
            onComplete?.invoke()
        }, onSuccess, onFailed)
    }

    fun getSimilar(
        type: Type,
        id: Long,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((shows: List<*>) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ){
        EspressoIdlingResource.increment()
        apiMovieDb.loadSimilar(type, id, {
            EspressoIdlingResource.decrement()
            onComplete?.invoke()
        }, onSuccess, onFailed)
    }

    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(loader: ApiLoader): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(loader)
            }
            return INSTANCE as RemoteRepository
        }
    }
}